package library;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class DataBase
{
     
     
       static boolean start;
    private static PreparedStatement createTable;
    private static PreparedStatement addBook;
    private static PreparedStatement getBooks;
    private static PreparedStatement getBook;
    private static PreparedStatement updateBook;
    private static PreparedStatement addCustomer;
    private static PreparedStatement getCustomer;
    private static PreparedStatement addAdmin;
    private static PreparedStatement getAdmin;
    private static PreparedStatement addCategory;
    private static PreparedStatement getAllCategory; 
    private static PreparedStatement getCategory;
    private static PreparedStatement getNoOfIssueBooks;
    private static PreparedStatement searchBook;
    private static PreparedStatement searchCategory;
    private static PreparedStatement borrowing; 
    private static PreparedStatement updateBorrowers;
    private static PreparedStatement checkBorrower;
    private static PreparedStatement updateBookCount;
    private static PreparedStatement addNoOfBorrowingBooks;
    private static PreparedStatement updateborrowingBooks;
    private static PreparedStatement getNoOfborrowingBooks;
    private static PreparedStatement addCopies;
    private static PreparedStatement updateCopies;
    private static PreparedStatement getCopies;
    private static PreparedStatement exist;
    private static PreparedStatement existbook;
    private static PreparedStatement fines;
    private static PreparedStatement members;
   private static FileInputStream file;
   private static Properties property=new Properties();
   private static Connection con;
static {
          try{
              file=new FileInputStream("//home//zoho//santhosh//finalProjects//libraryManagement//MyDatabase.properties");
               property.load(file);
              
          }catch(IOException e){}
	try {
	   con =DriverManager.getConnection(property.getProperty("url"), property.getProperty("username"), property.getProperty("password"));
	}catch(Exception e) {System.out.println(".."+e);}
}

    
  public static int addBook(Book book,int copies,int category_id) 
  {
    java.sql.Date sDate= new java.sql.Date(book.getPublishedDate().getTime());

        try{
            addBook=con.prepareStatement("insert into books (book_name,author,published_date,publisher,language,copies,price,category_id)values(?,?,?,?,?,?,?,?)returning book_id");
                
		addBook.setString(1,book.getBookName());
		addBook.setString(2,book.getAuthor());
		addBook.setDate(3,sDate);
		addBook.setString(4,book.getPublisher());
		addBook.setString(5,book.getLanguage());
		addBook.setInt(6,copies);
		addBook.setDouble(7,book.getPrice());
		addBook.setInt(8,category_id);
		addBook.execute();     
		ResultSet result=addBook.getResultSet();
		result.next();
		int bookID=result.getInt(1);
		addCopies(bookID,copies);  
		    return bookID;
	   }catch(SQLException e){  System.out.println("No adding book..."+e);}  
	   	
      return -1;	    
  }
  public static boolean addCopies(int bookID,int copies) 
  {
        try{
              addCopies=con.prepareStatement("insert into bookcopies(book_id,isavailable) values(?,?)");
		   while(copies>0)
		   {
		      
		      addCopies.setInt(1,bookID);
		      addCopies.setBoolean(2,true);
		      addCopies.executeUpdate();
		      copies--;
		   }
		return true;
	   }catch(SQLException e){  System.out.println("No copy book..."+e);}  	
      return false;	    
  }
  public static int addCustomer(Visitor customer) 
  {
        try{
               addCustomer=con.prepareStatement("insert into visitors(name,username,password,contact) values(?,?,?,?) returning visitor_id");
               
		addCustomer.setString(1,customer.getVisitorName());
		addCustomer.setString(2,customer.getUserID());
		addCustomer.setString(3,customer.getPassword());
		addCustomer.setLong(4,customer.getContact());
		addCustomer.execute();
		ResultSet result=addCustomer.getResultSet();
		result.next();
		int visitorID=result.getInt(1);
		    addNoOfBorrowingBooks=con.prepareStatement("insert into noofborrowingbooks(visitor_id,no_of_books) values(?,?)");
		    addNoOfBorrowingBooks.setInt(1,visitorID);
		    addNoOfBorrowingBooks.setInt(2,0);
		    addNoOfBorrowingBooks.executeUpdate();
		return visitorID;
	   }catch(SQLException e){  System.out.println("No adding customer..."+e);}  	
      return -1;	    
  }
  public static int addAdmin(Admin admin) 
  {
        try{
                 addAdmin=con.prepareStatement("insert into admin(name,username,password,contact) values(?,?,?,?) returning admin_id");
		addAdmin.setString(1,admin.getAdminName());
		addAdmin.setString(2,admin.getUserID());
		addAdmin.setString(3,admin.getPassword());
		addAdmin.setLong(4,admin.getContact());
		addAdmin.execute();
		ResultSet result=addAdmin.getResultSet();
		result.next();
		int adminid=result.getInt(1); 
		return adminid;
	   }catch(SQLException e){  System.out.println("No adding admin...");}  	
      return -1;	    
  }
  public static int addCategory(String name) 
  {
        try{
                addCategory=con.prepareStatement("insert into category(category) values(?) returning id");
		addCategory.setString(1,name);
		addCategory.execute();
		ResultSet result=addCategory.getResultSet();
		result.next();
		return result.getInt(1);
	   }catch(SQLException e){  System.out.println("No adding category...");}  	
      return -1;	    
  }
  public static boolean createTable(String query) 
  {
        try{
                createTable=con.prepareStatement(query);
		        createTable.execute();
		return true;
	   }catch(SQLException e){  System.out.println("No create table..."+e);}  	
      return false;	    
  }
  public static Book searchBook(int bookid) 
  {
        try{
                searchBook=con.prepareStatement("select book_id,book_name,author,price,published_date,publisher,language,copies  from books where book_id=?"); 
		searchBook.setInt(1,bookid);
		ResultSet result=searchBook.executeQuery();
		result.next();
		return new Book(result.getInt(1),result.getString(2),result.getString(3),result.getInt(4),result.getDate(5),result.getString(6),result.getString(7),result.getInt(8),getNoOfIssueBooks(result.getInt(1)));
	   }catch(SQLException e){  System.out.println("No book** search...");}  	
      return null;	
  }
  public static ArrayList getAllCategories()
  {
    ArrayList<Category>categories= new ArrayList();
         try{  
                 getAllCategory=con.prepareStatement("select id,category from category");
		     ResultSet result=getAllCategory.executeQuery(); 
	       while(result.next())
	       {
	          categories.add(new Category(result.getInt(1),result.getString(2)));
	          
	       }
	   }catch(Exception e){  System.out.println("not display all category...");}    
       return categories;	   
  }
  public static String searchCategory(String name) 
  {
           try{
               searchCategory=con.prepareStatement("select category.category from books join category on books.category_id=category.id where books.book_name=?");
		searchCategory.setString(1,name);
		ResultSet result=searchCategory.executeQuery();
		result.next();
		return ""+result.getString(1);
	   }catch(SQLException e){  System.out.println("No books...");}  	
      return "";
  }
  public static ArrayList getBook(int category)
  {
    ArrayList<Book>books= new ArrayList();
         try{  
                 getBook=con.prepareStatement("select book_id,book_name,author,price,published_date,publisher,language,copies from books where category_id=?");
                     getBook.setInt(1,category);
		     ResultSet result=getBook.executeQuery(); 
	       while(result.next())
	       {
	          books.add(new Book(result.getInt(1),result.getString(2),result.getString(3),result.getInt(4),result.getDate(5),result.getString(6),result.getString(7),result.getInt(8),getNoOfIssueBooks(result.getInt(1))));
	       }
	   }catch(Exception e){  System.out.println("No book...");}    
       return books;	   
  }
 
  public static ArrayList getAllBooks()
  {
    ArrayList<Book>books= new ArrayList();
         try{  
                 getBooks=con.prepareStatement("select book_id,book_name,author,price,published_date,publisher,language,copies from books");
		     ResultSet result=getBooks.executeQuery(); 
	       while(result.next())
	       {
	          books.add(new Book(result.getInt(1),result.getString(2),result.getString(3),result.getInt(4),result.getDate(5),result.getString(6),result.getString(7),result.getInt(8),getNoOfIssueBooks(result.getInt(1))));
	          
	       }
	   }catch(Exception e){  System.out.println("not get all books...");}    
       return books;	   
  }
  public static int getNoOfIssueBooks(int book_ID)
  {                       System.out.println("YES "+book_ID);
       try{
               getNoOfIssueBooks=con.prepareStatement("select count(id) from bookCopies where book_id=? and isavailable='false'");
           getNoOfIssueBooks.setInt(1,book_ID);
          ResultSet result= getNoOfIssueBooks.executeQuery();
          result.next();
          return result.getInt(1);
       }catch(Exception e){System.out.println("no book-category mapping"+e);}
       return -1;
  }
 
  public static boolean borrowing(int visitor_ID,int book_ID,int copy_ID,java.util.Date date)
  {     
      
       try{
       java.sql.Date sdate=new java.sql.Date(date.getTime());
             borrowing=con.prepareStatement("insert into borrowersDetails(visitor_id,book_id,borrowing_date,fine,copy_id) values(?,?,?,?,?)");
           borrowing.setInt(1,visitor_ID);                      
           borrowing.setInt(2,book_ID);                             
           borrowing.setDate(3,sdate);                                                      
           borrowing.setDouble(4,0.0);   
           borrowing.setDouble(5,copy_ID);                              
                updateCopies=con.prepareStatement("update bookcopies set isavailable=? where book_id=? and id=?");
                updateCopies.setBoolean(1,false);
                updateCopies.setInt(2,book_ID);
                updateCopies.setInt(3,copy_ID);     
                        getNoOfborrowingBooks=con.prepareStatement("select no_of_books from noofborrowingbooks where visitor_id=?");
		       getNoOfborrowingBooks.setInt(1,visitor_ID);
		       ResultSet result= getNoOfborrowingBooks.executeQuery();
		       result.next();
		       int nums=result.getInt(1);   
		       if(nums>=5)
		       {
		           System.out.println("not Allowed!");
		           return false;
		       }
		           updateborrowingBooks=con.prepareStatement("update noofborrowingbooks set no_of_books=? where visitor_id=?");
		          updateborrowingBooks.setInt(1,nums+1);
		          updateborrowingBooks.setInt(2,visitor_ID) ;  
		          updateborrowingBooks.executeUpdate();     
		          updateCopies.executeUpdate();  
		          borrowing.executeUpdate();       
           return true;
       }catch(Exception e){System.out.println("no borrowing"+e);}
       return false;
  }
  public static boolean updateBorrowers(int visitor_ID,int book_ID,int copy_ID,java.util.Date date,double fine)
  {
                                                          
       try{
       java.sql.Date sdate=new java.sql.Date(date.getTime());   
        updateBorrowers=con.prepareStatement("update borrowersdetails set return_date=? , fine=? where visitor_id=? and book_id=?  and copy_id=?");                  
           updateBorrowers.setDate(1,sdate);                        
           updateBorrowers.setDouble(2,fine);                             
           updateBorrowers.setInt(3,visitor_ID);                           
           updateBorrowers.setInt(4,book_ID);   
           updateBorrowers.setInt(5,copy_ID);                         
           updateBorrowers.executeUpdate(); 
                updateCopies=con.prepareStatement("update bookcopies set isavailable=? where book_id=? and id=?");
                updateCopies.setBoolean(1,true);
                updateCopies.setInt(2,book_ID);
                updateCopies.setInt(3,copy_ID);
                 
                        getNoOfborrowingBooks=con.prepareStatement("select no_of_books from noofborrowingbooks where visitor_id=?");                                    
		       getNoOfborrowingBooks.setInt(1,visitor_ID);
		       ResultSet result= getNoOfborrowingBooks.executeQuery();
		       result.next();
		       int nums=result.getInt(1);  System.out.println(nums+"/// ");
		       if(nums==0)
		       {
		           return true;
		       }
		           updateborrowingBooks=con.prepareStatement("update noofborrowingbooks set no_of_books=? where visitor_id=?");
		          updateborrowingBooks.setInt(1,nums-1);
		          updateborrowingBooks.setInt(2,visitor_ID);
		          updateborrowingBooks.executeUpdate();      
		          updateCopies.executeUpdate();
           return true;
       }catch(Exception e){System.out.println("no update borrowing"+e);}
       return false;
  }
  public static int getAdmin(String username,String password)
  {
     try{
            getAdmin=con.prepareStatement("select admin_id from admin where username=? and password=?");
           getAdmin.setString(1,username);
           getAdmin.setString(2,password);
           ResultSet result=getAdmin.executeQuery();
           System.out.println(" admin"+result.next());
           return result.getInt(1);
       }catch(Exception e){System.out.println("no admin");}
       return -1;
  }
  public static int getCustomer(String username,String password)
  {
     try{
           getCustomer=con.prepareStatement("select visitor_id from visitors where username=? and password=?");
           getCustomer.setString(1,username);
           getCustomer.setString(2,password);
           ResultSet result=getCustomer.executeQuery();
           result.next();
           return result.getInt(1);
       }catch(Exception e){System.out.println("no visiter");}
       return -1;
  }
  public static java.util.Date checkBorrower(int visitorID,int bookID) 
  {
           try{
                checkBorrower=con.prepareStatement("select borrowing_date from borrowersdetails where visitor_id=? and book_id=? and return_date is null");
		checkBorrower.setInt(1,visitorID);
		checkBorrower.setInt(2,bookID);
		ResultSet result=checkBorrower.executeQuery();
		result.next();
		return result.getDate(1);
	   }catch(SQLException e){  System.out.println("your not  borrowing this book...");}  	
      return null;
  }
  public static int noOfMembers() 
  {
           try{
               
		  members=con.prepareStatement("select count(visitor_id) from visitors");
		ResultSet result=members.executeQuery();
		result.next();
		return result.getInt(1);
	   }catch(SQLException e){  System.out.println("no visitors...");}  	
      return 0;
  }
  public static double sumOfFines() 
  {
           try{
               
		fines=con.prepareStatement("select sum(fine) from borrowersdetails");
		ResultSet result=fines.executeQuery();
		result.next();
		return result.getDouble(1);
	   }catch(SQLException e){  System.out.println("no fine...");}  	
      return 0;
  }
  public static boolean addNewExistingBook(String name,String author, String language,int copy) 
  {
           try{
               
		exist=con.prepareStatement("select book_id from books where book_name=? and author=? and language=?");
		exist.setString(1,name);
		exist.setString(2,author);
		exist.setString(3,language);
		ResultSet result=exist.executeQuery();
		result.next();
		Book book= searchBook(result.getInt(1)); 
		  int total=book.getCopies()+book.getIssue();
		        existbook=con.prepareStatement("update books set copies=? where book_id=?");
			existbook.setInt(1,book.getCopies()+copy);
			existbook.setInt(2,book.getBookID());
			    addCopies(book.getBookID(),copy);
			existbook.executeUpdate();
		return true;	
	   }catch(SQLException e){  System.out.println("");}  	
      return false;
  }
  public static ArrayList getBookCopies(int bookid) 
  {
           try{
       ArrayList<String>copies=new ArrayList();
                 getCopies=con.prepareStatement("select books.book_name, id from bookcopies join books on bookcopies.book_id=books.book_id where bookcopies.isavailable='true' and bookcopies.book_id=?");        
		getCopies.setInt(1,bookid);
		ResultSet result=getCopies.executeQuery();
		while(result.next())
		{
		   copies.add(result.getString(1)+"-"+result.getInt(2));
		}    
		return copies;
	   }catch(SQLException e){  System.out.println("no visitors...");}  	
      return null;
  }
  public static void close()
  {
      try{
             addBook.close();
	     getBooks.close();
	     getBook.close();
	     updateBook.close();
	     addCustomer.close();
	     getCustomer.close();
	     addAdmin.close();
	     getAdmin.close();
	     addCategory.close();
	     getAllCategory.close(); 
	     getCategory.close();      
	     searchBook.close();   
	     searchCategory.close();   
	     borrowing.close(); 
	     updateBorrowers.close();
	     checkBorrower.close();
	     updateBookCount.close();
	     exist.close();
             existbook.close();
             fines.close();
             members.close();
             con.close();
      }catch(Exception e){}
  }
 
}
