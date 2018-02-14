


import java.io.FileNotFoundException;
import java.io.PrintWriter;


 class HashTable {
	
	private DataItem[] hashArray;    //array to hold hash table
	private int hashTableSize;          //size to create hash table
    
    //private PrintWriter output;

//----------------------------------------------------------------	
	HashTable(int count) throws FileNotFoundException{                    // constructor
		
		hashTableSize = getPrime(count);             //creating the hash table size
		hashArray = new DataItem[hashTableSize];     //creating the hash table
		
	}
	
//----------------------------------------------------------------	
	public int hashFunc1(String key){
    
	   	int hashValue = key.charAt(0);     //starting with the first char
	   	
	   	for(int i = 1; i < key.length(); i++){    //starting at the 2nd char (left to right)
	   	
	   		int letter = key.charAt(i);     //get char code
	   		hashValue = (hashValue * 26 + letter) % hashTableSize; //reducing
	   	}
	   	return hashValue;      
    } //end of hashFunc1()
	
//----------------------------------------------------------------	
	public int hashFunc2(String key){
		char ch;
		int sum = 0;		
	
		for(int i = 0; i < key.length(); i++){
			 ch = key.charAt(i);                  //extracting each char
			 sum += (int)ch;			         // adding each char base 10 value
		}
		
		return (5 - sum % 5);
	} // end of hashFunc2()
	
//----------------------------------------------------------------	
	private static boolean isPrime(int n){
	   
		   for(int i = 2; (i * i <= n); i++){    //for all i
		                                   
			   if(n % i == 0)                    //divides evenly by i
				   return false;                 //yes, so not prime      
		   }
		   return true;                      //no, so prime
	   }// end of isPrime()
//----------------------------------------------------------------
	private static int getPrime(int count){    //returns first prime > min
	   
		   int min = 2 * count;               
		   for(int i = min + 1; true; i++){    //for all i > min
		   
			   if(isPrime(i))                //is i prime?
				   return i;                 //yes, return it
		   }
	   }//end of getPrime()
	
//----------------------------------------------------------------
	public void insert(DataItem item){	 
		  
	      String key = item.getKey();      // extract key
	      int hashVal = hashFunc1(key);  // hash the key
	      int step = hashFunc2(key);     // hash the key again for stepping	    	  
	      
	      while(hashArray[hashVal] != null){   //until empty cell	         
	    	   
	    	  hashVal += step;              //add a step        
	    	  hashVal %= hashTableSize;     // wrap around if necessary
	         }
	      
	      hashArray[hashVal] = item;    // insert item      
	      
	      
	   }  //end insert()
	
//----------------------------------------------------------------	
	public DataItem find(String key){
		
		int hashVal = hashFunc1(key);  // hash the key
	    int step = hashFunc2(key);     // hash the key again for stepping
	    
	    while(hashArray[hashVal] != null){  // until empty cell                                      
   	   
	    	if(hashArray[hashVal].getKey().equals(key)) // found the key?             	
	    		return hashArray[hashVal];              // yes, return item
        
	    	hashVal += step;            //add a step                
	    	hashVal %= hashTableSize;   // wrap around if necessary        
         }
	    
	    return null; //can't find it
	} //end of find()	
//----------------------------------------------------------------	
	public void DisplayTable(PrintWriter outputText){
		
		outputText.printf("Table Location    Label    Address");   //writing a header for the symbol table to the output file
		outputText.println(" ");
		for(int i = 0; i < hashArray.length; i++){                 //writing location, label, address to output file
			if(hashArray[i] != null){
				outputText.printf("%-5d             %-8s %-8s", i, hashArray[i].getKey(), hashArray[i].getAddress());
				outputText.println(" ");
			}
		}
	} //end of DisplayTable()
//----------------------------------------------------------------

} //end of HashTable Class
/////////////////////////////////////////////////////////////////////
