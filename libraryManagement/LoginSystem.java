package library;
abstract class LoginSystem
{
   
    public static int signin(String userName,String password,int option)
    {
         if(userName.length()<8 || password.length()<8){
            return -1;
         }   
         ////regex
         if(option==1){
            return  DataBase.getAdmin(userName,password);
         }else{
            return DataBase.getCustomer(userName,password);
         }
    }
    public static boolean signup(String firstName,String lastName,String userName,String password,long contact,int option)
    {
         if(userName.length()<8 || password.length()<8){
            return false;
         }   
         ////regex
         if(option==1){
               if( DataBase.getAdmin(userName,password)!=-1)
               {
                   return false;
               }
            DataBase.addAdmin(new Admin(firstName+" "+lastName,userName,password,contact));
            return true;
         }else
         {
              if(DataBase.getCustomer(userName,password)!=-1)
              {
                  return false;
              }
            DataBase.addCustomer(new Visitor(firstName+" "+lastName,userName,password,contact));
            return true;
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
