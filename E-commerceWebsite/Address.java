package e_commerce;
public class Address
{
    private int addressID;
    private int doorNumber;
    private String street;
    private String city;
    private String district;
    private int bincode;
    
   
    Address(int doorNumber,String street,String city,String district,int bincode)
    {
        this.doorNumber=doorNumber;
        this.street=street;
        this.city=city;
        this.district=district;
        this.bincode=bincode;
    }
    Address(int id,int doorNumber,String street,String city,String district,int bincode)
    {
        this.addressID=id;
        this.doorNumber=doorNumber;
        this.street=street;
        this.city=city;
        this.district=district;
        this.bincode=bincode;
    } 
    public int getAddressID() {
        return addressID;
    }
    public int getDoorNumber() {
        return doorNumber;
    }
    public String getStreet() {
        return street;
    }
    public String getCity() {
        return city;
    }
    public String getDistrict() {
        return district;
    }
    public int getBincode() {
        return bincode;
    }
}
