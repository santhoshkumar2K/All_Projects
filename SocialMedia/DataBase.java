package instagram;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Properties;

public class DataBase {

	private static FileInputStream file;
	private static Properties property=new Properties();
	private static Connection con;
static {
          try{
              file=new FileInputStream("//home//zoho//santhosh//finalProjects//SocialMedia//MyDatabase.properties");
               property.load(file);
          }catch(IOException e){}
	try {
	   con =DriverManager.getConnection(property.getProperty("url"), property.getProperty("username"), property.getProperty("password"));
	}catch(Exception e) {}
}

public static User checkUser(String userName,String password) {
            PreparedStatement pr=null;
	  try {
	        pr=con.prepareStatement("select user_id,first_name,last_name,gender,dob,user_name,password,contact, account_type,followers,followings from users where user_name=? and password=?");
	       pr.setString(1, userName);
	       pr.setString(2, password);
	       ResultSet result=pr.executeQuery();
	       result.next();  
	       return new User(result.getInt(1),result.getString(2),result.getString(3),Gender.valueOf(result.getString(4)),result.getDate(5),result.getString(6),result.getString(7),result.getLong(8),AccountType.valueOf(result.getString(9)),result.getInt(10),result.getInt(11));
	  } catch(Exception e) {}  
	  finally{
	    try{
	       if(pr!=null)
	       {
	          pr.close();
	       }
	     }catch(Exception e){}
	  }
	 
   return null;
}
public static int addUser(User user) {
           PreparedStatement pr=null;
	  try {
	       pr=con.prepareStatement("insert into  users(user_name,password,first_name,last_name,gender,dob,contact,followers,followings,account_type) values(?,?,?,?,?,?,?,?,?,?) returning user_id");
	      
	       pr.setString(1, user.getUserName());
	       pr.setString(2, user.getPassword());
	       pr.setString(3, user.getFirstname());
	       pr.setString(4, user.getLastname());
	       pr.setString(5, user.getGender());
	       pr.setDate(6, new java.sql.Date(user.getDateOfBirth().getTime()));
	       pr.setLong(7, user.getContact());
	       pr.setInt(8, 0);
	       pr.setInt(9, 0);
	       pr.setString(10, user.getAccountType());
	       pr.execute();
	       ResultSet result=pr.getResultSet();
	          result.next();
	       return result.getInt(1);
	  } catch(Exception e) {}  
	  finally{
	    try{
	       if(pr!=null)
	       {
	          pr.close();
	       }
	     }catch(Exception e){}
	  }
	 
	return -1;
}
public static boolean createTable(String query)
{
         PreparedStatement pr=null;
	try {
	       pr=con.prepareStatement(query);
	       pr.execute();
	       return true;
	  } catch(Exception e) {}  
	  finally{
	    try{
	       if(pr!=null)
	       {
	          pr.close();
	       }
	    }catch(Exception e){}
	  }
	 
	return false;
}
public static int checkLikes(User user,Post post)
{
         PreparedStatement pr=null;
	try {
	       pr=con.prepareStatement("select user_id from likes where user_id=? and post_id=?");
	       pr.setInt(1, user.getId());
	       pr.setInt(2, post.getId());
	       ResultSet result =pr.executeQuery();
	       result.next();
	       return result.getInt(1);
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  }
	  
	return -1;
}
public static int addLikes(User user,Post post)
{          
         PreparedStatement pr=null;             
	try {
	       pr=con.prepareStatement("insert into likes(user_id,post_id) values(?,?)");
	       pr.setInt(1, user.getId());
	       pr.setInt(2, post.getId());
	       pr.executeUpdate();
	       return updateLikes(post);
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	return -1;
}
public static int updateLikes(Post post)
{          
         PreparedStatement pr=null;             
	try {
	       pr=con.prepareStatement("update post set likes=? where post_id=?");
	       pr.setInt(1, post.getLikes()+1);
	       pr.setInt(2, post.getId());
	       int result=pr.executeUpdate();
	     
	       return result;
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	return -1;
}

public static int addPost(Post post,User user,boolean isDraft)
{
	java.sql.Timestamp sdate =java.sql.Timestamp.valueOf(post.getPostDate());
	 PreparedStatement pr=null;
	try {
	       pr=con.prepareStatement("insert into post(post,bio,post_date,likes,viewers,user_id,isdraft) values(?,?,?,?,?,?,?)");
	       
	       pr.setString(1, post.getPost());
	       pr.setString(2,post.getBio());
	       pr.setTimestamp(3, sdate);
	       pr.setInt(4, 0);
	       pr.setInt(5, 0);
	       pr.setInt(6, user.getId());
	       pr.setBoolean(7,isDraft);
	       pr.executeUpdate();
	     
	       return 1;
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	return -1;
}
public static ArrayList<Post> getPosts(ArrayList<Integer> friendID)
{
         PreparedStatement pr=null;
         PreparedStatement pr2=null;
         LinkedHashSet<Integer> availables=new LinkedHashSet(friendID);
	ArrayList list= new ArrayList();
	try {
	    pr2=con.prepareStatement("select user_id from users where account_type='PUBLIC'");
	   ResultSet result1= pr2.executeQuery();
	    while(result1.next())
	    {
	       availables.add(result1.getInt(1));
	    }
	    System.out.println(availables+"<");
	for(int id: availables)
	{
	       pr=con.prepareStatement("select post_id,post,bio,post_date,likes,viewers,users.user_id,users.first_name,users.last_name,users.gender,users.dob,users.user_name,users.password,users.contact,users.account_type,users.followers,users.followings from post join users on post.user_id=users.user_id where post.user_id=?  and post.isdraft='false' order by post_date desc;");
	       pr.setInt(1,id);
	       ResultSet result=pr.executeQuery();
	       while(result.next())
	       {
	          list.add(new Post(result.getInt(1),result.getString(2),result.getString(3),result.getTimestamp(4).toLocalDateTime(),result.getInt(5),result.getInt(6),new User(result.getInt(7),result.getString(8),result.getString(9),Gender.valueOf(result.getString(10)),result.getDate(11),result.getString(12),result.getString(13),result.getLong(14),AccountType.valueOf(result.getString(15)),result.getInt(16),result.getInt(17))));
	       }
	  }  
	  System.out.println(list+">");
	       return list;
	  } catch(Exception e) {System.out.println(e);}
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
			  pr2.close();
		       }
		    }catch(Exception e){}
	  }  
	return null;
}
public static ArrayList getFriends(User user)
{
       PreparedStatement pr=null;
      
      ArrayList list= new ArrayList();
      
	try {
	       pr=con.prepareStatement("select friend_id from friends where user_id=? and confirm='true'");
	       pr.setInt(1,user.getId());
	       ResultSet result=pr.executeQuery();
	       while(result.next())
	       {
	          list.add(result.getInt(1));
	       }
	       return list;
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	return null;
}
public static ArrayList<Post> getOwnPosts(User user)
{
         PreparedStatement pr=null;
	ArrayList list= new ArrayList();
	try {
	       pr=con.prepareStatement("select post_id,post,bio,post_date,likes,viewers,users.user_id,users.first_name,users.last_name,users.gender,users.dob,users.user_name,users.password,users.contact,users.account_type,users.followers,users.followings from post join users on post.user_id=users.user_id where post.user_id=?;");
	       pr.setInt(1,user.getId());
	     
	       ResultSet result=pr.executeQuery();
	       while(result.next())
	       {
	          list.add(new Post(result.getInt(1),result.getString(2),result.getString(3),result.getTimestamp(4).toLocalDateTime(),result.getInt(5),result.getInt(6),new User(result.getInt(7),result.getString(8),result.getString(9),Gender.valueOf(result.getString(10)),result.getDate(11),result.getString(12),result.getString(13),result.getLong(14),AccountType.valueOf(result.getString(15)),result.getInt(16),result.getInt(17))));
	       }
	     
	       return list;
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	return null;
}

public static boolean deleteUser(String uname, String pw2) {
         PreparedStatement pr=null;
	try {
	       pr=con.prepareStatement("delete from users where user_name=? and password=?");
	       pr.setString(1, uname);
	       pr.setString(2, pw2);
	       pr.executeUpdate();
	       return true;
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	return false;
}
public static ArrayList getUsers(int id)
{
     PreparedStatement pr=null;
    ArrayList<User>friends= new ArrayList();
	try {
	       pr=con.prepareStatement("select user_id,first_name,last_name,gender,dob,user_name,password,contact,account_type,followers,followings from users where user_id<>?");
	       pr.setInt(1, id);
	       ResultSet result=pr.executeQuery();
	       while(result.next())
	       {
		  friends.add(new User(result.getInt(1),result.getString(2),result.getString(3),Gender.valueOf(result.getString(4)),result.getDate(5),result.getString(6),result.getString(7),result.getLong(8),AccountType.valueOf(result.getString(9)),result.getInt(10),result.getInt(11)));
	       }
	       return friends;
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	return null;

}
public static User updateFollowings(User user)
{
         PreparedStatement pr1=null;
        try{  
	       
		       pr1=con.prepareStatement("update  users set followings =? where user_id=?");
		       pr1.setInt(1, user.getFollowings()+1);
		       pr1.setInt(2, user.getId());
		       pr1.executeUpdate();
		     
	       
	  } catch(Exception e) {}  
	  finally{
		    try{
		       if(pr1!=null)
		       {
			  pr1.close();
		       }
		    }catch(Exception e){}
	  }
	       User c=checkUser(user.getUserName(),user.getPassword());       
	return c;
	}
public static User updateFollowers(User user)
{
         PreparedStatement pr2=null; 
	try {
		       pr2=con.prepareStatement("update  users set followers =? where user_id=?");
		       pr2.setInt(1, user.getFollowers()+1);
		       pr2.setInt(2, user.getId());
		       pr2.executeUpdate();
	      
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr2!=null)
		       {
			  pr2.close();
		       }
		    }catch(Exception e){}
	  } 
	       User c=checkUser(user.getUserName(),user.getPassword());   
	return c;
	}	
public static User addFollowings(User user,User friend)
{
         PreparedStatement pr=null;
	try {
	       pr=con.prepareStatement("insert into friends(user_id,friend_id,confirm) values(?,?,?)");
	       pr.setInt(1, user.getId());
	       pr.setInt(2, friend.getId());
	        pr.setBoolean(3, false);
	       pr.executeUpdate();
	       if(friend.getAccountType().equals("PUBLIC"))
	       {
		  
		      friend= addFollowers(friend,user);
	       }
	     
	  } catch(Exception e) {} 
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  } 
	         User c=checkUser(user.getUserName(),user.getPassword());           
	return c;
	}
public static User addFollowers(User user,User friend)
{
     PreparedStatement pr=null;
    boolean added=false;  
	try {
	      pr=con.prepareStatement("update friends set confirm='true' where user_id=? and friend_id=?");
	      pr.setInt(1,friend.getId());
	      pr.setInt(2,user.getId());
	       pr.executeUpdate();
	                user= updateFollowers(user);//B
	               friend=  updateFollowings(friend);//A
	  } catch(Exception e) {}   
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  }
	       User c=checkUser(user.getUserName(),user.getPassword());                  
	return c;
	}
public static ArrayList getMessages(User from, User to)
{
      PreparedStatement pr=null;
     ArrayList<Comment>msg= new ArrayList();
	try {
	       pr=con.prepareStatement("select comments.comment_id,comments.from_id,comments.to_id,comments.messages,comments.date,c.messages,post.post from comments join comments c on c.comment_id=comments.comment_tag join post on post.post_id=comments.post_tag where (comments.from_id=? and comments.to_id =?) or ( comments.to_id=? and comments.from_id=?) order by comments.date;");
	       pr.setInt(1, from.getId());
	       pr.setInt(2, to.getId());
	       pr.setInt(3, from.getId());
	       pr.setInt(4, to.getId());
	       ResultSet result=pr.executeQuery();
	       while(result.next())
	       {
		  msg.add(new Comment(result.getInt(1),result.getInt(2),result.getInt(3),result.getString(4),result.getDate(5),result.getString(6),result.getString(7)
		  ));
	       }
	       return msg;
	  } catch(Exception e) {}  
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  }
	return null; 
}
public static void insertDefaultID(String query)
{
     PreparedStatement pr=null;
   try{
	   pr=con.prepareStatement(query);
	   pr.executeUpdate();
   
   }catch(Exception e){}
   finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  }
}
public static boolean sendComment(User from,User to,String msg,LocalDateTime date,int commentTag,int postTag)
{
  
   java.sql.Timestamp sDate =java.sql.Timestamp.valueOf(date);
    PreparedStatement pr=null;
    try{
               pr=con.prepareStatement("insert into comments(from_id,to_id,messages,date,post_tag,comment_tag) values(?,?,?,?,?,?)");
	       pr.setInt(1, from.getId());
	       pr.setInt(2, to.getId());
	       pr.setString(3, msg);
	       pr.setTimestamp(4, sDate);
	       pr.setInt(5, postTag);
	       pr.setInt(6, commentTag);
	       pr.executeUpdate();
       return true;
    }catch(Exception e){}
    finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  }
     return false;
}
public static ArrayList getPostComments(int post_id)
{
     PreparedStatement pr=null;
    ArrayList <String> cms=new ArrayList();    
     try{
               pr=con.prepareStatement("select Users.first_name,messages, date from comments join Users on comments.from_id=users.user_id where post_tag=? order by date");
	       pr.setInt(1, post_id);
	      ResultSet result= pr.executeQuery();
	      while(result.next())
	      {
	          cms.add(result.getString(1)+": "+result.getString(2)+"("+result.getTimestamp(3).toLocalDateTime()+")"); 
	      }
       return cms;
    }catch(Exception e){}
    finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  }
     return null;
}
public static ArrayList requestList(User user,int option)
{
      PreparedStatement pr=null;
    ArrayList<User>requests= new ArrayList();
       String query="";
       if(option==1)
       {
         query="select users.user_id,users.first_name,users.last_name,users.gender,users.dob,users.user_name,users.password,users.contact,users.account_type,users.followers,users.followings from friends join users on users.user_id=friends.user_id where friends.friend_id=? and friends.confirm='false'";
       }else if(option==0)
       {
        query="select users.user_id,users.first_name,users.last_name,users.gender,users.dob,users.user_name,users.password,users.contact,users.account_type,users.followers,users.followings from friends join users on users.user_id=friends.friend_id where friends.user_id=? and friends.confirm='true'";
       }   
	try {
	       pr=con.prepareStatement(query);
	       pr.setInt(1, user.getId());
	       ResultSet result=pr.executeQuery();
	       while(result.next())
	       {
		  requests.add(new User(result.getInt(1),result.getString(2),result.getString(3),Gender.valueOf(result.getString(4)),result.getDate(5),result.getString(6),result.getString(7),result.getLong(8),AccountType.valueOf(result.getString(9)),result.getInt(10),result.getInt(11)));
	       }
	       return requests;
	  } catch(Exception e) {}  
	  finally{
		    try{
		       if(pr!=null)
		       {
			  pr.close();
		       }
		    }catch(Exception e){}
	  }
	return null;
} 
public static void close()
{
  try{
     con.close();	        
  }catch(Exception e){}
}		
}
