public class Customer {
    public int customerID = 0;
    public String name = "-name required-";
    public String emailAddress = "-email optional-";
    public String Address = "-address required-";
    public String phoneNumber = "-phone required-";
	
	public void setCustomerInfo(int Id, String nm, String addr, String phNum){
		this.customerID = Id;
		this.name = nm;
		this.Address = addr;
		this.phoneNumber = phNum;
	};
	
	public void setCustomerInfo(int Id,String nm,String addr, String phNum,String email){
		this.customerID = Id;
		this.name = nm;
		this.Address = addr;
		this.phoneNumber = phNum;
		this.emailAddress = email;
	};
	
	public void display(){
		if(emailAddress!="-email optional-"){
			System.out.println("Customer Information");
			System.out.println("customerID:"+customerID);
			System.out.println("name:"+name);
			System.out.println("Address:"+Address);
			System.out.println("phoneNumber:"+phoneNumber);
			System.out.println("emailAddress:"+emailAddress);
		}else{
			System.out.println("Customer Information");
			System.out.println("customerID:"+customerID);
			System.out.println("name:"+name);
			System.out.println("Address:"+Address);
			System.out.println("phoneNumber:"+phoneNumber);
		};
		System.out.println("----------------------------");
	};
	
		
}