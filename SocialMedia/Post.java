package instagram;

import java.time.LocalDateTime;
import java.util.ArrayList;  
public class Post{

	private int id;
	private String post;
	private String bio;
	private LocalDateTime postDate;
	private int likes;
	private int viewers;
	private User user;
	public static ArrayList<Post> posts=new ArrayList();
	Post(String post,String bio,LocalDateTime date)
	{
		this.post=post;
		this.bio=bio;
		this.postDate=date;
		this.likes=0;
		this.viewers=0;
	}
	Post(int id,String post,String bio,LocalDateTime date,int likes,int viewers,User user)
	{
		this.id=id;
		this.post=post;
		this.bio=bio;
		this.postDate=date;
		this.likes=likes;
		this.viewers=viewers;
		this.user=user;
	}
	public int getId() {
	   return id;
	}
	public String getPost() {
	   return post;
	}
	public String getBio() {
	   return bio;
	}
	public LocalDateTime getPostDate() {
	   return postDate;
	}
	public int getLikes() {
	   return likes;
	}
	public int getViewers() {
	   return viewers;
	}
	public User getUser() {
	   return user;
	}
	
        public Post addPost(User user,boolean isDraft)
        {
             if(DataBase.addPost(this,user,isDraft)>0)
             {
                return this;
             }
             return null;
             
        }
       
}
