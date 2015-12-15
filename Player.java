package battleship;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.*;

/**
 *
 * @author JM
 */
public class Player {
    
    public enum Direction { up, down, left, right }
    private Ship[] fleet;
    private String name;
    private SeaGrid personalGrid;
    private SeaGrid targetGrid = new SeaGrid("Target grid");;
    private int rank;
    
    private Scanner cin = new Scanner(System.in);
    private Random rand = new Random(); 
   
    /**
     *
     * @param nameInput
     */
    public Player(String nameInput) {
        name = nameInput;
        personalGrid = new SeaGrid(name + "\'s grid");
        rank = 1;
        
        fleet = new Ship[5];
        fleet[0] = new Ship("Destroyer", 2);
        fleet[1] = new Ship("Submarine", 3);
        fleet[2] = new Ship("Frigate", 3);
        fleet[3] = new Ship("Cruiser", 4);
        fleet[4] = new Ship("Aircraft_Carrier", 5);
    }
    
    /**
     * Asks the player if he/she wants to position the ships manually
     * Only accepts y,Y or n,N as an answer
     */
    public void buildGrid() {
        String input;
        
        System.out.print("Do you want to position your ships manually? [Y] [N]: ");
        input = cin.next();
        cin.nextLine();//clears the input
        
        if (input.equalsIgnoreCase("N")) {
            randomPlacement();
        }
        else if (input.equalsIgnoreCase("Y")) {
            playerPlace();
        }
        else {
            System.out.print("Unknown command... ");
            buildGrid();
        }
    }
    
    /**
     * @return list of ships, their title and respective size
     */
    private String shipInfo() {
        String printShips = "";
        //prints out the list of ships to place
        for (Ship fleet1 : fleet) {
            printShips += "[" + fleet1.getTitle() + "]" + fleet1.getName() 
                    + "(" + fleet1.getSize() + ") ";
        }
        
        return printShips;
    }
    
    /**
     * Manually places the players ships on the personal SeaGrid
     */
    private void playerPlace() {
        int shipIndex = 0;
        String printShips = shipInfo();
        
        System.out.println(printShips + "\n" + "[U]Up [D]Down [L]Left [R]Right");
        System.out.print(personalGrid);
        
        //cycles through the array of ships
        while (shipIndex < fleet.length) { 
            printShips = fleet[shipIndex] +  ": [row] [column] [direction]";
            
            System.out.println(printShips);
            String input = cin.nextLine();
           
            // makes sure the input matches the criteria: [row] [column] [direction]
            while (!Pattern.matches("[0-9]{1,} [0-9]{1,} [u|U|d|D|l|L|r|R]?", input)) {
                System.out.println("Incorrect format entry.\n" + printShips);
                input = cin.nextLine();
            }
            //breakes the input into an array
            String arr[] = input.split(" "); 
            //processes the array according to the inputs
            int row = Integer.parseInt(arr[0]) - 1;
            int col = Integer.parseInt(arr[1]) - 1;
            int dir = charToIntDir(arr[2].toUpperCase().charAt(0));
            
            //if ship position is valid
            if (validPlacement(row, col, dir, fleet[shipIndex].getSize())) {
                for (int i = 0; i < fleet[shipIndex].getSize(); i++) {
                    //places ship
                    personalGrid.setSquare(row, col, fleet[shipIndex].getTitle());
                    if (dir == 0)//down
                        row++;
                    else if (dir == 1)//up
                        row--;
                    else if (dir == 2)//right
                        col++;
                    else if (dir == 3)//left
                        col--;
                }
                shipIndex++;//next ship
            }
            else 
                System.out.println("Invalid ship position.");
        }
    }
    
    /**
     * Takes in a character "D, U, R or L" and provides a direction in as an int
     * Default placement is down
     */
    private int charToIntDir(char in) {
        if (in == 'D') //down
            return 0;
        else if (in == 'U') //up
            return 1;
        else if (in == 'R') //right
            return 2;
        else if (in == 'L') //left
            return 3;
        else //failsafe down
            return 0;
    }
    
    /**
     * Randomly places the ships on the personal SeaGrid
     */
    protected void randomPlacement() {
        int index = fleet.length - 1;
        
        while (index >= 0) {
            int row = rand.nextInt(10);
            int col = rand.nextInt(10);
            int dir = rand.nextInt(4);
                
            if (validPlacement(row, col, dir, fleet[index].getSize())) {
                fleet[index].setLocation(col, row);
                fleet[index].setDirection(dir);
                for (int i = 0; i < fleet[index].getSize(); i++) {
                    personalGrid.setSquare(row, col, fleet[index].getTitle());
                if (dir == 0)//down
                    row++;
                else if (dir == 1)//up
                    row--;
                else if (dir == 2)//right
                    col++;
                else if (dir == 3)//left
                    col--;
                }
                index--;
            }
        }
    }
    
