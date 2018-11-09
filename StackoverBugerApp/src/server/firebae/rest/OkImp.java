package server.firebae.rest;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OkImp {
	public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
	public static OkHttpClient client = new OkHttpClient();

	static String put(String jsonUrl, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(jsonUrl)
				.put(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
	
	static String patch(String jsonUrl, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		Request request = new Request.Builder()
				.url(jsonUrl)
				.patch(body)
				.build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}
	
	static public String get(String jsonUrl) throws IOException{
		Request request = new Request.Builder()
				.header("content-type", "application/json")
				.url(jsonUrl)
				.build();	
		try(Response response = client.newCall(request).execute()){
			return response.body().string();
		}
	}
}
