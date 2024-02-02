package stock_tradings;
public class Stock {
	private int stock_id;
	private String name;
	private Company company;
	private double rate;
	
	public Stock(int stock_id, String name, Company company, double rate) {
		
		this.stock_id = stock_id;
		this.name = name;
		this.company = company;
		this.rate = rate;
	}
	public Stock( String name, Company company, double rate) {
		this.name = name;
		this.company = company;
		this.rate = rate;
	}
	public int getStock_id() {
		return stock_id;
	}
	public String getName() {
		return name;
	}
	public Company getCompany() {
		return company;
	}
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this. rate=rate;
	}
	

}
