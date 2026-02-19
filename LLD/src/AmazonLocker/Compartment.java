public class Compartment{

    private Size size;
    private boolean occupied;

    public Compartment(Size size, boolean occupied){
        this.size = size;
        this.occupied = occupied;
    }

    public Size getSize(){
        return size;
    }

    public void markOccupied(){
        this.occupied = true;
    }

    public void markFree(){
        this.occupied = false;
    }

    public boolean isOccupied(){
        return occupied;
    }

    public void open(){
        
    }
}