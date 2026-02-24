import java.util.*;

enum Direction {
    UP,
    DOWN,
    IDLE
}

public class Elevator{

    private int currentFLoor;
    private Direction direction;
    private Set<Request> requests;

    public Elevator(){
        this.currentFLoor = 0
        this.direction = Direction.IDLE;
        this.requests = new HashSet<>();
    }

    public boolean addRequest(int floor, RequestType type){

        if(floor < 0 || floor > 9){
            return false;
        }

        if(floor == currentFLoor){
            return true;
        }

        Request request = new Request(floor, type);
        if(requests.contains(request)){
            return false;
        }
        return requests.add(request);
    }

    public void step(){

        if(requests.isEmpty()){
            direction = Direction.IDLE;
            return;
        }

        if(direction == Direction.IDLE){
            // Find nearest request to establish initial direction
            Request nearest = null;
            int minDistance = Integer.MAX_VALUE;

            for(Request req : requests){
                int distance = Math.abs(req.getFloor()-currentFLoor);
                if(distance < minDistance || (distance == minDistance && (nearest == null
                || req.getFloor() < nearest.getFloor()))){
                    minDistance = distance;
                    nearest = req;
                }
            }
            direction = (nearest.getFloor() > currentFloor) ? Direction.UP : Direction.DOWN;
        }

        RequestType pickupType = (direction == Direction.UP) ? RequestType.PICKUP_UP : 
        RequestType.PICKUP_DOWN;

        Request pickupRequest = new Request(currentFLoor, pickupType);
        Request destinationRequest = new Request(currentFLoor, RequestType.DESTINATION);

        if(requests.contains(pickupRequest) || requests.contains(destinationRequest)){
            requests.remove(pickupRequest);
            requests.remove(destinationRequest);
        
        if(requests.isEmpty()){
            direction = Direction.IDLE;
            return;
        }
        if(!hasRequestAhead(direction)){
            direction = (direction == Direction.UP) ? Direction.DOWN : Direction.UP;
        }
    return;
    }

    if (direction == Direction.UP) { currentFloor++; } 
    else if (direction == Direction.DOWN) { currentFloor--; }

}

public boolean hasRequestAhead(Direction dir){

    for(Request req : requests){
        if(dir == Direction.UP && req.getFloor() > currentFLoor){
            return true;
        }
        if(dir == Direction.DOWN && req.getFloor() < currentFLoor){
            return true;
        }
    }
    return false;
}

public boolean hasRequestsAtOrBeyond(int floor, Direction dir) {

    for(Request request : requests){
        if(dir == Direction.UP && requests.getFloor() >= floor){
            if(request.getType() == RequestType.PICKUP_UP || 
            request.getType() == RequestType.DESTINATION){
                return true;
            }
        }
        if(dir == Direction.DOWN && requests.getFloor() <= floor){
            if(request.getType() == RequestType.PICKUP_DOWN ||
            request.getType() == RequestType.DESTINATION){
                return true;
            }
        }
    }
    return false;
}

public int getCurrentFloor(){
    return currentFLoor;
}

public Direction getCurrentDirection(){
    return direction;
}

}