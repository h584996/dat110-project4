package no.hvl.dat110.aciotdevice.client;

import java.io.IOException;

import com.google.gson.Gson;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RestClient {

	public RestClient() {
		// TODO Auto-generated constructor stub
	}

	private static String logpath = "/accessdevice/log";

	public void doPostAccessEntry(String message) {

		// TODO: implement a HTTP POST on the service to post the message
		
		OkHttpClient client = new OkHttpClient();
		Gson gson = new Gson();
		
		AccessMessage accessMessage = new AccessMessage(message);
		
		RequestBody requestBody = RequestBody.create(MediaType
									.parse("application/json; charset=utf-8"), gson.toJson(accessMessage));
		
		Request request = new Request
				.Builder()
				.url("http://localhost:8080" + logpath)
				.post(requestBody)
				.build();
		
		
		
		System.out.println(request.toString());
		
		try(Response response = client.newCall(request).execute()){
			
			String responseBodyAsString = response.body().string();
			
			System.out.println(responseBodyAsString);
		}catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static String codepath = "/accessdevice/code";
	
	public AccessCode doGetAccessCode() {

		AccessCode code = null;
		Gson gson = new Gson();
		
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
				.url("http://localhost:8080" + codepath)
				.get()
				.build();
		
		System.out.println(request.toString());
		
		try (Response response = client.newCall(request).execute()){
			
			String responseBodyAsString = response.body().string();
			
			System.out.println(responseBodyAsString);
			
			code = gson.fromJson(responseBodyAsString, AccessCode.class);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return code;
	}
}
