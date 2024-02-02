package stock_tradings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.io.*;
import java.util.HashMap;
public class Database {

   
	private static FileInputStream file;
	private static Properties property=new Properties();
	private static Connection con;
static {
          try{
              file=new FileInputStream("//home//zoho//santhosh//finalProjects//StockTrading//MyDatabase.properties");
               property.load(file);
            
          }catch(IOException e){}
	try {
	   con =DriverManager.getConnection(property.getProperty("url"), property.getProperty("username"), property.getProperty("password"));
	}catch(Exception e) {System.out.println("No connection"+e);}
}
	public static int addCustomer(Customer customer)
	{
		int addressID=addAddress(customer.getAddress());
		int accountID=addAccount(customer.getAccount());
	   PreparedStatement addCustomer=null;	
	   try{
	          addCustomer=con.prepareStatement("insert into customer(name,username,password,address_id,account_id,contact) values(?,?,?,?,?,?) returning customer_id");
	          addCustomer.setString(1,customer.getName());
	          addCustomer.setString(2,customer.getUserName());
		  addCustomer.setString(3, customer.getPassword());
		  addCustomer.setInt(4, addressID);
		  addCustomer.setInt(5, accountID);
		  addCustomer.setLong(6, customer.getContact());
		  addCustomer.execute();
		  ResultSet result=addCustomer.getResultSet();
		  result.next();
		  return result.getInt(1);
	   }catch(Exception e){System.out.println(e);}
	   finally{
	      try{
	         addCustomer.close();
	      }catch(Exception e){}
	   }
	   return -1;
	}
	public static boolean createTable(String query) 
       {
             PreparedStatement createTable=null;
		try{
		        createTable=con.prepareStatement(query);
				createTable.execute();
			return true;
		   }catch(Exception e){  System.out.println("No create table..."+e);}  	
		   finally{
		   try{
		       createTable.close();
		   }catch(Exception e){}
		}
	      return false;	    
       }
    public static int addAddress(Address address)
	{
	        PreparedStatement addAddress=null;
		try {
		       addAddress=con.prepareStatement("insert into Address(door_number,street,city,district,pincode) values(?,?,?,?,?) returning id");
		       addAddress.setInt(1, address.getDoor_no());
			addAddress.setString(2,address.getStreet());
			addAddress.setString(3, address.getCity());
			addAddress.setString(4, address.getDistrict());
			addAddress.setInt(5, address.getPincode());
			addAddress.execute();
			ResultSet result=addAddress.getResultSet();
			result.next();
			return result.getInt(1);
		} catch (Exception e) {}
		finally{
		      try{
			 addAddress.close();
		      }catch(Exception e){}
	        }
		return -1;
	}
	public static int addAccount(Account account)
	{
	        PreparedStatement	addAccount=null;
		try {
		       addAccount=con.prepareStatement("insert into Account(account_no,password,balance) values(?,?,?) returning account_id");
		       addAccount.setLong(1, account.getAc_number());
			addAccount.setString(2,account.getPassword());
			addAccount.setDouble(3, account.getBalance());
			addAccount.execute();
			ResultSet result= addAccount.getResultSet();
			result.next();
			return result.getInt(1);
		} catch (Exception e) {}
		finally{
		      try{
			 addAccount.close();
		      }catch(Exception e){}
	        }
		return -1;
	}
	public static int addCompany(Company company)
	{
		PreparedStatement addCompany=null;
		int addressID=addAddress(company.getAddress());
		try {
	               addCompany=con.prepareStatement("insert into Company(name,username,password,address_id) values(?,?,?,?) returning company_id");
		       addCompany.setString(1, company.getName());
			addCompany.setString(2,company.getUsername());
			addCompany.setString(3, company.getPassword());
			addCompany.setInt(4,addressID);
			addCompany.execute();
			ResultSet result= addCompany.getResultSet();
			result.next();
			return result.getInt(1);
		} catch (Exception e) {}
		finally{
		      try{
			 addCompany.close();
		      }catch(Exception e){}
	        }
		return -1;
	}
	public static Company checkCompany(String username,String password)
	{
	        PreparedStatement	getCompany=null;
		try {
		        getCompany=con.prepareStatement("select company_id,name,username,password,address.id,address.door_number,address.street,address.city,address.district,address.pincode from company join address on company.address_id=address.id where username=? and password=?");
			getCompany.setString(1,username);
			getCompany.setString(2,password);
			ResultSet result=getCompany.executeQuery();
			result.next();
			return new Company(result.getInt(1), result.getString(2), result.getString(3),result.getString(4),new Address(result.getInt(5),result.getInt(6), result.getString(7), result.getString(8), result.getString(9), result.getInt(10)), null);
		} catch (Exception e) {System.out.println(e);}
		finally{
		      try{
			 getCompany.close();
		      }catch(Exception e){}
	        }
		   return null;
	}
	public static Customer checkCustomer(String username,String password)
	{
	          PreparedStatement	getCustomer=null;
		try {
		       getCustomer=con.prepareStatement("select customer_id,name,username,customer.password,address.id,address.door_number,address.street,address.city,address.district,address.pincode,contact,account.account_id,account.account_no,account.password,account.balance from customer join address on customer.address_id=address.id join account on customer.account_id=account.account_id where username=? and customer.password=?");
			getCustomer.setString(1,username);
			getCustomer.setString(2,password);
			ResultSet result=getCustomer.executeQuery();
			result.next();
			return new Customer(result.getInt(1), result.getString(2), result.getString(3),result.getString(4),new Address(result.getInt(5),result.getInt(6), result.getString(7), result.getString(8), result.getString(9), result.getInt(10)),result.getLong(11), new Account(result.getInt(12),result.getLong(13),result.getString(14),result.getDouble(15)));
		} catch (Exception e) {}
		finally{
		      try{
			 getCustomer.close();
		      }catch(Exception e){}
	        }
		   return null;
	}
	public static HashMap<Stock,Integer> getAllStocks()
	{
		HashMap<Stock,Integer>stocks=new HashMap<Stock,Integer>();
		PreparedStatement getStock_s=null;
			try 
			{
			
			   getStock_s=con.prepareStatement("select stock.stock_id,stock.name,stock.rate,company.company_id,company.name,company.username,company.password,address.door_number,address.street,address.city,address.district,address.pincode ,stock.no_of_stocks from stock join company on company.company_id=stock.company_id join address on company.address_id=address.id");
			    
			    ResultSet result=getStock_s.executeQuery();
			    
				while(result.next())
				{
				
				     stocks.put(new Stock(result.getInt(1), result.getString(2),new Company(result.getInt(4), result.getString(5), result.getString(6),result.getString(7),new Address(result.getInt(8), result.getString(9), result.getString(10), result.getString(11), result.getInt(12)),null),result.getDouble(3)),result.getInt(13));
				}
				return stocks;
			} catch (Exception e) {System.out.println(e);}
			finally{
			      try{
				 getStock_s.close();
			      }catch(Exception e){}
	                 }
		   return null;
	}
	public static HashMap<Stock,Integer> getStocks(Object object)
	{
	     if(object instanceof Company)
	     {
	      Company company=(Company)object;
	         return getStocks("select stock.stock_id,stock.name,stock.rate,company.company_id,company.name,company.username,company.password,address.door_number,address.street,address.city,address.district,address.pincode, stock.no_of_stocks from stock    join company on company.company_id=stock.company_id join address on company.address_id=address.id  where stock.company_id="+company.getCompany_id());
	     }else if(object instanceof Customer)
	     {
	       Customer customer=(Customer)object;
	         return getStocks("select stock.stock_id,stock.name,stock.rate,company.company_id,company.name,company.username,company.password,address.door_number,address.street,address.city,address.district,address.pincode, stock.no_of_stocks from purchasestock join stock on purchasestock.stock_id=stock.stock_id   join company on company.company_id=stock.company_id join address on company.address_id=address.id  where purchasestock.customer_id="+customer.getCustomer_id()+" and purchasestock.no_of_stock>0");
	     }else
	     {
	        return null;
	     }
	}
	public static HashMap<Stock,Integer> getStocks(String query)
	{
		HashMap<Stock,Integer>stocks=new HashMap<Stock,Integer>();
		 PreparedStatement getStocks=null;
			try 
			{
			   getStocks=con.prepareStatement(query);
			    ResultSet result=getStocks.executeQuery();
				while(result.next())
				{
				    stocks.put(new Stock(result.getInt(1), result.getString(2),new Company(result.getInt(4), result.getString(5), result.getString(6),result.getString(7),new Address(result.getInt(8), result.getString(9), result.getString(10), result.getString(11), result.getInt(12)),null),result.getDouble(3)),result.getInt(13));
				}
				return stocks;
			} catch (Exception e) {System.out.println(e);}
			finally{
			      try{
				 getStocks.close();
			      }catch(Exception e){}
	               }
		   return null;
	}
	public static boolean addPurchase(Stock stock,Customer customer,int count,java.util.Date date)
	{
	      PreparedStatement addPurchase=null;
	     try{
	         addPurchase=con.prepareStatement("insert into purchasestock(customer_id,stock_id,no_of_stock,purchase_price,date) values(?,?,?,?,?)");
	         addPurchase.setInt(1,customer.getCustomer_id());
	         addPurchase.setInt(2,stock.getStock_id());
	         addPurchase.setInt(3,count);
	         
	         addPurchase.setDouble(4,stock.getRate());
	          addPurchase.setDate(5,new java.sql.Date(date.getTime()));
	         addPurchase.executeUpdate();
	         return true;  
	     }catch(Exception e){System.out.println(e);}
	     finally{
			      try{
				 addPurchase.close();
			      }catch(Exception e){}
	           }
	        return false;
	}
	public static boolean updatePurchase(int id,int count)
	{
	   PreparedStatement updatePurchase=null;
	   try{
	        updatePurchase=con.prepareStatement("update purchasestock set no_of_stock=? where id=?");
	         updatePurchase.setInt(1,count);
	         updatePurchase.setInt(2,id);
	        updatePurchase.executeUpdate();
	         return true;  
	     }catch(Exception e){System.out.println(e);}
	      finally{
			      try{
				 updatePurchase.close();
			      }catch(Exception e){}
	           }
	        return false;
	}
	public static boolean addSellings(Stock stock,Customer customer,int count,java.util.Date date)
	{   
	     PreparedStatement addSellings=null;
	     try{
	        addSellings=con.prepareStatement("insert into sellingstock(customer_id,stock_id,no_of_stock,selling_price,date) values(?,?,?,?,?)");
	         addSellings.setInt(1,customer.getCustomer_id());
	         addSellings.setInt(2,stock.getStock_id());
	         addSellings.setInt(3,count);	  
	         addSellings.setDouble(4,stock.getRate());
	          addSellings.setDate(5,new java.sql.Date(date.getTime()));
	         addSellings.executeUpdate();
	         return true;  
	     }catch(Exception e){System.out.println(e);}
	      finally{
			      try{
				 addSellings.close();
			      }catch(Exception e){}
	           }
	        return false;
	}
	
