package library;
public class Category
{
  private int categoryID;
  private String category;
  private int noOfBooks;
  Category(int id,String category)
  {
     this.categoryID=id;
     this.category=category;
   
  }
  public int getCategoryID()
  {
      return categoryID;
  }
  public String getCategory()
  {
      return category;
  }
  public int getNoOfBooks()
  {
      return noOfBooks;
  }
}
