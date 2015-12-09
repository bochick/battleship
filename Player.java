/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        fleet[0] = new Ship("Destroyer",2);
        fleet[1] = new Ship("Submarine",3);
        fleet[2] = new Ship("Frigate",3);
        fleet[3] = new Ship("Cruiser",4);
        fleet[4] = new Ship("Aircraft Carrier",5);
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
    
    private void playerPlace() {
        String printShips = "";
        int dir = 0;
        int ship = 0;
        
        for (int i = 0; i < fleet.length; i++) //prints out the list of ships to place
            printShips += "["+ fleet[i].title() + "]" + fleet[i].getName() 
                    + "(" + fleet[i].getSize() + ") ";

        System.out.println(printShips + "\n" + "[U]Up [D]Down [L]Left [R]Right");
        System.out.print(personalGrid);
        
        while (ship < fleet.length) { //cycles through the array of ships
            String input = "";
            
            // makes sure the input matches the criteria: [row] [column] [direction]
            while (!Pattern.matches("[0-9]{1,} [0-9]{1,} [u|U|d|D|l|L|r|R]?", input)) {
                System.out.println(fleet[ship] +  ": [row] [column] [direction]");
                input = cin.nextLine();
            }
            String arr[] = input.split(" "); //breakes the input into an array

            int row = Integer.parseInt(arr[0]) - 1;
            int col = Integer.parseInt(arr[1]) - 1;
            
            //sets the direction
            if (arr[2].equalsIgnoreCase("D"))
                dir = 0;
            else if (arr[2].equalsIgnoreCase("U"))
                dir = 1;
            else if (arr[2].equalsIgnoreCase("R"))
                dir = 2;
            else if (arr[2].equalsIgnoreCase("L"))
                dir = 3;

            if (validPlacement(row, col, dir, fleet[ship].getSize())) {//if ship position is not invalid
                for (int i = 0; i < fleet[ship].getSize(); i++) {
                    personalGrid.update(row, col, fleet[ship].title());//places ship
                    if (dir == 0)//down
                        row++;
                    else if (dir == 1)//up
                        row--;
                    else if (dir == 2)//right
                        col++;
                    else if (dir == 3)//left
                        col--;
                }
                ship++;//next ship
            }
            else 
                System.out.println("Invalid ship position.");
        }
    }
    
    private void randomPlacement() {
        int index = fleet.length - 1;
        
        while (index >= 0) {
            int row = rand.nextInt(10);
            int col = rand.nextInt(10);
            int dir = rand.nextInt(4);
                
            if (validPlacement(row, col, dir, fleet[index].getSize())) {
                fleet[index].setLocation(col, row);
                fleet[index].setDirection(dir);
                for (int i = 0; i < fleet[index].getSize(); i++) {
                    personalGrid.update(row, col, fleet[index].title());
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
    
    public int[] attack(Player in){
        System.out.print("What area would you like to attack? [row] [column]: ");
        int row = cin.nextInt() - 1;
        int col = cin.nextInt() - 1;
        
        int output[] = {col, row};
        
        return output;
    }
    
    public boolean hitOrMiss(int row, int col) {
        char shot = personalGrid.getSquare(row, col);
        
        if (shot != '^') 
            return true;
        else
            return false;
    }
    
    public String getName() {
        return name;
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
}
