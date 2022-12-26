import java.awt.image.AreaAveragingScaleFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;

public class project4main {
    public static void discharge(Vertex ver, ArrayList<Vehicle> vehicles, ArrayList<Vehicle> vehicles2, ArrayDeque<Vertex> deque, Vertex source, Vertex sink) {
        int n = ver.edges.size();
        while (ver.excessflow > 0) {
            if (ver.seen < n) {
                Vertex ver2 = ver.edges.get(ver.seen).v;
                if (ver.edges.get(ver.seen).capacity - ver.edges.get(ver.seen).flow > 0 && ver.height > ver2.height) {
                    push(ver, ver2, deque, source, sink);
                }
                else {
                    ver.seen ++;
                }
            }
            else {
                relabel(ver, vehicles2, vehicles);
                ver.seen = 0;
            }
        }
    }
    public static void reverseEdge(Edge edge, int myFlow) {
        for (Edge value : edge.v.edges) {
            if (value.v == edge.u) {
                value.flow -= myFlow;
                return;
            }
        }
        edge.v.edges.add(new Edge(edge.v, edge.u, -myFlow, 0));
    }
    public static void push(Vertex ver, Vertex ver2, ArrayDeque<Vertex> deque, Vertex source, Vertex sink) {
        int myFlow = Math.min(ver.excessflow, ver.edges.get(ver.seen).capacity - ver.edges.get(ver.seen).flow);
        ver.excessflow -= myFlow;
        ver2.excessflow += myFlow;
        ver.edges.get(ver.seen).flow += myFlow;
        reverseEdge(ver.edges.get(ver.seen), myFlow);
        if (!ver2.equals(source) && !ver2.equals(sink) && !deque.contains(ver2)) {
            deque.addFirst(ver2);
        }
    }
    public static void relabel(Vertex ver, ArrayList<Vehicle> vehicles, ArrayList<Vehicle> relabeled) {
        int min_height = Integer.MAX_VALUE;
        for (Edge edge : ver.edges) {
            if (edge.flow != edge.capacity && ver.height < min_height) {
                min_height = ver.height;
            }
        }
        ver.height = min_height + 1;
        if (vehicles.contains(ver) && !relabeled.contains(ver)) {
            relabeled.add(vehicles.get(vehicles.indexOf(ver)));
        }
    }
    public static void main (String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File(args[0]));
        PrintStream output = new PrintStream(new File(args[1]));
        int greenTrain = Integer.parseInt(input.next());
        ArrayList<Vehicle> allVehicles = new ArrayList<>();
        ArrayList<Vehicle> greenTrains = new ArrayList<>();
        int totalbag = 0;
        for (int i = 0; i < greenTrain; i++) {
            Vehicle vehicle = new Vehicle(Integer.parseInt(input.next()));
            allVehicles.add(vehicle);
            greenTrains.add(vehicle);
        }
        int redTrain = Integer.parseInt(input.next());
        ArrayList<Vehicle> redTrains = new ArrayList<>();
        for (int i = 0; i < redTrain; i++) {
            Vehicle vehicle = new Vehicle(Integer.parseInt(input.next()));
            allVehicles.add(vehicle);
            redTrains.add(vehicle);
        }
        int greenReindeer = Integer.parseInt(input.next());
        ArrayList<Vehicle> greenReindeers = new ArrayList<>();
        for (int i = 0; i < greenReindeer; i++) {
            Vehicle vehicle = new Vehicle(Integer.parseInt(input.next()));
            allVehicles.add(vehicle);
            greenReindeers.add(vehicle);
        }
        int redReindeer = Integer.parseInt(input.next());
        ArrayList<Vehicle> redReindeers = new ArrayList<>();
        for (int i = 0; i < redReindeer; i++) {
            Vehicle vehicle = new Vehicle(Integer.parseInt(input.next()));
            allVehicles.add(vehicle);
            redReindeers.add(vehicle);
        }
        Vertex source = new Vertex(0);
        int bagNumber = Integer.parseInt(input.next());
        ArrayList<Bag> bags = new ArrayList<>();
        String name;
        boolean b = false;
        boolean c = false;
        boolean d = false;
        boolean e = false;
        boolean bd = false;
        boolean be = false;
        boolean cd = false;
        boolean ce = false;
        int indexb = -1;
        int indexc = -1;
        int indexd = -1;
        int indexe = -1;
        int indexbd = -1;
        int indexbe = -1;
        int indexcd = -1;
        int indexce = -1;
        for (int i = 0; i < bagNumber; i ++) {
            name = input.next();
            int gift = Integer.parseInt(input.next());
            if (name.contains("bc") || name.contains("de")) {
                totalbag += gift;
                continue;
            }
            if (!name.contains("a")) {
                totalbag += gift;
                if (Objects.equals(name, "b")) {
                    if (!b) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexb = bags.indexOf(bag);
                        b = true;
                    }
                    else {
                        bags.get(indexb).capacity += gift;
                    }
                }
                else if (Objects.equals(name, "c")) {
                    if (!c) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexc = bags.indexOf(bag);
                        c = true;
                    }
                    else {
                        bags.get(indexc).capacity += gift;
                    }
                }
                else if (Objects.equals(name, "d")) {
                    if (!d) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexd = bags.indexOf(bag);
                        d = true;
                    }
                    else {
                        bags.get(indexd).capacity += gift;
                    }
                }
                else if (Objects.equals(name, "e")) {
                    if (!e) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexe = bags.indexOf(bag);
                        e = true;
                    }
                    else {
                        bags.get(indexe).capacity += gift;
                    }
                }
                else if (Objects.equals(name, "bd")) {
                    if (!bd) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexbd = bags.indexOf(bag);
                        bd = true;
                    }
                    else {
                        bags.get(indexbd).capacity += gift;
                    }
                }
                else if (Objects.equals(name, "be")) {
                    if (!be) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexbe = bags.indexOf(bag);
                        be = true;
                    }
                    else {
                        bags.get(indexbe).capacity += gift;
                    }
                }
                else if (Objects.equals(name, "cd")) {
                    if (!cd) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexcd = bags.indexOf(bag);
                        cd = true;
                    }
                    else {
                        bags.get(indexcd).capacity += gift;
                    }
                }
                else if (Objects.equals(name, "ce")) {
                    if (!ce) {
                        Bag bag = new Bag(gift,name);
                        bags.add(bag);
                        indexce = bags.indexOf(bag);
                        ce = true;
                    }
                    else {
                        bags.get(indexce).capacity += gift;
                    }
                }
            }
            else {
                bags.add(new Bag(gift, name));
                totalbag += gift;
            }
        }
        ArrayList<Edge> edges = new ArrayList<>();
        Vertex sink = new Vertex(0);
        source.height = bags.size() + redTrain + redReindeer + greenReindeer + greenTrain + 2;
        for (Bag bag : bags) {
            source.edges.add(new Edge(source, bag, bag.capacity, bag.capacity));
            bag.excessflow += bag.capacity;
            bag.edges.add(new Edge(bag, source, -bag.capacity, 0));
            if (Objects.equals(bag.name, "a")) {
                for (Vehicle redreindeer : redReindeers) {
                    bag.edges.add(new Edge(bag, redreindeer, 0, 1));
                }
                for (Vehicle greenreindeer : greenReindeers) {
                    bag.edges.add(new Edge(bag, greenreindeer, 0, 1));
                }
                for (Vehicle redtrain : redTrains) {
                    bag.edges.add(new Edge(bag, redtrain, 0, 1));
                }
                for (Vehicle greentrain : greenTrains) {
                    bag.edges.add(new Edge(bag, greentrain, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "b")) {
                for (Vehicle greenreindeer : greenReindeers) {
                    bag.edges.add(new Edge(bag, greenreindeer, 0, greenreindeer.capacity));
                }
                for (Vehicle greentrain : greenTrains) {
                    bag.edges.add(new Edge(bag, greentrain, 0, greentrain.capacity));
                }
            }
            else if (Objects.equals(bag.name, "c")) {
                for (Vehicle redreindeer : redReindeers) {
                    bag.edges.add(new Edge(bag, redreindeer, 0, redreindeer.capacity));
                }
                for (Vehicle redtrain : redTrains) {
                    bag.edges.add(new Edge(bag, redtrain, 0, redtrain.capacity));
                }
            }
            else if (Objects.equals(bag.name, "d")) {
                for (Vehicle redtrain : redTrains) {
                    bag.edges.add(new Edge(bag, redtrain, 0, redtrain.capacity));
                }
                for (Vehicle greentrain : greenTrains) {
                    bag.edges.add(new Edge(bag, greentrain, 0, greentrain.capacity));
                }
            }
            else if (Objects.equals(bag.name, "e")) {
                for (Vehicle redreindeer : redReindeers) {
                    bag.edges.add(new Edge(bag, redreindeer, 0, redreindeer.capacity));
                }
                for (Vehicle greenreindeer : greenReindeers) {
                    bag.edges.add(new Edge(bag, greenreindeer, 0, greenreindeer.capacity));
                }
            }
            else if (Objects.equals(bag.name,"ab")) {
                for (Vehicle greenreindeer : greenReindeers) {
                    bag.edges.add(new Edge(bag, greenreindeer, 0, 1));
                }
                for (Vehicle greentrain : greenTrains) {
                    bag.edges.add(new Edge(bag, greentrain, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "ac")) {
                for (Vehicle redreindeer : redReindeers) {
                    bag.edges.add(new Edge(bag, redreindeer, 0, 1));
                }
                for (Vehicle redtrain : redTrains) {
                    bag.edges.add(new Edge(bag, redtrain, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "ad")) {
                for (Vehicle redtrain : redTrains) {
                    bag.edges.add(new Edge(bag, redtrain, 0, 1));
                }
                for (Vehicle greentrain : greenTrains) {
                    bag.edges.add(new Edge(bag, greentrain, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "ae")) {
                for (Vehicle redreindeer : redReindeers) {
                    bag.edges.add(new Edge(bag, redreindeer, 0, 1));
                }
                for (Vehicle greenreindeer : greenReindeers) {
                    bag.edges.add(new Edge(bag, greenreindeer, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "bd")) {
                for (Vehicle greentrain : greenTrains) {
                    bag.edges.add(new Edge(bag, greentrain, 0, greentrain.capacity));
                }
            }
            else if (Objects.equals(bag.name, "be")) {
                for (Vehicle greenreindeer : greenReindeers) {
                    bag.edges.add(new Edge(bag, greenreindeer, 0, greenreindeer.capacity));
                }
            }
            else if (Objects.equals(bag.name, "cd")) {
                for (Vehicle redtrain : redTrains) {
                    bag.edges.add(new Edge(bag, redtrain, 0, redtrain.capacity));
                }
            }
            else if (Objects.equals(bag.name, "ce")) {
                for (Vehicle redreindeer : redReindeers) {
                    bag.edges.add(new Edge(bag, redreindeer, 0, redreindeer.capacity));
                }
            }
            else if (Objects.equals(bag.name, "abd")) {
                for (Vehicle greentrain : greenTrains) {
                    bag.edges.add(new Edge(bag, greentrain, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "abe")) {
                for (Vehicle greenreindeer : greenReindeers) {
                    bag.edges.add(new Edge(bag, greenreindeer, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "acd")) {
                for (Vehicle redtrain : redTrains) {
                    bag.edges.add(new Edge(bag, redtrain, 0, 1));
                }
            }
            else if (Objects.equals(bag.name, "ace")) {
                for (Vehicle redreindeer : redReindeers) {
                    bag.edges.add(new Edge(bag, redreindeer, 0, 1));
                }
            }
        }
        for (Vehicle redreindeer : redReindeers) {
            redreindeer.edges.add(new Edge(redreindeer, sink, 0, redreindeer.capacity));
        }
        for (Vehicle greenreindeer : greenReindeers) {
            greenreindeer.edges.add(new Edge(greenreindeer, sink, 0, greenreindeer.capacity));
        }
        for (Vehicle redtrain : redTrains) {
            redtrain.edges.add(new Edge(redtrain, sink, 0, redtrain.capacity));
        }
        for (Vehicle greentrain : greenTrains) {
            greentrain.edges.add(new Edge(greentrain, sink, 0, greentrain.capacity));
        }
        ArrayList<Vertex> allVertices = new ArrayList<>();
        allVertices.add(source);
        allVertices.addAll(bags);
        allVertices.addAll(greenReindeers);
        allVertices.addAll(greenTrains);
        allVertices.addAll(redReindeers);
        allVertices.addAll(redTrains);
        allVertices.add(sink);
        ArrayList<Vehicle> relabeled = new ArrayList<>();
        ArrayDeque<Vertex> deque = new ArrayDeque<>(bags);
        while (!deque.isEmpty()) {
            Vertex u = deque.poll();
            int oldheight = u.height;
            discharge(u, relabeled, allVehicles, deque, source, sink);
            if (u.height > oldheight) {
                if (allVehicles.contains(u)) {
                    deque.addLast(u);
                }
                else {
                    deque.addFirst(u);
                }
            }
            if (relabeled.size() == allVehicles.size()) {
                break;
            }
        }
        output.println(totalbag - allVertices.get(allVertices.size() - 1).excessflow);
    }
}
