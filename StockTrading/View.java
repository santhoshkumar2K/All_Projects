package stock_tradings;
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Date;
import java.util.InputMismatchException;
public class View {
   
   public void homePage(Scanner scan)
   {
	   int option=1;
	   while(option>0)
	   {
	      try{
			   System.out.println("-------------------------------");
			   System.out.println("1.Customer");
			   System.out.println("2.Company");
			   System.out.println("-------------------------------");
			   option=scan.nextInt();
			   if(option==1)
			   {
				   loginPage(scan,0);
			   }else
			   {
				   loginPage(scan,1); 
			   }
	          }catch(InputMismatchException e)
	          {
			    scan.next();
			    System.out.println("invalied input...");
                   }   	  		   
	   }
   }
   public void loginPage(Scanner scan,int opt)
   {
	   int option=1;
	   while(option>0)
	   {
	      try{
			   System.out.println("-------------------------------");
			   System.out.println("1.Signin");
			   System.out.println("2.Signup");
			   System.out.println("0.Exit");
			   System.out.println("-------------------------------");
			   option=scan.nextInt();
			  switch(option)
			  {
				  case 1:if(opt==1)
					 {
					      System.out.println("------------------Company------------------");
					      System.out.println("Enter username:");
					      String username=scan.next();
					      System.out.println("Enter password:");
						   String password=scan.next();
						Company com=(Company)Login.signIn(username,password,0);
						if(com==null)
						{
						   break;
						}
						companyPage(scan,com);
					 }else {
						 System.out.println("-------------------Customer-------------------");
						 System.out.println("Enter username:");
						      String user_name=scan.next();
						      System.out.println("Enter password:");
							   String pass_word=scan.next();
							 Customer cus=(Customer)Login.signIn(user_name,pass_word,1);  
							 if(cus==null){
							    break;
							 }
							
								customerPage(scan,cus); 
					 }
					    break;
				  case 2:if(opt==1)
				     {
						 Company newCompany=createCompany(scan);
						 Login.signUp(newCompany);
						 
				     }else {
					 
				    	 Customer newCustomer=createCustomer(scan);
				    	 Login.signUp(newCustomer);
				    	 
				     } 
					    break;
			  }
	          }catch(InputMismatchException e)
	          {
			    scan.next();
			    System.out.println("invalied input...");
                   }   
	   }
   }
   public void companyPage(Scanner scan,Company company)
   {
	   int option=1;
	   while(option>0)
	   {
	      try{
			   System.out.println("-------------------------------");
			   System.out.println("1.view own Stocks");
			   System.out.println("2.add Stock");
			   System.out.println("0.Exit");
			   System.out.println("-------------------------------");
			   option=scan.nextInt();
			  switch(option)
			  {
				  case 1:HashMap<Stock,Integer>stocks=Database.getStocks(company);
					 if(!displayStocks(stocks))
					 {
					     System.out.println("no stocks");
					     break;
					 }
					    break;
				  case 2: Stock stock=createStock(company,scan); 
					  System.out.println("Enter number of stocks:");
					  int nums=scan.nextInt();
					  if(!Database.addStock(stock,nums))
					  {
					      System.out.println("no adding ! try again..");
					     break;
					  }
					    System.out.println("success...");
					    break;
				     
			  }
	          }catch(InputMismatchException e)
	          {
			    scan.next();
			    System.out.println("invalied input...");
                   }     
	   }
   }
   public boolean displayStocks(HashMap<Stock,Integer>stocks)
   {
       if(stocks==null || stocks.size()==0)
       {
           return false;
       }
       for(Map.Entry<Stock,Integer> stock: stocks.entrySet())
       {
           Stock object=stock.getKey();
           System.out.println(object.getStock_id()+"."+object.getName()+" --> "+object.getCompany().getName()+" = "+object.getRate()+" per a stock ("+stock.getValue()+").");
       }
       return true;
   }
   public void customerPage(Scanner scan,Customer customer)
   {
     
	   int option=1;
	   while(option>0)
	   {
	      try{
			   System.out.println("-------------------------------");
			   System.out.println("1.view stocks");
			   System.out.println("2.sell Stock");
			   System.out.println("3.view portfolio");
			   System.out.println("4.view Account details");
			   System.out.println("0.Exit");
			   System.out.println("-------------------------------");
			   option=scan.nextInt();
			  switch(option)
			  {
			  case 1: HashMap<Stock,Integer>stocks=Database.getAllStocks();
				Stock stock=selectStock(scan,customer,stocks);
				if(stock==null)
				{
				   System.out.println("no stock");
				   break;
				}
				  System.out.println("How many stocks:");
			             int count=scan.nextInt();
				     int noOfStock=stocks.get(stock);
				     if(count>noOfStock)
				     {
					System.out.println("invalied counts");
					break;
				     }
				    double totalAmount= checkBalance(customer,count,stock.getRate());
				     if((totalAmount<0))
				     {
					 System.out.println("insafficiend balance");
					 break;
				     }  
				     Date date= new Date();
				 Database.addPurchase(stock,customer,count,date);             
				 Database.updateAccountBalance((customer.getAccount().getBalance()-totalAmount),customer);      
				 Database.updateStock(count,stock,0);  //0 for minus        
				 customer=Database.checkCustomer(customer.getUserName(),customer.getPassword());
				    break;
			  case 2:ArrayList<HoldingStocks>stock_s=Database.getpurchaseDetails(customer);
				;
				if(!displayHoldings(stock_s))
				{
				   System.out.println("no stock");
				   break;
				}
				 System.out.println("select a number: ");
				 int opt=scan.nextInt();
				 if(opt<=0 || opt>stock_s.size())
				 {
				    System.out.println("invalied number...");
				    break;
				 }
				 HoldingStocks holding=stock_s.get(opt-1);
				  System.out.println("How many stocks:");
			             int counts=scan.nextInt();
			             int available=holding.getQuantity();
			           if(counts>available)
			           {
			             System.out.println("your option is greater than stocks!:");
			              break;
			           }  
			           Date now=new Date();
			          Database.updatePurchase(holding.getHolding_id(),(available-counts));  
			          Database.addSellings(holding.getStock(),customer,counts,now);
			          double total=counts*holding.getStock().getRate();           
				 Database.updateAccountBalance((customer.getAccount().getBalance()+total),customer);      
				 Database.updateStock(counts,holding.getStock(),1); //1 for plus        
				 customer=Database.checkCustomer(customer.getUserName(),customer.getPassword());
				    break;
			   case 3:ArrayList<HoldingStocks>holdings=Database.getpurchaseDetails(customer);
				;
				if(!portfolio(holdings))
				{
				   System.out.println("no stock");
				   break;
				}
			          break;
			   case 4:  displayAccountDetails(customer);
			          break;         		    
			  }
		  }catch(InputMismatchException e)
	          {
			    scan.next();
			    System.out.println("invalied input...");
                   }   
	   }
   }
   public void displayAccountDetails(Customer customer)
   {
         System.out.println("------------------------------------------------------------------");
         System.out.println("name:"+customer.getName());
         System.out.println("contact: "+customer.getContact());
         System.out.println("Account balance: "+customer.getAccount().getBalance());
         System.out.println("------------------------------------------------------------------");   
   }
   public boolean portfolio(ArrayList<HoldingStocks>holdings)
   {
     if(holdings.size()==0)
       {
           return false;
       }
       int count=0;
       double currentAmount=0;
       double profit=0;
       double invest=0;
       for(HoldingStocks stock_s: holdings)
       {
           count+=stock_s.getQuantity();
           currentAmount+=stock_s.getStock().getRate();
           invest+=stock_s.getPurchaseRate();
           
       }
	       System.out.println("-----------------------------portfolio-------------------------------------");
	       System.out.println("Total invet:"+invest+"               no.of stocks:"+count);
	       System.out.println("current value:"+currentAmount+"         profit:"+(currentAmount-invest));
	       int i=1;
       for(HoldingStocks stock: holdings)
       {
               System.out.println(i+++". ["+stock.getStock().getName()+"] purchasing date: "+stock.getDate()+" --> price$: "+stock.getPurchaseRate()+"   = currentPrice"+stock.getStock().getRate());
       }
               System.out.println("----------------------------------------------------------------------------");
               return true;
       
   }
   public boolean displayHoldings(ArrayList<HoldingStocks>holdings)
   {
       if(holdings.size()==0)
       {
           return false;
       }
       int i=1;
       for(HoldingStocks stock: holdings)
       {
           System.out.println(i+++". ["+stock.getStock().getName()+"] purchasing date: "+stock.getDate()+" --> price$: "+stock.getPurchaseRate());
       }
       return true;
   }
   public Stock selectStock(Scanner scan,Customer customer,HashMap<Stock,Integer>stocks)
   {
      
		          if(!displayStocks(stocks))
			  {
			           
			             return null;
	                   }
	                   System.out.println("select :");
	                   int opt=scan.nextInt();
	       for(Stock stock : stocks.keySet())   
	       {
	          if(stock.getStock_id()==opt)
	          {
	             
	             return stock;
	          }
	       }        
	       return null;
   }
   public double checkBalance(Customer customer,int noOfStocks, double amount)
   {
      double total=noOfStocks*amount; 
      if(customer.getAccount().getBalance()>=total)
      { System.out.println(customer.getAccount().getBalance()+" ? "+total);
         
         return total;
      }
      return -1;
   }
   public String checkUserName(String username)
   {
	   char[]array=username.toCharArray();
	   int number=0;
	   int lowerCase=0;
	   int upperCase=0;
	   int special=0;
	   if(username.length()!=8)
	   {
		   return null;
	   }
	   for(int index=0;index<array.length;index++)
	   {
		   
		   if(array[index]>=48 && array[index]<=57)
		   {
			   number++;
		   }else if(array[index]>=97 && array[index]<=123) {
			   lowerCase++;
		   }else if(array[index]>=65 && array[index]<=91)
		   {
			   upperCase++;
		   }else if(array[index]=='@' || array[index]=='#' ||array[index]=='-')
		   {
			   special++;
		   }
	   }
	   if(number!=2 || upperCase!=2 || lowerCase!=2 || special!=2)
	   {
		   return null;
	   }
	   return username;
   }	   
   public String checkPassword(String password)
   {
	   if(password.length()!=8)
	   {
		   return null;
	   }
	   return password;
   }
   public Address createAddress(Scanner scan)
   {
	   System.out.println("door no:");
	   int doorNo=scan.nextInt();
	   System.out.println("street");
	   String street=scan.next();
	   System.out.println("city:");
	   String city=scan.next();
	   System.out.println("district:");
	   String district=scan.next();
	   System.out.println("Pincode:");
	   int pincode=scan.nextInt();
	   return new Address(doorNo,street,city,district,pincode);
   }
  
