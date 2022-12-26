public class Physiotherapist implements Comparable<Physiotherapist> {
    int ID;
    double duration;
    public Physiotherapist(int ID, double duration) {
        this.ID = ID;
        this.duration = duration;
    }
    public int compareTo(Physiotherapist o) {
        return this.ID - o.ID;
    }

    public String toString() {
        return "Physiotherapist{" +
                "ID=" + ID +
                ", duration=" + duration +
                '}';
    }
}
