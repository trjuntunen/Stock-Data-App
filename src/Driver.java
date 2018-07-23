
/**
 * Application to manage stock data.
 * @author Teddy Juntunen
 *
 */

public class Driver {

	public static void main(String[] args) {
//		Ticker ticker = new Ticker("jnj");
		
		String[] tickers = {"abt", "agn", "azo", "avy", "celg"};
		
		for(int i = 0; i < tickers.length; i++) {
			Ticker ticker = new Ticker(tickers[i]);
			String name = ticker.getString("companyName");
			double price = ticker.getDouble("latestPrice");
			System.out.println(name);
			System.out.println(price);
			System.out.println();
		}
		
//		String name = ticker.getString("companyName");
//		System.out.println(name);
//		
//		System.out.println();
//		
//		double price = ticker.getDouble("latestPrice");
//		System.out.println(price);
	}

}
