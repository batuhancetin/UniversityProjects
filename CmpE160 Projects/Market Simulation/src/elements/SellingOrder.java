package elements;
/**
 * This is a class that inherit Order class and implements Comparable<SellingOrder> interface. It includes fields and methods that indicates properties of selling orders. 
 * @author Batuhan Cetin
 *
 */
public class SellingOrder extends Order implements Comparable<SellingOrder> {
	/**
	 * This is a constructor for SellingOrder class.
	 * @param traderID this is the id of the trader that gives the order.
	 * @param amount this is the coin amount of the order.
	 * @param price this is the price of the order.
	 */
	public SellingOrder(int traderID, double amount, double price) {
		super(traderID, amount, price);
	}
	/**
	 * This is a compareTo method for SellingOrder class.
	 * @param e this is the other order in the comparison.
	 * @return this returns 1 and 0 due to comparison.
	 */
	public int compareTo(SellingOrder e) {
		if (this.price > e.price) {
			return 1;
		}
		else if (this.price == e.price) {
			if (this.amount > e.price) {
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
			return -1;
		}
	}
}
