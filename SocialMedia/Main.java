
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
public class Main {

    static {
            instagram.Storage.createUserTable();
            instagram.Storage.createPostTable();
            instagram.Storage.createFriendTable();
            instagram.Storage.createLikeTable();
            instagram.Storage.createCommentTable();
            instagram.Storage.defaultCommentAndPostSet();
    }
    public static void main(String[] args) {
	try{	
		instagram.View viewObject=new instagram.View();
		viewObject.authentication();
	  }catch(Exception e){}
	  finally{
	    instagram. DataBase.close();
	  }	
    }

}

