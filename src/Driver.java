/**
 * Application to manage stock data.
 * @author Teddy Juntunen
 *
 */

public class Driver {

	public static void main(String[] args) {
		DataManager dataManager = new DataManager();
		dataManager.getDataForTicker("ibm");
	}

}
