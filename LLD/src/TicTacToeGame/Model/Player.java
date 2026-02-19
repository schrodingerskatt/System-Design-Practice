public class Player{

    public String name;
    public PlayingPiece playingPiece;

    public Player(String name, PlayingPiece playingPiece){
        this.name = name;
        this.playingPiece = playingPiece;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public setPlayingPiece(PlayingPiece playingPiece){
        this.playingPiece = playingPiece;
    }

    public PlayingPiece getPlayingPiece(){
        return PlayingPiece;
    }
}