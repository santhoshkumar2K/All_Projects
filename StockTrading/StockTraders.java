package stock_tradings;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.HashMap;
import java.util.Random;
public class StockTraders extends Thread{

	public  void sensex()
	{
		Random random= new Random();
		while(true)
		{
		  ArrayList<Stock>stocks=null;
		  ListIterator<Stock> stock=null;
	     	  HashMap<Stock,Integer> stock_s=Database.getAllStocks();
	     	  if(stock_s!=null)
	     	  {
	     	     stocks=new ArrayList(stock_s.keySet());
	     	     stock=stocks.listIterator();
	     	  }else{
	     	  System.out.println("not available Stocks! ");
	     	     return;
	     	  }  
	     	while(stock.hasNext())
	     	{
	     		Stock sk=stock.next();
	     		double value=sk.getRate();
	     		value=(random.nextBoolean())?value-random.nextInt(10)+1:value+random.nextInt(10)+1;
	     		if(value<=0)
	     		{
	     		   value=50;
	     		}
	     		Database.updateStockPrice(value,sk);
	     	}
	     	try {
	     		Thread.sleep(10000);
	     	}catch(Exception e) {}
		} 	
	}
	public void run()
	{
		sensex();
	}
	public static void createThread()
	{
		 StockTraders daemon=new  StockTraders();
		daemon.setDaemon(true);
		daemon.start();
	}
}
