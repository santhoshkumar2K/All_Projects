package stock_tradings;
public class Account {
   private int account_id;
   private long ac_number;
   private String password;
   private double balance;
   
   public Account(int account_id, long ac_number, String password, double balance) {
		this.account_id = account_id;
		this.ac_number = ac_number;
		this.password = password;
		this.balance = balance;
	}
public Account(long ac_number, String password, double balance) {
	this.ac_number = ac_number;
	this.password = password;
	this.balance = balance;
}
public long getAc_number() {
	return ac_number;
}
public String getPassword() {
	return password;
}
public double getBalance() {
	return balance;
}
public int getAccount_id() {
	return account_id;
}
   
}
