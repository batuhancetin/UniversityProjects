package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import elements.BuyingOrder;
import elements.Market;
import elements.SellingOrder;
import elements.Trader;
/**
 * This is the Main class that makes implementations of the project.
 * @author Batuhan Cetin
 *
 */
public class Main {
	public static Random myRandom;
	public static void main(String[] args) throws FileNotFoundException {	
		Scanner input = new Scanner(new File(args[0]));
		PrintStream output = new PrintStream(new File(args[1]));
		myRandom = new Random(input.nextInt());
		Market market = new Market(input.nextInt());
		ArrayList<Trader> traders = new ArrayList<Trader>();
		int traderNumber = input.nextInt();
		int numberOfQueries = input.nextInt();
		int[] firstInts = new int[numberOfQueries];
		int traderidx;
		double pricex;
		double amountx;
		int invalidQueries = 0;
		for (int i = 0; i < traderNumber; i++) {
			traders.add(new Trader(input.nextDouble(), input.nextDouble()));
		}
		for (int a = 0; a < numberOfQueries; a++) {
			firstInts[a] = input.nextInt();
			if (firstInts[a] == 10) {
				traderidx = input.nextInt();
				pricex = input.nextDouble();
				amountx = input.nextDouble();
				if (traders.get(traderidx).getWallet().getDollars() >= amountx * pricex) {
					market.giveBuyOrder(new BuyingOrder(traderidx, amountx, pricex));
					traders.get(traderidx).getWallet().setBlockedDollars(amountx * pricex + traders.get(traderidx).getWallet().getBlockedDollars());
					traders.get(traderidx).getWallet().setDollars(traders.get(traderidx).getWallet().getDollars() - (amountx * pricex));
				}
				else {
					invalidQueries ++;
				}
				market.checkTransactions(traders);
			}
			else if (firstInts[a] == 11) {
				if (market.getSellingOrders().isEmpty()) {
					invalidQueries ++;
				}
				else {
					traderidx = input.nextInt();
					amountx = input.nextDouble();
					pricex = market.getSellingOrders().peek().getPrice();
					if (traders.get(traderidx).buy(amountx, pricex, market) == 1) {
						market.giveBuyOrder(new BuyingOrder(traderidx, amountx, pricex));
						traders.get(traderidx).getWallet().setBlockedDollars(traders.get(traderidx).getWallet().getBlockedDollars() + amountx * pricex);
						traders.get(traderidx).getWallet().setDollars(traders.get(traderidx).getWallet().getDollars() - (amountx * pricex));
					}
					else {
						invalidQueries ++;
					}
				}
				market.checkTransactions(traders);
			}
			else if (firstInts[a] == 20) {
				traderidx = input.nextInt();
				pricex = input.nextDouble();
				amountx = input.nextDouble();
				if (traders.get(traderidx).sell(amountx, pricex, market) == 1) {
					market.giveSellOrder(new SellingOrder(traderidx, amountx, pricex));
					traders.get(traderidx).getWallet().setBlockedCoins(traders.get(traderidx).getWallet().getBlockedCoins() + amountx);
					traders.get(traderidx).getWallet().setCoins(traders.get(traderidx).getWallet().getCoins() - amountx);
				}
				else {
					invalidQueries ++;
				}
				market.checkTransactions(traders);
			}
			else if (firstInts[a] == 21) {
				if (market.getBuyingOrders().isEmpty()) {
					invalidQueries ++;
				}
				else {
					traderidx = input.nextInt();
					pricex = market.getBuyingOrders().peek().getPrice();
					amountx = input.nextDouble();
					if (traders.get(traderidx).getWallet().getCoins() >= amountx) {
						market.giveSellOrder(new SellingOrder(traderidx, amountx, pricex));
						traders.get(traderidx).getWallet().setBlockedCoins(amountx + traders.get(traderidx).getWallet().getBlockedCoins());
						traders.get(traderidx).getWallet().setCoins(traders.get(traderidx).getWallet().getCoins() - amountx);
					}
					else {
						invalidQueries ++;
					}
				}
				market.checkTransactions(traders);
			}
			else if (firstInts[a] == 3) {
				traderidx = input.nextInt();
				amountx = input.nextDouble();
				traders.get(traderidx).getWallet().setDollars(traders.get(traderidx).getWallet().getDollars() + amountx);
			}
			else if (firstInts[a] == 4) {
				traderidx = input.nextInt();
				amountx = input.nextDouble();
				if (traders.get(traderidx).getWallet().getDollars() >= amountx) {
					traders.get(traderidx).getWallet().setDollars(traders.get(traderidx).getWallet().getDollars() - amountx);
				}
				else {
					invalidQueries ++;
				}
			}
			else if (firstInts[a] == 5) {
				traderidx = input.nextInt();
				double dollars = traders.get(traderidx).getWallet().getDollars() + traders.get(traderidx).getWallet().getBlockedDollars();
				double coins = traders.get(traderidx).getWallet().getCoins() + traders.get(traderidx).getWallet().getBlockedCoins();
				output.print("Trader " + traderidx + ": " + String.format("%.5f", dollars) + "$ " + String.format("%.5f", coins) + "PQ" + "\n");
			}
			else if (firstInts[a] == 777) {
				for (int i = 0; i < traders.size(); i++) {
					traders.get(i).getWallet().setCoins(traders.get(i).getWallet().getCoins() + myRandom.nextDouble()*10);
				}
			}
			else if (firstInts[a] == 500) {
				double coinAmount = 0;
				double dollarAmount = 0;
				for (BuyingOrder order : market.getBuyingOrders()) {
					dollarAmount += order.getAmount() * order.getPrice();
				}
				for (SellingOrder order : market.getSellingOrders()) {
					coinAmount += order.getAmount();
				}
				output.print("Current market size: " + String.format("%.5f", dollarAmount) + " " + String.format("%.5f", coinAmount) + "\n");
			}
			else if (firstInts[a] == 502) {
				output.print("Number of invalid queries: " + invalidQueries + "\n");
			}
			else if (firstInts[a] == 505) {
				double buyingPrice;
				double sellingPrice;
				double averagePrice;
				if(market.getBuyingOrders().isEmpty()) {
					sellingPrice = 0;
				}
				else {
					sellingPrice = market.getBuyingOrders().peek().getPrice();

				}
				if (market.getSellingOrders().isEmpty()) {
					buyingPrice = 0;
				}
				else {
					buyingPrice = market.getSellingOrders().peek().getPrice();
				}
				if (buyingPrice == 0 && sellingPrice == 0) {
					averagePrice = 0;
				}
				else if (buyingPrice == 0 && sellingPrice != 0) {
					averagePrice = sellingPrice;
				}
				else if (buyingPrice != 0 && sellingPrice == 0) {
					averagePrice = buyingPrice;
				}
				else {
					averagePrice = (buyingPrice + sellingPrice)/2;
				}
				output.print("Current prices: " + String.format("%.5f", sellingPrice) + " " + String.format("%.5f", buyingPrice) + " " + String.format("%.5f", averagePrice) + "\n");
			}
			else if (firstInts[a] == 555) {
				for (Trader trader : traders) {
					double dollars = trader.getWallet().getDollars() + trader.getWallet().getBlockedDollars();
					double coins = trader.getWallet().getCoins() + trader.getWallet().getBlockedCoins();
					output.print("Trader " + trader.getId() + ": " + String.format("%.5f", dollars) + "$ " + String.format("%.5f", coins) + "PQ" + "\n"); 
				}
			}
			else if (firstInts[a] == 666) {
				market.makeOpenMarketOperation(input.nextDouble(), traders);
				market.checkTransactions(traders);
			}
			else if (firstInts[a] == 501) {
				output.print("Number of successful transactions: " + market.getSuccessfulTransactions() + "\n");
			}
		}
	}
}

