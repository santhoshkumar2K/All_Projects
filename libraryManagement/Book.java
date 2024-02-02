package library;
import java.util.Date;
import java.util.ArrayList;
public class Book
{
   private int bookID;
   private String bookName;
   private String author;
   private double price;
   private Date publishedDate;
   private String publisher;
   private String language;
   private int copies;
   private int issue;

     public Book(String name,String author,double price,Date publishedDate,String publisher,String language)
     {
         this.bookName=name;
         this.author=author;
         this.price=price;
         this.publishedDate=publishedDate;
         this.publisher=publisher;
         this.language=language;
     }
     public Book(int id,String name,String author,double price,Date publishedDate,String publisher,String language,int copies,int issue)
     {
         this.bookID=id;
         this.bookName=name;
         this.author=author;
         this.price=price;
         this.publishedDate=publishedDate;
         this.publisher=publisher;
         this.language=language;
         this.copies=copies;
         this.issue=issue;
     }
      
public int getBookID() {
    return bookID;
}
public String getBookName() {
    return bookName;
}
public String getAuthor() {
    return author;
}
public double getPrice() {
    return price;
}
public Date getPublishedDate() {
    return publishedDate;
}
public String getPublisher() {
    return publisher;
}
public String getLanguage() {
    return language;
}
public int getCopies() {
    return copies;
}
public int getIssue() {
    return issue;
}
 public static  boolean displayCategory(ArrayList<Category>categories)
    {
        if(categories.size()==0)
        {
            return false;
        }
        int i=1;
          for(Category category: categories)
          {
              System.out.println(i+++"."+category.getCategory());
          }
          return true;
    }
    public static  boolean displayBooks(ArrayList<Book>books)
    {
        if(books.size()==0)
        {
            return false;
        }  int i=1;
          for(Book book: books)
          {
              System.out.println(i+++" ."+book);
          }
          return true;
    }
    public static  boolean displaySection(ArrayList<String>sections)
    {
        if(sections.size()==0)
        {
            return false;
        }
          for(String section: sections)
          {
             
              System.out.println(section);
          }
          return true;
    }
    
  public String toString()
  {
       return this.bookID+". ["+this.bookName+"] by "+this.author+", publisher: "+this.publisher+"("+this.publishedDate+") "+this.language+" = "+this.copies+" copies.";
  }  
     

}
