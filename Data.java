

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

 class Data {
	
	private File inputText;
	private File outputText;
    private PrintWriter output;
	private int count;
	private String label;
	private String mneumonic;
	private String operand;    
    private String address;
    
    private String format2 = "MULR" + "TIXR" + "SVC" + "SHIFTR" + "SUBR" + "DIVR" + "CLEAR"     //string for format 2 
    				+ "RMO" + "COMPR" + "SHIFTL" + "ADDR";
    
    private String format3 = "WD" + "STX" + "OR" + "AND" + "LDA" + "LPS" + "LDCH" + "LDL" + "SUBF"   //string for format 3
    				+ "JSUB" + "LDX" + "STT" + "TIX" + "LDT" + "STA" + "TD" + "STB" + "DIVF"
    				+ "LDCH" + "JEQ" + "DIV" + "SSK" + "RD" + "LDS" + "MUL" + "J" + "COMP"
    				+ "JLT" + "SUB" + "LDB" + "RSUB" + "MULF" + "JSUB" + "LDL" + "STL" + "STSW"
    				+ "COMPF" + "JGT" + "STS" + "STCH" + "LDF" + "ADD" + "STF" + "ADDF"
    				+ "STCH" + "STI";
    
    private String format4 = "+LDB" + "+SSK" + "+JGT" + "+STL" + "+STI" + "+LDT" + "+MULF" + "+J"  //string for format 4
    				+ "+COMP" + "+STS" + "+JSUB" + "+COMPF" + "+STT" + "+SUBF" + "+OR"
    				+ "+JLT" + "+LDS" + "+DIV" + "+MUL" + "+STX" + "+LDA" + "+SUB" + "+STB"
    				+ "+ADDF" + "+JEQ" + "+STCH" + "+STA" + "+DIVF" + "+STF" + "+STSW"
    				+ "+LPS" + "+LDL" + "+RSUB" + "+WD" + "+LDCH" + "+LDF" + "+LDX" + "+ADD"
    				+ "+AND" + "+TD" + "+TIX" + "+RD";
    
    
	
	public Data(String file_name) throws FileNotFoundException{         // constructor
		
		inputText =  new File(file_name);		                          //created new file object
		
	}
	
//----------------------------------------------------------------	
	public int getCount() throws FileNotFoundException{
		
		Scanner input = new Scanner(inputText);
		
		if(inputText.canRead() == false){       //checking to see if the file can be read or not
			System.out.println("Can't read file");
			System.exit(0);
		}
		
		while(input.hasNext()){
			
			input.next();     //read input
			count++;          //count string
		}
		
		input.close();        //closing file
		return count;
		
	} //end of getCount()
		
		
//----------------------------------------------------------------	
	public void getData2(HashTable object) throws FileNotFoundException{
		
		Scanner input = new Scanner(inputText);
		outputText = new File("result.txt");             //made a new file object
		output = new PrintWriter(outputText);            //PrintWriter object to write to the output file
		
		DataItem aDataItem;         //create a DataItem for a string
		String str;			
			
		    //beginning pass 1
		    str = input.nextLine();        //reading the first string
		    
		    if(str.charAt(0) != '.'){     //if the string is not a comment line then continue
		    	
		    	splitString(str);        
		    	
		    	if(mneumonic.equals("START")){	                     //if mneumonic is "START" 	    		
		    		                                                 //then save operand for starting address
		    		address = operand;		    		
		    		output.println(address + " " + str);	         //write the string with address to file	    		
		    		aDataItem = new DataItem(label, address);        //make a new DataItem
			    	object.insert(aDataItem);                        //inserting the new DataItem into the hash table
			    	str = input.nextLine();                          //reading the next string			    	
		    	}
		    	else{
		    			address = "0";                //there is no "START", so starting address begins at 0
		    	}
		    	
		    }
		    do{                                      //starting loop
		    	
		    	if(str.charAt(0) != '.'){            //if this is not a comment line then
		    		splitString(str);
		    		                                 
		    		if(label != null){                                                   //if there is an label
		    			if(object.find(label) != null){                                  //search the hash table for it
		    				output.println("ERROR: " + label + " duplicate symbol");     //if found then print an error
		    				
		    			}
		    			else{
		    				aDataItem = new DataItem(label, address);                   //else make a new DataItem 
					    	object.insert(aDataItem);                                   //insert the new DataItem into the hash table
					    	
					    	output.println(address + " " + str);                        //write it to the output file
					    	
		    			}
		    			
		    		}//end of symbol if
		    		
		    		searchOPTable();              
		    		str = input.nextLine();
		    		splitString(str);
		    	}
		    	else{
		    		output.println(str);          //else it is a comment line so write it to output file
		    		str = input.nextLine();       //get next line
		    		splitString(str);
		    	}
		    } while(!mneumonic.equals("END"));      //end loop when the mneumonic is "END"
		    output.println(address + " " + str);    //write last line to file
		    
		    output.println(" ");
		    output.println(" ");
		    object.DisplayTable(output);           //write the symbol table to the output file
		    output.close();                        //close file
		    
		    	
	input.close();	    	
	} //end of getDataA()
//----------------------------------------------------------------		
	private void splitString(String str1){
			
			if(str1.charAt(0) == ' ' && str1.charAt(2) == ' '){      //if the first 3 parts of a string is empty then there is no label
				label = null;                                        //set label to null
				
				String[] words = str1.split(" ", 80);               //split string at spaces and store them into an string array
				StringBuilder str2 = new StringBuilder();
				
				for(int i = 0; i < words.length; i++){               
					if(words[i] != null && !words[i].isEmpty() ){     //if the string is not null and is not empty then append it with a space to StringBuilder
						str2.append(words[i]);
						str2.append(' ');
					}
				}
				
				String finalstring = str2.toString();               //get the final string from StringBuilder
				String[] lastArry = finalstring.split(" ");         //split the string at spaces
								
				mneumonic = lastArry[0];                            
				operand = lastArry[1];				

			}else{                                                   //else there is an label
			String[] words = str1.split(" ", 80);                    //split string at spaces and store them into an string array
			StringBuilder str2 = new StringBuilder();
			
			for(int i = 0; i < words.length; i++){
				if(words[i] != null && !words[i].isEmpty() ){       //if the string is not null and is not empty then append it with a space to StringBuilder      
					str2.append(words[i]);
					str2.append(' ');
				}
			}
			
			String finalstring = str2.toString();                      //get the final string from StringBuilder
			String[] lastArry = finalstring.split(" ");                //split the string at spaces
			label = lastArry[0];
			mneumonic = lastArry[1];
			operand = lastArry[2];
			
			}
		} //end of splitString()
//----------------------------------------------------------------	
	private void searchOPTable(){
			
			if(format2.contains(mneumonic)){                   //if mneumonic is format 2 then add 2 bytes to the address
				int value = Integer.parseInt(address, 16); 
				value = value + 2;
				address = Integer.toHexString(value);				
			}
			else if(format3.contains(mneumonic)){             //if mneumonic is format 3 then add 3 bytes to the address
				int value = Integer.parseInt(address, 16); 
				value = value + 3;
				address = Integer.toHexString(value);	
			}
			else if(format4.contains(mneumonic)){              //if mneumonic is format 4 then add 4 bytes to the address
				int value = Integer.parseInt(address, 16); 
				value = value + 4;
				address = Integer.toHexString(value);	
			}
			else if(mneumonic.equals("WORD")){                 //if mneumonic is word 2 then add 3 bytes to the address
				int value = Integer.parseInt(address, 16); 
				value = value + 3;
				address = Integer.toHexString(value);	
			}
			else if(mneumonic.equals("RESW")){               //if mneumonic is RESW then add 3 * the operand to the address
				int k = Integer.parseInt(operand);
				int value = Integer.parseInt(address, 16); 
				value = value + (3*k);
				address = Integer.toHexString(value);	
			}
			else if(mneumonic.equals("RESB")){               //if mneumonic is RESB then add the operand to the address
				 int k = Integer.parseInt(operand);
				 int value = Integer.parseInt(address, 16); 
					value = value + k;
					address = Integer.toHexString(value);	
			}
			else if(mneumonic.equals("BYTE")){                 //if mneumonic is BYTE then find the length of constant in bytes
				int length = operand.length();                 //add the length to the address
				int value = Integer.parseInt(address, 16); 
				value = value + length;
				address = Integer.toHexString(value);	
			}
			else{                                               //else print error
				
				output.println("ERROR: " + mneumonic + " invalid operation code");
			}
		} //end of searchOPTable()
//----------------------------------------------------------------	
		
 } //end of Data Class 
//////////////////////////////////////////////////////////////////
