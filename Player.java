package battleship;

import java.util.Random;
import java.util.Scanner;
import java.util.regex.*;

/**
 *
 * @author JM
 */
public class Player {
    
    private Ship[] fleet;
    private String name;
    private SeaGrid personalGrid;
    private SeaGrid guessGrid = new SeaGrid("Target grid");;
    private int rank;
    
    private Scanner cin = new Scanner(System.in);
    private Random rand = new Random(); 
   
    public Player(String nameInput) {
        name = nameInput;
        rank = 1;
        personalGrid = new SeaGrid(name + "\'s grid");
        
        fleet = new Ship[5];
        fleet[0] = new Ship("Destroyer", 2);
        fleet[1] = new Ship("Submarine", 3);
        fleet[2] = new Ship("Frigate", 3);
        fleet[3] = new Ship("Cruiser", 4);
        fleet[4] = new Ship("Aircraft Carrier", 5);
    }
    
    public void buildGrid() {
        String input;
        
        System.out.print("Do you want to position your ships manually? [Y] [N]: ");
        input = cin.next();
        cin.nextLine();//clears the input
        
        if (input.equalsIgnoreCase("N")) {
            randomPlacement();
            System.out.println(personalGrid);
        }
        else if (input.equalsIgnoreCase("Y")) {
            playerPlace();
            System.out.println(personalGrid);
        }
        else {
            System.out.print("Unknown command... ");
            buildGrid();
        }
    }
    
    private String shipInfo() {
        String printShips = "";
        //prints out the list of ships to place
        for (int i = 0; i < fleet.length; i++) 
            printShips += "["+ fleet[i].getTitle() + "]" + fleet[i].getName() 
                    + "(" + fleet[i].getSize() + ") ";
        
        return printShips;
    }
    
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
            String arr[] = input.split(" "); //breakes the input into an array

            int row = Integer.parseInt(arr[0]) - 1;
            int col = Integer.parseInt(arr[1]) - 1;
            //third array element String
            int dir = charToIntDir(arr[2].toUpperCase().charAt(0));
            
            //if ship position is not invalid
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
    
    public String getPersonal()    // if u no wat i mean ;)
    {
        return personalGrid.toString();
    }
    
    public String getGuess()
    {
        return guessGrid.toString();
    }
    
    public int[] attack(){
        System.out.print("What area would you like to attack? [row] [column]: ");
        int row = cin.nextInt() - 1;
        int col = cin.nextInt() - 1;
        
        int output[] = {col, row};
        
        return output;
    }
    
    public char hitOrMiss(int row, int col) {
        char square = personalGrid.getSquare(row, col);
        if (square != '^') 
            return square;
        else
            return '^';
    }
    
    public String getName() {
        return name;
    }
    
    public void updateGuessGrid(int row, int col)
    {
        guessGrid.setSquare(row, col, 'H');
    }
    
    public void updatePersonalGrid(int row, int col)
    {
        char oldMark = personalGrid.getSquare(row, col);
        char shipTitle = '0';
        int index = 0;
        
        for (int i = 0; i < fleet.length-1 || oldMark == shipTitle; i++) {
            System.out.println(i + "/" + fleet.length + "ASD");
            shipTitle = fleet[i].getTitle();
            index = i;
        }
        
        fleet[index].hit();
        if(fleet[index].isSunk()) {
            System.out.println(name + "'s " + fleet[index].getName() 
                    + " was sunk!");
        }
        else
            personalGrid.setSquare(row, col, 'H');
    }
    
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
    
    public void promote() {
        rank++;
    }
    
    public String toString()
    {
        return this.getName();
    }
}
