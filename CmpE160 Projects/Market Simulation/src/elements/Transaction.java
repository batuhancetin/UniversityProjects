package elements;
/**
 * This is a class that includes fields and methods that indicates properties of transactions.
 * @author Batuhan Cetin
 *
 */
public class Transaction {
	/**
	 * This is selling order of transaction.
	 */
	private SellingOrder sellingOrder;
	/**
	 * This is buying order of transaction.
	 */
	private BuyingOrder buyingOrder;
	/**
	 * This is a constructor for Transaction class.
	 * @param sellingOrder this is selling order of transaction.
	 * @param buyingOrder this is buying order of transaction.
	 */
	public Transaction(SellingOrder sellingOrder, BuyingOrder buyingOrder) {
		this.sellingOrder = sellingOrder;
		this.buyingOrder = buyingOrder;
	}
	/**
	 * This is a getter method for sellingOrder.
	 * @return returns sellingOrder.
	 */
	public SellingOrder getSellingOrder() {
		return sellingOrder;
	}
	/**
	 * This is a getter method for buyingOrder.
	 * @return returns buyingOrder.
	 */
	public BuyingOrder getBuyingOrder() {
		return buyingOrder;
	}
	/**
	 * This is a setter method for sellingOrder.
	 * @param sellingOrder sellingOrder
	 */
	public void setSellingOrder(SellingOrder sellingOrder) {
		this.sellingOrder = sellingOrder;
	}
	/**
	 * This is a setter method for buyingOrder.
	 * @param buyingOrder buyingOrder
	 */
	public void setBuyingOrder(BuyingOrder buyingOrder) {
		this.buyingOrder = buyingOrder;
	}
}
