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
 * Class that represents a JSONObject storing API data
 * about the given ticker. Methods getStringValueWithKey() 
 * and getDoubleValueWithKey() are used to get the data.
 * @author Teddy Juntunen
 *
 */

public class Ticker {
	
	private JSONObject tickerDataObject;
	private String ticker;
	
	public Ticker(String ticker) {
		this.ticker = ticker;
		tickerDataObject = new JSONObject();
		
		/* Build the Ticker object when it's instantiated. */
		buildTicker();
	}
	
	/* Returns a JSONObject storing the returned API data for the given ticker. */
	private void buildTicker() {
		URL url = buildURLForTicker(ticker);
		URLConnection connection = openConnectionToURL(url);
		InputStreamReader inputStreamReader = getInputStreamReaderForConnection(connection);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		
		/* Only need to read one line because json is stored in one line. */
		String jsonData = readLineWithReader(reader);
		JSONObject jsonObject = createJsonObjectFromString(jsonData);
		
		/* The key "quote" is just the key for this API's json data. */
		tickerDataObject = getNestedJsonObject(jsonObject, "quote");
	}
	
	private InputStreamReader getInputStreamReaderForConnection(URLConnection connection) {
		InputStream inputStream = getInputStreamFromConnection(connection);
		return new InputStreamReader(inputStream);
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
	
	private String readLineWithReader(BufferedReader reader) {
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
	
	public String getStringValueWithKey(String key) {
		String result = "";
		try {
			result = (String) tickerDataObject.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public double getDoubleValueWithKey(String key) {
		double result = 0.0;
		try {
			result = (double) tickerDataObject.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private JSONObject createJsonObjectFromString(String json) {
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	
}
