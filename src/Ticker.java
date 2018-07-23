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
	
	private JSONObject tickerObject;
	private String ticker;
	
	public Ticker(String ticker) {
		this.ticker = ticker;
		tickerObject = new JSONObject();
		buildTicker();
	}
	
	private void buildTicker() {
		URL url = buildURLForTicker(ticker);
		String jsonData = read(url);
		try {
			tickerObject = createJsonObject(jsonData);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public String getString(String key) {
		String result = "";
		try {
			result = (String) tickerObject.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public double getDouble(String key) {
		double result = 0.0;
		try {
			result = (double) tickerObject.get(key);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private String read(URL url) {
		try {
			return readURL(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	private String readURL(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		InputStream inputStream = connection.getInputStream();
		InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
		BufferedReader reader = new BufferedReader(inputStreamReader);
		/* Only need to read one line because json is stored in one line. */
		return reader.readLine();
	}
	
	private JSONObject createJsonObject(String jsonData) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonData);
		return jsonObject.getJSONObject("quote");
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
	
}
