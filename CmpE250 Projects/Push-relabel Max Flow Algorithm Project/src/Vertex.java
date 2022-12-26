import java.util.ArrayList;

public class Vertex {
    int height;
    int capacity;
    int excessflow;
    int seen;
    ArrayList<Edge> edges;
    public Vertex(int capacity) {
        this.capacity = capacity;
        this.excessflow = 0;
        this.height = 0;
        edges = new ArrayList<>();
        seen = 0;
    }
}
