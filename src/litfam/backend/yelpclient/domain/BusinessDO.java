package litfam.backend.yelpclient.domain;

public class BusinessDO {

	//string representation of phone number
	private String display_phone;
	//picture representation
	private String image_url;
	//yelp url
	private String url;
	//open or closed
	private boolean is_closed;
	
	//unique ID found in yelp url after /biz/
	private String id;
	
	private CoordinatesDO coordinates;
	
	//number of reviews
	private int review_count;
	
	//easily parsed phone number
	private String phone;
	
	private String name;
	
	private LocationDO location;

	private float rating;
	
	private String price;
	
	private CategoryDO[] categories;
	
	private double distance;
	
	
	
	public String getDisplay_phone() {
		return display_phone;
	}

	public String getImage_url() {
		return image_url;
	}

	public String getUrl() {
		return url;
	}

	public boolean isIs_closed() {
		return is_closed;
	}

	public String getId() {
		return id;
	}

	public CoordinatesDO getCoordinates() {
		return coordinates;
	}

	public int getReview_count() {
		return review_count;
	}

	public String getPhone() {
		return phone;
	}

	public String getName() {
		return name;
	}

	public LocationDO getLocation() {
		return location;
	}

	public float getRating() {
		return rating;
	}

	public String getPrice() {
		return price;
	}

	public CategoryDO[] getCategories() {
		return categories;
	}

	public double getDistance() {
		return distance;
	}

	@Override
	public String toString(){
		String closedOrOpenStr;
		if(isIs_closed()) {
			closedOrOpenStr = "CLOSED";
		}
		else
		{
			closedOrOpenStr = "OPEN NOW";
		}
		return getName() + "\n" + getDisplay_phone() + 
				"\n" + getLocation() + "\n" 
				+ closedOrOpenStr + "\nCoordinates: " + getCoordinates() + "}\n" + "Distance: " + getDistance() + "\n"
				+ "Webpage: " + getUrl();
	}
}
