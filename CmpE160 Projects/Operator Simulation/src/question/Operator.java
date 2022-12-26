
package question;

public class Operator {
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	int ID;
	private double talkingCharge;
	private double messageCost;
	private double networkCharge;
	private int discountRate;
	int minute;
	int quantity;
	double network;
	
	public Operator(int ID, double talkingCharge, double messageCost, double networkCharge, int discountRate) {
		this.ID = ID;
		this.talkingCharge = talkingCharge;
		this.messageCost = messageCost;
		this.networkCharge = networkCharge;
		this.discountRate = discountRate;
		minute = 0;
		quantity = 0;
		network = 0;
	}
	public double calculateTalkingCost(int minute, Customer customer) {
		if (customer.getAge() <= 65 && customer.getAge() >= 18) { 
			return minute * talkingCharge;
		}
		else {
			return minute * talkingCharge * (1 - (double)discountRate/100);
		}
	}
	public double calculateMessageCost(int quantity, Customer customer, Customer other) {
		if(customer.getOperator() == other.getOperator()) {
			return quantity * messageCost * (1 - (double)discountRate/100);
		}
		else {
			return quantity * messageCost;
		}
	}
	public double calculateNetworkCost(double amount) {
		return amount * networkCharge;
	}
	public void minuteCalculator(int minute) {
		this.minute += minute;
	}
	public void quantityCalculator(int quantity) {
		this.quantity += quantity;
	}
	public void networkCalculator(double amount) {
		this.network += amount;
	}
	public double getTalkingCharge() {
		return talkingCharge;
	}
	public double getMessageCost() {
		return messageCost;
	}
	public double getNetworkCharge() {
		return networkCharge;
	}
	public int getDiscountRate() {
		return discountRate;
	}
	public void setTalkingCharge(double talkingCharge) {
		this.talkingCharge = talkingCharge;
	}
	public void setMessageCost(double messageCost) {
		this.messageCost = messageCost;
	}
	public void setNetworkCharge(double networkCharge) {
		this.networkCharge = networkCharge;
	}
	public void setDiscountRate(int discountRate) {
		this.discountRate = discountRate;
	}
	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

