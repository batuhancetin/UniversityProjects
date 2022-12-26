import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Locale;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project2main {
    public static void main(String[] args) throws FileNotFoundException {
        Locale.setDefault(new Locale("en", "US"));
        Scanner input = new Scanner(new File(args[0]));
        PrintStream output = new PrintStream(new File(args[1]));
        int playerNumber = input.nextInt();
        ArrayList<Player> players = new ArrayList<Player>();
        PriorityQueue<Event> allEvents = new PriorityQueue<Event>();
        PriorityQueue<Training> trainingQueue = new PriorityQueue<Training>(Training::compareTR);
        PriorityQueue<Massage> massageQueue = new PriorityQueue<Massage>(Massage::compareMSG);
        PriorityQueue<Physiotherapy> physiotherapyQueue = new PriorityQueue<Physiotherapy>(Physiotherapy::comparePYH);
        PriorityQueue<Physiotherapist> physiotherapists1 = new PriorityQueue<Physiotherapist>(Physiotherapist ::compareTo);
        int maxtrainingqueue= 0;
        int maxtherapyqueue = 0;
        int maxmassagequeue = 0;
        int trainingnumber = 0;
        int massagenumber= 0;
        int invalid = 0;
        int massagecanceled = 0;
        int trainingcanceled = 0;
        double totaltrainingtime = 0;
        double totaltherapytime = 0;
        double totalmassagetime = 0;
        double totaltrainingduration = 0;
        double totaltherapyduration = 0;
        double totalmassageduration = 0;
        double maxtherapytime = 0;
        int maxtherapytimeid = 0;
        double minmassagetime = 1000000000.0;
        int minmassagetimeid = 0;
        boolean minmassagetimex = true;
        for (int i = 0; i < playerNumber; i++) {
            players.add(new Player(Integer.parseInt(input.next()), Integer.parseInt(input.next())));
        }
        int eventNumber = Integer.parseInt(input.next());
        for (int i = 0; i < eventNumber; i++) {
            if (input.hasNext("t")) {
                input.next();
                allEvents.add(new Event(players.get(Integer.parseInt(input.next())), Double.parseDouble(input.next()), Double.parseDouble(input.next()), 1, 0, null));
            } else {
                input.next();
                allEvents.add(new Event(players.get(Integer.parseInt(input.next())), Double.parseDouble(input.next()), Double.parseDouble(input.next()), 2, 0, null));
            }
        }
        int physiotherapistNumber = Integer.parseInt(input.next());
        for (int i = 0; i < physiotherapistNumber; i++) {
            physiotherapists1.add(new Physiotherapist(i, Double.parseDouble(input.next())));
        }
        int coachNumber = Integer.parseInt(input.next());
        int masseurNumber = Integer.parseInt(input.next());
        Event newevent;
        double time = 0;
        while (!allEvents.isEmpty()) {
            newevent = allEvents.poll();
            time = newevent.arrivalTime;
            if (trainingQueue.size() > maxtrainingqueue) {
                maxtrainingqueue = trainingQueue.size();
            }
            if (physiotherapyQueue.size() > maxtherapyqueue) {
                maxtherapyqueue = physiotherapyQueue.size();
            }
            if (massageQueue.size() > maxmassagequeue) {
                maxmassagequeue = massageQueue.size();
            }
            if (newevent.eventType == 4) {
                newevent.player.physiotherapy = false;
                physiotherapists1.add(new Physiotherapist(newevent.physiotherapist.ID, newevent.physiotherapist.duration));
                if (!physiotherapyQueue.isEmpty()) {
                    if (!physiotherapists1.isEmpty()) {
                        Physiotherapy physiotherapy = physiotherapyQueue.poll();
                        Physiotherapist physiotherapist = physiotherapists1.poll();
                        allEvents.add(new Event(physiotherapy.player, (time + physiotherapist.duration), 0, 4, 0, physiotherapist));
                        physiotherapy.player.physiotherapy = true;
                        totaltherapytime += time - physiotherapy.therapyqueuestarttime;
                        physiotherapy.player.therapywaitingtime += time - physiotherapy.therapyqueuestarttime;
                        totaltherapyduration += physiotherapist.duration;
                    }
                }
            }
            else if (newevent.eventType == 3) {
                newevent.player.training = false;
                coachNumber++;
                if (!physiotherapists1.isEmpty()) {
                    Physiotherapist physiotherapist = physiotherapists1.poll();
                    allEvents.add(new Event(newevent.player, (time + physiotherapist.duration), 0 ,4, newevent.duration, physiotherapist));
                    newevent.player.physiotherapy = true;
                    totaltherapyduration += physiotherapist.duration;
                }
                else {
                    physiotherapyQueue.add(new Physiotherapy(newevent.player, time, 0, 0, newevent.duration, null, time));
                    newevent.player.physiotherapy = true;
                }
                if (!trainingQueue.isEmpty()) {
                    Training training = trainingQueue.poll();
                    training.player.training = true;
                    allEvents.add(new Event(training.player, (time + training.duration), training.duration, 3, 0, null));
                    coachNumber--;
                    totaltrainingtime += time - training.queuestarttime;
                    totaltrainingduration += training.duration;
                }
            }
            else  if (newevent.eventType == 5) {
                newevent.player.massage = false;
                masseurNumber ++;
                if (!massageQueue.isEmpty()) {
                    Massage massage = massageQueue.poll();
                    massage.player.massage = true;
                    allEvents.add(new Event(massage.player, (time + massage.duration), 0, 5, 0, null));
                    masseurNumber --;
                    totalmassagetime += time - massage.massagequeuestarttime;
                    massage.player.massagewaitingtime += time - massage.massagequeuestarttime;
                    totalmassageduration += massage.duration;
                }
            }
            else if (newevent.eventType == 1) {
                trainingnumber ++;
                if (newevent.player.massage || newevent.player.training || newevent.player.physiotherapy) {
                    trainingcanceled ++;
                }
                else {
                    if (coachNumber != 0) {
                        allEvents.add(new Event(newevent.player, (time + newevent.duration), newevent.duration, 3, 0,null));
                        coachNumber --;
                        newevent.player.training = true;
                        totaltrainingduration += newevent.duration;
                    }
                    else {
                        trainingQueue.add(new Training(newevent.player, time, newevent.duration, 0, 0, null, time));
                        newevent.player.training = true;
                    }
                }
            }
            else if (newevent.eventType == 2) {
                massagenumber ++;
                if (newevent.player.massageCount == 3) {
                    invalid ++;
                }
                else if (newevent.player.massage || newevent.player.training || newevent.player.physiotherapy) {
                    massagecanceled ++;
                }
                else {
                    if (masseurNumber != 0) {
                        allEvents.add(new Event(newevent.player, (time + newevent.duration), 0, 5, 0, null));
                        masseurNumber --;
                        newevent.player.massage = true;
                        newevent.player.massageCount ++;
                        totalmassageduration += newevent.duration;
                    }
                    else {
                        massageQueue.add(new Massage(newevent.player, time, newevent.duration, 0, 0, null, time));
                        newevent.player.massage = true;
                        newevent.player.massageCount ++;
                    }
                }
            }
        }
        for (Player player : players) {
            if (player.therapywaitingtime > maxtherapytime) {
                maxtherapytime = player.therapywaitingtime;
                maxtherapytimeid = player.ID;
            }
            if (player.massageCount == 3) {
                if (player.massagewaitingtime < minmassagetime) {
                    minmassagetime = player.massagewaitingtime;
                    minmassagetimeid = player.ID;
                }
                minmassagetimex = false;
            }
        }
        output.println(maxtrainingqueue);
        output.println(maxtherapyqueue);
        output.println(maxmassagequeue);
        output.println(String.format("%.3f", totaltrainingtime/(trainingnumber - trainingcanceled)));
        output.println(String.format("%.3f", totaltherapytime/(trainingnumber - trainingcanceled)));
        output.println(String.format("%.3f", totalmassagetime/(massagenumber - massagecanceled - invalid)));
        output.println(String.format("%.3f", totaltrainingduration/(trainingnumber - trainingcanceled)));
        output.println(String.format("%.3f", totaltherapyduration/(trainingnumber - trainingcanceled)));
        output.println(String.format("%.3f", totalmassageduration/(massagenumber - massagecanceled - invalid)));
        output.println(String.format("%.3f", (totaltrainingtime + totaltrainingduration + totaltherapyduration + totaltherapytime)/(trainingnumber - trainingcanceled)));
        output.println(maxtherapytimeid + " " + String.format("%.3f",maxtherapytime));
        if (minmassagetimex) {
            output.println("-1 -1");
        }
        else {
            output.println(minmassagetimeid + " " + String.format("%.3f",minmassagetime));
        }
        output.println(invalid);
        output.println(trainingcanceled + massagecanceled);
        output.println(String.format("%.3f",time));
    }
}
