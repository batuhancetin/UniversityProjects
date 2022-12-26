package elements;
/**
 * This is a class that inherit Order class and implements Comparable<BuyingOrder> interface. It includes fields and methods that indicates properties of buying orders. 
 * @author Batuhan Cetin
 *
 */
public class BuyingOrder extends Order implements Comparable<BuyingOrder> {
	/**
	 * This is a constructor for BuyingOrder class.
	 * @param traderID this is the id of the trader that gives the order.
	 * @param amount this is the coin amount of the order.
	 * @param price this is the price of the order.
	 */
	public BuyingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}
	/**
	 * This is a compareTo method for BuyingOrder class.
	 * @param e this is the other order in the comparison.
	 * @return this returns 1 and 0 due to comparison.
	 */
	public int compareTo(BuyingOrder e) {
		if (this.price > e.price) {
			return -1;
		}
		else if (this.price == e.price) {
			if (this.amount > e.amount) {
				return -1;
			}
			else if (this.amount == e.amount) {
				if (this.traderID > e.traderID) {
					return 1;
				}
				else if (this.traderID == e.traderID) {
					return 0;
				}
				else {
					return -1;
				}
			}
			else {
				return 1;
			}
		}
		else {
			return 1;
		}
	}
}
