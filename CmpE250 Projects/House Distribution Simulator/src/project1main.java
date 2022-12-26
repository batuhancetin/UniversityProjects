import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/**
 * This is the main class.
 * @author Batuhan Çetin
 *
 */
public class project1main {
	public static void main(String[] args) throws FileNotFoundException {
		Scanner input = new Scanner(new File(args[0]));
		PrintStream output = new PrintStream(new File(args[1]));
		ArrayList<House> houses = new ArrayList<House>();
		ArrayList<Student> students = new ArrayList<Student>();
		/**
		 * This is id of the house.
		 */
		int houseId;
		/**
		 * This is duration of the house.
		 */
		int houseDuration;
		/**
		 * This is rating of the house.
		 */
		double houseRating;
		/**
		 * This is id of the student.
		 */
		int studentId;
		/**
		 * This is name of the student.
		 */
		String studentName;
		/**
		 * This is duration of the student.
		 */
		int studentDuration;
		/**
		 * This is rating of thr student.
		 */
		double studentRating;
		/**
		 * This is the maximum remaining semester that students have.
		 */
		int maxSemester = 0;
		while (input.hasNext()) {
			if (input.hasNext("h")) {
				input.next();
				houseId = Integer.parseInt(input.next());
				houseDuration = Integer.parseInt(input.next());
				houseRating = Double.parseDouble(input.next());
				houses.add(new House(houseId, houseDuration, houseRating));
			}
			else {
				input.next();
				studentId = Integer.parseInt(input.next());
				studentName = input.next();
				studentDuration = Integer.parseInt(input.next());
				studentRating = Double.parseDouble(input.next());
				students.add(new Student(studentId, studentName, studentDuration, studentRating));
			}
		}
		for (Student abc : students) {
			if (abc.getDuration() > maxSemester) {
				maxSemester = abc.getDuration();
			}
		}
		Collections.sort(students);
		Collections.sort(houses);
		for (int i = 0; i < maxSemester; i++) {
			int a = 0;
			while (a < students.size()) {
				if (students.get(a).getDuration() != 0 && students.get(a).getOwner() == false) {
					int b = 0;
					while (b < houses.size()) {
						if (houses.get(b).getDuration() == 0) {
							if (houses.get(b).getRating() >= students.get(a).getRating()) {
								houses.get(b).setDuration(students.get(a).getDuration());
								students.get(a).setOwner(true);
								break;
							}
						}
						b++;
					}
				}
				if (students.get(a).getDuration() != 0) {
					students.get(a).setDuration(students.get(a).getDuration() - 1);
				}
				a++;
			}
			int c = 0;
			while (c < houses.size()) {
				if(houses.get(c).getDuration() != 0) {
					houses.get(c).setDuration(houses.get(c).getDuration() - 1);
				}
				c++;
			}
		}
		int d = 0;
		while (d < students.size()) {
			if (students.get(d).getOwner() == false) {
				output.println(students.get(d).getName());
			}
			d++;
		}
		input.close();
		output.close();
	}
}		
