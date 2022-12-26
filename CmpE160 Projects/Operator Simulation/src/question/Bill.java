
package question;

public class Bill {

	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	private double limitingAmount;
	private double currentDebt;
	double totalMoneySpent;
	
	public Bill(double limitingAmount) {
		this.limitingAmount = limitingAmount;
		currentDebt = 0;
		totalMoneySpent = 0;
	}
	public boolean check(double amount) {
		if (amount + currentDebt > limitingAmount) {
			return true;
		}
		else {
			return false;
		}
	}
	public void add(double amount) {
		currentDebt += amount;
	}
	public void pay(double amount) {
		if (amount > currentDebt) {
			amount = currentDebt;
		}
		currentDebt -= amount;
		totalMoneySpent += amount;
	}
	public void changeTheLimit(double amount) {
		if (currentDebt <= amount) {
			limitingAmount = amount;
		}
	}
	public double getLimitingAmount() {
		return this.limitingAmount;
	}
	public double getCurrentDebt() {
		return this.currentDebt;
	}
	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

