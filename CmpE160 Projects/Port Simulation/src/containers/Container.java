
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/** This is an abstract class that implements Comparable<Container> interface and includes fields and methods that indicates properties of containers.
 * @author Batuhan Cetin
 */
public abstract class Container implements Comparable<Container> {
	/** This is the container's ID.
	 */
	protected final int ID;
	/** This is the container's weight.
	 */
	protected final int weight;
	/** This is a constructor for Container Class.
	 * @param ID this is the container's ID.
	 * @param weight this is the container's weight.
	 */
	public Container(int ID, int weight) {
		this.ID = ID;
		this.weight = weight;
	}
	/** This is an abstract method that calculates the fuel consumption per km of the container. 
	 * @return This returns the fuel consumption per km of the container.
	 */
	public abstract double consumption();
	/** This is an equals method for Container Class.
	 * @param other this is the other container in the equality.
	 * @return returns true if the containers are the same, otherwise returns false.
	 */
	public boolean equals(Container other) {
		return this.ID == other.ID && this.weight == other.weight && this.getClass() == other.getClass();
	}
	/** This is a getter method for weight.
	 * @return returns weight of the container.
	 */
	public int getWeight() {
		return weight;
	}
	/** This is a toString method for Container Class.
	 * @return returns a String that shows the container's ID and weight.
	 */
	public String toString() {
		return this.ID + " " + this.weight;
	}
	/** This method compares the containers by their ID.
	 * @param other this is the other container in the comparison.
	 */
	public int compareTo(Container other) {
		if(this.ID > other.ID) {
			return 1;
		}
		else if (this.ID == other.ID) {
			return 0;
		}
		else {
			return -1;
		}
	}
	/** This is the getter method for ID.
	 * @return returns the container's ID.
	 */
	public int getID() {
		return ID;
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

