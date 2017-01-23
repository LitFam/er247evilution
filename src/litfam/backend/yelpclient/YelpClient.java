package litfam.backend.yelpclient;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import litfam.backend.yelpclient.domain.BusinessDO;
import litfam.backend.yelpclient.domain.BusinessesListDO;

public class YelpClient {
	
	//tied to my yelp account
	public static final String AUTH_ID = "RZI8UapvZI8IdtCXsRhadw";
	public static final String AUTH_SECRET = "v3zOykZ2JVcjIwtowCOqA2yHmyOI8oNATkSw0bAKNnNaSIoTHKSCSXU0LQJPx1aH";
	
	//link to yelp authorization
	public static final String AUTH_URL = "https://api.yelp.com/oauth2/token";
	
	public static final String BASE_BUSINESS_QUERY = "https://api.yelp.com/v3/businesses/search?";

	private AuthenticationPojo authObj;
	
	private static YelpClient singletonInstance;
	
	private YelpClient(){
		authObj = null;
	}
	
	//send request with client id and secret and transfer response into an authentication object with key for OAuth2
	private AuthenticationPojo authenticateClient() {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(AUTH_URL);
		
		//parameters for request
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("grant_type", "client_credentials"));
		formparams.add(new BasicNameValuePair("client_id", AUTH_ID));
		formparams.add(new BasicNameValuePair("client_secret", AUTH_SECRET));
		
		//set body of POST with parameters in UTF-8 format
		UrlEncodedFormEntity requestEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);	
		httpPost.setEntity(requestEntity);	
		CloseableHttpResponse response;
		try {
			//execute request and get response
			response = httpClient.execute(httpPost);
			
			//status of response
			StatusLine responseStatus = response.getStatusLine();
	
	        //a status greater than 300 is an error
	        if (responseStatus.getStatusCode() >= 300) {
	        	System.out.println("Http Response error code: " + responseStatus.getStatusCode());
	        	return null;
	        }
	        HttpEntity responseEntity = response.getEntity();
	        if (responseEntity != null) {
	            ContentType contentType = ContentType.getOrDefault(responseEntity);
	            Charset charset = contentType.getCharset();
	            //read character stream
	            Reader reader = new InputStreamReader(responseEntity.getContent(), charset);
	            //convert json to pogo using gson
	            Gson gson = new GsonBuilder().create();
	            authObj = gson.fromJson(reader, AuthenticationPojo.class);
	            reader.close();
	        }
            response.close();
		}catch(Exception e) {
			System.out.println(e);;
		}

		return authObj;
	}
	
	//sent request with OAuth2 key and transfer response into businesses object
	//TODO: should take a param query, can make a query maker later that can use different urls
	public BusinessesListDO queryClientForBusinesses(QueryBuilder queryBuilder) throws ClientProtocolException, IOException {
		BusinessesListDO businessesList = null;
		AuthenticationPojo authPojo = getAuthObj();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//create a new get request using the 
		HttpGet httpGet = new HttpGet(queryBuilder.getQuery());	
		//add the authorization token to the header
		httpGet.addHeader("Authorization", (authPojo.getToken_type() +" "+authPojo.getAccess_token()));
		CloseableHttpResponse response = httpClient.execute(httpGet);
		
		//status of response
		StatusLine responseStatus = response.getStatusLine();

        //a status greater than 300 is an error
        if (responseStatus.getStatusCode() >= 300) {
        	System.out.println("Http Response error code: " + responseStatus.getStatusCode());
        	return null;
        }
		
		try {		    
		    HttpEntity entity = response.getEntity();
			
		   if (entity != null) {		    	
	        	ContentType contentType = ContentType.getOrDefault(entity);
		        Charset charset = contentType.getCharset();
		        Reader reader = new InputStreamReader(entity.getContent(), charset);
		        Gson gson = new GsonBuilder().create();
		        businessesList = gson.fromJson(reader, BusinessesListDO.class);
		        
		        reader.close();
		    }
		} finally {
		    response.close();
		}
		return businessesList;
	}
	
	//TODO: make separate method to get the stream as a string for help debugging
    /*System.out.println(reader);
    int data = reader.read();
    while(data != -1){
        char theChar = (char) data;
        data = reader.read();
        System.out.print(theChar);
    }*/

	public AuthenticationPojo getAuthObj() {
		if(authObj == null) {
			return authenticateClient();
		}
		return authObj;
	}

	public void setAuthObj(AuthenticationPojo authObj) {
		this.authObj = authObj;
	}
	
	public static YelpClient getInstance() {
		if (singletonInstance == null) {
			singletonInstance = new YelpClient();
		}
		return singletonInstance;
	}

}
	
