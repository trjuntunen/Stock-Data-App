
/**
 * Application to manage stock data.
 * @author Teddy Juntunen
 *
 */

public class Driver {

	public static void main(String[] args) {
		Ticker ticker = new Ticker("pg");
		
		String name = ticker.getStringValueWithKey("companyName");
		System.out.println(name);
		
		System.out.println();
		
		double price = ticker.getDoubleValueWithKey("latestPrice");
		System.out.println(price);
	}

}
