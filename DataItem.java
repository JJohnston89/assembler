

class DataItem {
	
	private String label;	
	private String address;
	
	
//----------------------------------------------------------------	
	public DataItem(String label, String address){        // constructor
		
		this.label  = label;
		this.address = address;
		
	}
		
//----------------------------------------------------------------	
	public String getKey(){
		
		return label;   //returns a string (label)
	
	} //end of getKey()
//----------------------------------------------------------------	
	public String getAddress(){
		
		return address;   //returns a string (address)
		
	} //end of getNumber()
	
//----------------------------------------------------------------
	

} //end of DataItem Class
////////////////////////////////////////////////////////////////////
