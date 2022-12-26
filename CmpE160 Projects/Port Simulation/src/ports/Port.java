
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package ports;

import java.util.ArrayList;
import java.util.Collections;

import containers.BasicContainer;
import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import interfaces.IPort;
import ships.Ship;
/**
 * This class implements IPort interface and includes fields that shows properties of ports, and methods that make calculations and implementations about ports.
 * @author Batuhan Cetin
 */
public class Port implements IPort {
	/** This is the port's ID.
	 */
	private final int ID;
	/** This is the port's X coordinate.
	 */
	private final double X;
	/** This is the port's Y coordinate.
	 */
	private final double Y;
	/** This is the arraylist of containers in the port.
	 */
	private ArrayList<Container> containers;
	/** This is the arraylist of ships that have been previously in the port.
	 */
	private ArrayList<Ship> history;
	/** This is the arraylist of ships that is in the port.
	 */
	private ArrayList<Ship> current;
	/** This is a constructor for the Port Class.
	 * @param ID this is the ID of the port.
	 * @param X this is the X coordinate of the port.
	 * @param Y this is the Y coordinate of the port.
	 */
	public Port(int ID, double X, double Y) {
		this.ID = ID;
		this.X = X;
		this.Y = Y;
		containers = new ArrayList<Container>();
		history = new ArrayList<Ship>();
		current = new ArrayList<Ship>();
	}
	/** This method calculates the distance between this port and other port.
	 * @param other This is the parameter that indicates the other port.
	 * @return This returns the distance between this port and other port.
	 */
	public double getDistance(Port other) {
		return Math.sqrt(Math.pow(this.X - other.X, 2) + Math.pow(this.Y - other.Y, 2));
	}
	/** This method adds the given ship to current arraylist that shows the current ships in this port.
	 * @param s This parameter indicates the ship that will be added to current arraylist.
	 */
	public void incomingShip(Ship s) {
		if (current.contains(s) == false) {
			current.add(s);
		}
	}
	/** This method adds the given ship to history arraylist that shows the ships that have been previously in this port.
	 * @param s This parameter indicates the ship that will be added to history arraylist.
	 */
	public void outgoingShip(Ship s) {
		current.remove(s);
		if (history.contains(s) == false) {
			history.add(s);
		}
	}
	/** This is a getter method for containers.
	 * @return returns containers.
	 */
	public ArrayList<Container> getContainers() {
		return containers;
	}
	/** This is a toString method for Port class.
	 * @return returns a String that shows the port's ID, location and containers.
	 */
	public String toString() {
		return "Port " + this.ID + ": (" + String.format("%.2f", this.X) + ", " + String.format("%.2f", this.Y) + ")" + listofContainers();
	}
	/** This method parse container arraylist to the different types of container arraylists.
	 * @return This returns the containers in the port type by type.
	 */
	public String listofContainers() {
		ArrayList<Container> basicContainers = new ArrayList<Container>();
		ArrayList<Container> heavyContainers = new ArrayList<Container>();
		ArrayList<Container> liquidContainers = new ArrayList<Container>();
		ArrayList<Container> refrigeratedContainers = new ArrayList<Container>();
		String basic = "\n  BasicContainer:";
		String heavy = "\n  HeavyContainer:";
		String liquid = "\n  LiquidContainer:";
		String refrigerated = "\n  RefrigeratedContainer:";
		Collections.sort(containers);
		for (int i = 0; i < containers.size(); i ++) {
			if (containers.get(i) instanceof BasicContainer) {
				basicContainers.add(containers.get(i));
			}
			else if (containers.get(i) instanceof LiquidContainer) {
				liquidContainers.add(containers.get(i));
			}
			else if (containers.get(i) instanceof RefrigeratedContainer) {
				refrigeratedContainers.add(containers.get(i));
			}
			else if (containers.get(i) instanceof HeavyContainer) {
				heavyContainers.add(containers.get(i));
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
	/** This is a getter method for X.
	 * @return returns X.
	 */
	public double getX() {
		return X;
	}
	/** This is a getter method for Y.
	 * @return returns Y.
	 */
	public double getY() {
		return Y;
	}
	/** This is a getter method for history.
	 * @return returns history.
	 */
	public ArrayList<Ship> getHistory() {
		return history;
	}
	/** This is a getter method for current.
	 * @return returns current.
	 */
	public ArrayList<Ship> getCurrent() {
		Collections.sort(current);
		return current;
	}
	/** This is a setter method for current.
	 * @param current this is the current ships in the port.
	 */
	public void setCurrent(ArrayList<Ship> current) {
		this.current = current;
	}
	/** This is a setter method for history.
	 * @param history this is the ships that have been previously in the port.
	 */
	public void setHistory(ArrayList<Ship> history) {
		this.history = history;
	}
	/** This is a getter method for ID.
	 * @return returns ID.
	 */
	public int getID() {
		return ID;
	}
}




//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

