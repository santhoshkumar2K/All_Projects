package library;
public class Admin
{
    private int adminID;
    private String adminName;
    private String userID;
    private String password;
    private long contact;
     
   
    public Admin(String name,String userID,String password,long contact)
     {
         this.adminName=name;
         this.userID=userID;
         this.password=password;
         this.contact=contact;
     }
     public Admin(int id,String name,String userID,String password,long contact)
     {
         this.adminID=id;
         this.adminName=name;
         this.userID=userID;
         this.password=password;
         this.contact=contact;
     }
      public int getAdminID() {
        return adminID;
    }
    public String getAdminName() {
        return adminName;
    }
    public String getUserID() {
        return userID;
    }
    public String getPassword() {
        return password;
    }
    public long getContact() {
        return contact;
    }
}

