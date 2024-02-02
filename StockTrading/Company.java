package stock_tradings;
import java.util.ArrayList;
public class Company {
       private int company_id;
       private String name;
       private String username;
       private String password;
       private Address address;
       private ArrayList<Stock> stocks;
       
	public Company(int company_id, String name,String username,String password, Address address, ArrayList<Stock> stocks) {
	
		this.company_id = company_id;
		this.name = name;
		this.address = address;
		this.stocks = stocks;
		this.username=username;
		this.password=password;
	}
	public Company( String name,String username,String password, Address address, ArrayList<Stock> stocks) {
		this.name = name;
		this.address = address;
		this.stocks = stocks;
		this.username=username;
		this.password=password;
	}
	public int getCompany_id() {
		return company_id;
	}
	public String getName() {
		return name;
	}
	public Address getAddress() {
		return address;
	}
	public ArrayList<Stock> getStocks() {
		return stocks;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
