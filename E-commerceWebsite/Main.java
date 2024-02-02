import  e_commerce.*;
public class Main
{
   static{
	   String []tables= e_commerce.Storage.tables;
	    for(int index=0;index<tables.length;index++)
	    {
	          if(!e_commerce.Database.createTable(tables[index])){System.out.println(tables[index]);}
	         
	    }   
   }
	public static void main(String[] args) {
	   try{
		 e_commerce.View view= new  e_commerce.View();
		view.login();
	      }catch(Exception e){}
	      finally{
	         e_commerce.Database.close();
	      }	
	}
}

