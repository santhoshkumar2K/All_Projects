package e_commerce;
import java.util.Date;
import java.util.ArrayList;
public class Product implements Comparable
{
    private int productID;
    private String productName;
    private String description;
    private double price;
    private int GST;
  

    public Product(String name,String description,double price,int GST)
     {
         this.productName=name;
         this.description=description;
         this.price=price;
         this.GST=GST;
     }
     public Product(int id, String name,String description,double price,int GST)
     {
         this.productID=id;
         this.productName=name;
         this.description=description;
         this.price=price;  
         this.GST=GST;
     }
     public int getProductID()
     {
         return this.productID;
     }
     public void setProductID(int id)
     {
         this.productID=id;
     }
     public String getProductName()
     {
         return this.productName;
     }
     public void setProductName(String name)
     {
         this.productName=name;
     }
     public String getDescription()
     {
         return this.description;
     }
     public void setDescription(String description)
     {
         this.description=description;
     }
     public double getPrice()
     {
         return this.price;
     }
     public void setPrice(double price)
     {
         this.price=price;
     }
     public int getGST() 
     {
        return GST;
     }
    public void setGST(int GST) 
    {
        this.GST=GST;
    }
     public int compareTo(Object obj)
     {
          Product product=(Product)obj;
       
        return this.productName.compareTo(product.productName);
     }
     public String toString()
     {
        return "product: [ "+this.getProductName()+" ]\n* "+this.getDescription()+"\nprice: $"+this.getPrice();
     }
     /*public String calculateRating(Product product)
     {
         int rating=product.getRating();
     }*/
     
}

