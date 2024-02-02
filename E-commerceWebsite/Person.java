package e_commerce;
public class Person
{
  private String name;
  private String userName;
  private String password;
  private Address address;
  private long contact;
    
    Person(String name,String userName,String password,Address address,long contact)
    {
         this.name=name;
         this.userName=userName;
         this.password=password;
         this.address=address;
         this.contact=contact;
    } 
     public String getName()
     {
         return this.name;
     }
     
     public String getUserName()
     {
         return this.userName;
     }
     public String getPassword()
     {
         return this.password;
     }
     public Address getAddress()
     {
         return this.address;
     }
     public long getContact()
     {
         return this.contact;
     }
     public void setName(String name) {
        this.name = name;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public void setContact(long contact) {
        this.contact = contact;
    }
     
}     
