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

import litfam.backend.yelpclient.domain.BusinessesListDO;

public class YelpClient {
	
	public static final String AUTH_ID = "RZI8UapvZI8IdtCXsRhadw";
	public static final String AUTH_SECRET = "v3zOykZ2JVcjIwtowCOqA2yHmyOI8oNATkSw0bAKNnNaSIoTHKSCSXU0LQJPx1aH";
	public static final String AUTH_URL = "https://api.yelp.com/oauth2/token";
	
	public static final String QUERY_URL = "https://api.yelp.com/v3/businesses/search?";
	public static final String TEST_QUERY = "term=fancy&latitude=38.8736&longitude=-77.3899";

	private static AuthenticationPojo authObj;
	
	public static void main(String[] args) {
		authenticateClient();
		try {
			queryClient();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//send request with client id and secret and transfer response into an authentication object with key for OAuth2
	public static void authenticateClient() {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(AUTH_URL);
		
		//parameters for request
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("grant_type", "client_credentials"));
		formparams.add(new BasicNameValuePair("client_id", AUTH_ID));
		formparams.add(new BasicNameValuePair("client_secret", AUTH_SECRET));
		//set body of POST with parameters in UTF-8 format
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);	
		httpPost.setEntity(entity);	
		
		
		//inner class response handler
		ResponseHandler<AuthenticationPojo> rh = new ResponseHandler<AuthenticationPojo>() {

			@Override
			public AuthenticationPojo handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        //a status greater than 300 is an error
		        if (statusLine.getStatusCode() >= 300) {
		            throw new HttpResponseException(
		                    statusLine.getStatusCode(),
		                    statusLine.getReasonPhrase());
		        }
		        if (entity == null) {
		            throw new ClientProtocolException("Response contains no content");
		        }

		        ContentType contentType = ContentType.getOrDefault(entity);
		        Charset charset = contentType.getCharset();
		        //read character stream
		        Reader reader = new InputStreamReader(entity.getContent(), charset);
		        //convert json to pogo using gson
		        Gson gson = new GsonBuilder().create();
		        AuthenticationPojo authPojo = gson.fromJson(reader, AuthenticationPojo.class);
		        reader.close();
		        return authPojo;       
			}

		};
		
		try {
			authObj = httpClient.execute(httpPost, rh);
			setAuthObj(authObj);
			System.out.println(authObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//sent request with OAuth2 key and transfer response into businesses object
	public static void queryClient() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(QUERY_URL+TEST_QUERY);	
		httpGet.addHeader("Authorization", (getAuthObj().getToken_type() +" "+getAuthObj().getAccess_token()));
		CloseableHttpResponse response = httpClient.execute(httpGet);
		
		try {
		    
		    //handle decompression of gzip content
			//GzipDecompressingEntity entity = new GzipDecompressingEntity(response.getEntity());
		    HttpEntity entity = response.getEntity();
			//Header ceHeader = entity.getContentEncoding();
			
		   if (entity != null) {
		    	
		    	/*if (ceHeader != null) {
		    		HeaderElement[] codecs = ceHeader.getElements();
		    		for (HeaderElement codec : codecs) {
		    			if (codec.getName().equalsIgnoreCase("gzip")) {
		    				response.setEntity(new GzipDecompressingEntity(response.getEntity()));
		    			}
		    		}
		    	}*/
		    	
	        	ContentType contentType = ContentType.getOrDefault(entity);
		        Charset charset = contentType.getCharset();
		        Reader reader = new InputStreamReader(entity.getContent(), charset);
		        Gson gson = new GsonBuilder().create();
		        BusinessesListDO businessesList = gson.fromJson(reader, BusinessesListDO.class);
		        System.out.println(businessesList);
		        
		        /*System.out.println(reader);
		        int data = reader.read();
		        while(data != -1){
		            char theChar = (char) data;
		            data = reader.read();
		            System.out.print(theChar);
		        }*/
		        reader.close();
		    }
		} finally {
		    response.close();
		}
		
		
		
		
	}

	public static AuthenticationPojo getAuthObj() {
		return authObj;
	}

	public static void setAuthObj(AuthenticationPojo authObj) {
		YelpClient.authObj = authObj;
	}


}
	
