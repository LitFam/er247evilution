package litfam.frontend.testApp;

import litfam.backend.yelpclient.domain.BusinessDO;
import litfam.backend.yelpclient.domain.BusinessesListDO;

public class HelperUtilities {

	public static BusinessDO getFirstBusiness(BusinessesListDO businessList) {	
		if(businessList.getBusinesses().length > 0 ) {
			return businessList.getBusinesses()[0];
		}
		return null;
	}
	
	//since the yelp api only returns 20 results we want to offset
	public static int calculateOffsetForRandomBusiness(BusinessesListDO businessList) {
		int total = businessList.getTotal();	
		System.out.println("Array Length: " + businessList.getBusinesses().length);
		System.out.println("Total: " + total);
		int randomNum = (int) (Math.random() * total);
		System.out.println("Random: " + randomNum);
		
		return randomNum;
	}
}
