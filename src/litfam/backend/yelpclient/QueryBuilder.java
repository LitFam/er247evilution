package litfam.backend.yelpclient;

public class QueryBuilder {
	//Search for businesses by keyword, category, location, price level, etc.
	public static final String SEARCH_URL = "https://api.yelp.com/v3/businesses/search?"; 
	//Search for businesses by phone number.
	public static final String PHONE_SEARCH_URL = "https://api.yelp.com/v3/businesses/search/phone?"; 
	//Transaction search
	public static final String TRANSACTION_SEARCH_URL = "https://api.yelp.com/v3/transactions/{transaction_type}/search"; 
	//	Get rich business data, such as name, address, phone number, photos, Yelp rating, price levels and hours of operation.
	public static final String BUSINESSES_URL = "https://api.yelp.com/v3/businesses/{id}";
	//	Get up to three review excerpts for a business
	public static final String REVIEWS_URL = "https://api.yelp.com/v3/businesses/{id}/reviews";
	//Provide autocomplete suggestions for businesses, search keywords and categories.
	public static final String AUTOCOMPLETE_URL = "https://api.yelp.com/v3/autocomplete";
	
	public static final String DELIMITER = "&";
	
	public static final String APPEND_LATITUDE = "latitude=";
	public static final String APPEND_LONGITUDE = "longitude=";
	public static final String APPEND_KEYWORD = "term=";
	public static final String APPEND_OFFSET = "offset=";
	public static final String APPEND_LIMIT = "limit=";
	
	private String offset;
	private String limit;
	private String keyword;
	private String latitude;
	private String longitude;
	
	private String query;
	
	private static QueryBuilder singletonInstance;
	
	private QueryBuilder(){
		super();
	}
	
	//use string builder later to make more optimized
	public String computeRandomBusinessQuery(int offsetParam, String latitudeParam, String longitudeParam) {
		limit = "1";
		offset = String.valueOf(offsetParam);
		latitude = latitudeParam;
		longitude = longitudeParam;
		String randomBusinessStr = SEARCH_URL + APPEND_KEYWORD + keyword + DELIMITER + APPEND_LATITUDE + latitude + DELIMITER
				+ APPEND_LONGITUDE + longitude + DELIMITER + APPEND_OFFSET + offset + DELIMITER + APPEND_LIMIT + limit;
		System.out.println(randomBusinessStr);
		query = randomBusinessStr;
		return randomBusinessStr;
	}
	
	//use string builder later to make more optimized
	public String computeRandomTermedBusinessQuery(String keywordParam, int offsetParam, String latitudeParam, String longitudeParam) {
		limit = "1";
		offset = String.valueOf(offsetParam);
		keyword= keywordParam;
		latitude = latitudeParam;
		longitude = longitudeParam;
		String randomBusinessStr = SEARCH_URL + APPEND_KEYWORD + keyword + DELIMITER + APPEND_LATITUDE + latitude + DELIMITER
				+ APPEND_LONGITUDE + longitude + DELIMITER + APPEND_OFFSET + offset + DELIMITER + APPEND_LIMIT + limit;
		System.out.println(randomBusinessStr);
		query = randomBusinessStr;
		return randomBusinessStr;
	}
	
	//use string builder later to make more optimized
	public String computeSimpleTermedQuery(String keywordParam, String latitudeParam, String longitudeParam) {
		keyword= keywordParam;
		latitude = latitudeParam;
		longitude = longitudeParam;
		String randomBusinessStr = SEARCH_URL + APPEND_KEYWORD + keyword + DELIMITER + APPEND_LATITUDE + latitude + DELIMITER
				+ APPEND_LONGITUDE + longitude;
		System.out.println(randomBusinessStr);
		query = randomBusinessStr;
		return randomBusinessStr;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = "latitude=" + latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = "longitude=" + longitude;
	}

	public String getOffset() {
		return offset;
	}

	public void setOffset(String offset) {
		this.offset = offset;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public static QueryBuilder getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new QueryBuilder();
		}
		return singletonInstance;
	}
	
	
	/*Info from yelp about other query properties
	 * term	string	Optional. Search term (e.g. "food", "restaurants"). If term isn’t included we search everything. The term keyword also accepts business names such as "Starbucks".
location	string	Required if either latitude or longitude is not provided. Specifies the combination of "address, neighborhood, city, state or zip, optional country" to be used when searching for businesses.
latitude	decimal	Required if location is not provided. Latitude of the location you want to search near by.
longitude	decimal	Required if location is not provided. Longitude of the location you want to search near by.
radius	int	Optional. Search radius in meters. If the value is too large, a AREA_TOO_LARGE error may be returned. The max value is 40000 meters (25 miles).
categories	string	Optional. Categories to filter the search results with. See the list of supported categories. The category filter can be a list of comma delimited categories. For example, "bars,french" will filter by Bars and French. The category identifier should be used (for example "discgolf", not "Disc Golf").
locale	string	Optional. Specify the locale to return the business information in. See the list of supported locales.
limit	int	Optional. Number of business results to return. By default, it will return 20. Maximum is 50.
offset	int	Optional. Offset the list of returned business results by this amount.
sort_by	string	Optional. Sort the results by one of the these modes: best_match, rating, review_count or distance. By default it's best_match. The rating sort is not strictly sorted by the rating value, but by an adjusted rating value that takes into account the number of ratings, similar to a bayesian average. This is so a business with 1 rating of 5 stars doesn’t immediately jump to the top.
price	string	Optional. Pricing levels to filter the search result with: 1 = $, 2 = $$, 3 = $$$, 4 = $$$$. The price filter can be a list of comma delimited pricing levels. For example, "1, 2, 3" will filter the results to show the ones that are $, $$, or $$$.
open_now	boolean	Optional. Default to false. When set to true, only return the businesses open now. Notice that open_at and open_now cannot be used together.
open_at	int	Optional. An integer represending the Unix time in the same timezone of the search location. If specified, it will return business open at the given time. Notice that open_at and open_now cannot be used together.
attributes	string	Optional. Additional filters to restrict search results. Possible values are:
hot_and_new - Hot and New businesses
request_a_quote - Businesses that have the Request a Quote feature
waitlist_reservation - Businesses that have an online waitlist
cashback - Businesses that offer Cash Back
deals - Businesses that offer Deals
You can combine multiple attributes by providing a comma separated like "attribute1,attribute2". If multiple attributes are used, only businesses that satisfy ALL attributes will be returned in search results. For example, the attributes "hot_and_new,cashback" will return businesses that are Hot and New AND offer Cash Back.
	 */
	
	
}
