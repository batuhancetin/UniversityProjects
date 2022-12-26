
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/** This class inherits HeavyContainer class and includes fields and methods for liquid containers. 
 * @author Batuhan Cetin
 */
public class LiquidContainer extends HeavyContainer {
	/** This is a constructor for LiquidContainer Class.
	 * @param ID this is the container's ID.
	 * @param weight this is the container's weight.
	 */
	public LiquidContainer(int ID, int weight) {
		super(ID, weight);
	}
	/** This method calculates the fuel consumption per km of liquid containers.
	 * @return This returns the amount of fuel that this liquid container consume per km.
	 */
	public double consumption() {
		return 4.00 * (double)weight;
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

