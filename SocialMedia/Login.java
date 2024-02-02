package instagram;
public class Login implements Authorization
{
   public Object signIn(String username,String password)
   {
      if(username.length()<8 || password.length()<8)
      {
		    return null;
      }  
     
           return  DataBase.checkUser(username,password);
   }
   public Object signUp(Object obj)
   {
        
	      if(obj instanceof User)
	      {
		 User newUser=(User)obj;
		 if(newUser.getUserName().length()<8 || newUser.getPassword().length()<8)
		 {
		    return null;
		 }     
		 if(signIn(newUser.getUserName(),newUser.getPassword())==null)
		 {
		  return DataBase.addUser(newUser);
		 }else
		 {
		   return null;
		 } 
	      } 
	      return null;  
   }
   public boolean signOut(String username,String password){return false;}


}
