import java.util.ArrayList;
import java.util.TreeSet;

public class SingleElevator implements Runnable {

    enum Direction{UP,DOWN,NONE};
    private Direction direction;
    private Integer currentFloor;
    private TreeSet<Integer> selectedFloors;
    private TreeSet<Integer>[] called;

    public SingleElevator() {
        direction = Direction.NONE;
        currentFloor = Integer.valueOf(0);
        selectedFloors = new Treeset<Integer>();
        called = new TreeSet<Integer>[2];
    }

    public Direction oppositeDirection() {
        if (direction == Direction.NONE) return Direction.NONE;
        else return (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
    }

    bool nextFloorAvailable(Direction direction) {
        if (direction == Direction.NONE) return false;
        else if (direction == Direction.UP) {
            if (called[0].higher(currentFloor) != null) return true;
            return selectedFloors.higher(currentFloor) != null;
        }
        else if (direction == Direction.DOWN) {
            if (called[1].lower(currentFloor) != null) return true;
            return selectedFloors.lower(currentFloor) != null;
        }
    }

    public void call(Integer floor, Direction direction) {
        called[direction].add(floor);
        if (this.direction == Direction.NONE) this.direction = direction;
    }

    public void setDestination(Integer floor) {
        if (floor == currentFloor) return;  
        selectedFloors.add(floor);
        if (direction == Direction.NONE) direction = (floor < currentFloor) ? Direction.DOWN : Direction.UP;
    }

    public void run() {
        while (true) {
            
            if (!nextFloorAvailable()) direction = oppositeDirection();
            if (!nextFloorAvailable()) direction = Direction.NONE;
            
            while (nextFloorAvailable()) {
                moveOneFloor(direction); // does the actual physical moving. I explicitly provide direction as an argument
                                         // because I'm assuming this goes out of Elevator class. I could make a wrapper though.
                currentFloor += (direction == Direction.UP) ? 1 : -1; 
                if (called[direction].contains(currentFloor) || selectedFloor.contains(currentFloor)) {
                    // Stop, open doors, wait a little, close doors, delete current floor from selected floors/called.
                }
            }
        }
    }
}