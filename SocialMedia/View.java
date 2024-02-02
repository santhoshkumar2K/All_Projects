package instagram;
import java.util.Scanner;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.ArrayList;
import java.time.LocalDateTime;  
import java.text.ParseException;
import java.text.SimpleDateFormat;
public class View {
 private static Scanner scan= new Scanner(System.in);
 private static Login login= new Login();

public void authentication()
{
     int option=1;
	     while(option>0)
	     {
	        try{
				System.out.println("__________𝕀𝕟𝕤𝕥𝕒𝕘𝕣𝕒𝕞____________");
				System.out.println("1. sign in");
				System.out.println("2. sign up");
				System.out.println("____________________________");
				option =scan.nextInt();
			   switch(option)
			  {
			     case 1:User user=signin();
				      if(user==null)
				      {
				        break;
				      }
				       System.out.println("      ✅   ");
				       profile(user);
				break;
			     case 2:
				      while(!(signup()!=-1))
				      {  
					 System.out.println("try again..");
				      }
					 System.out.println("success");
					 
				break;
			     
			    }
		   }catch(Exception e)
		    {
			    scan.next();
			    System.out.println("invalied input...");
			  
		    }  	    
	      }

}
 public  void profile(User currentUser) {
           String gender=(currentUser.getGender().equals("MALE"))?"🧒":"👧";
		int option=1;
		while(option>0)
		{
		   try{
				System.out.println("__________WELLCOME__________");
				System.out.println("1."+gender+" view profile");
				System.out.println("2.👥 friends");
				System.out.println("3.💌 posts");
				System.out.println("4.💬 chat");
				System.out.println("____________________________");
				option =scan.nextInt();
				switch(option)
				{
				     case 1:currentUser=DataBase.checkUser(currentUser.getUserName(),currentUser.getPassword());
					     displayProfile(currentUser);
					  break;
				     case 2:friends(currentUser);            
					  break;
				     case 3:post(currentUser);
					  break;
				     case 4:msg(currentUser);
					  break;
				}
			}catch(InputMismatchException e)
		        {
			    scan.next();
			    System.out.println("invalied input...");
		        }  	
		}

 }
 public User signin()
 {
     int count=1;
                            while(count<=3)
		           {
			     System.out.println("enter username(8 characters): ");
			       String uname=scan.next();
			     System.out.println("enter password(8 characters): ");
			       String pw=scan.next();
			       User user=(User) login.signIn(uname,pw);
			       if(user!=null)
			       {
				
				return user;
			       }
			       System.out.println("sign up first..! ("+count+++")");
			    } 
			 return null;     
 }
 public int signup()
 {
     int count=1;
                             System.out.println("enter first name: ");
			       String fname=scan.next();
			     System.out.println("enter last name: ");
			       String lname=scan.next();
			     System.out.println("enter Gender(1.Male or 2.Female): ");
			     int gen=scan.nextInt();
			     if(!(gen>0 && gen<3))
			     {    
			         System.out.println("Invalied input!");
			         return -1;
			     } 
			       Gender gender=(gen==1)?Gender.MALE: Gender.FEMALE;
			     System.out.println("enter Date Of Birth(yyyy-MM-dd): ");
			       String dob=scan.next();
			       Date date=null;
			       try{
			           date=new SimpleDateFormat("yyyy-MM-dd").parse(dob);  
			          }catch(Exception e){}    
			     System.out.println("enter username(8 characters): ");
			       String u_name=scan.next();
			     System.out.println("enter password (8 characters): ");
			       String u_pw=scan.next();
			     System.out.println("enter contact: ");
			       long contact=scan.nextLong();
			     System.out.println("AccountType (1.public or 2.private): ");
			        int type=scan.nextInt(); 
			     if(!(type>0 && type<3))
			     {    
			         System.out.println("Invalied input!");
			         return -1;
			     }   
			        AccountType account=(type==1)?AccountType.PUBLIC:AccountType.PRIVATE; 
			       User newUser=new User(fname,lname,gender,date,u_name,u_pw,contact,account);
			       Object obj=login.signUp(newUser);
			       if(obj!=null)
			       {
			          return (Integer)obj;
			       }
                               return -1;
 }
 public  void msg(User currentUser) {
		int option=1;
		while(option>0)
		{
		  try{
				System.out.println("__________Messages__________");
				System.out.println("1.write message");
				System.out.println("0.exit");
				System.out.println("____________________________");
				option =scan.nextInt();
				if(option==1)
				{
					   writeMessage(currentUser);
				}else{
				   break;
				}	
			
		  }catch(InputMismatchException e)
		    {
			    scan.next();
			    System.out.println("invalied input...");
		    }  	 
			
		}
		
       
 }
 private void writeMessage(User currentUser)
 {
     ArrayList<User> users=DataBase.getUsers(currentUser.getId());
				    if(!displayOthers(users,currentUser.getId()))
				    {
				       System.out.println(" 🤷‍♂️Nobody is here");
				       return;
				    }
					System.out.println("0 for exit");
				       System.out.println("select for adding friend:");
				       int input=scan.nextInt();
				       if(input<=0 || input>users.size())
				       {
					   return;
				       }
				    User frd=users.get(input-1);
				    chat(currentUser,frd,0,0);
 }
 public static void chat(User from, User to,int comment_Tag,int post_Tag)
 {
    
     int opt=1;
   while(opt>0)
   {  
       ArrayList<Comment>messages=DataBase.getMessages(from,to);
       String hold="";
       int line=1; 
    System.out.println("--------------------------------------------------------------------------------");
     for(Comment msg: messages)
     {
		  String printDate="["+(msg.date+"")+"]";
	    
	       if(!hold.contains(printDate))
	       {
		   System.out.println(" - - - - - - - - - - - - - - - - - ("+msg.date+") - - - - - - - - - - - - - -\n");
		   hold=printDate;
	       }
		if(msg.from_id==to.getId())
		{
		   if(!msg.commentTag.equals("null"))
		    {
		       System.out.println("                                           [comment: "+msg.commentTag+"]");
		    }else if(!msg.postTag.equals("null"))
		    {
		       System.out.println("                                           [post: "+msg.postTag+"]");
		    }
		    System.out.println(line+++".                                   <-["+to.getFirstname()+" "+to.getLastname()+"]: "+msg.message+"\n");
		}else
		{
		    if(!msg.commentTag.equals("null"))
		    {
		         System.out.println("   [comment: "+msg.commentTag+"]");
		    }else if(!msg.postTag.equals("null"))
		    {
		       System.out.println("   [post: "+msg.postTag+"]");
		    }
		   System.out.println(line+++". [you]: "+msg.message+"\n");
		}
	       	
     }

      System.out.println("----------------");
      System.out.println("| 0. exit:      |");
      System.out.println("| 1. chat:      |");
      System.out.println("| 2. refresh:   |");
      System.out.println("| 3. replay:    |");
      System.out.println("-----------------");
      opt=scan.nextInt();
          scan.nextLine();
      if(opt==1)
      {
          LocalDateTime date=LocalDateTime.now();
       System.out.println("You :");
          String message=scan.nextLine();
       System.out.println(DataBase.sendComment(from,to,message,date,post_Tag,comment_Tag));
       post_Tag=0;comment_Tag=0;
      }else if(opt==2)
      {
        continue;
      }else if(opt==3)
      {
         System.out.println("Enter comment line for tag:");
          int tag=scan.nextInt();  
          if(tag<=0 || tag>messages.size())
          {
             System.out.println("invalied...");
             break;
          }  
         chat(from,to,0,messages.get(tag-1).comment_id);
         return;
      }else if(opt==0)
      {
         return;
      }
      
   }  
     
 }
 public  void friends(User currentUser) {
		int option=1;
		while(option>0)
		{
		     try{
					System.out.println("__________WELLCOME__________");
					System.out.println("1.👤 add followings");
					System.out.println("2.🤝 request followers");
					System.out.println("3.👥 view followers");
					System.out.println("____________________________");
					option =scan.nextInt();
				switch(option)
				{
				     case 1:
					    ArrayList<User> users=DataBase.getUsers(currentUser.getId());
					    if(!displayOthers(users,currentUser.getId()))
					    {
					       System.out.println(" 🤷‍♂️Nobody is here");
					       break;
					    }
						System.out.println("0 for exit");
					       System.out.println("select for adding friend:");
					       int input=scan.nextInt();
					       if(input<=0 || input>users.size() )
					       {
						   break;
					       }
					    User frd=users.get(input-1);
					    
					    currentUser= DataBase.addFollowings(currentUser,frd);
					     
					  break;
				     case 2:ArrayList<User>requests=DataBase.requestList(currentUser,1);
					    if(!displayFriends(requests))
					    {
					       System.out.println(" 🤷‍♂️Nobody is here");
					       break;
					    }
					       System.out.println("select for friend for confirm:");
					    User requestFriend =requests.get(scan.nextInt()-1);     
					    currentUser=DataBase.addFollowers(currentUser,requestFriend);   
					  break;
				     case 3:
					    ArrayList<User> friend_s=DataBase.requestList(currentUser,0);
					    if(friend_s==null || !displayFriends(friend_s))
					    {
					       System.out.println(" 🤷‍♂️Nobody is here");
					       break;
					    }
					  break;
				    
				}
			}catch(InputMismatchException e)
		        {
			    scan.next();
			    System.out.println("invalied input...");
		        }  	
		}
		
       
 }
 public  void post(User currentUser) {
		int option=1;
		while(option>0)
		{
		      try{
					System.out.println("__________WELLCOME__________");
					System.out.println("1. view posts");
					System.out.println("2. create post");
					System.out.println("3. view myself");
					System.out.println("____________________________");
					option =scan.nextInt();
				switch(option)
				{
				     case 1:ArrayList<Integer>friendid=DataBase.getFriends(currentUser);
					    friendid.add(currentUser.getId());
					     ArrayList<Post> posts=DataBase.getPosts(friendid);
					     if(posts==null ||!displayPosts(currentUser,posts))
					     {
						System.out.println(" 🤷‍♂️ Nobody is here");
					       break;
					     }
					  break;
				     case 2: Post newPost= createPost();
					      System.out.println("1. Post this! \n2. Draft this post):");
					      int opt=scan.nextInt();
					      boolean isDraft=false;
					      if(newPost==null || !(opt>0 && opt<3))
					      {
						  break;
					      }
					      if(opt==2)
					      {
						   isDraft=true;
					      }
					      newPost.addPost(currentUser,isDraft);
					      Post.posts.add(newPost);
					      
					  break;
				     case 3:ArrayList post_s=DataBase.getOwnPosts(currentUser);
					     if(!displayPosts(currentUser,post_s))
					     {
						System.out.println(" 🤷‍♂️ Nobody is here");
					       break;
					     }
					  break;
				  
				}
			}catch(InputMismatchException e)
		       {
			    scan.next();
			    System.out.println("invalied input...");
		       }  	
		}
	
 }
private static boolean displayOthers(ArrayList<User> users,int id)
{
        if(users.size()==0)
        {
           return false;
        }
	int num=1;
	  for(User user: users)
	  {
		  if(user.getId()!=id)
		  {
		    System.out.println(num+++". "+user.getFirstname()+" "+user.getLastname());
		  }
	 
	   }
       return true; 
}
private static boolean displayPosts( User user,ArrayList<Post> posts)
{
	if(posts.size()==0)
	{
	   return false;
	}
	   int lk;
	  for(Post post: posts)
	  {     lk=0;
	       while(true)
	       {
		    System.out.println("-------------------------------------------------------------------------");
		    System.out.println("["+post.getUser().getFirstname()+" "+post.getUser().getLastname()+"] \n                   "+post.getBio()+"\n                              "+post.getPost());
		 
		    System.out.println(((DataBase.checkLikes(user,post)>0)?"1. ❤️️":"1. 💛")+"   "+(post.getLikes()+lk)+" like");
		    System.out.println(" 2. 💬 comment");
		    System.out.println(" 3.next");
		    System.out.println(" 4.exit");
		    int like=scan.nextInt();
		    if(like==1)
		    {
			int i=DataBase.addLikes(user,post);
			lk=(i>0)?lk+1:lk;
			
		       System.out.println((i>0)?"❤️️   Like": "already Liked💓");
		    }
			    if(like==2)
			    {
			        while(true)
			        {
			           displayPostComments(post.getId());
			           System.out.println(" 0.exit");
			         
				  chat(user,post.getUser(),post.getId(),0);
				  break;
			        }	
			    }
			    if(like==3)
			    {
			       break;
			    }else if(like==4)
			    {
				return true;
			    }
		    
		    	    System.out.println("-------------------------------------------------------------------------");
	      }	    	    
	   }
	   return true;
}
private static void displayPostComments(int post_id)
{
   ArrayList<String>comments=DataBase.getPostComments(post_id);
   for(String comment:comments )
   {
      System.out.println(comment);
   }
}
private static boolean displayFriends(ArrayList<User>friends)
{
      if(friends.size()==0)
      {
         return false;
      }
	int num=1;
	  for(User friend: friends)
	  {
	   System.out.println(num+++". "+friend.getFirstname()+" "+friend.getLastname());
	  }
	 return true; 
}
private static Post createPost()
{
	scan.nextLine();
	System.out.println("Post : ");
	  String post=scan.nextLine();
	System.out.println("bio:");
	  String description=scan.nextLine();
	System.out.println("Date: ");
	  LocalDateTime date=LocalDateTime.now();
	
  return new Post(post,description,date);
}
static void displayProfile(User user)
{
	System.out.println("*************************");
	System.out.println("                  ");
	System.out.println("      "+user.getFirstname()+" "+user.getLastname());
	System.out.println("followins: "+user.getFollowings());
	System.out.println("followers: "+user.getFollowers());
	System.out.println("*************************");
}

}
