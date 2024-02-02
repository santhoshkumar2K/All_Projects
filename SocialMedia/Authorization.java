package instagram;
interface Authorization
{
   Object signIn(String username,String password);
   Object signUp(Object obj);
   boolean signOut(String username,String password);
 
}
