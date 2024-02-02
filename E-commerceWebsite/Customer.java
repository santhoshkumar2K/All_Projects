package e_commerce;

public class Customer extends Person
{
    private int customerID;
   
     public Customer(String name,String username,String password,Address address,long contact)
     {
         super(name,username,password,address,contact);
     }
     public Customer(int id,String name,String username,String password,Address address,long contact)
     {
         super(name,username,password,address,contact);
         this.customerID=id;
     }
     public int getCustomerID()
     {
         return this.customerID;
     }
     public void setCustomerID(int id)
     {
         this.customerID=id;
     }
         
}

