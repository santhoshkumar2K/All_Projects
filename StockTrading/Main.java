
import java.util.Scanner;
public class Main  {

static {
       String []tables= stock_tradings.Storage.tables;
	    for(int index=0;index<tables.length;index++)
	    {
	          if(stock_tradings.Database.createTable(tables[index])){}else{System.out.println("*** "+tables[index]);}
	         
	    } 
       }	      
	public static void main(String[] args) {
	 try{
		stock_tradings.StockTraders.createThread();
		stock_tradings.View v= new stock_tradings.View();
		Scanner scan=new Scanner(System.in);
		v.homePage(scan);
	    }catch(Exception e){}
	    finally{
	       stock_tradings.Database.close();
	    }	
   
   }

}
