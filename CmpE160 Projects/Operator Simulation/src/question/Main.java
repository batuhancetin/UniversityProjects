
package question;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class Main {


	public static void main(String args[]) {

		Customer[] customers;
		Operator[] operators;

		int C, O, N;

		File inFile = new File(args[0]);  // args[0] is the input file
		File outFile = new File(args[1]);  // args[1] is the output file
		try {
			PrintStream outstream = new PrintStream(outFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Scanner reader;
		try {
			reader = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("Cannot find input file");
			return;
		}

		C = reader.nextInt();
		O = reader.nextInt();
		N = reader.nextInt();

		customers = new Customer[C];
		operators = new Operator[O];

		//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE
		PrintStream outstream1;
		try {
		        outstream1 = new PrintStream(outFile);
		}catch(FileNotFoundException e2) {
		        e2.printStackTrace();
		        return;
		}
		int customernumber = 0;
		int operatornumber = 0;
		int[] firstInts = new int[N];
		int[] talkingIDs = new int[2];
		int[] messagingIDs = new int[2];
		int internetID; 
		int talkingAmount;
		int messagingAmount;
		double networkAmount;
		for (int i = 0; i < N; i++) {
			firstInts[i] = reader.nextInt();
			if (firstInts[i] == 1) {
				customers[customernumber] = new Customer(customernumber, reader.next(), reader.nextInt(), operators[reader.nextInt()], reader.nextDouble());
				customernumber ++;
				}
			else if (firstInts[i] == 2) {
				operators[operatornumber] = new Operator(operatornumber, reader.nextDouble(), reader.nextDouble(), reader.nextDouble(), reader.nextInt());
				operatornumber ++;
				}
			else if (firstInts[i] == 3) {
				talkingIDs[0] = reader.nextInt();
				talkingIDs[1] = reader.nextInt();
				talkingAmount = reader.nextInt();
				customers[talkingIDs[0]].talk(talkingAmount, customers[talkingIDs[1]]);
			}
			else if (firstInts[i] == 4) {
				messagingIDs[0] = reader.nextInt();
				messagingIDs[1] = reader.nextInt();
				messagingAmount = reader.nextInt();
				customers[messagingIDs[0]].message(messagingAmount, customers[messagingIDs[1]]);
			}
			else if (firstInts[i] == 5) {
				internetID = reader.nextInt();
				networkAmount = reader.nextDouble();
				customers[internetID].connection(networkAmount);
				if (internetID == 0) {
				}
			}
			else if (firstInts[i] == 6) {
				customers[reader.nextInt()].getBill().pay(reader.nextDouble());
			}
			else if (firstInts[i] == 7) {
				customers[reader.nextInt()].setOperator(operators[reader.nextInt()]);
			}
			else if (firstInts[i] == 8) {
				customers[reader.nextInt()].getBill().changeTheLimit(reader.nextDouble());
			}
		}
		for (int i = 0; i < O; i++) {
			outstream1.print("Operator " + operators[i].ID + " : " + operators[i].minute + " " + operators[i].quantity + " " + String.format("%.2f",operators[i].network) + "\n");
		}
		for (int i = 0; i < C; i++) {
			outstream1.print("Customer " + customers[i].ID + " : " + String.format("%.2f", customers[i].getBill().totalMoneySpent) + " " + String.format("%.2f", customers[i].getBill().getCurrentDebt()) + "\n");
		}
		int maxMinute = 0;
		for (int i = 0; i < C; i++) {
			if (customers[i].minute >= maxMinute) {
				maxMinute = customers[i].minute;
			}
		}
		for (int i = 0; i < C; i++) {
			if(customers[i].minute == maxMinute) {
				outstream1.print(customers[i].name + " : " + customers[i].minute + "\n");
				break;
			}
		}
		int maxQuantity = 0;
		for (int i = 0; i < C; i++) {
			if (customers[i].quantity >= maxQuantity) {
				maxQuantity = customers[i].quantity;
			}
		}
		for (int i = 0; i < C; i++) {
			if (customers[i].quantity == maxQuantity) {
				outstream1.print(customers[i].name + " : " + customers[i].quantity + "\n");
				break;
			}
		}
		double maxNetwork = 0;
		for (int i = 0; i < C; i++) {
			if (customers[i].network >= maxNetwork) {
				maxNetwork = customers[i].network;
			}
		}
		for (int i = 0; i < C; i++) {
			if (customers[i].network == maxNetwork) {
				outstream1.print(customers[i].name + " : " + String.format("%.2f", customers[i].network) + "\n");
				break;
			}
		}
		//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE
	} 
}

