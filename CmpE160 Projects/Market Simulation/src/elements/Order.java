package elements;
/**
 * This is a class that includes fields and methods that indicates properties of orders.
 * @author Batuhan Cetin
 *
 */
public class Order {
	/**
	 * This is the coin amount of the order.
	 */
	protected final double amount;
	/**
	 * This is the price of the order.
	 */
	protected final double price;
	/**
	 * This is the ID of the trader that gives the order.
	 */
	protected final int traderID;
	/**
	 * This is a constructor for Order class.
	 * @param traderID this is the id of the trader that gives the order.
	 * @param amount this is amount of the order.
	 * @param price this is price of the order.
	 */
	public Order(int traderID, double amount, double price) {
		this.traderID = traderID;
		this.amount = amount;
		this.price = price;
	}
	/**
	 * This is a getter method for amount.
	 * @return returns amount.
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * This is a getter method for price.
	 * @return returns price.
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * This is a getter method for traderID.
	 * @return returns traderID.
	 */
	public int getTraderID() {
		return traderID;
	}
}