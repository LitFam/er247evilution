package litfam.frontend.testApp;

import java.io.IOException;

import litfam.backend.yelpclient.QueryBuilder;
import litfam.backend.yelpclient.YelpClient;
import litfam.backend.yelpclient.domain.BusinessDO;
import litfam.backend.yelpclient.domain.BusinessesListDO;

public class TestApp {
	//yelp returns the first 20 results normally. we need to get a random result out of all of the results so we will include an offset in the query
	public static final String TEST_TERM = "indian";
	public static final String TEST_LATITUDE = "38.9091";
	public static final String TEST_LONGITUDE = "-77.5275";
	public static final int NO_OFFSET = 0;
	
	public static void main(String[] args) {
		//our client connects to yelp and retrieves businesses using the query builder
		YelpClient client = YelpClient.getInstance();
		
		//we'll use this to build our queries 
		QueryBuilder queryBuilder = QueryBuilder.getInstance();
		
		BusinessesListDO businessList = null;
		
		try {
			//initial client response determines the number of businesses to calculate random offset
			String simpleBusinessQuery = queryBuilder.computeRandomTermedBusinessQuery(TEST_TERM, NO_OFFSET, TEST_LATITUDE, TEST_LONGITUDE);
			businessList = client.queryClientForBusinesses(simpleBusinessQuery);
			
			//we need to get a random result out of the total results, not just the first page of results (20), which are the businesses yelp gives us
			//so we will include an offset in the query based on the total number of businesses so it will start at a random page so to speak
			int offset = HelperUtilities.calculateOffsetForRandomBusiness(businessList);
			
			//create the random business query
			String randomBusinessQuery = queryBuilder.computeRandomTermedBusinessQuery(TEST_TERM, offset, TEST_LATITUDE, TEST_LONGITUDE);
			
			//second client response gets a list with just one business
			businessList = client.queryClientForBusinesses(randomBusinessQuery);
			
			//get the one business from the list
			BusinessDO randomBusiness = HelperUtilities.getFirstBusiness(businessList);
			
			System.out.println(randomBusiness);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
}
