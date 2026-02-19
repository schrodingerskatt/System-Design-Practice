import java.util.*;

public class TicTacToe{

    Deque<Player>players;
    Board gameBoard;
    Player winner;

    public void initializeGame(){

        players = new LinkedList<>();
        PlayingPieceX crossPiece = new PlayingPieceX();
        Player player1 = new Player("Player1", crossPiece);

        PlayingPieceO naughtPiece = new PlayingPieceO();
        Player player2 = new Player("Player2", naughtPiece);

        players.add(player1);
        players.add(player2);
        gameBoard = new Board(3);
    }

    public GameStatus startGame(){

        boolean noWinner = true;
        while(noWinner){

            Player currentPlayer = players.removeFirst();
            gameBoard.printBoard();
            List<Pair<Integer, Integer>> freeSpaces = gameBoard.getFreeCells();
            if(freeSpaces.isEmpty()){
                noWinner = false;
                continue;
            }

            System.out.print("Player: " + currentPlayer.name + " - Please enter [row, column]: ");
            Scanner inputScanner = new Scanner(System.in);
            String s = inputScanner.nextLine();
            String[] values = s.split(",");
            int inputRow = Intger.valueOf(values[0]);
            int inputCol = Integer.valueOf(values[1]);
            boolean validMove = gameBoard.addPiece(inputRow, inputCol, currentPlayer.playingPiece.pieceType);
            if(!validMove){
                System.out.println("Incorrect position chosen, try again !!");
                players.addFirst(currentPlayer);
                continue;
            }
            players.addLast(currentPlayer);
            boolean isWinner = checkForWinner(inputRow, inputCol, currentPlayer.PlayingPiece.pieceType);
            if(isWinner){
                gameBoard.printBoard();
                winner = currentPlayer;
                return GameStatus.WIN;
            }
        }
        return GameStatus.DRAW;
    }

    public boolean checkForWinner(int row, int col, PieceType pieceType){

        boolean rowMatch = true;
        boolean columnMatch = true;
        boolean diagonalMatch = true;
        boolean antidiagonalMatch = true;

        for(int i = 0; i < gameBoard.size; i++){
            if(gameBoard.board[row][i] == null || gameBoard.board[row][i].pieceType != pieceType) {
                rowMatch = false;
                break;
            }
        }

        for (int i = 0; i < gameBoard.size; i++) {
            if (gameBoard.board[i][column] == null || gameBoard.board[i][column].pieceType != pieceType) {
                columnMatch = false;
                break;
            }
        }

        for (int i = 0, j = 0; i < gameBoard.size; i++, j++) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                diagonalMatch = false;
                break;
            }
        }

        for (int i = 0, j = gameBoard.size - 1; i < gameBoard.size; i++, j--) {
            if (gameBoard.board[i][j] == null || gameBoard.board[i][j].pieceType != pieceType) {
                antiDiagonalMatch = false;
                break;
            }
        }

    return rowMatch || columnMatch || diagonalMatch || antiDiagonalMatch;

    }
}