/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.Random;

/**
 *
 * @author Anthony&Brad
 */
public class AiPlayer extends Player {

    //private Ship[] fleet;
    private Random rand = new Random();
    //private SeaGrid personalGrid;
    //private SeaGrid targetGrid = new SeaGrid("Target grid");

    private int[] lastAttack = new int[2];
    private int[] temp = new int[2];
    private int numTriedAttacks;
    private int numConsHits = 0;//remembers the number of hits in a row
    private int adjacentAttacks;
    private Direction prevDirection;//remembers the previous direction

    private boolean inversed = false;
    private boolean multiHits = false;//if true, continue attack pattern
    private boolean lastAttackHit;

    public AiPlayer(String nameInput) {
        super(nameInput);
        //personalGrid = new SeaGrid(nameInput + "\'s grid");
        lastAttackHit = false;
        adjacentAttacks = 0;
        numTriedAttacks = 0;
        //fleet = new Ship[5];
        //fleet[0] = new Ship("Destroyer", 2);
       // fleet[1] = new Ship("Submarine", 3);
        //fleet[2] = new Ship("Frigate", 3);
        //fleet[3] = new Ship("Cruiser", 4);
        //fleet[4] = new Ship("Aircraft Carrier", 5);
    }

//    @Override
//    public void buildGrid() {
//        randomPlacement();
//    }

    //-------------------------------------------------
    //attack() returns the cordinates of the ai's attack
    //if the ai has no previous hits, attack at random
    //otherwise call nextAttack() if the last attack hit
    //-------------------------------------------------
    @Override
    public int[] attack() {
        int row = rand.nextInt(9);
        int col = rand.nextInt(9);
        int[] currentAttack = {row, col};

        if (lastAttackHit) {
            adjacentAttacks++;
            System.out.println("ai got into lastattackhit, multihits is " + multiHits);
            System.out.println("num of cons hits: " + numConsHits);
           currentAttack =  nextAttack();
           System.out.println("direction is: " + prevDirection);
           return currentAttack;
        }
        else {
            System.out.println("ai did not get into lastattackhit");
            return lastAttack = currentAttack;
        }
    }
    //-------------------------------------------------
    //chose a direction at random by using a random number
    //0 = up
    //1 = down
    //2 = left
    //3 = right
    //is up by default
    //-------------------------------------------------
    private Direction choseRandomDirection() {
        numTriedAttacks++;
        switch (numTriedAttacks) {
            case 1: {
                return Direction.up;
            }
            case 2: {
                return Direction.down;
            }

            case 3: {
                return Direction.left;
            }
            case 4: {
                return Direction.right;
            }
            default: {
                return Direction.up;
            }
        }
    }
    
