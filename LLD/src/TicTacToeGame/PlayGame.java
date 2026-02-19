public class PlayGame{

    public static void main(String[] args){

        System.out.println("***** WELCOME TO TIC-TAC-TOE GAME *****");
        TicTacToe game = new TicTacToe();
        game.initializeGame();
        GameStatus status = game.startGame();
        System.out.println("***** GAME OVER ! *****");
        switch(status){

            case WIN:
                System.out.println(game.winner.name + " won the game ! ");
                break;
            case DRAW:
                System.out.println(" It's a DRAW !!! ");
                break;
            default:
                System.out.println(" Game ends ! ");
                break;
        }
    }
}