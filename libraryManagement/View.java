package library;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class View
{                
    static Scanner scan= new Scanner(System.in);   
      
    public void  loginAdmin(Library newLibrary)
    {
               while(true)
	       {	
			  System.out.println("-------------------Admin-----------------------");
			if(!authentication(1))
			 {
				       System.out.println("sorry try again!");
			 } 
		         homePage(newLibrary);
	        }
    }
    public void homePage(Library newLibrary)
    {
         	
		int option =1;
		 while(option>0)
		 {
		     System.out.println("-------------------WELCOME---------------------");
		     System.out.println("1.Administration");
		     System.out.println("2.visitor");
		     System.out.println("3.library Details");
		     System.out.println("0.Exist");
		     System.out.println("-------------------------------------------------");
		     option=scan.nextInt();
		     switch(option)
		     {
		        case 1:
		               Administration();
		              break;
		        case 2:visiting();
		              break;
		        case 3:libraryDetails(newLibrary);
				         
		              break;            
		     }
		 }
    }           
    public  void Administration()
    {
        int option =1;
		 while(option>0)
		{
		   try{
			     System.out.println("----------------------------------------");
			     System.out.println("1.add new Book");
			     System.out.println("2.add category");
			     System.out.println("3.view books");
			     System.out.println("0.Exit");
			     System.out.println("----------------------------------------");
			     option=scan.nextInt();
			       switch(option)
			       {
				   case 1:Book book=createBook();
				          addNewBook(book); 
				       break;
				   case 2:
				          createCategory();
				       break;
				    
				   case 3:
				         ArrayList<Book>books=DataBase.getAllBooks();
				         if(!Book.displayBooks(books))
				         {
				            System.out.println("");
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
    public void addNewBook(Book book)
    {
         System.out.println("Enter no.of copies: ");
            int copies=scan.nextInt();	
         if(DataBase.addNewExistingBook(book.getBookName(),book.getAuthor(),book.getLanguage(),copies))
         {
	         System.out.println("book is adding!");
	           return;
         }                                    				             
        ArrayList<Category>categories=DataBase.getAllCategories();
          Book.displayCategory(categories);
        System.out.println("0. new catecory");
        System.out.println("select category based on number: ");
          int categoryID=scan.nextInt();
          if(categoryID>categories.size())
	  {			          
	          System.out.println("No category!");
	              return;
          }else if(categoryID==0)
	  {			          
	           categoryID= createCategory();
	  }
	  	          DataBase.addBook(book,copies,categoryID);
    }
    public int createCategory()
    {
          System.out.println("category name: ");
	  String categoryName=scan.next();
	 return DataBase.addCategory(categoryName);
    }
    public  boolean authentication(int opt)
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
				   case 1:boolean correct=(searchUser(opt)!=-1)?true:false;
				          if(!correct){
				            System.out.println("                  invalied....\n                     signup first!\n------------------------");
				            break;
				          }
				          System.out.println("                    success !");
				         
				         return true;
				       
				   case 2:while(true){
				              System.out.println("------------------------------ ");
				              System.out.println("Enter your firstname:");
		                               String first_name=scan.next();
		                              System.out.println("Enter your lastname:");
		                                String last_name=scan.next();
				              System.out.println("Enter user name:");
		                                String user_name=scan.next();
		                              System.out.println("Enter password:");
		                                String password=scan.next();
		                              System.out.println("Enter contact:");
		                                long contact=scan.nextLong();
				              if(LoginSystem.signup(first_name,last_name,user_name,password,contact,opt))
				              {
				                  break;
				              }
				          } 
				          System.out.println("                    success !");
				          return true;
				  
				   default:
				       return false;
				                    
				  
			       }
		    }catch(InputMismatchException e)
		    {
			    scan.next();
			    System.out.println("invalied input...");
		    }     
		 }
    }
    public  int searchUser(int option)
    {
                                  int count=1;
		                  String user_name="";
		                  String password="";
		                  int id=-1; 
		                  while(!(id!=-1) && count<=3)
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
    public void returnBook()
    {                                   System.out.println("Enter book id: ");
				          int book_id=scan.nextInt();
				       System.out.println("Enter book copy id: ");
				          int copy_id=scan.nextInt();
				       System.out.println("enter Date:");
				            Date return_date=null;
				          try{
				             return_date=new SimpleDateFormat("yyyy-MM-dd").parse(scan.next());
				          }catch(Exception e){}
				           int id=searchUser(0);
				            if(id==-1)
				            {
				                 System.out.println("Sorry...!(signup first)");
				                 return;
				            }
				        Date taken=DataBase.checkBorrower(id,book_id);
				              if(taken!=null && !(return_date.compareTo(taken)>=0))
				              {
				                System.out.println("invalied..");
				                  return;
				              }
				            if(taken==null)
				            {
				               return;
				            }
				            int fine= Library.checkDueDate(taken,return_date);
				            if(DataBase.updateBorrowers(id,book_id,copy_id,return_date,fine))
				            {
				                 System.out.println("Update...");
				                    // 0 meanse return book
				            }   
				         System.out.println((fine==0)?"your submitting ...":"your submitting late !\nfine amount : "+fine);    
    }
    public  void visiting()
    {
        int option =1;
		 while(option>0)
		 {
		     try{
			     System.out.println("----------------------------------------");
			     System.out.println("1.view & get Books: ");
			     System.out.println("2.return Book");
			     System.out.println("3.create library account");
			     System.out.println("0.Exit");
			     System.out.println("----------------------------------------");
			     option=scan.nextInt();
			       switch(option)
			       {
				   case 1:visit();
				       break;
				   case 2:
				        returnBook();
				       break;
				   case 3:if(authentication(0)){          // 0 meanse return book
				             
				                break;
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
    public  void visit()
    {
        int option =1;
		 while(option>0)
		 {
		      try{
			     System.out.println("----------------------------------------");
			     System.out.println("1.Search  book: ");
			     System.out.println("2.get book");
			     System.out.println("0.Exit");
			     System.out.println("----------------------------------------");
			     option=scan.nextInt();
			       switch(option)
			       {
				   case 1:System.out.println("Book name: ");
				          String name=scan.next();
				          System.out.println(name+" book is -> "+DataBase.searchCategory(name));    //
				       break;
				   case 2:
				          ArrayList<Category>category=DataBase.getAllCategories();
				          if(!Book.displayCategory(category))
				          {
				             System.out.println("No category...retry! ");
				             break;
				          }
				          System.out.println("select a category based on categoryID");
				          int category_id=scan.nextInt();
				          if(category_id>category.size() || category_id<=0)
				          {
				              System.out.println("No category!");
				              break;
				          }
				          category_id=category.get(category_id-1).getCategoryID();   
				          ArrayList<Book>books=DataBase.getBook(category_id);
				          if(!Book.displayBooks(books))
				          {
				              break;
				          }
				          System.out.println("select a book based on number");
				          int bookObj=scan.nextInt();
				          if(bookObj>books.size() || bookObj<=0)
				          {
				              System.out.println("No book!");
				              break;
				          }
				          Book book_obj=books.get(bookObj-1);
				          int book=book_obj.getBookID();
				          System.out.println(DataBase.getBookCopies(book));
				          System.out.println("select a copy id:");
				          int copyid=scan.nextInt();
				          if(book_obj.getCopies()==0)
				          {
				             System.out.println("this book is not available now!");
				             break;
				          }
				         
						    int id=searchUser(0);
						      if(id==-1)
						      {
						         System.out.println("Sorry...!(signup first)");
						         break;
						      }
						      System.out.println("Enter date(yyyy-mm-dd)");
						      try{
						        Date date=new SimpleDateFormat("yyyy-MM-dd").parse(scan.next());
						        if(!DataBase.borrowing(id,book,copyid,date))
						        {
						             System.out.println("");
						             break;
						        }
						       
						        System.out.println("success ! return (rental date 5)");
						        }catch(Exception e){}
					
				       break;
				   
			       }
		       }catch(InputMismatchException e)
			 {
			    scan.next();
			    System.out.println("invalied input...");
			 }  	       
		 }
    }
    
    private  Book createBook()
    {
          System.out.println("Enter book name: ");
          String bookname=scan.next();
          System.out.println("Enter book author: ");
          String author=scan.next();
          System.out.println("Enter  price of book: ");
          double price=scan.nextDouble();
          System.out.println("Enter book published date(yyyy-MM-dd): ");
          Date date=null;
          try
          {
            date=new SimpleDateFormat("yyyy-MM-dd").parse(scan.next());
          }catch(Exception e){createBook();}   
          System.out.println("Enter book publisher: ");
          String publisher=scan.next();
          System.out.println("Enter language of book: ");
          String language=scan.next();
          return new Book(bookname,author,price,date,publisher,language);
    }
    public Library createLibrary()
    {
        System.out.println("Ender Library name: ");
        String name=scan.nextLine();
        System.out.println("Starting date: ");
        String date=scan.nextLine();
        System.out.println("Ender owner name: ");
        String owner=scan.nextLine();
        return new Library(name,date,owner);
    
    }
  public  void libraryDetails(Library newLibrary)
  {
	ArrayList<Book>books=DataBase.getAllBooks();
	ArrayList category=DataBase.getAllCategories();
	   newLibrary.setTotalBooks(books.size());
	   newLibrary.setTotalCategories(category.size());
	   newLibrary.setTotalMembers(DataBase.noOfMembers());
     
		System.out.println("------------------------------------------------------");
		System.out.println("Name              :"+newLibrary.getName());
		System.out.println("Owner             :"+newLibrary.getOwner());
		System.out.println("Starting Date     :"+newLibrary.getStartingDate());
		System.out.println("Total Books       :"+newLibrary.getTotalBooks());
		System.out.println("Total Categories  :"+newLibrary.getTotalCategories());
		System.out.println("Total members     :"+newLibrary.getTotalMembers());
		System.out.println("Total fine Amount$:"+DataBase.sumOfFines());
		System.out.println("-------------------------------------------------------");
		int opt=1;
		while(opt>0)
		{
		         System.out.println("-----------------------------");
			 System.out.println("1 .view books");
			 System.out.println("2 .view categories"); 
			 System.out.println("0.Exit"); 
			 System.out.println("-----------------------------");
			 opt=scan.nextInt();
			 switch (opt) {
				 case 1:
					   Book.displayBooks(books); break;
			     case 2:
				       Book.displayCategory(category); break;
				 default:
					 break;
			 }
		}	 
			
  }	
}

