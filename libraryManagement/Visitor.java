package library;
public class Visitor
{
    private int visitorID;
    private String visitorName;
    private String userID;
    private String password;
    private long contact;    
    
    public Visitor(String name,String userID,String password,long contact)
     {
         this.visitorName=name;
         this.userID=userID;
         this.password=password;
         this.contact=contact;
     }
     public Visitor(int id,String name,String userID,String password,long contact)
     {
         this.visitorID=id;
         this.visitorName=name;
         this.userID=userID;
         this.password=password;
         this.contact=contact;
     }
     public int getVisitorID() {
        return visitorID;
    }
    public String getVisitorName() {
        return visitorName;
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

