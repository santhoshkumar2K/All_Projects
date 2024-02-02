package stock_tradings;
public class Address {

	private int address_id;
	private int door_no;
	private String street;
	private String city;
	private String district;
	private  int pincode;
	
	public Address(int door_no, String street, String city, String district, int pincode) {
	
		this.door_no = door_no;
		this.street = street;
		this.city = city;
		this.district = district;
		this.pincode = pincode;
	}
	public Address(int address_id, int door_no, String street, String city, String district, int pincode) {
	
		this.address_id = address_id;
		this.door_no = door_no;
		this.street = street;
		this.city = city;
		this.district = district;
		this.pincode = pincode;
	}
	public int getAddress_id() {
		return address_id;
	}
	public int getDoor_no() {
		return door_no;
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
	public int getPincode() {
		return pincode;
	}
}

