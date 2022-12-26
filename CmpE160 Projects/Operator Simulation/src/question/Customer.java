
package question;

public class Customer {
	
	//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
	int ID;
	String name;
	private int age;
	private Operator operator;
	private Bill bill;
	int minute;
	int quantity;
	double network;	
	public Customer(int ID, String name, int age, Operator operator, double limitingAmount) {
		this.ID = ID;
		this.name = name;
		this.age = age;
		this.operator = operator;
		bill = new Bill(limitingAmount);
		minute = 0;
		quantity = 0;
		network = 0;
	}
	public void talk(int minute, Customer other) {
		if (this != other) {
			if (bill.check(operator.calculateTalkingCost(minute, this)) == false) {
				bill.add(operator.calculateTalkingCost(minute, this));
				this.operator.minuteCalculator(minute);
				other.operator.minuteCalculator(minute);
				this.minute += minute;
				other.minute += minute;
			}
		}
	}
	public void message(int quantity, Customer other) {
		if (this != other) {
			if (bill.check(operator.calculateMessageCost(quantity, this, other)) == false) {
				bill.add(operator.calculateMessageCost(quantity, this, other));
				this.operator.quantityCalculator(quantity);
				this.quantity += quantity;
			}
		}
	}
	public void connection(double amount) {
		if (bill.check(operator.calculateNetworkCost(amount)) == false) {
			bill.add(operator.calculateNetworkCost(amount));
			this.operator.networkCalculator(amount);
			this.network += amount;
		}
	}
	public int getAge() {
		return age;
	}
	public Operator getOperator() {
		return operator;
	}
	public Bill getBill() {
		return bill;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public void setBill(Bill bill) {
		this.bill = bill;
	}
	//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
}

