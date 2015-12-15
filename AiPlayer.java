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
    private SeaGrid personalGrid;
    private SeaGrid targetGrid = new SeaGrid("Target grid");

    private int[] lastAttack = new int[2];
    private int numConsHits = 0;//remembers the number of hits in a row
    private int prevDirection;//remembers the previous direction
    private int orientation = 0;
    // 0 = n/a
    // 1 = vertical
    // -1 = horizontal

    private boolean multiHits = false;//if true, continue attack pattern

    public AiPlayer(String nameInput) {
        super(nameInput);
    }
    
    //------------------------------------------
    //ai players attack() method:
    //will select a random valid location on the grid
    //if they have not attacked there before, return row, col
    //if they have, method will recurse.
    //------------------------------------------
    @Override
    public int[] attack() {
        int row = 0;
        int col = 0;

        if (lastAttackHit()) {
            numConsHits++;
            if (numConsHits >= 2) {
                multiHits = true;
            }
            controlConsHits(row, col);
        } else {
            numConsHits = 0;
            multiHits = false;
            row = rand.nextInt(10);
            col = rand.nextInt(10);
        }
        if (targetGrid.getSquare(row, col) == '^') {
            int output[] = {col, row};
            lastAttack = output;
            return output;
        } else {
            return attack();
        }
    }

    //---------------------------------------------------------
    //nextAttackDirection() is used to tell
    //the ai where to attack next.
    //if there has not yet been a hit, return a value at random
    //if there was a hit, return the previous value
    //---------------------------------------------------------
    private int nextAttackDirection() {
        if (multiHits) {
            if (prevDirection == 2 || prevDirection == 3) {
                orientation = -1;
            } else if (prevDirection == 0 || prevDirection == 1) {
                orientation = 1;
            }
            return prevDirection;
        } else {
            int direction = rand.nextInt(4);

            if (orientation == -1) {
                if (prevDirection == 3) {
                    direction = 2;
                } else if (prevDirection == 2) {
                    direction = 3;
                }
            } else if (orientation == 1) {
                if (prevDirection == 1) {
                    direction = 0;
                } else if (prevDirection == 0) {
                    direction = 1;
                }
            }

            return direction;
        }
    }

    //--------------------------------------------
    //lastAttackHit() used to check if the last
    //shot fired was a hit.
    //if it was, apply different logic in attack()
    //--------------------------------------------
    private boolean lastAttackHit() {
        if ( hitOrMiss(lastAttack[0], lastAttack[1]) ) {
//            System.out.println("HOOPLAH");
            return true;
        } else {
            return false;
        }
    }
    
//    private boolean lastAttackSunk() {
//        char square = personalGrid.getSquare(lastAttack[0], lastAttack[1]);
//        if (square == 'S') {
//            orientation = 0;
//            return true;
//        } else {
//            return false;
//        }
//    }

    @Override
    public void buildGrid() {
        randomPlacement();
    }

//    public void getSunk() {
//        orientation = 0;
//    }

    //------------------NOTES NOT YET COMPLETE-----------------
    //if next attack hits, chose a direction to try at random.
    //"next" will have to be remembered if the attack hits again
    //to keep trying that direction.
    //---------------------------------------------------------
    private void controlConsHits(int row, int col) {
        //prevDirection = nextAttackDirection();
        int next = nextAttackDirection();
        if (lastAttack[0] > 9 && lastAttack[1] > 9) {
            switch (next) {
                case 0: {
                    row = lastAttack[0]--;
                    col = lastAttack[1];
                }//up
                case 1: {
                    row = lastAttack[0]++;
                    col = lastAttack[1];
                }//down

                //-----------------------------
                case 2: {
                    row = lastAttack[0];
                    col = lastAttack[1]++;
                }//left
                case 3: {
                    row = lastAttack[0];
                    col = lastAttack[1]--;
                }//right
                }
        }
        prevDirection = next;
    }

}
