public class Training extends Event{
    double queuestarttime;
    public Training(Player player, double arrivalTime, double duration, int eventType, double trainingduration, Physiotherapist physiotherapist, double queuestarttime) {
        super(player, arrivalTime, duration, eventType, trainingduration, physiotherapist);
        this.queuestarttime = queuestarttime;
    }

    public int compareTR(Training o) {
        if (Double.compare(this.arrivalTime, o.arrivalTime) != 0) {
            return Double.compare(this.arrivalTime, o.arrivalTime);
        }
        else {
            if (this.player.ID > o.player.ID) {
                return 1;
            }
            else {
                return -1;
            }
        }
    }
}
