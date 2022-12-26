import java.util.Comparator;

public class Event implements Comparable<Event> {
    Player player;
    double arrivalTime;
    double duration;
    /*
    1 = yeni training
    2 = yeni massage
    3 = training bitiş eventi
    4 = fizyoterapi bitiş eventi
    5 = masaj bitiş eventi
     */
    int eventType;
    Physiotherapist physiotherapist;
    double trainingduration;
    public Event(Player player, double arrivalTime, double duration, int eventType, double trainingduration, Physiotherapist physiotherapist) {
        this.player = player;
        this.arrivalTime = arrivalTime;
        this.duration = duration;
        this.eventType = eventType;
        this.trainingduration = trainingduration;
        this.physiotherapist = physiotherapist;
    }

    public int compareTo(Event o) {
        if (Double.compare(this.arrivalTime, o.arrivalTime) == 0) {
            if (this.eventType == o.eventType) {
                if (this.eventType == 2) {
                    if (this.player.skillLevel == o.player.skillLevel) {
                        if (this.player.ID > o.player.ID) {
                            return  1;
                        }
                        else {
                            return -1;
                        }
                    }
                    else if (this.player.skillLevel > o.player.skillLevel) {
                        return -1;
                    }
                    else {
                        return 1;
                    }
                }
                else if (this.eventType == 1) {
                    if (this.player.ID > o.player.ID) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
                else if (this.eventType == 4) {
                    if (this.player.ID > o.player.ID) {
                        return 1;
                    }
                    else {
                        return -1;
                    }
                }
                else {
                    return 1;
                }
            }
            else if (this.eventType > 2 && o.eventType <= 2) {
                return -1;
            }
            else {
                return 1;
            }
        }
        else if (Double.compare(this.arrivalTime, o.arrivalTime) == 1) {
            return 1;
        }
        else {
            return -1;
        }
    }
}
