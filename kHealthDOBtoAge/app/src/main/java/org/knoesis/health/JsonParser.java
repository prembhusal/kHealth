package org.knoesis.health;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * This class will be used to interpret JSON responses
 * 
 * @author quintin
 */
public class JsonParser {
	
	String url = null;
	JSONObject jsonObject;
	String tag = "JsonParser";

	public JsonParser(String url){
		Log.d(tag, "In constructor");
		this.url = testUrl(url);
		if(url != null){
			Log.d(tag, "url: " + url);
			try{
				Log.d(tag, "Getting response");
				this.jsonObject = getJsonResponse(url);
			}catch(Exception e){
				Log.e("JsonParser", "Error reading JSON response");
			}
		}
	}
	
	public JSONObject getJsonObject(){
		return this.jsonObject;
	}
	
	private String testUrl(String url){
		Log.d(tag, "Testing url");
		if(!url.startsWith("http")){
			if(!url.startsWith("//")){
				url = "http://" + url;
			}
		}
		return url;
	}
	
	private JSONObject getJsonResponse(String url) throws JSONException{
		String result = null;
		
		DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
		HttpPost httpPost = new HttpPost(url);
		httpPost.setHeader("Content-type", "application/json");
		
		InputStream inputStream = null;
		
		try{
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			
			inputStream = entity.getContent();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
			StringBuilder sb = new StringBuilder();
			
			String line = null;
			int count = 5;
			while(count > 0){
				line = reader.readLine();
				if(line != null){
					Log.i(tag, line + "");
					sb.append(line).append("\n");
				}else{
					Log.i(tag, "null line");
					count--;
				}
			}
			result = sb.toString();
		}catch(Exception e){
			// Error reading url
			Log.e("JsonParser", "Error reading url: " + url);
		}finally{
			try {
				if(inputStream != null){
					inputStream.close();
				}
			} catch (IOException e) {
				Log.e("JsonParser", "Failed to close input stream");
			}
		}
		
		return new JSONObject(result);
	}
	
}
