import java.util.ArrayList;
import java.util.TreeSet;

public class SingleElevator implements Runnable {

    int id;
    enum Direction{UP,DOWN,NONE};
    private Direction direction;
    private Integer currentFloor;
    private TreeSet<Integer> selectedFloors;
    private ArrayList<TreeSet<Integer>> called;

    public SingleElevator(int id) {
        this.id = id;
        direction = Direction.NONE;
        currentFloor = Integer.valueOf(0);
        selectedFloors = new TreeSet<Integer>();
        called = new ArrayList<TreeSet<Integer>>(2);
        called.add(new TreeSet<Integer>());
        called.add(new TreeSet<Integer>());
    }

    public Direction oppositeDirection() {
        if (direction == Direction.NONE) return Direction.NONE;
        else return (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
    }

    boolean nextFloorAvailable() {
        if (direction == Direction.NONE) return false;
        else if (direction == Direction.UP) {
            if (called.get(0).higher(currentFloor) != null) return true;
            return selectedFloors.higher(currentFloor) != null;
        }
        else if (direction == Direction.DOWN) {
            if (called.get(1).lower(currentFloor) != null) return true;
            return selectedFloors.lower(currentFloor) != null;
        }
        return false;
    }

    // 0 is up, 1 is down.
    public void call(Integer floor, int direction) {
        // System.out.println("Floor is: " + floor + ",direction is: " + direction + ", current dir="+this.direction.ordinal());
        // System.out.println(called.size());
        called.get(direction).add(floor);
        if (this.direction == Direction.NONE && floor != currentFloor) this.direction = (floor < currentFloor) ? Direction.DOWN : Direction.UP;
    }

    public void removeCall(Integer floor) {
        called.get(direction.ordinal()).remove(floor);
    }

    public void setDestination(Integer floor) {
        if (floor == currentFloor) return;  
        selectedFloors.add(floor);
        if (direction == Direction.NONE) direction = (floor < currentFloor) ? Direction.DOWN : Direction.UP;
    }

    public void moveOneFloor() {
        try {
            currentFloor += (direction == Direction.UP) ? 1 : -1;
            System.out.println("Elevator " + id + " on floor " + currentFloor+"!");
            Thread.sleep(2000);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            
            // System.out.println("currentdir = " + direction.ordinal());
            if (!nextFloorAvailable()) direction = oppositeDirection();
            if (!nextFloorAvailable()) direction = Direction.NONE;
            while (nextFloorAvailable()) {
                moveOneFloor();
                if (called.get(direction.ordinal()).contains(currentFloor) || selectedFloors.contains(currentFloor)) {
                    // Stop, open doors, wait a little, close doors, delete current floor from selected floors/called.
                    System.out.println("Opening doors on floor " + currentFloor);
                    try {
                        Thread.sleep(3000);
                        System.out.println("Closing doors!");
                        removeCall(currentFloor);
                        selectedFloors.remove(currentFloor);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            // System.out.println(called.toString());
            // System.out.println(selectedFloors.toString());
            try{ Thread.sleep(5000); } catch (Exception e) { e.printStackTrace(); }
        }
    }
}