public class Edge {
    Vertex u, v;
    int flow;
    int capacity;
    public Edge(Vertex u, Vertex v, int flow, int capacity) {
        this.u = u;
        this.v = v;
        this.flow = flow;
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Edge{" +
                "u=" + u +
                ", v=" + v +
                '}';
    }
}
