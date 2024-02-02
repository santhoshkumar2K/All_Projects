package e_commerce;
abstract class LoginSystem
{
   
    public static Person signin(String userName,String password,int option)
    {
         if(userName.length()<8 || password.length()<8){
            return null;
         }   
         ////regex
         if(option==1){
            return  Database.getSeller(userName,password);
         }else{
            return Database.getCustomer(userName,password);
         }
         
    }
    public static  int signup(String name,String userName,String password,long contact,Address address,int option)
    {
         if(userName.length()<8 || password.length()<8){
            return -1;
         }   
         ////regex
         if(option==1){
               
            return Database.addSeller(new Seller(name,userName,password,address,contact));
         }else
         {
              
            int i=Database.addCustomer(new Customer(name,userName,password,address,contact)); System.out.println(i+" i");
            return i;
         }   
    }
    public static boolean signout(String userName,String password)
    {
         if(userName.length()<8){
            return false;
         }   
         ////regects
            return true;
    }  

}
