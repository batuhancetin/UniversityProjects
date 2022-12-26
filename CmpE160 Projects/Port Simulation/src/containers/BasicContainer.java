
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/** This class inherits Container class and includes fields and methods for basic containers. 
 * @author Batuhan Cetin
 */
public class BasicContainer extends Container {
	/** This is a constructor for BasicContainer Class.
	 * @param ID this is the container's ID.
	 * @param weight this is the container's weight.
	 */
	public BasicContainer(int ID, int weight) {
		super(ID, weight);
	}
	/** This method calculates the fuel consumption per km of basic containers.
	 * @return This returns the amount of fuel that this basic container consume per km.
	 */
	public double consumption() {
		return 2.50 * (double)weight;
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

