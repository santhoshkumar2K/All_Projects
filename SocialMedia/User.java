package instagram;

import java.util.Date;

public class User {
     
	private int id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private Date dateOfBirth; 
	private String userName;
	private String password;
	private long contact;
	private AccountType type;
	private int followers;
	private int followings;
     User(String firstName,String lastName,Gender gender,Date dateOfBirth,String userName,String password,long contact,AccountType type)
     {
	this.firstName=firstName;
	this.lastName=lastName;
	this.gender=gender;
	this.dateOfBirth=dateOfBirth;
	this.userName=userName;
	this.password=password;
	this.contact=contact;
	this.type=type;
	this.followers=0;
	this.followings=0;
     }
     User(int id,String firstName,String lastName,Gender gender,Date dateOfBirth,String userName,String password,long contact,AccountType type,int followers,int followings)
     {
	this.id=id;
	this.firstName=firstName;
	this.lastName=lastName;
	this.gender=gender;
	this.dateOfBirth=dateOfBirth;
	this.userName=userName;
	this.password=password;
	this.contact=contact;
	this.type=type;
	this.followers=followers;
	this.followings=followings;
    }
	public int getId() {
	    return id;
	}
	public String getFirstname() {
	    return firstName;
	}
	public String getLastname() {
	    return lastName;
	}
	public String getGender() {
	    return gender.toString();
	}
	public Date getDateOfBirth() {
	    return dateOfBirth;
	}
	public String getUserName() {
	    return userName;
	}
	public String getPassword() {
	   return password;
	}
	public long getContact() {
	   return contact;
	}
	
	public String getAccountType() {
	   return type.toString();
	}
	public int getFollowers() {
	   return followers;
	}
	public int getFollowings() {
	   return followings;
	}
	
	
	
}
enum Gender
{
    MALE,FEMALE
}
enum AccountType
{
    PRIVATE,PUBLIC
}


