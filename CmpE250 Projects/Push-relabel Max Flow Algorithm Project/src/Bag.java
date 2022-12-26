public class Bag extends Vertex {
    String name;
    public Bag(int capacity, String name) {
        super(capacity);
        this.name = name;
        this.height = 2;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "name='" + name + '\'' +
                '}';
    }
}
