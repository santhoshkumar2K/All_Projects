package e_commerce;
import java.util.*;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
public class View
{
   static Scanner scan= new Scanner(System.in);
    public void login()
    {
       int option =1;
		while(option>0)
		{
		   try{
			    System.out.println("-------------------------------------");
			    System.out.println("1.customer");
			    System.out.println("2.seller");
			    System.out.println("-------------------------------------");
			      option =scan.nextInt();
			    switch(option)
			    {
				case 1:Customer customer=(Customer)authentication(0);
				     if(!(customer!=null))
				     {
		                          break;
		                      }
				      customerSection(customer);
				    break;
			       case 2:Seller seller=(Seller)authentication(1);
				     if(seller==null)
				     {
		                          break;
		                      }
		                      sellerSection(seller);
		                    break;       
			    }
		      }catch(InputMismatchException e)
		     {
			    scan.next();
			    System.out.println("invalied input...");
		     }   	    
		}
    }
    public  Person authentication(int opt)
    {
      
       int option =1;
		 while(true)
		 {
		    try{
			     System.out.println("----------------------------------------");
			     System.out.println("1.signIn");
			     System.out.println("2.signUp");
			     System.out.println("0.Exit");
			     System.out.println("----------------------------------------");
			     option=scan.nextInt();
			       switch(option)
			       {
				   case 1: Person person=searchUser(opt);
				          if(person==null){
				            System.out.println("                  invalied....\n                     signup first!\n------------------------");
				            break;
				          }
				          System.out.println("                    success !");
				         
				         return person;
				       
				   case 2:int out=-1;
				         while(true){
				              System.out.println("------------------------------ ");
				             
		                              System.out.println("Enter your name:");
		                                String name=scan.next();
				              System.out.println("Enter user name:");
		                                String user_name=scan.next();
		                              System.out.println("Enter password:");
		                                String password=scan.next();
		                              System.out.println("Enter contact:");
		                                long contact=scan.nextLong();
		                              Address address=createAddress();
		                                out=LoginSystem.signup(name,user_name,password,contact,address,opt);
				              if(!(out==-1))
				              {
				                  break;
				              }
				          } 
				          System.out.println("                    success !");
				         // return out;
				  
				   default:
				       return null;
				                    
				  
			       }
		    }catch(InputMismatchException e)
		    {
			    scan.next();
			    System.out.println("invalied input...");
		    }     
		 }
    }
    public Address createAddress()
    {
                    System.out.println("Enter door no:");
		        int doorNo=scan.nextInt();
                    System.out.println("Enter street:");
                         String street=scan.next();
                    System.out.println("Enter city:");
                         String city=scan.next();
                    System.out.println("Enter district:");
                         String district=scan.next();  
                    System.out.println("Enter pincode:");
                         int pincode=scan.nextInt();  
             return new Address(doorNo,street,city,district,pincode);            
    }
     public  Person searchUser(int option)
    {
                                  int count=1;
		                  String user_name="";
		                  String password="";
		                  Person id=null; 
		                  while(!(id!=null) && count<=3)
		                  {
		                      System.out.println("------------------------------ "+count);
		                      System.out.println("Enter user name:");
                                        user_name=scan.next();
                                      System.out.println("Enter password:");
                                        password=scan.next();
                                      id = LoginSystem.signin(user_name,password,option);
                                      count++;
		                  }
		                  return id;
    }
    public void customerSection(Customer customer)
    {
        int option =1;
        while(option>0)
        {
            try{
                     System.out.println("-------------------------------------");
		    System.out.println("1.shopping");
		    System.out.println("2.Cart");
                     System.out.println("3.view Order");
                     System.out.println("4.cancel Order");
		    System.out.println("-------------------------------------");
		     option = scan.nextInt();
		    switch(option)
		    {
		        case 1: int category=selectCategory();
		                ArrayList<Product>products=Database.getProductsBasedOnCategoryID(category);
		                
		               if(!customerViewProducts(products))
		               {
		                  System.out.println("no products!");
		                  break;
		               }sort(products);
		               System.out.println("select based on number: ");
		               int opt=scan.nextInt();
		               if(opt<=0 || opt>products.size())
		               {
		                  break;
		               }
		               Product product=products.get(opt-1);
		               purchase(customer,product);
		            break;
		       case 2:ArrayList<Product>product_s=Database.getCartProducts(customer);
		              sort(product_s);
		               if(!customerViewProducts(product_s))
		               {
		                  System.out.println("no products!");
		                  break;
		               }  
		             break;
		      case 3:ArrayList<Order>orders=Database.getOrders(customer);  
		             if(!(displayOrders(orders)))
		             {
		               System.out.println("no orders!");
		                  break;
		             }   
		             break;
		      case 4:ArrayList<Order>order_s=Database.getOrders(customer);  
		             if(!(displayOrders(order_s)))
		             {
		               System.out.println("no orders!");
		                  break;
		             } 
		             System.out.println("select based on number: ");
		             int orderObj=scan.nextInt();
		             if(orderObj<=0 || orderObj>order_s.size())
		             {
		                   break;
		             }
		             Order order=order_s.get(orderObj-1);
		             cancelOrder(order);
		    }
	      }catch(InputMismatchException e)
	     {
			    scan.next();
			    System.out.println("invalied input...");
             }  	    
        }
    }
    public boolean cancelOrder(Order order)
    {
          System.out.println("Enter date(yyyy-MM-dd): ");	
		       Date cancelDate=null;
		       Date date=null;
		         try{
	                      cancelDate= new SimpleDateFormat("yyyy-MM-dd").parse(scan.next());    	                   
				       LocalDate deadLine=LocalDate.of(order.getDate().getYear()+1900,order.getDate().getMonth()+1,order.getDate().getDate()+1);
				       date=new SimpleDateFormat("yyyy-MM-dd").parse(deadLine.plusDays(2).toString());   
				         
		             }catch(Exception e){}  
		              if(cancelDate.compareTo(date)<=0)
		              {
		                 int stock=Database.getQuantity(order.getProduct());
		                  Database.updateStock(order.getProduct(),stock+order.getNoOfProducts()); 
		                  Database.orderCancel(order);
		                 double total=Database.getEarning(order.getProduct().getProductID())-(order.getNoOfProducts()* order.getProduct().getPrice());
				 Database.earnings(order.getProduct().getProductID(),total);
		                  return true;
		              }else
		              {
		                 System.out.println("sorry out of Date!");
		                   return false;
		              }
    }
    public void purchase(Customer customer,Product product)
    {
        int option =1;
        while(option>0)
        {
             try{
                     System.out.println("-------------------------------------");
		    System.out.println("1.order");
		    System.out.println("2.add to Cart");
                     System.out.println("0.Exit");
		    System.out.println("-------------------------------------");
		     option = scan.nextInt();
		    switch(option)
		    {
		        case 1: Date date= new Date();
		               System.out.println("---------------------------");
		               System.out.println("| 1.change Address        |");
		               System.out.println("| 0.existing Address      |");
		               System.out.println("---------------------------");
		                int opt=scan.nextInt();
		               if(!(opt==0 || opt==1))
		               {
		                   System.out.println("invalied...");
		                   break;
		               }else if(opt==1)
		               {
				         Address newAddress=createAddress();
				         customer.setAddress(newAddress);
				         int addressID=Database.addAddress(newAddress);
				         if(!(Database.updateCustomerAddress(customer.getCustomerID(),addressID)>0))
				         {
				             System.out.println("something wrong try again ...");
				            break;
				         }
		                } 
		                 int paymentType=selectPaymentType();
		                 System.out.println("How many "+product.getProductName());
		                 int count=scan.nextInt();
		                 if(count>0)
		                 {
		                    int stock=Database.getQuantity(product);
		                    if((stock-count)<0)
		                    {
		                           System.out.println("No stock "+stock);
		                    }else
		                    {
		                       System.out.println("total price: "+count*product.getPrice());
				       System.out.println("---------------------------");
				       System.out.println("| 1.order confirm         |");
				       System.out.println("| 0.cancel                |");
				       System.out.println("---------------------------");
				       if(scan.nextInt()>0)
				       {
				          Database.updateStock(product,stock-count);  
				          Database.earnings(product.getProductID(),(Database.getEarning(product.getProductID())+count*product.getPrice()));
				          Database.addOrder(customer.getCustomerID(),product.getProductID(),date,paymentType,count,count*product.getPrice());
				          System.out.println("Ordered...");
				          return;
				       }
		                       
		                    } 
		                 
		               }
		            break;
		         case 2:  Database.addCart(customer,product);
		                   System.out.println("added...");
		                   return;
		            
		            
		    }
	      }catch(InputMismatchException e)
			 {
			    scan.next();
			    System.out.println("invalied input...");
			 }  	    
        }
    }
    public boolean displayOrders(ArrayList<Order>orders)
    {
          if(orders.size()==0)
        {
            return false;
        }
        int i=1;
        for(Order order: orders)
        {
            System.out.println(i+++".("+order.getOrderID()+") "+order.getCustomer()+" --> "+order.getProduct().getProductName()+"["+order.getNoOfProducts()+"] price: "+order.getTotalPrice()+"paid by-> "+order.getPaymentType());
        }
          return true;
    }
    public boolean sort(ArrayList<Product>products)
    {
         System.out.println("----------------------------------");
         System.out.println("|1.sorting based on product name |");
         System.out.println("|2.sorting based on product price|");
         System.out.println("----------------------------------");
         int opt=scan.nextInt();
          if(!(opt>0 && opt<3))
          {
             return false;
          }else if(opt==1)
          {
            
            Collections.sort(products);
            
          }else{
             Collections.sort(products,(product1,product2)->(int)(product1.getPrice()-product2.getPrice()));
          }
                 return true;  
    }
    public int selectPaymentType()
    {
       ArrayList<String> types=Database.getPaymentTypes();
           if(!displayPaymentType(types))
           {
                     return -1;
           }
	System.out.println("select type:");
        int input=scan.nextInt();
        if(input<=0 || input>types.size())
         {
                  System.out.println("invalied...");
                   return-1;
         } 
         return input;
    } 
    public int selectCategory()
    {
           ArrayList<String>category=Database.getCategories();

	      if(!displayCategories(category))
              {
                  System.out.println("");
              }
              System.out.println("select based on number:");
              int input=scan.nextInt();
             if(input<=0 || input>category.size())
             {
                  System.out.println("invalied...");
                   return-1;
             }
           return input;  

    }
    public void sellerSection(Seller seller)
    {
        int option =1;
        while(option>0)
        {
             try{
                     System.out.println("-------------------------------------");
		    System.out.println("1.check stock");
		    System.out.println("2.add products");
                     System.out.println("3.update product");
                     System.out.println("4.view earnings");
		    System.out.println("-------------------------------------");
		    option = scan.nextInt();
		    switch(option)
		    {
		        case 1:ArrayList<Product>products=Database.getProductsBasedOnSellerID(seller.getSellerID());

		              if(!sellerViewProduct(products))
		              {
		                 System.out.println("no products...");
		                 break;
		              }
		            break;
		       case 2:Product newProduct=createProduct();
			        System.out.println("Quantity: ");
				 int nums=scan.nextInt();     
				 ArrayList<String>category=Database.getCategories();
				 if(!displayCategories(category))
				 {
				    break;
				 }
				 System.out.println("0.create new categoy\nselect :");
				 int ender=scan.nextInt();
				 if(ender<0 || ender>category.size())
				 {
				    break;
				 }else if(ender==0)
				 {
				    System.out.println("ender category name:");
				    ender=Database.addCategory(scan.next());
				 }
				 
				 int product_id=Database.addProduct(newProduct,ender,seller.getSellerID());
				if(!(product_id!=-1))
				{
				   break;
				} 
				Database.addStock(product_id,nums);
			         System.out.println(newProduct.getProductName()+" is Added..");	
			     break;
		       case 3: updateProduct(seller.getSellerID());
		             
		             break;
		       case 4:System.out.println("------------------------:");
		              System.out.println("Earned: $ "+Database.getSeller(seller.getUserName(),seller.getPassword()).getEarned());
		              System.out.println("------------------------:");
		             break;      	         
		            
		    }
             }catch(InputMismatchException e)
			 {
			    scan.next();
			    System.out.println("invalied input...");
			 }  		    
        }
    }
    public void updateProduct(int sellerID)
    {
        int option =1;
        double updateInput=0;
        int selectOption=0;
        int input=0;
        while(option>0)
        {
             try{
                     System.out.println("-------------------------------------");
		    System.out.println("1.update price");
		    System.out.println("2.increasing stock");
                     System.out.println("3.remove product");
		    System.out.println("-------------------------------------");
		     option = scan.nextInt();
		    switch(option)
		    {
		        case 1:ArrayList<Product>products=Database.getProductsBasedOnSellerID(sellerID);
		              if(!sellerViewProduct(products))
		              {
		                 System.out.println("no products...");
		                 break;
		              }
		              System.out.println("select product based on number:");
		                input=scan.nextInt();
		              if(input<=0 || input>products.size())
		              {
		                  System.out.println("invalied...");
		                  break;
		              }
		               Product product=products.get(input-1);
		              if(updatePrice(product)){
		                 System.out.println("updated...");
		              }
		            break;
		       case 2:ArrayList<Product>product_s=Database.getProductsBasedOnSellerID(sellerID);
		              if(!sellerViewProduct(product_s))
		              {
		                 System.out.println("no products...");
		                 break;
		              }
		              System.out.println("select product based on number:");
		                input=scan.nextInt();
		              if(input<=0 || input>product_s.size())
		              {
		                  System.out.println("invalied...");
		                  break;
		              }
		               Product select_product=product_s.get(input-1);
		               int quantity=Database.getQuantity(select_product);
		              System.out.println("current Quantity: "+quantity);
		              System.out.println("Ender Quantity:");
		                int Input=scan.nextInt();
		                     Input=quantity+Input;
		               if(!Database.updateStock(select_product,Input))
		               {
		                 System.out.println("some thingwrong!!!");
		                  break;
		               }
		                 System.out.println("updated...");
		              
			     break;
		   
		    }
	     }catch(InputMismatchException e)
			 {
			    scan.next();
			    System.out.println("invalied input...");
			 }  	    
        }
    }
    public boolean updatePrice(Product product)
    {
         int option =1;
        double updateInput=0;
        int selectOption=0;
        int input=0;
                      System.out.println("current price: $ "+product.getPrice()+"\n1.increase price,\n2.decrease price.\nselect option: ");
		                selectOption=scan.nextInt();
		              System.out.println("Ender amount:");
		                updateInput=scan.nextInt();
		              if(!(selectOption>0 && selectOption<=2))
		              {
		                 System.out.println("invalied...");
		                  return false;
		              }else if(selectOption==1)
		              {
		                 updateInput=product.getPrice()+updateInput;
		              }else if(selectOption==2)
		              {
		                 if(!(product.getPrice()>=updateInput))
		                 {
		                    System.out.println("this is negative amount!!!");
		                    return false;
		                 }
		                 updateInput=Math.abs(product.getPrice()-updateInput);
		              }
		                product.setPrice(updateInput);
		               if(!Database.updateProduct(product))
		               {
		                 System.out.println("some thingwrong!!!");
		                 return false;
		               }  
		           return true;    
    }
    public Product createProduct()
    {               scan.nextLine();
                 System.out.println("------------------------------------");
		System.out.println("Product name: ");
		   String product=scan.nextLine();
		System.out.println("Product description: ");
		   String description=scan.nextLine();
		System.out.println("Product price $: ");
		   double price=scan.nextDouble();
		System.out.println("Ender GST for this %: ");
		   int gst=scan.nextInt();   
       return new Product(product, description, price,gst);
    }
    public boolean displayCategories(ArrayList<String>categories)
    {
        if(categories.size()==0)
        {
            return false;
        }
        int i=1;
        for(String category: categories)
        {
            System.out.println(category);
        }
          return true;
    }
     public boolean displayPaymentType(ArrayList<String>payments)
    {
        if(payments.size()==0)
        {
            return false;
        }
        int i=1;
        for(String payment: payments)
        {
            System.out.println(payment);
        }
          return true;
    }
	public boolean sellerViewProduct(ArrayList<Product>products)
    {
        if(products.size()==0)
        {
            return false;
        }
        int i=1;
        for(Product product: products)
        {
            System.out.println(i+++"."+product.getProductName()+" = "+product.getPrice()+" --> "+Database.getQuantity(product));
        }
          return true;
    }
    public static boolean customerViewProducts(ArrayList<Product>products)
     {
         if(products.size()==0)
         {
             return false;
         }
         int i=1;
         for(Product product: products)
         {
             System.out.println("**************************************************");
             System.out.println(i+++"."+product);
             System.out.println("**************************************************");
         }
         return true;
     }

}
