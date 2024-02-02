package library;
public class Storage
{
   
   public boolean bookTable()
   {
       String query="create table if not exists books(book_id serial primary key, book_name varchar(20), author varchar(20),published_date date, publisher varchar(20),language varchar(15),copies int,price float(4),category_id int references bookcategory(id))";
       return DataBase.createTable(query);
   }
   public boolean categoryTable()
   {
       String query="create table if not exists category(id serial primary key, category varchar(15))";
       return DataBase.createTable(query);
   }
   public boolean copyOfBookTable()
   {
       String query="create table if not exists bookcopies(id serial ,book_id int references books(book_id),isavailable boolean,primary key(id,book_id))";
       return DataBase.createTable(query);
   }
   public boolean borrowersDetailsTable()
   {
       String query="create table if not exists borrowersdetails(id serial primary key,visitor_id int references visitors(visitor_id),book_id int references books(book_id),borrowing_date date,return_date date,fine float(4),copy_id int references bookcopies(id))";
       return DataBase.createTable(query);
   }
   public boolean noOfBorrowingBooksTable()
   {
       String query="create table if not exists noofborrowingbooks(id serial primary key,visitor_id int references visitors(visitor_id),no_of_books int)";
       return DataBase.createTable(query);
   }
   public boolean visitorsTable()
   {
       String query="create table if not exists visitors(visitor_id serial ,name varchar(20) ,username varchar(20),password varchar(20),contact bigint,primary key(visitor_id,username,password))";
       return DataBase.createTable(query);
   }
   public boolean adminTable()
   {
       String query="create table if not exists admin(admin_id serial,name varchar(20) ,username varchar(20),password varchar(20),contact bigint,primary key(admin_id,username,password))";
       return DataBase.createTable(query);
   }
}