    public void reportSunkenShip()
    {
        //Stuff for going back and checking for other hits goes here
        setMultiHits(false);
        setNumConsHits(0);
        setLastAttackHit(false);
    }
    //-------------------------------------------------
    //used to get the inverse of the current direction
    //-------------------------------------------------
    private Direction inverse(Direction current) {
        if(current == Direction.up)
            return Direction.down;
        else if(current == Direction.down)
            return Direction.up;
        else if(current == Direction.left)
            return Direction.right;
        else if (current == Direction.right)        
                return Direction.left;
        else
            return current;
        }
    //--------------------------------------------------------------------------
    //nextAttack returns the cordinates of the next attack ai will make
    //also sets multiHits to true if ai has landed atleast 2 attacks in a row
    //checks if the direction is valid on the grid, if not inverse the direction
    //--------------------------------------------------------------------------
    private int[] nextAttack() {
        temp = lastAttack;
        if (numConsHits >= 2) {
            multiHits = true;
        }
        if (multiHits) {
            switch (prevDirection) {
                case up: {
                    lastAttack[0]--;
                    prevDirection = Direction.up;
                    break;
                }
                case down: {
                    lastAttack[0]++;
                    prevDirection = Direction.down;
                    break;
                }

                case left: {
                    lastAttack[1]--;
                    prevDirection = Direction.left;
                    break;
                }
                case right: {
                    lastAttack[1]++;
                    prevDirection = Direction.right;
                    break;
                }
            }
            if (isValidAttack(lastAttack[0], lastAttack[1])) {
                return lastAttack;
            } else {
                prevDirection = inverse(prevDirection);
                return nextAttack();
            }
        } else {
            switch (choseRandomDirection()) {
                case up: {
                    if ( lastAttack[0] >= 2 )
                    {
                    lastAttack[0]--;
                    prevDirection = Player.Direction.up;
                    System.out.println(prevDirection);
                    break;
                    }
                    else
                    {
                    numTriedAttacks++;
                    lastAttack[0]++;
                    prevDirection = Player.Direction.down;
                    System.out.println(prevDirection);
                    break;
                    }
                }
                case down: {
                    if ( lastAttack[0] <= 8 )
                    {
                    lastAttack[0] += 2;
                    prevDirection = Player.Direction.down;
                    System.out.println(prevDirection);
                    break;
                    }
                }

                case left: {
                    if ( prevDirection == Player.Direction.down )
                        lastAttack[0]--;
                    
                    if ( lastAttack[1] >= 2 )
                    {
                    lastAttack[1]--;
                    prevDirection = Player.Direction.left;
                    System.out.println(prevDirection);
                    break;
                    }
                    else
                    {
                    numTriedAttacks++;
                    lastAttack[1]++;
                    prevDirection = Player.Direction.right;
                    System.out.println(prevDirection);
                    break;  
                    }
                }
                case right: {
                    lastAttack[1] += 2;
                    prevDirection = Player.Direction.right;
                    System.out.println(prevDirection);
                    break;
                }
            }
            if (isValidAttack(lastAttack[0], lastAttack[1])) {
                return lastAttack;
            } else {
                prevDirection = inverse(prevDirection);
                return nextAttack();
            }
        }
    }
    //-------------------------------------------------
    //***** to be used in battleShipGame ******
    //*****************************************
    //-------------------------------------------------
    //used to change the direction if we know the orientation
    //but still miss
    //-------------------------------------------------
    public void changeDirection(boolean missed) {
        if (missed && multiHits) {
            if(prevDirection == Direction.up)
                lastAttack[0] += numConsHits;
            else if (prevDirection == Direction.down)
                lastAttack[0] -= numConsHits;
            else if (prevDirection == Direction.left)
                lastAttack[1] += numConsHits;
            else if (prevDirection == Direction.right)
                lastAttack[1] -= numConsHits;
            prevDirection = inverse(prevDirection);
            System.out.println("direction has been inversed");
        }
    }
    
    public void setLastAttackHit(boolean hit) {
        lastAttackHit = hit;
    }
    //-------------------------------------------------
    //checks to make sure the attack is within the grid
    //-------------------------------------------------
    private boolean isValidAttack(int row, int col) {
        if (row < 10 && col < 10 && row > -1 && col > -1) {
            return true;
        } else {
            return false;
        }
    }
   //-----------------------------------------
    //***** to be used in battleShipGame ******
    //*****************************************
    //-----------------------------------------
    //checks if the ai has landed multiple hits
    //-----------------------------------------
    public boolean hasMultiHits() {
        return multiHits;
    }
    public void setMultiHits(boolean h){
        multiHits = h;
    }
   //-----------------------------------------
    //***** to be used in battleShipGame ******
    //*****************************************
    //-----------------------------------------
    //chekcs the number of adjacent attacks
    //used so the ai will check all 4 adjacent
    //squares in the worse case
    //-----------------------------------------
    public int getAdjacentAttacks() {
        return adjacentAttacks;
    }
   //-----------------------------------------
    //***** to be used in battleShipGame ******
    //*****************************************
    //-----------------------------------------
    //used to add to or reset the number of
    //cons hits
    //-----------------------------------------

    public void setNumConsHits(int n) {
        numConsHits = n;
    }
   //-----------------------------------------
    //***** to be used in battleShipGame ******
    //*****************************************
    //-----------------------------------------
    //used to get the current number of cons hits
    //-----------------------------------------

    public int getNumConsHits() {
        return numConsHits;
    }
   //-----------------------------------------
    //***** to be used in battleShipGame ******
    //*****************************************
    //-----------------------------------------
    //used to reset last attack after missing
    //when attacking an adjacent square. only
    //up to 4 times
    //-----------------------------------------

    public boolean getInversed()
    {
        return inversed;
    }
    
    public void setInversed(boolean inv)
    {
        inversed = inv;
    }
    
    public void resetLastAttack() {
        lastAttack = temp;
    }

    public Direction getDirection(){
        return prevDirection;
    }
    
    public void setNumTriedAttacks(int num)
    {
        numTriedAttacks = num;
    }
    
    public int getNumTriedAttacks(){
        return numTriedAttacks;
    }

   
}