   public Customer createCustomer(Scanner scan)
   {
	   System.out.println("Enter your name:");
	   String name=scan.next();
	   System.out.println("Enter username(two Uppercase two lowercase two digit two special(@/#/-)):");
	   String username=checkUserName(scan.next());
	   System.out.println("Enter password(8 digit):");
	   String password=checkPassword(scan.next());
	   Address address=createAddress(scan);
	   System.out.println("Enter your contact:");
	   long contact=scan.nextLong();
	   Account account=createAccount(scan);
	   return new Customer(name,username,password,address,contact,account);
	   
   }
   public Account createAccount(Scanner scan)
   {
           System.out.println("Enter Ac number:");
           long ac=scan.nextLong();
	   System.out.println("Enter account password(8 digit):");
	   String password=checkPassword(scan.next());
	   System.out.println("Your Account no: "+ac);
	   return new Account(ac,password,500.0);
   }
   public Company createCompany(Scanner scan)
   {
	   System.out.println("Enter company name:");
	      String name=scan.next();
	   System.out.println("Enter username:");
	      String username=checkUserName(scan.next());
	      System.out.println("Enter password:");
		   String password=checkPassword(scan.next());
		   Address address=createAddress(scan);
		   ArrayList<Stock>stocks= new ArrayList<>();
		   return new Company(name,username,password,address,stocks);
   }
   public Stock createStock(Company company,Scanner scan)
   {
	   System.out.println("Enter stock name:");
	      String name=scan.next();
	   System.out.println("Enter rate per 1:");
	      int rate=scan.nextInt();
	      
	  return new Stock(name,company,rate);     
   }
}
