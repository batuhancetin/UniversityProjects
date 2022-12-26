/**
 * 
 * @author Batuhan Çetin
 *
 */
public class House implements Comparable<House> {
	/**
	 * This is id of the House.
	 */
	private int id;
	/**
	 * This is duration of the House.
	 */
	private int duration;
	/**
	 * This is rating of the House.
	 */
	private double rating;
	/**
	 * This is constructor of the House class.
	 * @param id This is id of the House.
	 * @param duration This is duration of the House.
	 * @param rating This is rating of the House.
	 */
	public House(int id, int duration, double rating) {
		this.id = id;
		this.duration = duration;
		this.rating = rating;
	}
	public int getId() {
		return id;
	}
	public int getDuration() {
		return duration;
	}
	public double getRating() {
		return rating;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public int compareTo(House other) {
		if (this.id > other.id) {
			return 1;
		}
		else if (this.id < other.id) {
			return -1;
		}
		else {
			return 0;
		}
	}
}
