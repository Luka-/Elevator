import java.util.ArrayList;
import java.util.Scanner;

public class ElevatorController implements Runnable {
    ArrayList<SingleElevator> elevators;

    ElevatorController(int n) {
        elevators = new ArrayList<>();
        for (int i = 0; i < n; i++) elevators.add(new SingleElevator(i+1));
    }

    public void run() {
        for (SingleElevator elevator : elevators) {
            Thread nThread = new Thread(elevator);
            nThread.start();
        }
        while(true) {
            try {
                Scanner in = new Scanner(System.in);
                int floor = in.nextInt();
                int destination = in.nextInt();
                // For now, only the first elevator is used. Any load balancing
                // can be implemented here, i.e. round robin, selecting 
                // a random elevator, or something more sophisticated.
                elevators.get(0).call(floor, destination);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
