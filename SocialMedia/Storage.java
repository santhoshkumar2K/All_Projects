package instagram;
public class Storage
{
public static final void createUserTable()
{
String query="create table if not exists users(user_id serial unique not null , user_name varchar(20) unique,password varchar(20) unique, first_name varchar(20),last_name varchar(20),gender varchar(10), dob date, contact bigint, followers int ,followings int , account_type varchar(10),primary key(user_name,password))";
      DataBase.createTable(query);

}
public static final void createPostTable()
{
String query="create table if not exists post(post_id serial primary key , post varchar(50), bio varchar(20), post_date date, likes int ,viewers int,user_id int references users(user_id),isdraft boolean)";
      DataBase.createTable(query);
}
public static final void createFriendTable()
{
String query="create table if not exists friends(id serial unique , user_id int references users(user_id), friend_id int references users(user_id), confirm boolean ,primary key(user_id,friend_id))";
    DataBase.createTable(query);
}
public static final void createLikeTable()
{
String query="create table if not exists likes(id serial unique, user_id int references users(user_id) ,post_id int references post,primary key(user_id,post_id))";

   DataBase.createTable(query);
}
public static final void createCommentTable()
{
String query="create table if not exists comments(comment_id serial primary key, from_id int references users (user_id),to_id int references users(user_id),messages varchar(100),post_tag int references post(post_id),comment_tag int references comments(comment_id),date date)";

    DataBase.createTable(query);
}
public static final void defaultCommentAndPostSet()
{
  String query1="insert into comments(comment_id,messages)  select 0,'null' where not exists (select comment_id from comments where comment_id=0);";
    DataBase.insertDefaultID(query1);
 String query2="insert into post(post_id,post)  select 0,'null' where not exists (select post_id from post where post_id=0);";
    DataBase.insertDefaultID(query2);   
    
}


}
