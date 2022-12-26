public class Player {
    int ID;
    int skillLevel;
    int massageCount;
    boolean training = false;
    boolean physiotherapy = false;
    boolean massage = false;
    double therapywaitingtime;
    double massagewaitingtime;
    public Player(int ID, int skillLevel) {
        this.ID = ID;
        this.skillLevel = skillLevel;
        massageCount = 0;
        therapywaitingtime = 0;
        massagewaitingtime = 0;
    }
}
