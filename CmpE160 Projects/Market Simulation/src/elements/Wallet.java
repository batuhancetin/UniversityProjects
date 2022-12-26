package elements;
/**
 * This class includes fields and methods that indicates dollars and coins in the trader's wallet.
 * @author Batuhan Cetin
 *
 */
public class Wallet {
	/**
	 * This is the amount of dollars in the wallet.
	 */
	private double dollars;
	/**
	 * This is the amount of coins in the wallet.
	 */
	private double coins;
	/**
	 * This is the amount of blocked dollars in the wallet.
	 */
	private double blockedDollars;
	/**
	 * This is the amount of blocked coins in the wallet.
	 */
	private double blockedCoins;
	/**
	 * This is constructor for Wallet class.
	 * @param dollars the amount of dollars in the wallet.
	 * @param coins the amount of coins in the wallet.
	 */
	public Wallet(double dollars, double coins) {
		this.dollars = dollars;
		this.coins = coins;
		this.blockedDollars = 0;
		this.blockedCoins = 0;
	}
	/**
	 * This is a getter method for dollars.
	 * @return dollars
	 */
	public double getDollars() {
		return dollars;
	}
	/**
	 * This is a getter method for coins.
	 * @return coins
	 */
	public double getCoins() {
		return coins;
	}
	/**
	 * This is a getter method for blockedDollars.
	 * @return blockedDollars
	 */
	public double getBlockedDollars() {
		return blockedDollars;
	}
	/**
	 * This is a getter method for blockedCoins.
	 * @return blockedCoins
	 */
	public double getBlockedCoins() {
		return blockedCoins;
	}
	/**
	 * This is a setter method for dollars.
	 * @param dollars dollars
	 */
	public void setDollars(double dollars) {
		this.dollars = dollars;
	}
	/**
	 * This is a setter method for coins.
	 * @param coins coins
	 */
	public void setCoins(double coins) {
		this.coins = coins;
	}
	/**
	 * This is a setter method for blockedDollars.
	 * @param blockedDollars blockedDollars
	 */
	public void setBlockedDollars(double blockedDollars) {
		this.blockedDollars = blockedDollars;
	}
	/**
	 * This is a setter method for blockedCoins.
	 * @param blockedCoins blockedCoins
	 */
	public void setBlockedCoins(double blockedCoins) {
		this.blockedCoins = blockedCoins;
	}
}
