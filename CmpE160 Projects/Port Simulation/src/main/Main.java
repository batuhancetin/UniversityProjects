
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import containers.BasicContainer;
import containers.Container;
import containers.HeavyContainer;
import containers.LiquidContainer;
import containers.RefrigeratedContainer;
import ports.Port;
import ships.Ship;
/** This is main method that reads input file, makes calculations and implementations and writes output file.
 * @author Batuhan Cetin
 */
public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		
		//
		// Main receives two arguments: path to input file and path to output file.
		// You can assume that they will always be provided, so no need to check them.
		// Scanner and PrintStream are already defined for you.
		// Use them to read input and write output.
		// 
		// Good Luck!
		// 
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		int N = in.nextInt();
		int[] firstInts = new int[N];
		ArrayList<Container> containers = new ArrayList<Container>();
		ArrayList<Port> ports = new ArrayList<Port>();
		ArrayList<Ship> ships = new ArrayList<Ship>();
		int portNumber = 0;
		int shipNumber = 0;
		int containerNumber = 0;
		int containerWeight, containerPortID;
		int containerID, shipID, portID;
		
		for (int i = 0; i < N; i++) {
			firstInts[i] = in.nextInt();
			if (firstInts[i] == 1) {
				containerPortID = in.nextInt();
				containerWeight = in.nextInt();
				if (in.hasNext("R")) {
					in.next();
					containers.add(new RefrigeratedContainer(containerNumber, containerWeight));
				}
				else if (in.hasNext("L")) {
					in.next();
					containers.add(new LiquidContainer(containerNumber, containerWeight));
				}
				else {
					if (containerWeight <= 3000) {
						containers.add(new BasicContainer(containerNumber, containerWeight));
					}
					else {
						containers.add(new HeavyContainer(containerNumber, containerWeight));
					}
				}
				ports.get(containerPortID).getContainers().add(containers.get(containerNumber));
				containerNumber ++;
			}
			else if (firstInts[i] == 2) {
				ships.add(new Ship(shipNumber, ports.get(in.nextInt()), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextInt(), in.nextDouble()));
				ships.get(shipNumber).getCurrentPort().incomingShip(ships.get(shipNumber));
				shipNumber ++;
			}
			else if (firstInts[i] == 3) {
				ports.add(new Port(portNumber, in.nextDouble(), in.nextDouble()));
				portNumber ++;
			}
			else if (firstInts[i] == 4) {
				shipID = in.nextInt();
				containerID = in.nextInt();
				ships.get(shipID).load(containers.get(containerID));
			}
			else if (firstInts[i] == 5) {
				shipID = in.nextInt();
				containerID = in.nextInt();
				ships.get(shipID).unLoad(containers.get(containerID));
			}
			else if (firstInts[i] == 6) {
				shipID = in.nextInt();
				portID = in.nextInt();
				ships.get(shipID).sailTo(ports.get(portID));
			}
			else if (firstInts[i] == 7) {
				ships.get(in.nextInt()).reFuel(in.nextDouble());
			}
		}
		for (int i = 0; i < ports.size(); i ++) {
			if (i == 0) {
				out.print(ports.get(i));
			}
			else {
				out.print("\n" + ports.get(i));
			}
			for (int a = 0; a < ports.get(i).getCurrent().size(); a ++) {
				out.print("\n  " + ports.get(i).getCurrent().get(a));
			}
		}
		in.close();
		out.close();
	}
}



//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

