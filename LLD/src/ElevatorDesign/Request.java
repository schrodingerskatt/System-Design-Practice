import java.util.*;

public class Request{

    private final int floor;
    private final RequestType type;

    public Request(int floor, RequestType type){
        this.floor = floor;
        this.type = type;
    }

    public int getFloor(){
        return floor;
    }

    public RequestType getType(){
        return type;
    }

    @Override
    public boolean equals(Object o){ // equals() → compares memory addresses
        if(this == o) return o;
        if(!(o instanceof Request)) return false;
        Request Request = (Request)o;
        return floor == request.floor && type == request.type; // we dont allow same request values
    }

    @Override // returns a hash based on memory identity
    public int hashCode() {
        return Objects.hash(floor, type);
    }
}

/*
This generates a hash value based on: floor and type
If two objects are equal according to equals(), they MUST have the same hashCode().
If you use: Set<Request> requests = new HashSet<>();
Without overriding equals + hashCode:
requests.add(new Request(5, UP));
requests.add(new Request(5, UP));
You would get 2 entries. Because Java thinks they’re different objects.
But with our override: You’ll get only 1 entry
Which is exactly what you want for elevator request deduping.
*/