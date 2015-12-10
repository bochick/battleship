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
    private SeaGrid guessGrid = new SeaGrid("Target grid");
  
    private int[] lastAttack = new int[2];

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
            //------------------NOTES NOT YET COMPLETE-----------------
            //if next attack hits, chose a direstion to try at random.
            //"next" will have to be remembered if the attack hits again
            //to keep trying that direction.
            //more logic is needed to check if a direction is 
            //even valid(on the grid)
            //---------------------------------------------------------
            int next = nextAttackDirection();
            switch(next){
                case 0:{row = lastAttack[1]++; col = lastAttack[0];}//up
                case 1:{row = lastAttack[1]--; col = lastAttack[0];}//down
                case 2:{row = lastAttack[1]; col = lastAttack[0]--;}//left
                case 3:{row = lastAttack[1]; col = lastAttack[0]++;}//right
            }
        } 
        else {
            row = rand.nextInt(10);
            col = rand.nextInt(10);
        }
        if (guessGrid.getSquare(row, col) == '^') {
            int output[] = {col, row};
            lastAttack = output;
            return output;
        } else {
            return attack();
        }
    }

    private int nextAttackDirection(){
       int direction = rand.nextInt(3);
        return direction;
    }
    //--------------------------------------------
    //lastAttackHit() used to check if the last
    //shot fired was a hit.
    //if it was, apply different logic in attack()
    //--------------------------------------------
    private boolean lastAttackHit() {
        if (hitOrMiss(lastAttack[0], lastAttack[1]) != '^') {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public void buildGrid(){
        randomPlacement();
    }

    
}