	public static ArrayList<HoldingStocks> getpurchaseDetails(Customer customer)
	{
	        ArrayList<HoldingStocks> holdings=new ArrayList<HoldingStocks>();
	      PreparedStatement getPurchase=null;  
	      try{
	          getPurchase=con.prepareStatement("select id,customer.username,customer.password,stock.stock_id,stock.name,company.username,company.password,stock.rate, date,purchase_price,no_of_stock from purchasestock join customer on customer.customer_id=purchasestock.customer_id join stock on purchasestock.stock_id=stock.stock_id join company on stock.company_id=company.company_id where purchasestock.customer_id=? and purchasestock.no_of_stock>0");
	          getPurchase.setInt(1,customer.getCustomer_id());
	         
	            ResultSet result=getPurchase.executeQuery();
	           while(result.next())
	           {
	              holdings.add(new HoldingStocks(result.getInt(1),checkCustomer(result.getString(2),result.getString(3)),new Stock(result.getInt(4),result.getString(5),checkCompany(result.getString(6),result.getString(7)),result.getDouble(8)),result.getDate(9),result.getDouble(10),result.getInt(11)));
	           }
	         return holdings;
	     }catch(Exception e){System.out.println(e);}
	      finally{
			      try{
				 getPurchase.close();
			      }catch(Exception e){}
	           }
	        return null;
	}
	public static boolean updateAccountBalance(double amount,Customer customer)
	{
	      PreparedStatement updateAccount=null;
	      try{
	    
	         updateAccount=con.prepareStatement("update account set balance=? where account_id=?");
	         updateAccount.setDouble(1,amount);
	         updateAccount.setInt(2,customer.getAccount().getAccount_id());
	         updateAccount.executeUpdate();
	         return true;  
	     }catch(Exception e){System.out.println(e);}
	      finally{
			      try{
				 updateAccount.close();
			      }catch(Exception e){}
	           }
	        return false;
	}
	public static boolean updateStockPrice(double amount,Stock stock)
	{
	      PreparedStatement updateStockPrice=null;
	      try{
	         updateStockPrice=con.prepareStatement("update stock set rate="+amount+" where stock_id=?");              
	         updateStockPrice.setInt(1,stock.getStock_id());    
	         updateStockPrice.executeUpdate();
	         return true;  
	     }catch(Exception e){System.out.println(e);}
	      finally{
			      try{
				 updateStockPrice.close();
			      }catch(Exception e){}
	           }
	        return false;
	}
	public static boolean updateStock(int count,Stock stock,int opt)
	{
	     String appentQuery="";
	        if(opt==1)
	        {
	           appentQuery="+"+count+"";
	        }else if(opt==0)
	        {
	           appentQuery="-"+count;
	        }else
	        {
	           return false;
	        }
	        PreparedStatement updateStock=null;
	      try{
	         updateStock=con.prepareStatement("update stock set no_of_stocks=(no_of_stocks"+appentQuery+") where stock_id=?");              
	         updateStock.setInt(1,stock.getStock_id());    
	         updateStock.executeUpdate();
	         return true;  
	     }catch(Exception e){System.out.println(e);}
	      finally{
			      try{
				 updateStock.close();
			      }catch(Exception e){}
	           }
	        return false;
	}
	public static boolean addStock(Stock stock,int noOfStocks)
	{
	       PreparedStatement addStock=null;  
	     try{
	         addStock=con.prepareStatement("insert into stock(name,company_id,rate,no_of_stocks) values(?,?,?,?)");
	         addStock.setString(1,stock.getName());
	         addStock.setInt(2,stock.getCompany().getCompany_id());
	         addStock.setDouble(3,stock.getRate());
	         addStock.setInt(4,noOfStocks);
	         addStock.executeUpdate();
	         return true;  
	     }catch(Exception e){System.out.println(e);}
	      finally{
			      try{
				 addStock.close();
			      }catch(Exception e){}
	           }
	        return false;
	}
  public static void close()
  {
       try{
          con.close();
       }catch(Exception e){}
  }
}
