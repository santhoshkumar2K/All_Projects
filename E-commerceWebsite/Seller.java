package e_commerce;
public class Seller extends Person
{
    private int sellerID;
    private double earned;
     public Seller(String name,String username,String password,Address address,long contact)
     {
         super(name,username,password,address,contact);
     }
     public Seller(int id,String name,String username,String password,Address address,long contact,double earn)
     {
         super(name,username,password,address,contact);
         this.sellerID=id;
         this.earned=earn;
     }
     public int getSellerID()
     {
         return this.sellerID;
     }
     public void setSellerID(int id)
     {
         this.sellerID=id;
     }
     public double getEarned()
     {
         return this.earned;
     }
     public void setEarned(int earn)
     {
         this.earned=earn;
     }
         
}
