package elements;

import java.util.ArrayList;
import java.util.PriorityQueue;
/**
 * This class includes fields and methods that indicates the properties of market.
 * @author Batuhan Cetin
 *
 */
public class Market {
	/**
	 * This is a priority queue that keeps selling orders.
	 */
	private PriorityQueue<SellingOrder> sellingOrders;
	/**
	 * This is a priority queue that keeps buying orders.
	 */
	private PriorityQueue<BuyingOrder> buyingOrders;
	/**
	 * This is an arraylist that keeps transactions.
	 */
	private ArrayList<Transaction> transactions;
	/**
	 * This number keeps number of successful transactions.
	 */
	private int successfulTransactions;
	/**
	 * This is market's fee.
	 */
	private final int fee;
	/**
	 * This method adds given selling order to sellingOrders priority queue.
	 * @param order this is given order.
	 */
	public void giveSellOrder(SellingOrder order) {
		sellingOrders.add(order);
	}
	/**
	 * This method adds given buying order to buyingOrders priority queue.
	 * @param order this is given order.
	 */
	public void giveBuyOrder(BuyingOrder order) {
		buyingOrders.add(order);
	}
	/**
	 * This method makes open market operations to regulate current price to given price.
	 * @param price this is given price.
	 * @param traders this is arraylist of traders in the market.
	 */
	public void makeOpenMarketOperation(double price, ArrayList<Trader> traders) {
		if (buyingOrders.isEmpty() == false && sellingOrders.isEmpty() == false) {
			boolean xyz = true;
			while(xyz && buyingOrders.peek().getPrice() >= price) {
				giveSellOrder(new SellingOrder(0, buyingOrders.peek().getAmount(), buyingOrders.peek().getPrice()));
				checkTransactions(traders);
				if (buyingOrders.isEmpty()) {
					xyz = false;
				}
			}
			boolean ghz = true;
			while(ghz && sellingOrders.peek().getPrice() <= price) {
				giveBuyOrder(new BuyingOrder(0, sellingOrders.peek().getAmount(), sellingOrders.peek().getPrice()));
				checkTransactions(traders);
				if (sellingOrders.isEmpty()) {
					ghz = false;
				}
			}
		}
	}
	/**
	 * This method checks the transactions in the queues. If there are overlapping orders, the method executes them.
	 * @param traders this is arraylist of traders in the market.
	 */
	public void checkTransactions(ArrayList<Trader> traders) {
		if (sellingOrders.isEmpty() || buyingOrders.isEmpty()) {
			return;
		}
		else {
			boolean abc = true;
			while(abc && buyingOrders.peek().getPrice() >= sellingOrders.peek().getPrice()) {
				SellingOrder sellPeek = sellingOrders.peek();
				BuyingOrder buyPeek = buyingOrders.peek();
				transactions.add(new Transaction(sellPeek, buyPeek));
				if(buyPeek.getAmount() > sellPeek.getAmount()) {
					traders.get(buyPeek.getTraderID()).getWallet().setBlockedDollars(traders.get(buyPeek.getTraderID()).getWallet().getBlockedDollars() - (sellPeek.getAmount() * buyPeek.getPrice()));
					traders.get(buyPeek.getTraderID()).getWallet().setDollars(traders.get(buyPeek.getTraderID()).getWallet().getDollars() + (sellPeek.getAmount() * (buyPeek.getPrice() - sellPeek.getPrice())));
					traders.get(buyPeek.getTraderID()).getWallet().setCoins(traders.get(buyPeek.getTraderID()).getWallet().getCoins() + sellPeek.getAmount());
					buyingOrders.poll();
					buyingOrders.add(new BuyingOrder(buyPeek.getTraderID(), (buyPeek.getAmount() - sellPeek.getAmount()), buyPeek.getPrice()));
					traders.get(sellPeek.getTraderID()).getWallet().setBlockedCoins(traders.get(sellPeek.getTraderID()).getWallet().getBlockedCoins() - sellPeek.getAmount());
					traders.get(sellPeek.getTraderID()).getWallet().setDollars(traders.get(sellPeek.getTraderID()).getWallet().getDollars() + (sellPeek.getAmount() * sellPeek.getPrice() * (1 - ((double)fee/1000))));
					sellingOrders.poll();
				}
				else if(buyPeek.getAmount() == sellPeek.getAmount()) {
					traders.get(buyPeek.getTraderID()).getWallet().setBlockedDollars(traders.get(buyPeek.getTraderID()).getWallet().getBlockedDollars() - (buyPeek.getAmount() * buyPeek.getPrice()));
					traders.get(buyPeek.getTraderID()).getWallet().setDollars(traders.get(buyPeek.getTraderID()).getWallet().getDollars() + (sellPeek.getAmount() * (buyPeek.getPrice() - sellPeek.getPrice())));
					traders.get(buyPeek.getTraderID()).getWallet().setCoins(traders.get(buyPeek.getTraderID()).getWallet().getCoins() + buyPeek.getAmount());
					buyingOrders.poll();
					traders.get(sellPeek.getTraderID()).getWallet().setBlockedCoins(traders.get(sellPeek.getTraderID()).getWallet().getBlockedCoins() - sellPeek.getAmount());
					traders.get(sellPeek.getTraderID()).getWallet().setDollars(traders.get(sellPeek.getTraderID()).getWallet().getDollars() + (sellPeek.getAmount() * sellPeek.getPrice() * (1 - ((double)fee/1000))));
					sellingOrders.poll();
				}
				else {
					traders.get(sellPeek.getTraderID()).getWallet().setBlockedCoins(traders.get(sellPeek.getTraderID()).getWallet().getBlockedCoins() - buyPeek.getAmount());
					traders.get(sellPeek.getTraderID()).getWallet().setDollars(traders.get(sellPeek.getTraderID()).getWallet().getDollars() + (buyPeek.getAmount() * sellPeek.getPrice() * (1 - ((double)fee/1000))));
					sellingOrders.poll();
					sellingOrders.add(new SellingOrder(sellPeek.getTraderID(), (sellPeek.getAmount() - buyPeek.getAmount()), sellPeek.getPrice()));
					traders.get(buyPeek.getTraderID()).getWallet().setBlockedDollars(traders.get(buyPeek.getTraderID()).getWallet().getBlockedDollars() - (buyPeek.getAmount() * buyPeek.getPrice()));
					traders.get(buyPeek.getTraderID()).getWallet().setDollars(traders.get(buyPeek.getTraderID()).getWallet().getDollars() + (buyPeek.getAmount() * (buyPeek.getPrice() - sellPeek.getPrice())));
					traders.get(buyPeek.getTraderID()).getWallet().setCoins(traders.get(buyPeek.getTraderID()).getWallet().getCoins() + buyPeek.getAmount());
					buyingOrders.poll();
				}
				successfulTransactions ++;
				if(buyingOrders.isEmpty() || sellingOrders.isEmpty()) {
					abc = false;
				}
			}
		}
	}
	/**
	 * This is constructor for Market class.
	 * @param fee this is market's fee.
	 */
	public Market(int fee) {
		this.fee = fee;
		buyingOrders = new PriorityQueue<BuyingOrder>();
		sellingOrders = new PriorityQueue<SellingOrder>();
		transactions = new ArrayList<Transaction>();
		successfulTransactions = 0;
	}
	/**
	 * This is a getter method for sellingOrders.
	 * @return sellingOrders
	 */
	public PriorityQueue<SellingOrder> getSellingOrders() {
		return sellingOrders;
	}
	/**
	 * This is getter method for buyingOrders.
	 * @return buyingOrders
	 */
	public PriorityQueue<BuyingOrder> getBuyingOrders() {
		return buyingOrders;
	}
	/**
	 * This is getter method for successfulTransactions.
	 * @return successfulTransactions
	 */
	public int getSuccessfulTransactions() {
		return successfulTransactions;
	}
}
