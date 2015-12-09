package battleship;

/**
 *
 * @author 
 */
public class SeaGrid {
    
    private final int SIZE = 10;
    private static final String LETTER[]
            = {"A","B","C","D","E","F","G","H","I","J"};
    private char[][] grid = new char[SIZE][SIZE];
    String name;
    
    private final char open = '^';
    
    public SeaGrid(String nameIn) {
        name = nameIn;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = open;
            }
        }
    }
    
    public char getSquare(int row, int column) {
        return grid[row][column];
    }
    
    public void update(int row, int column, char shell) {
        grid[row][column] = shell;
    }
    
    public String toString() {
        String result = name + "\n0\t1\t2\t3\t4\t5\t6\t7\t8\t9\t10\n";

        for (int row=0; row < grid.length; row++)
        {
            result += LETTER[(row)] + "\t";
            for (int column=0; column < grid[row].length; column++)
                result += grid[row][column] + "\t";
            result += "\n";
        }

        return result;
    }
}
