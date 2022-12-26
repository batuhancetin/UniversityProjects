/**
 *
 * @author Batuhan Çetin
 *
 */
public class Student implements Comparable<Student> {
	/**
	 * This is id of the Student.
	 */
	private int id;
	/**
	 * This is name of the Student.
	 */
	private String name;
	/**
	 * This is duration of the Student.
	 */
	private int duration;
	/**
	 * This is rating of the Student.
	 */
	private double rating;
	/**
	 * This indicates that student is owner or not.
	 */
	private boolean owner;
	/**
	 * This is constructor of the Student class.
	 * @param id this is id of the student.
	 * @param name this is name of the student.
	 * @param duration this is duration of the student.
	 * @param rating this is rating of the student.
	 */
	public Student(int id, String name, int duration, double rating) {
		this.id = id;
		this.name = name;
		this.duration = duration;
		this.rating = rating;
		owner = false;
	}
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public int getDuration() {
		return duration;
	}
	public double getRating() {
		return rating;
	}
	public boolean getOwner() {
		return owner;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public void setRating(double rating) {
		this.rating = rating;
	}
	public void setOwner(boolean owner) {
		this.owner = owner;
	}
	public int compareTo(Student other) {
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
