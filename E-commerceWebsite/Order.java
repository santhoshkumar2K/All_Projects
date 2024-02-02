package e_commerce;
import java.util.Date;
public class Order
{
   private int orderID;
   private String customer;
   private Product product;
   private Date date;
   private String paymentType;
   private int noOfProducts;
   private double totalPrice;
   
   public Order(int orderID, String customer, Product product, Date date, String paymentType, int noOfProducts,double totalPrice) {
      this.orderID = orderID;
      this.customer = customer;
      this.product = product;
      this.date = date;
      this.paymentType = paymentType;
      this.noOfProducts = noOfProducts;
      this.totalPrice = totalPrice;
   }
   public Order(String customer, Product product, Date date, String paymentType, int noOfProducts, double totalPrice) {
      this.customer = customer;
      this.product = product;
      this.date = date;
      this.paymentType = paymentType;
      this.noOfProducts = noOfProducts;
      this.totalPrice = totalPrice;
   }
   public int getOrderID() {
      return orderID;
   }
   public String getCustomer() {
      return customer;
   }
   public Product getProduct() {
      return product;
   }
   public Date getDate() {
      return date;
   }
   public String getPaymentType() {
      return paymentType;
   }
   public int getNoOfProducts() {
      return noOfProducts;
   }
   public double getTotalPrice() {
      return totalPrice;
   }
  
   
}
