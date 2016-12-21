package litfam.backend.yelpclient.domain;

public class LocationDO {

	private String address1;
	private String address2;
	private String address3;
	private String city;
	private String zip_code;
	private String state;
	private String country;
	private String[] display_address;
	
	public String getAddress1() {
		return address1;
	}

	public String getZip_code() {
		return zip_code;
	}

	public String getState() {
		return state;
	}

	
	public String getAddress2() {
		return address2;
	}

	public String getAddress3() {
		return address3;
	}

	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}

	public String[] getDisplay_address() {
		return display_address;
	}

	@Override
	public String toString(){
		String extraAddrInfo ="";
		if(address2 != null){
			extraAddrInfo = extraAddrInfo + " " + address2;
		}
		if(address3 != null){
			extraAddrInfo = extraAddrInfo + " " + address3;
		}
		
		return getAddress1() + extraAddrInfo + "\n" + getCity() + " " + getZip_code() + ", " + getState() + " " + getCountry(); 
	}
}
