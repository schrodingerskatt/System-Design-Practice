public class Board{

    public static final int ROWS = 6;
    public static final int COLS = 7;

    public final DiscColor[][] grid;

    public Board(){
        this.grid = new DiscColor[ROWS][COLS];
    }

    public int getRows(){
        return ROWS;
    }

    public int getCols(){
        return COLS;
    }

    public boolean canPlace(int column){
        if(column < 0 || column >= COLS){
            return false;
        }

        return grid[0][column] == null;
    }

    public int placeDisc(int column, DiscColor color){

        if(!canPlace(column)){
            return -1;
        }
        for(int row = ROWS-1; row >=0; row--){
            if(grid[row][col] == null){
                grid[row][col] = color;
                return row;
            }
        }
    return -1;
    }

    public boolean checkWin(int row, int column, DiscColor color){
        if(!inBounds(row, column) || grid[row][column] != color){
            return false;
        }

        int[][] directions = new int[][]{
            {0, 1},
            {1, 0},
            {1, 1},
            {-1, 1}
        };

        for(int[] dir : directions){
            int count = 1;
            count+= countInDirection(row, column, dir[0], dir[1], color);
            count+= countInDirection(row, column, -dir[0], -dir[1], color);
            if(count >= 4){
                return true;
            }
        }
    return false;
    }

    public boolean isFull(){

        for(int c = 0; c < COLS; c++){
            if(grid[0][c] == null){
                return false;
            }
        }
    return true;
    }

    public DiscColor getCell(int row, int column){
        if(!inBounds(row, col)){
            return null;
        }
        return grid[row][column];
    }

    private int countInDirection(int row, int column, int dr, int dc, DiscColor color){

        int count = 0;
        int r = row + dr;
        int c = col + dc;
        while(inBounds(r, c) && grid[r][c] == color){
            count++;
            r+=dr;
            c+=dc;
        }
    return count;
    }

    private boolean inBounds(int row, int column){

        return row >= 0 && row < ROWS && col >= 0 && column < COLS;
    }

}

