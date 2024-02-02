package e_commerce;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Properties;
import java.sql.SQLException;

public class Database
{
   private static FileInputStream file;
	private static Properties property=new Properties();
	private static Connection con;
static {
          try{
              file=new FileInputStream("//home//zoho//santhosh//finalProjects//E-commerceWebsite//MyDatabase.properties");
               property.load(file);
            
          }catch(IOException e){}
	try {
	   con =DriverManager.getConnection(property.getProperty("url"), property.getProperty("username"), property.getProperty("password"));
	}catch(Exception e) {System.out.println("No connection"+e);}
}
   
	public static int addCategory(String category)
	{
		PreparedStatement addCategory=null;
		try{
			addCategory=con.prepareStatement("insert into category(category) values(?) returning id");
			addCategory.setString(1, category);
			addCategory.execute();
			ResultSet result=addCategory.getResultSet();
			result.next();
           return result.getInt(1);
		}catch(Exception e){}
		finally{
		   try{
		       addCategory.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static int addCustomer(Customer customer)
	{                                                   
		PreparedStatement addCustomer=null;
		try{
		        int address_id=addAddress(customer.getAddress());      
		        
			addCustomer=con.prepareStatement("insert into customer(name,username,password,contact,address_id) values(?,?,?,?,?) returning customer_id");                                                        
			addCustomer.setString(1, customer.getName());
			addCustomer.setString(2, customer.getUserName());
			addCustomer.setString(3, customer.getPassword());
			addCustomer.setLong(4, customer.getContact());
			addCustomer.setInt(5, address_id);
			addCustomer.execute();                         
			ResultSet result=addCustomer.getResultSet();    
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       addCustomer.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static int addOrder(int customerID,int productID,java.util.Date date,int paymentType,int noOfProducts,double total)
	{
		PreparedStatement addOrder=null;
		try{
			addOrder=con.prepareStatement("insert into orders(customer_id,product_id,order_date,payment_type_id,count,total_price,canceled) values(?,?,?,?,?,?,?) returning order_id");
			addOrder.setInt(1, customerID);
			addOrder.setInt(2, productID);
			addOrder.setDate(3, new java.sql.Date(date.getTime()));
			addOrder.setLong(4, paymentType);
			addOrder.setInt(5, noOfProducts);
			addOrder.setDouble(6, total);
			addOrder.setBoolean(7, false);
			addOrder.execute();
			ResultSet result=addOrder.getResultSet();
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       addOrder.close();
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
		   }catch(SQLException e){  System.out.println("No create table..."+e);}  	
		   finally{
		   try{
		       createTable.close();
		   }catch(Exception e){}
		}
	      return false;	    
       }
	public static int updateCustomerAddress(int customerID,int addressID)
	{
		PreparedStatement updateCustomerAddress=null;
		try{
			updateCustomerAddress=con.prepareStatement("update customer set address_id=? where customer_id=?");
			updateCustomerAddress.setInt(1, addressID);
			updateCustomerAddress.setInt(2, customerID);
			return updateCustomerAddress.executeUpdate();
			
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       updateCustomerAddress.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static ArrayList getPaymentTypes()
	{
	   ArrayList<String>list = new ArrayList<>();
	   PreparedStatement getPaymentTypes=null;
		try{
			getPaymentTypes=con.prepareStatement("select * from payment_type");
			ResultSet result=getPaymentTypes.executeQuery();
			while(result.next())
			{
				list.add(result.getInt(1)+". "+result.getString(2));
			}
           return list;
		}catch(Exception e){ System.out.println(" 4 "+e);}
		finally{
		   try{
		       getPaymentTypes.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static ArrayList<Order> getOrders(Customer customer)
	{
	     ArrayList<Order>orders=new ArrayList<>();
	     PreparedStatement getOrders=null;
	     try{    
			getOrders=con.prepareStatement("select orders.order_id,c.name,product.product_id,product.product_name,product.description,product.price,product.gst, orders.order_date,payment_type.payment_type,orders.count,orders.total_price from orders join customer c on orders.customer_id=c.customer_id join product on orders.product_id=product.product_id join payment_type on payment_type.id=orders.payment_type_id where orders.customer_id=? and orders.canceled='false'");
			getOrders.setInt(1, customer.getCustomerID());
			ResultSet result=getOrders.executeQuery();
		      while(result.next())
		      {
		          orders.add(new Order(result.getInt(1),result.getString(2),new Product(result.getInt(3),result.getString(4),result.getString(5),result.getDouble(6),result.getInt(7)),result.getDate(8),result.getString(9),result.getInt(10),result.getDouble(11)));
		      }
		      return orders;
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       getOrders.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static boolean orderCancel(Order order)
	{
	   PreparedStatement orderCancel=null;
	    try{
		    
			orderCancel=con.prepareStatement("update orders set canceled='true' where order_id=?");
			orderCancel.setInt(1, order.getOrderID());
			orderCancel.executeQuery();
			
			return true;
	}catch(Exception e){ System.out.println("1 "+e);}
	finally{
		   try{
		       orderCancel.close();
		   }catch(Exception e){}
		}
		  return false;
	}
	public static Customer getCustomer(String username,String password)
	{
		PreparedStatement getCustomer=null;      
		try{
		    
			getCustomer=con.prepareStatement("select customer_id,name,username,password,address.door_number,address.street,address.city,address.district,address.pincode,contact from customer join address on customer.address_id=address.address_id where username=? and password =?"); 
			getCustomer.setString(1, username);
			getCustomer.setString(2, password);
			getCustomer.executeQuery();
			ResultSet result=getCustomer.getResultSet();
			
			result.next();
			
           return new Customer(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),
                  new Address(result.getInt(5),result.getString(6),result.getString(7),result.getString(8),result.getInt(9)),result.getLong(10));
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       getCustomer.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static boolean addCart(Customer customer,Product product)
	{
	   PreparedStatement addCart=null;
	   try{
		    
			addCart=con.prepareStatement("insert into cart(customer_id,product_id) values(?,?)");
			addCart.setInt(1,customer.getCustomerID());
			addCart.setInt(2, product.getProductID());
			addCart.executeUpdate();
			
                return true;
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       addCart.close();
		   }catch(Exception e){}
		}
		  return false;
	}
	public static ArrayList<Product> getCartProducts(Customer customer)
	{
	   PreparedStatement getCartProducts=null;
	   try{
		    
			getCartProducts=con.prepareStatement("select product.product_id,product.product_name,product.description,product.price,product.gst from cart join product on product.product_id=cart.product_id where cart.customer_id=?");
			getCartProducts.setInt(1,customer.getCustomerID());
			ResultSet result=getCartProducts.executeQuery();
			return getProducts(result);
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       getCartProducts.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static Seller getSeller(String username,String password)
	{
		PreparedStatement getSeller=null;
		try{
		                                   System.out.println("2^ ");
			getSeller=con.prepareStatement("select seller_id,name,username,password,address.door_number,address.street,address.city,address.district,address.pincode,contact,earned from seller join address on seller.address_id=address.address_id where username=? and password =?");    System.out.println("2^ 2");
			getSeller.setString(1, username);
			getSeller.setString(2, password);
			getSeller.execute();
			ResultSet result=getSeller.getResultSet();              System.out.println("2^3 ");
			result.next();
           return new Seller(result.getInt(1),result.getString(2),result.getString(3),result.getString(4),
                  new Address(result.getInt(5),result.getString(6),result.getString(7),result.getString(8),result.getInt(9)),result.getLong(10),result.getDouble(11));
		}catch(Exception e){ System.out.println("1 "+e);}
		finally{
		   try{
		       getSeller.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static int addSeller(Seller seller)
	{
		 PreparedStatement addSeller=null;
		try{
		        int address_id=addAddress(seller.getAddress());
		        
			addSeller=con.prepareStatement("insert into seller(name,username,password,contact,address_id) values(?,?,?,?,?,?) returning seller_id");
			addSeller.setString(1, seller.getName());
			addSeller.setString(2, seller.getUserName());
			addSeller.setString(3, seller.getPassword());
			addSeller.setLong(4, seller.getContact());
			addSeller.setInt(5, address_id);
			addSeller.setDouble(6, 0.0);
			addSeller.execute();
			ResultSet result=addSeller.getResultSet();
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println("2 "+e);}
		finally{
		   try{
		       addSeller.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static boolean earnings(int productID,double amount)
	{
	    PreparedStatement earnings=null;
	    try{
			earnings=con.prepareStatement("update seller set earned=? where seller_id=(select seller_id from product where product_id=?)");
			earnings.setDouble(1,amount);
			earnings.setInt(2,productID);
			earnings.executeUpdate();
			
           return true;
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       earnings.close();
		   }catch(Exception e){}
		}
		  return false;	
	}public static double getEarning(int productID)
	{
	    PreparedStatement getEarning=null;
	    try{
			getEarning=con.prepareStatement("select earned from seller where seller_id=(select seller_id from product where product_id=?)");
			getEarning.setInt(1,productID);
			ResultSet result=getEarning.executeQuery();
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       getEarning.close();
		   }catch(Exception e){}
		}
		  return -1;	
	}
	public static int getQuantity(Product product)
	{
	  PreparedStatement getQuantity=null;
	try{
			getQuantity=con.prepareStatement("select quantity from stock where product_id=?");
			getQuantity.setInt(1, product.getProductID());
			getQuantity.executeQuery();
			ResultSet result=getQuantity.getResultSet();
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       getQuantity.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static int addAddress(Address address)
	{
		PreparedStatement addAddress=null;
		try{
			addAddress=con.prepareStatement("insert into address(door_number,street,city,district,pincode) values(?,?,?,?,?) returning address_id");
			addAddress.setInt(1, address.getDoorNumber());
			addAddress.setString(2, address.getStreet());
			addAddress.setString(3, address.getCity());
			addAddress.setString(4, address.getDistrict());
			addAddress.setInt(5,address.getBincode());
			addAddress.execute();
			ResultSet result=addAddress.getResultSet();
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       addAddress.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static boolean updateStock(Product product,int quantity)
	{
		PreparedStatement updateStoke=null;
		try{
			updateStoke=con.prepareStatement("update stock set quantity=? where product_id=?");
			updateStoke.setInt(1,quantity);
			updateStoke.setInt(2,  product.getProductID());
			updateStoke.executeUpdate();
			
           return true;
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       updateStoke.close();
		   }catch(Exception e){}
		}
		  return false;
	}
	public static boolean updateProduct(Product product)
	{
		PreparedStatement updateStoke=null;
		try{
			updateStoke=con.prepareStatement("update product set price=? where product_id=?");
			updateStoke.setDouble(1,product.getPrice());
			updateStoke.setInt(2,  product.getProductID());
			updateStoke.executeUpdate();
			
           return true;
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       updateStoke.close();
		   }catch(Exception e){}
		}
		  return false;
	}
	public static int addStock(int product_id,int quantity)
	{
		PreparedStatement addStock=null;
		try{
			addStock=con.prepareStatement("insert into stock(product_id,quantity) values(?,?) returning stock_id");
			addStock.setInt(1, product_id);
			addStock.setInt(2,quantity);
			addStock.execute();
			ResultSet result=addStock.getResultSet();
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       addStock.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static int addProduct(Product product,int category,int seller)
	{
		PreparedStatement addProduct=null;
		try{
			addProduct=con.prepareStatement("insert into product(product_name,description,price,seller_id,category_id,gst) values(?,?,?,?,?,?) returning product_id");
			addProduct.setString(1, product.getProductName());
			addProduct.setString(2, product.getDescription());
			addProduct.setDouble(3, product.getPrice());
			addProduct.setDouble(4, seller);
			addProduct.setDouble(5, category);
			addProduct.setDouble(6, product.getGST());
			addProduct.execute();
			ResultSet result=addProduct.getResultSet();
			result.next();
           return result.getInt(1);
		}catch(Exception e){ System.out.println(" 3 "+e);}
		finally{
		   try{
		       addProduct.close();
		   }catch(Exception e){}
		}
		  return -1;
	}
	public static ArrayList getCategories()
	{
		ArrayList<String>list = new ArrayList<>();
		PreparedStatement getCategory=null;
		try{
			getCategory=con.prepareStatement("select * from category");
			ResultSet result=getCategory.executeQuery();
			while(result.next())
			{
				list.add(result.getInt(1)+". "+result.getString(2));
			}
           return list;
		}catch(Exception e){ System.out.println(" 4 "+e);}
		finally{
		   try{
		       getCategory.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static ArrayList getProductsBasedOnSellerID(int sellerID)
	{
	     PreparedStatement getProducts=null;
		try{
			getProducts=con.prepareStatement("select product_id,product_name,description,price,gst from product where seller_id=?");
			getProducts.setInt(1,sellerID);
			ResultSet result=getProducts.executeQuery();
			
                     return getProducts(result);
		}catch(Exception e){ System.out.println(" 5 "+e);}
		finally{
		   try{
		       getProducts.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static ArrayList<Product> getProductsBasedOnCategoryID(int categoryID)
	{
	     PreparedStatement getProducts=null;
		try{
			getProducts=con.prepareStatement("select product_id,product_name,description,price,gst from product where category_id=?");
			getProducts.setInt(1,categoryID);
			ResultSet result=getProducts.executeQuery();
			
                     return getProducts(result);
		}catch(Exception e){ System.out.println(" 5 "+e);}
		finally{
		   try{
		       getProducts.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static ArrayList getProducts(ResultSet result)
	{
	    PreparedStatement products=null;
		ArrayList<Product>product_s = new ArrayList<>();
		try{
			while(result.next())
			{
				product_s.add(new Product(result.getInt(1),result.getString(2),result.getString(3),result.getDouble(4),result.getInt(5)));
			}
           return product_s;
		}catch(Exception e){ System.out.println(" 5 "+e);}
		finally{
		   try{
		       products.close();
		   }catch(Exception e){}
		}
		  return null;
	}
	public static void close()
	{
	   try{
	       con.close();
	   }catch(Exception e){}
	}
}
