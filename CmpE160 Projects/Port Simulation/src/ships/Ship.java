
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ships;

import java.util.ArrayList;
import java.util.Collections;

import containers.BasicContainer;
import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import interfaces.IShip;
import ports.Port;
/** This class implements IShip and Comparable<Ship> interfaces and includes fields that shows the properties of ships, and methods that make calculations and checks about the properties of ships.
 * @author Batuhan Cetin
 */
public class Ship implements IShip, Comparable<Ship> {
	/** This is the ship's ID.
	 */
	private final int ID;
	/** This is the ship's current fuel.
	 */
	private double fuel;
	/** This is the ship's current port.
	 */
	private Port currentPort;
	/** This is the ship's maximum weight capacity.
	 */
	private final int totalWeightCapacity;
	/** This is the ship's maximum container capacity.
	 */
	private final int maxNumberOfAllContainers;
	/** This is the ship's maximum heavy container capacity.
	 */
	private final int maxNumberOfHeavyContainers;
	/** This is the ship's maximum liquid container capacity.
	 */
	private final int maxNumberOfLiquidContainers;
	/** This is the ship's maximum refrigerated container capacity.
	 */
	private final int maxNumberOfRefrigeratedContainers;
	/** This is the ship's the amount of fuel consumption per km.
	 */
	private final double fuelConsumptionPerKM;
	/** This is the ship's and containers' total weight.
	 */
	public int totalWeight;
	/** This is the ship's number of containers.
	 */
	public int numberOfAllContainers;
	/** This is the ship's number of heavy containers.
	 */
	public int numberOfHeavyContainers;
	/** This is the ship's number liquid of containers.
	 */
	public int numberOfLiquidContainers;
	/** This is the ship's number of refrigerated containers.
	 */
	public int numberOfRefrigeratedContainers;
	/** This is an arraylist that includes all of the containers in the ship.
	 */
	private ArrayList<Container> containers;
	/** This is a constructor for Ship Class.
	 * @param ID The ship's ID.
	 * @param p The ship's current port.
	 * @param totalWeightCapacity The ship's maximum weight capacity.
	 * @param maxNumberOfAllContainers The ship's maximum container capacity.
	 * @param maxNumberOfHeavyContainers The ship's maximum heavy container capacity.
	 * @param maxNumberOfRefrigeratedContainers The ship's maximum refrigerated container capacity.
	 * @param maxNumberOfLiquidContainers The ship's maximum liquid container capacity.
	 * @param fuelConsumptionPerKM The ship's amount of fuel consumption per km.
	 */
	public Ship(int ID, Port p, int totalWeightCapacity, int maxNumberOfAllContainers, int maxNumberOfHeavyContainers, int maxNumberOfRefrigeratedContainers, int maxNumberOfLiquidContainers, double fuelConsumptionPerKM) {
		this.ID = ID;
		this.currentPort = p;
		this.totalWeightCapacity = totalWeightCapacity;
		this.maxNumberOfAllContainers = maxNumberOfAllContainers;
		this.maxNumberOfHeavyContainers = maxNumberOfHeavyContainers;
		this.maxNumberOfLiquidContainers = maxNumberOfLiquidContainers;
		this.maxNumberOfRefrigeratedContainers = maxNumberOfRefrigeratedContainers;
		this.fuelConsumptionPerKM = fuelConsumptionPerKM;
		fuel = 0;
		containers = new ArrayList<Container>();
		totalWeight = 0;
		numberOfAllContainers = 0;
		numberOfHeavyContainers = 0;
		numberOfLiquidContainers = 0;
		numberOfRefrigeratedContainers = 0;
	}
	/** This method sorts containers arraylist by ID.
	 * @return This returns the list of all containers currently in the ship sorted by ID.
	 */
	public ArrayList<Container> getCurrentContainers() {
		Collections.sort(containers);
		return containers;
	}
	/** This method calculates the required amount of fuel per km to sail to the given port.
	 * @return This returns the sum of the containers' fuel consumption per km in the ship and the ship's fuel consumption per km.
	 */
	public double calculateConsumption() {
		double containerConsumption = 0;
		double totalConsumption = 0;
		for (Container abc : containers) {
			containerConsumption += abc.consumption();
		}
		totalConsumption = (containerConsumption + this.fuelConsumptionPerKM);
		return totalConsumption;
	}
	/** This method checks whether the ship can sail to given port or not.
	 * @param p This is the given port that the ship sail to.
	 * @return This returns true if the ship is able to sail to the given port otherwise returns false.
	 */
	public boolean sailTo(Port p) {
		if (this.fuel >= calculateConsumption() * currentPort.getDistance(p)) {
			this.setFuel(this.fuel - this.calculateConsumption() * this.currentPort.getDistance(p));
			this.getCurrentPort().outgoingShip(this);
			this.setCurrentPort(p);
			this.getCurrentPort().incomingShip(this);
			return true;
		}
		else {
			return false;
		}
	}
	/** This method refuel the ship.
	 * @param newFuel This is the parameter that indicates the fuel that is added to the ship.
	 */
	public void reFuel(double newFuel) {
		this.fuel += newFuel;
	}
	/** This method compares the ships by their ID.
	 * @param other this is the other ship in the comparison.
	 */
	public int compareTo(Ship other) {
		if (this.ID == other.ID) {
			return 0;
		}
		else if (this.ID > other.ID) {
			return 1;
		}
		else {
			return -1;
		}
	}
	/** This method checks whether the container can load to the ship or not.
	 * @param cont This is the parameter that indicates the container that is loaded to the ship.
	 * @return This returns true if the container can be loaded to the ship, otherwise returns false.
	 */
	public boolean load(Container cont) {
		if (this.currentPort.getContainers().contains(cont) && !this.containers.contains(cont)) {
			if (this.totalWeight + cont.getWeight() <= this.totalWeightCapacity) {
				if (cont instanceof HeavyContainer) {
					if (cont instanceof LiquidContainer) {
						if(this.numberOfAllContainers + 1 <= this.maxNumberOfAllContainers) {
							if (this.numberOfHeavyContainers + 1 <= this.maxNumberOfHeavyContainers) {
								if (this.numberOfLiquidContainers + 1 <= this.maxNumberOfLiquidContainers) {
									this.numberOfLiquidContainers += 1;
									this.numberOfHeavyContainers += 1;
									this.numberOfAllContainers += 1;
									this.totalWeight += cont.getWeight();
									this.containers.add(cont);
									this.currentPort.getContainers().remove(cont);
									return true;
								}
								else {
									return false;
								}
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}
					else if (cont instanceof RefrigeratedContainer) {
						if(this.numberOfAllContainers + 1 <= this.maxNumberOfAllContainers) {
							if (this.numberOfHeavyContainers + 1 <= this.maxNumberOfHeavyContainers) {
								if (this.numberOfRefrigeratedContainers + 1 <= this.maxNumberOfRefrigeratedContainers) {
									this.numberOfRefrigeratedContainers += 1;
									this.numberOfHeavyContainers += 1;
									this.numberOfAllContainers += 1;
									this.totalWeight += cont.getWeight();
									this.containers.add(cont);
									this.currentPort.getContainers().remove(cont);
									return true;
								}
								else {
									return false;
								}
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}
					else {
						if (this.numberOfAllContainers + 1 <= this.maxNumberOfAllContainers) {
							if (this.numberOfHeavyContainers + 1 <= this.maxNumberOfHeavyContainers) {
								this.numberOfHeavyContainers += 1;
								this.numberOfAllContainers += 1;
								this.totalWeight += cont.getWeight();
								this.containers.add(cont);
								this.currentPort.getContainers().remove(cont);
								return true;
							}
							else {
								return false;
							}
						}
						else {
							return false;
						}
					}
				}
				else {
					if (this.numberOfAllContainers + 1 <= this.maxNumberOfAllContainers) {
						this.numberOfAllContainers += 1;
						this.totalWeight += cont.getWeight();
						this.containers.add(cont);
						this.currentPort.getContainers().remove(cont);
						return true;
					}
					else {
						return false;
					}
				}
			}
			else {
				return false;
			}
		}
		return false;
	}
	/** This method checks whether the container can be unloaded from the ship or not.
	 * @param cont This is the parameter that indicates the container that is unloaded from the ship.
	 * @return This returns true if the container can be unloaded from the ship, otherwise returns false.
	 */
	public boolean unLoad(Container cont) {
		if (this.containers.contains(cont) && !this.currentPort.getContainers().contains(cont)) {
			if (cont instanceof HeavyContainer) {
				if (cont instanceof RefrigeratedContainer) {
					this.numberOfRefrigeratedContainers -= 1;
					this.numberOfHeavyContainers -= 1;
					this.numberOfAllContainers -= 1;
					this.totalWeight -= cont.getWeight();
					this.containers.remove(cont);
					this.currentPort.getContainers().add(cont);
					return true;
				}
				else if (cont instanceof LiquidContainer) {
					this.numberOfLiquidContainers -= 1;
					this.numberOfHeavyContainers -= 1;
					this.numberOfAllContainers -= 1;
					this.totalWeight -= cont.getWeight();
					this.containers.remove(cont);
					this.currentPort.getContainers().add(cont);
					return true;
				}
				else {
					this.numberOfHeavyContainers -= 1;
					this.numberOfAllContainers -= 1;
					this.totalWeight -= cont.getWeight();
					this.containers.remove(cont);
					this.currentPort.getContainers().add(cont);
					return true;
				}
			}
			else {
				this.numberOfAllContainers -= 1;
				this.totalWeight -= cont.getWeight();
				this.containers.remove(cont);
				this.currentPort.getContainers().add(cont);
				return true;
			}
		}
		else {
			return false;
		}
	}
	/** This is a getter method for currentPort.
	 * @return returns the ship's current port.
	 */
	public Port getCurrentPort() {
		return currentPort;
	}
	/** This is a setter method for currentPort.
	 * @param currentPort this is the ship's current port.
	 */
	public void setCurrentPort(Port currentPort) {
		this.currentPort = currentPort;
	}
	/** This is a toString method for Ship Class.
	 * @return returns a String that shows the ship's ID, current fuel and containers.
	 */
	public String toString() {
		return "Ship " + this.ID + ": " + String.format("%.2f", this.fuel) + listofContainers();
	}
	/** This method parse containers arraylist to the different types of container arraylists.
	 * @return This returns the containers in the ship type by type.
	 */
	public String listofContainers() {
		ArrayList<Container> basicContainers = new ArrayList<Container>();
		ArrayList<Container> heavyContainers = new ArrayList<Container>();
		ArrayList<Container> liquidContainers = new ArrayList<Container>();
		ArrayList<Container> refrigeratedContainers = new ArrayList<Container>();
		String basic = "\n    BasicContainer:";
		String heavy = "\n    HeavyContainer:";
		String liquid = "\n    LiquidContainer:";
		String refrigerated = "\n    RefrigeratedContainer:";
		for (int i = 0; i < getCurrentContainers().size(); i ++) {
			if (getCurrentContainers().get(i) instanceof BasicContainer) {
				basicContainers.add(getCurrentContainers().get(i));
			}
			else if (getCurrentContainers().get(i) instanceof LiquidContainer) {
				liquidContainers.add(getCurrentContainers().get(i));
			}
			else if (getCurrentContainers().get(i) instanceof RefrigeratedContainer) {
				refrigeratedContainers.add(getCurrentContainers().get(i));
			}
			else if (getCurrentContainers().get(i) instanceof HeavyContainer) {
				heavyContainers.add(getCurrentContainers().get(i));
			}
		}
		if (basicContainers.size() == 0) {
			basic = "";
		}
		else {
			for (int i = 0; i < basicContainers.size(); i ++) {
				basic += " " + basicContainers.get(i).getID();
			}
		}
		if (heavyContainers.size() == 0) {
			heavy = "";
		}
		else {
			for (int i = 0; i < heavyContainers.size(); i ++) {
				heavy += " " + heavyContainers.get(i).getID();
			}
		}
		if (refrigeratedContainers.size() == 0) {
			refrigerated = "";
		}
		else {
			for (int i = 0; i < refrigeratedContainers.size(); i ++) {
				refrigerated += " " + refrigeratedContainers.get(i).getID();
			}
		}
		if (liquidContainers.size() == 0) {
			liquid = "";
		}
		else {
			for (int i = 0; i < liquidContainers.size(); i ++) {
				liquid += " " + liquidContainers.get(i).getID();
			}
		}
		
		return basic + heavy + refrigerated + liquid;
	}
	/** This is a getter method for fuel.
	 * @return returns the ship's fuel.
	 */
	public double getFuel() {
		return fuel;
	}
	/** This is a setter method for fuel.
	 * @param fuel this is the ship's fuel.
	 */
	public void setFuel(double fuel) {
		this.fuel = fuel;
	}
	/** This is a getter method for containers.
	 * @return returns containers.
	 */
	public ArrayList<Container> getContainers() {
		return containers;
	}
	/** This is a getter method for fuelConsumptionPerKM.
	 * @return returns fuelConsumptionPerKM.
	 */
	public double getFuelConsumptionPerKM() {
		return fuelConsumptionPerKM;
	}
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

