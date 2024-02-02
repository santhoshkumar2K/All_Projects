package stock_tradings;
import java.util.Date;
public class HoldingStocks
{
   private int holding_id;
   private Customer customer;
   private Stock stock;
   private Date date;
   private double purchaseRate;
   private int quantity;
   
   public HoldingStocks(int holding_id, Customer customer, Stock stock, Date date, double purchaseRate, int quantity) {
      this.holding_id = holding_id;
      this.customer = customer;
      this.stock = stock;
      this.date = date;
      this.purchaseRate = purchaseRate;
      this.quantity = quantity;
   }
   public int getHolding_id() {
      return holding_id;
   }
   public Customer getCustomer() {
      return customer;
   }
   public Stock getStock() {
      return stock;
   }
   public Date getDate() {
      return date;
   }
   public double getPurchaseRate() {
      return purchaseRate;
   }
   public int getQuantity() {
      return quantity;
   }

}
