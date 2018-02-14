

import java.io.FileNotFoundException;

public class MainApp {
	
	public static void main(String [] args) throws FileNotFoundException{
		
		Data data1 = new Data(args[0]);	           //creating a data object passing the command line 	
		int count = data1.getCount();              //counting every string in the file
		HashTable HashT = new HashTable(count);	   //creating a hash table object passing the count
		data1.getData2(HashT);                      //extracting the data from a file, and passing the hash table
				
		
		
	}  //end of main()
///////////////////////////////////////////
} //end of mainAPP Class