    /**
     * Checks if the placement of the ship on the personal SeaGrid is valid
     * Returns false if otherwise
     */
    private boolean validPlacement(int row, int col, int dir, int size) {
        try {
        boolean valid = true;
        for (int i = 0; i < size; i++) {
                if (personalGrid.getSquare(row, col) != '^') 
                        valid = false;
                if (dir == 0)//down
                    row++;
                else if (dir == 1)//up
                    row--;
                else if (dir == 2)//right
                    col++;
                else if (dir == 3)//left
                    col--;
            }
                return valid;
        } catch (ArrayIndexOutOfBoundsException error) {
            return false;//if the ship goes off the board
        }
    }
    
    /**
     *
     * @return PersonalGrid
     */
    public String getPersonalGrid()
    {
        return name + "'s Personal Grid:" + personalGrid.toString();
    }
    
    /**
     *
     * @return TargetGrid
     */
    public String getTargetGrid()
    {
        return name + "'s Target Grid: " + targetGrid.toString();
    }
    
    /**
     * Asks the player what row and column to attack next
     * @return row and column as an int array
     */
    public int[] attack(){
        System.out.print("What area would you like to attack? [row] [column]: ");
        int[] output;
        try {
            int row = cin.nextInt() - 1;
            int col = cin.nextInt() - 1;
            output = new int[] {row, col};
            
        } catch (Exception error) { 
            System.out.println("Input error!");
            cin.nextLine();//clears the input
            output = attack();
        }
            return output;
    }
    
    /**
     *
     * @param row
     * @param col
     * @return true if the inputed row and column is a ship
     */
    public boolean hit(int row, int col) {
        boolean output = false;
        char square = personalGrid.getSquare(row, col);
        
        if (square != '^' || square != 'H' || square != 'G' || square != 'X') 
            output = true;
        
        return output;
    }
    
    /**
     *
     * @param row
     * @param col
     * @param hit
     */
    public void updateGuessGrid(int row, int col, boolean hit)
    {
        if (hit)
            targetGrid.setSquare(row, col, 'H');
        else
            targetGrid.setSquare(row, col, 'G');
    }
    
    /**
     *
     * @param row
     * @param col
     */
    public Ship.Enum updatePersonalGrid(int row, int col)
    {
        char square = personalGrid.getSquare(row, col);
        char ship = '^';
        int index = 0;
        Ship.Enum output = null;
        
        for (int i = 0; i < fleet.length && square != ship; i++) {
            ship = fleet[i].getTitle();
            index = i;
        }
        
        //if shot is a hit
        if (square != '^' && square != 'G') {
            fleet[index].hit();
            System.out.println("Index: " + index);
            System.out.println("Ship Hits: "+ fleet[index].getHits());
            if(fleet[index].isSunk()) {
                personalGrid.setSquare(row, col, 'X');
                System.out.println(name + "'s " + fleet[index].getName() 
                        + " was sunk!");
                output = fleet[index].getEnum(); //returns the sunk ship
                int dir = fleet[index].getDirection();
                int[] pos = fleet[index].getLocation();
                for (int i = 0; i < fleet[index].getSize(); i++) {
                    personalGrid.setSquare(pos[0], pos[1], 'X');
                    if (dir == 0)//down
                        pos[0]++;
                    else if (dir == 1)//up
                        pos[0]--;
                    else if (dir == 2)//right
                        pos[1]++;
                    else if (dir == 3)//left
                        pos[1]--;
                }
            }
            else
                personalGrid.setSquare(row, col, 'H');
        }
        else //shot is not a hit
            personalGrid.setSquare(row, col, 'G');
        
        return output;
    }
    
    /**
     *
     * @return
     */
    public boolean fleetSunk()
    {
        if( fleet[0].isSunk() && 
            fleet[1].isSunk() &&
            fleet[2].isSunk() &&
            fleet[3].isSunk() &&
            fleet[4].isSunk()   )
            return true;
        
        return false;
    }
    
    public String getRank() {
        if (rank == 1)
            return "Ensign";
        else if (rank == 2)
            return "Lieutenant";
        else if (rank == 3)
            return "Commander";
        else if (rank == 4)
            return "Captain";
        else if (rank == 5)
            return "Admiral";
        else 
            return "Fleet Admiral";
    }
    
    /**
     *
     */
    public void promote() {
        rank++;
    }
    
    /**
     *
     * @return
     */
    public String toString()
    {
        return name;
    }
}
