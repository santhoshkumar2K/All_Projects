package instagram;

import java.sql.Date;

public class Comment
{
  int comment_id;
  int from_id;
  int to_id;
  String message;
  Date date;
  String commentTag;
  String postTag;
  Comment(int from,int to,String msg,Date date,String commentTag,String postTag)
  {
     this.from_id=from;
     this.to_id=to;
     this.message=msg;
     this.date=date;
     this.commentTag=commentTag;
     this.postTag=postTag;
  }
  Comment(int id,int from,int to,String msg,Date date,String commentTag,String postTag)
  {
     this.comment_id=id;
     this.from_id=from;
     this.to_id=to;
     this.message=msg;
     this.date=date;
     this.commentTag=commentTag;
     this.postTag=postTag;
  }
}
