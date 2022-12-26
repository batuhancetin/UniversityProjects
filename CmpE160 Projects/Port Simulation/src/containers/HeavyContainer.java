
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package containers;
/** This class inherits Container class and includes fields and methods for heavy containers. 
 * @author Batuhan Cetin
 */
public class HeavyContainer extends Container {
	/** This is a constructor for HeavyContainer Class.
	 * @param ID this is the container's ID.
	 * @param weight this is the container's weight.
	 */
	public HeavyContainer(int ID, int weight) {
		super(ID, weight);
	}
	/** This method calculates the fuel consumption per km of heavy containers.
	 * @return This returns the amount of fuel that this heavy container consume per km.
	 */
	public double consumption() {
		return 3.00 * (double)weight;
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

