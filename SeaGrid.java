package battleship;

/**
 *
 * @author 
 */
public class SeaGrid {
    
    private final int SIZE = 10;
    private char[][] grid = new char[SIZE][SIZE];
    private final char open = '^';
    String name;
    
    public SeaGrid(String nameIn) {
        name = nameIn;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = open;
            }
        }
    }
    
    public void setSquare(int row, int col, char mark)
    {
        grid[row][col] = mark; 
    }
    
    public char getSquare(int row, int col) {
        return grid[row][col];
    }
    
    public String toString() {
        String result = name + "\n0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\n";

        for (int row=0; row < SIZE; row++)
        {
            result += (row + 1) + "\t";
            for (int col=0; col < SIZE; col++)
                result += grid[row][col] + "\t";
            result += "\n";
        }

        return result;
    }
}
