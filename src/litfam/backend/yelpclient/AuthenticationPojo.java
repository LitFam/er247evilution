/**
 * 
 */
package litfam.backend.yelpclient;

/**
 * @author Caleb
 *
 */
public class AuthenticationPojo {
	private String access_token;
	private String token_type;
	private String expires_in;
	
    @Override
    public String toString() {
    	return "Access token: " + getAccess_token() + "\nToken type: "+ getToken_type() + "\nExpires in: " + expires_in +" seconds";
    }

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getToken_type() {
		return token_type;
	}

	public void setToken_type(String token_type) {
		this.token_type = token_type;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}


}
