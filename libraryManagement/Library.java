package library;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
public class Library
{
   private String name;
   private Date startingDate;
   private String owner; 
   private int totalBooks;
   private int totalCategories;
   private int totalMembers;
 
   

   public Library(String name, String startingDate, String owner) {
      this.name = name;
      try{
        this.startingDate = new SimpleDateFormat("yyyy-MM-dd").parse(startingDate);
      }catch(Exception e){}
      this.owner = owner;
      this.totalBooks=0;
      this.totalCategories=0;
      this.totalMembers=0;
     
   }
    
   public String getName() {
      return name;
   }

   public Date getStartingDate() {
      return startingDate;
   }

   public String getOwner() {
      return owner;
   }

   public int getTotalBooks() {
      return totalBooks;
   }

   public void setTotalBooks(int totalBooks) {
      this.totalBooks = totalBooks;
   }

   public int getTotalCategories() {
      return totalCategories;
   }

   public void setTotalCategories(int totalCategories) {
      this.totalCategories = totalCategories;
   }

   public int getTotalMembers() {
      return totalMembers;
   }

   public void setTotalMembers(int totalMembers) {
      this.totalMembers = totalMembers;
   }
   public static int calculateFine(int days,int amount,int fine)
    {
         for(int i=1;i<=days;i++)
         {
              fine+=amount;
         }
         return fine;
    }
   public static int checkDueDate(Date taken_date,Date return_date)
    {
        if(taken_date==null || return_date==null)
        {
            return -1;
        }
         	 LocalDate first_date = LocalDate.of(taken_date.getYear()+1900, taken_date.getMonth()+1, taken_date.getDate());   
                 LocalDate second_date = LocalDate.of(return_date.getYear()+1900, return_date.getMonth()+1, return_date.getDate());
                 Period diff=Period.between(first_date,second_date);
                 int days=diff.getDays()-5;
                int fine=0;
                if(days>0)
                {
                  fine=calculateFine(days,1,fine);
                }
                 if(diff.getMonths()>0)
                {
                  fine=calculateFine(diff.getMonths(),15,fine);
                }
                 if(diff.getYears()>0)
                {
                   fine=calculateFine(diff.getYears(),500,fine);
                }  
        return fine;
    }

}
