package fooddelivery;

public class Review{

    private final String userId;
    private final int rating;
    private final String comment;

    public Review(String userId, int rating, String comment){
        this.userId = userId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getUserId(){
        return userId;
    }

    public int getRating(){
        return rating;
    }
}