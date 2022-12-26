import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
public class project3main {
    public static void main (String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(new File(args[0]));
        PrintStream output = new PrintStream(new File(args[1]));
        int timeLimit = Integer.parseInt(input.nextLine()), cityNumber = Integer.parseInt(input.nextLine());
        ArrayList<Integer> cCities = new ArrayList<>();
        ArrayList<Integer> dCities = new ArrayList<>();
        String[] twoCities = input.nextLine().split(" "), myLine;
        String mecnunCity = twoCities[0], leylaCity = twoCities[1];
        int cCitiesNumber = Integer.parseInt(leylaCity.split("c")[1]);
        boolean[] check = new boolean[cCitiesNumber];
        int[] distances = new int[cCitiesNumber];
        String[] parents = new String[cCitiesNumber];
        boolean[] check2 = new boolean[cityNumber - cCitiesNumber + 1];
        int[] distances2 = new int[cityNumber - cCitiesNumber + 1];
        ArrayList<String> parents2 = new ArrayList<>();
        int a = 0;
        while (a < cCitiesNumber) {
            check[a] = false;
            distances[a] = Integer.MAX_VALUE;
            parents[a] = "-1";
            a ++;
        }
        HashMap<String, HashMap<String, Integer>> graph = new HashMap<>();
        HashMap<String, HashMap<String, Integer>> graphyedek = new HashMap<>();
        ArrayList<String[]> linelist = new ArrayList<>();
        for (int i = 0; i < cityNumber; i++) {
            linelist.add(input.nextLine().split(" "));
        }
        for (int i = 0; i < cityNumber; i++) {
            HashMap<String, Integer> innergraph = new HashMap<>();
            for (int k = 1; k < linelist.get(i).length; k += 2) {
                innergraph.put(linelist.get(i)[k], Integer.parseInt(linelist.get(i)[k + 1]));
            }
            graph.put(linelist.get(i)[0], innergraph);
        }
        distances[0] = 0;
        int k = 0;
        while (k < cCitiesNumber - 1) {
            int minDistance = Integer.MAX_VALUE, minindex = -1;
            int i = 0;
            while (i < cCitiesNumber) {
                if (!check[i] && distances[i] <= minDistance) {
                    minDistance = distances[i];
                    minindex = i;
                }
                i ++;
            }
            check[minindex] = true;
            if (graph.get("c" + (minindex + 1)) != null) {
                for (String city : graph.get("c" + (minindex + 1)).keySet()) {
                    if (city.contains("d")) {
                        continue;
                    }
                    if (distances[minindex] + graph.get("c" + (minindex + 1)).get(city) < distances[Integer.parseInt(city.split("c")[1]) - 1]) {
                        distances[Integer.parseInt(city.split("c")[1]) - 1] = distances[minindex] + graph.get("c" + (minindex + 1)).get(city);
                        parents[Integer.parseInt(city.split("c")[1]) - 1] = "c" + (minindex + 1);
                    }
                }
            }
            k ++;
        }
        int i = 0;
        int dCitiesNumber = cityNumber - cCitiesNumber;
        while (i < dCitiesNumber + 1) {
            check2[i] = false;
            distances2[i] = Integer.MAX_VALUE;
            i ++;
        }
        for (String city : graph.keySet()) {
            for (String city2 : graph.get(city).keySet()) {
                if (graph.containsKey(city2)) {
                    if (graph.get(city2).containsKey(city)) {
                        if (graph.get(city).get(city2) < graph.get(city2).get(city)) {
                            graph.get(city2).put(city, graph.get(city).get(city2));
                        }
                    }
                    else {
                        graph.get(city2).put(city, graph.get(city).get(city2));
                    }
                }
                else {
                    HashMap<String, Integer> innergraph = new HashMap<>();
                    innergraph.put(city, graph.get(city).get(city2));
                    graphyedek.put(city2, innergraph);
                }
            }
        }
        for (String city : graphyedek.keySet()) {
            graph.put(city, graphyedek.get(city));
        }
        distances2[0] = 0;
        int x = 0;
        while (x < dCitiesNumber) {
            int minDistance = Integer.MAX_VALUE, minindex = -1;
            int y = 0;
            while (y < dCitiesNumber + 1) {
                if (!check2[y] && distances2[y] < minDistance) {
                    minDistance = distances2[y];
                    minindex = y;
                }
                y ++;
            }
            check2[minindex] = true;
            if (minindex != 0 && graph.get("d" + minindex) != null) {
                for (String city : graph.get("d" + minindex).keySet()) {
                    if (city.contains("d")) {
                        if (check2[Integer.parseInt(city.split("d")[1])]) {
                            continue;
                        }
                        if (graph.get("d" + (minindex)).get(city) <= distances2[Integer.parseInt(city.split("d")[1])]) {
                            distances2[Integer.parseInt(city.split("d")[1])] = graph.get("d" + (minindex)).get(city);
                        }
                    }
                }
            }
            else if (minindex == 0 && graph.get(leylaCity) != null) {
                for (String city : graph.get(leylaCity).keySet()) {
                    if (city.contains("c")) {
                        continue;
                    }
                    if (graph.get(leylaCity).get(city) <= distances2[Integer.parseInt(city.split("d")[1])]) {
                        distances2[Integer.parseInt(city.split("d")[1])] = graph.get(leylaCity).get(city);
                    }
                }
            }
            x ++;
        }
        if (distances[distances.length - 1] < 0) {
            output.println(-1);
            output.println(-1);
        }
        else {
            String myCity = leylaCity;
            parents2.add(leylaCity);
            while (!Objects.equals(myCity, mecnunCity)) {
                parents2.add(parents[Integer.parseInt(myCity.split("c")[1]) - 1]);
                myCity = parents[Integer.parseInt(myCity.split("c")[1]) - 1];
            }
            int z = 0;
            while (z < parents2.size()) {
                output.print(parents2.get(parents2.size() - 1 - z) + " ");
                z ++;
            }
            output.println();
            if (distances[distances.length - 1] > timeLimit) {
                output.println(-1);
            }
            else {
                boolean check3 = true;
                int sumx = 0;
                int u = 0;
                while (u < dCitiesNumber + 1) {
                    if (distances2[u] < 0 || distances2[u] == Integer.MAX_VALUE) {
                        check3 = false;
                        break;
                    }
                    sumx += distances2[u];
                    u ++;
                }
                if (!check3) {
                    output.println(-2);
                }
                else {
                    output.println(2 * sumx);
                }
            }
        }

    }
}
