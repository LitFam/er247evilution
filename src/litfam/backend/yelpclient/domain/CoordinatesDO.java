package litfam.backend.yelpclient.domain;

public class CoordinatesDO {

	private double longitude;
	private double latitude;
	
	public double getLongitude() {
		return longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	
	@Override
	public String toString(){
		return "{Longitude: " + getLongitude() + ", Latitude: " + getLatitude() + "}";
	}
}
