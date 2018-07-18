import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Class that provides a getDataForTicker() function that
 * returns data for the specified ticker.
 * @author Teddy Juntunen
 *
 */

public class DataManager {

	public void getDataForTicker(String ticker) {
		URL url = buildURLForTicker(ticker);
		
		/* Connect to the URL and read it. */
		URLConnection connection = openConnectionToURL(url);
		InputStream inputStream = getInputStreamFromConnection(connection);
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		
		/* Only need to read one line because json is stored in one line. */
		String jsonData = readLineFromBufferedReader(reader);
		
		/* Get the data in the nested Json objects. */
		JSONObject jsonObject = createNewJsonObject(jsonData);
		JSONObject nestedJsonObject = getNestedJsonObject(jsonObject, "quote");
		
		double latestPrice = getDoubleValueFromJson("latestPrice", nestedJsonObject);
		String companyName = getStringValueFromJson("companyName", nestedJsonObject);
		
		System.out.println(companyName);
	}
	
	private URL buildURLForTicker(String ticker) {
		StringBuilder urlBuilder = new StringBuilder("https://api.iextrading.com/1.0/stock/");
		urlBuilder.append(ticker);
		urlBuilder.append("/batch?types=quote,news,chart&range=1m&last=10");
		URL url = createNewURL(urlBuilder.toString());
		return url;
	}
	
	private URL createNewURL(String url) {
		URL newURL = null;
		try {
			newURL = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return newURL;
	}
	
	private URLConnection openConnectionToURL(URL url) {
		URLConnection connection = null;
		try {
			connection = url.openConnection();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	private InputStream getInputStreamFromConnection(URLConnection connection) {
		InputStream inputStream = null;
		try {
			inputStream = connection.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputStream;
	}
	
	private String readLineFromBufferedReader(BufferedReader reader) {
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}
	
	private JSONObject getNestedJsonObject(JSONObject jsonObject, String key) {
		JSONObject newObject = null;
		try {
			newObject = jsonObject.getJSONObject(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return newObject;
	}
	
	private String getStringValueFromJson(String key, JSONObject jsonObject) {
		String result = "";
		try {
			result = (String) jsonObject.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private double getDoubleValueFromJson(String key, JSONObject jsonObject) {
		double result = 0.0;
		try {
			result = (double) jsonObject.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private JSONObject createNewJsonObject(String json) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
}
