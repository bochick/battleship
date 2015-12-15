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
    private Random rand = new Random();

    private int[] lastAttack = new int[2];
    private int[] temp = new int[2];
    private int numConsHits = 0; //remembers the number of hits in a row
    private int adjacentAttacks;
    private Direction prevDirection; //remembers the previous direction

    private boolean multiHits = false; //if true, continue attack pattern
    private boolean lastAttackHit;

    public AiPlayer(String nameInput) {
        super(nameInput);
        
        lastAttackHit = false;
        adjacentAttacks = 0;
    }

    @Override
    public void buildGrid() {
        randomPlacement();
    }

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
        } else {
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
        int randDirection = rand.nextInt(4);
        switch (randDirection) {
            case 0: {
                return Direction.up;
            }
            case 1: {
                return Direction.down;
            }

            case 2: {
                return Direction.left;
            }
            case 3: {
                return Direction.right;
            }
            default: {
                return Direction.up;
            }
        }
    }

    //-------------------------------------------------
    //used to get the inverse of the current direction
    //-------------------------------------------------

    private Direction inverse(Direction current) {
        switch (current) {
            case up: {
                return Direction.down;
            }
            case down: {
                return Direction.up;
            }

            case left: {
                return Direction.right;
            }
            case right: {
                return Direction.left;
            }
            default: {
                return current;
            }
        }
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
                    lastAttack[0]--;
                    prevDirection = Direction.up;
                    System.out.println(prevDirection);
                    break;
                }
                case down: {
                    lastAttack[0]++;
                    prevDirection = Direction.down;
                    System.out.println(prevDirection);
                    break;
                }

                case left: {
                    lastAttack[1]--;
                    prevDirection = Direction.left;
                    System.out.println(prevDirection);
                    break;
                }
                case right: {
                    lastAttack[1]++;
                    prevDirection = Direction.right;
                    System.out.println(prevDirection);
                    break;
                }
            }
            if (isValidAttack(lastAttack[0], lastAttack[1])) {
                return lastAttack;
            } else {
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
        if (missed || multiHits) {
            prevDirection = inverse(prevDirection);
        }
    }

    public void setLastAttackHit(boolean hit) {
        lastAttackHit = hit;
    }

    //-------------------------------------------------
    //checks to make sure the attack is within the grid
    //-------------------------------------------------

    private boolean isValidAttack(int row, int col) {
        if (row < 10 || col < 10) {
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

    public void resetLastAttack() {
        lastAttack = temp;
    }

}
