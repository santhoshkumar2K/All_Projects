package stock_tradings;
abstract class Login 
{
   public static Object signIn(String username,String password,int opt)
   {
   
      if(username.length()<8 || password.length()<8)
      {
		    return null;
      }  
        if(opt==0)
        {
             return  Database.checkCompany(username,password);
        }else if(opt>0){
     
           return  Database.checkCustomer(username,password);
        } else{
           return null;
        }  
   }
   public static Object signUp(Object obj)
   {
        
	      if(obj instanceof Customer)
	      {
		 Customer newUser=(Customer)obj;
		 if(newUser.getUserName().length()<8 || newUser.getPassword().length()<8)
		 {
		    return null;
		 }     
		 if(signIn(newUser.getUserName(),newUser.getPassword(),1)==null)
		 {
		  return Database.addCustomer(newUser);
		 }else
		 {
		   return null;
		 } 
	      } else if(obj instanceof Company)
	      {
	         Company newCompany=(Company)obj;
		 if( newCompany.getUsername().length()<8 ||  newCompany.getPassword().length()<8)
		 {
		    return null;
		 }     
		 if(signIn( newCompany.getUsername(), newCompany.getPassword(),0)==null)
		 {
		  return Database.addCompany( newCompany);
		 }else
		 {
		   return null;
		 } 
	      }
	      return null;  
   }
   public boolean signOut(String username,String password){return false;}


}
