public class Physiotherapy extends Event{
    double therapyqueuestarttime;
    public Physiotherapy(Player player, double arrivalTime, double duration, int eventType, double trainingduration, Physiotherapist physiotherapist, double therapyqueuestarttime) {
        super(player, arrivalTime, duration, eventType, trainingduration, physiotherapist);
        this.therapyqueuestarttime = therapyqueuestarttime;
    }

    public int comparePYH(Physiotherapy o) {
        if (Double.compare(this.trainingduration, o.trainingduration) == 0) {
            if (Double.compare(this.arrivalTime, o.arrivalTime) == 0) {
                return this.player.ID - o.player.ID;
            }
            else if (Double.compare(this.arrivalTime, o.arrivalTime) == 1) {
                return 1;
            }
            else {
                return -1;
            }
        }
        else if ((Double.compare(this.trainingduration, o.trainingduration) == 1)) {
            return -1;
        }
        else {
            return 1;
        }
    }
}