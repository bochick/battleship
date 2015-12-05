package battleship;

/**
 * @author John Miller
 */
public class Ship {
    private String name;
    private int size;
    private int hits;
    
    public Ship (String nameIn, int sizeIn) {
        name = nameIn;
        size = sizeIn;
        hits = 0;
    }
    
    public String getName() {
        return name;
    }
    
    public void hit() {
        hits--;
    }
    
    public boolean isSunk() {
        if (size == hits)
            return true;
        
        return false;
    }
    
    public String toString() {
        return name + "has size " + size 
                + " and has been hit " + hits + " times";
    }
}
