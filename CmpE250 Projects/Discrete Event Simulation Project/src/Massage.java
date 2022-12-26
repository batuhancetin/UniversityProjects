public class Massage extends Event  {
    double massagequeuestarttime;
    public Massage(Player player, double arrivalTime, double duration, int eventType, double trainingduration, Physiotherapist physiotherapist, double massagequeuestarttime) {
        super(player, arrivalTime, duration, eventType, trainingduration, physiotherapist);
        this.massagequeuestarttime = massagequeuestarttime;
    }


    public int compareMSG(Massage o) {
        if (this.player.skillLevel == o.player.skillLevel) {
            if (Double.compare(this.arrivalTime, o.arrivalTime) == 0) {
                if (this.player.ID > o.player.ID) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else if (Double.compare(this.arrivalTime, o.arrivalTime) == 1) {
                return 1;
            }
            else {
                return -1;
            }
        }
        else if (this.player.skillLevel > o.player.skillLevel){
            return -1;
        }
        else {
            return 1;
        }
    }
}
