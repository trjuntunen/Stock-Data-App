
/**
 * Application to manage stock data.
 * @author Teddy Juntunen
 *
 */

public class Driver {

	public static void main(String[] args) {
		Ticker ticker = new Ticker("aapl");
		String name = ticker.getStringValueWithKey("companyName");
		System.out.println(name);
	}

}
