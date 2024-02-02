
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
public class Main
{
   static 
   {
       library.Storage tables = new library.Storage();
       tables.bookTable();
	   tables.adminTable();
	   tables.visitorsTable();
	   tables.copyOfBookTable();
	   tables.borrowersDetailsTable();
	   tables.categoryTable();
	   tables.noOfBorrowingBooksTable();
	   
   }
	public static void main(String[] args) { 
	     try{
		 library.View viewObject= new library.View();
		 library.Library newLibrary=viewObject.createLibrary();
		 viewObject.loginAdmin(newLibrary);
		}catch(Exception e){}
		finally{
		  library.DataBase.close();
		} 

	}
}
