package stock_tradings;
public class Customer {
  
	 private int customer_id;
	 private String name;
	 private String userName;
	 private String password;
	 private Address address;
	 private Account account;
	 private long contact;
	 
	 
	public Customer(String name, String userName, String password, Address address, long contact,Account account) {
		
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.address = address;
		this.contact = contact;
		this.account=account;
	}
	public Customer(int customer_id, String name, String userName, String password, Address address, long contact,Account account) {
		
		this.customer_id = customer_id;
		this.name = name;
		this.userName = userName;
		this.password = password;
		this.address = address;
		this.contact = contact;
		this.account=account;
	}
	public int getCustomer_id() {
		return customer_id;
	}
	public String getName() {
		return name;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	public Address getAddress() {
		return address;
	}
	public long getContact() {
		return contact;
	}
	public Account getAccount() {
		return account;
	}
}

