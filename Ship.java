package battleship;

/**
 * @author John Miller
 */
public class Ship {
    private String name;
    private int size;
    private int hits;
    private int direction;
    private int[] location = new int[2];
    
    public Ship (String nameIn, int sizeIn) {
        name = nameIn;
        size = sizeIn;
        hits = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public int getSize() {
        return size;
    }
    
    public int[] getLocation() { 
        return location;
    }
    
    public int getDirection() {
        return direction;
    }
    
    public void setLocation(int row, int col) {
        location[0] = row;
        location[1] = col;
    }
    
    public void setDirection(int dir) {
        direction = dir;
    }
    
    public void hit() {
        hits--;
    }
    
    public boolean isSunk() {
        if (size == hits)
            return true;
        
        return false;
    }
    
    public char title() {
        return name.charAt(0);
    }
    
    public String toString() {
        return name;
    }
}
