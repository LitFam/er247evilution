package litfam.backend.yelpclient.domain;

public class BusinessesListDO {


	private BusinessDO[] businesses;
	private int total;
	
	public BusinessDO[] getBusinesses() {
		return businesses;
	}

	public int getTotal() {
		return total;
	}
	
	@Override
	public String toString(){
		String businessesStr ="";
		for(int i = 0; i<getBusinesses().length;i++){
			businessesStr = businessesStr + getBusinesses()[i] + "\n";
		}
		return "Number of businesses: "+ getTotal() + "\nList of businesses:\n" + businessesStr; 
	}
	
}
