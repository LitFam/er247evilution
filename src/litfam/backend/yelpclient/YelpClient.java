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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class YelpClient{
	
	public static final String AUTH_ID = "RZI8UapvZI8IdtCXsRhadw";
	public static final String AUTH_SECRET = "v3zOykZ2JVcjIwtowCOqA2yHmyOI8oNATkSw0bAKNnNaSIoTHKSCSXU0LQJPx1aH";
	public static final String AUTH_URL = "https://api.yelp.com/oauth2/token";
	

	public static void main(String[] args) {
		authenticateClient();
	}
	
	public static void authenticateClient() {

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(AUTH_URL);
		
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("grant_type", "client_credentials"));
		formparams.add(new BasicNameValuePair("client_id", AUTH_ID));
		formparams.add(new BasicNameValuePair("client_secret", AUTH_SECRET));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);	
		httpPost.setEntity(entity);	
		
		
		//inner class response handler
		ResponseHandler<AuthenticationPojo> rh = new ResponseHandler<AuthenticationPojo>() {

			@Override
			public AuthenticationPojo handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
				StatusLine statusLine = response.getStatusLine();
		        HttpEntity entity = response.getEntity();
		        if (statusLine.getStatusCode() >= 300) {
		            throw new HttpResponseException(
		                    statusLine.getStatusCode(),
		                    statusLine.getReasonPhrase());
		        }
		        if (entity == null) {
		            throw new ClientProtocolException("Response contains no content");
		        }
		        Gson gson = new GsonBuilder().create();
		        ContentType contentType = ContentType.getOrDefault(entity);
		        Charset charset = contentType.getCharset();
		        Reader reader = new InputStreamReader(entity.getContent(), charset);
		        AuthenticationPojo authPojo = gson.fromJson(reader, AuthenticationPojo.class);
		        return authPojo;       
			}

		};
		
		AuthenticationPojo authObj;
		try {
			authObj = httpClient.execute(httpPost, rh);
			System.out.println(authObj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
	
