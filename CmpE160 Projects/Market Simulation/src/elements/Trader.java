package elements;
/**
 * This class includes fields and methods that indicates the properties of traders.
 * @author Batuhan Cetin
 *
 */
public class Trader {
	/**
	 * This is id of the trader.
	 */
	private int id;
	/**
	 * This is wallet of the trader.
	 */
	private Wallet wallet;
	/**
	 * This is constructor for Trader class.
	 * @param dollars this is the amount of dollars in the trader's wallet.
	 * @param coins this is the amount of coins in the trader's wallet.
	 */
	public Trader(double dollars, double coins) {
		wallet = new Wallet(dollars, coins);
		id = numberOfUsers;
		numberOfUsers ++;
	}
	/**
	 * This field keeps the number of users.
	 */
	public static int numberOfUsers = 0;
	/**
	 * This is the method that checks whether selling order is ordered successfully or not. 
	 * @param amount this is the coin amount of the selling order.
	 * @param price this is the price of the sell order.
	 * @param market this is the market.
	 * @return returns 1 and 0 due to true or false.
	 */
	public int sell(double amount, double price, Market market) {
		if (this.getWallet().getCoins() >= amount) {
			return 1;
		}
		else {
			return 0;
		}
	}
	/**
	 * This is the method that checks whether buying order is ordered successfully or not. 
	 * @param amount this is the coin amount of the buying order.
	 * @param price this is the price of the buying order.
	 * @param market this is the market.
	 * @return returns 1 and 0 due to true or false.
	 */
	public int buy(double amount, double price, Market market) {
		if (this.getWallet().getDollars() >= amount * price) {
			return 1;
		}
		else {
			return 0;
		}
	}
	/**
	 * This is getter method for id.
	 * @return id
	 */
	public int getId() {
		return id;
	}
	/**
	 * This is getter method for wallet.
	 * @return wallet
	 */
	public Wallet getWallet() {
		return wallet;
	}
	/**
	 * This is setter method for id.
	 * @param id id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * This is setter method for wallet.
	 * @param wallet wallet
	 */
	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}
}
