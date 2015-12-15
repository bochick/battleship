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

   public enum Direction {up, down, left, right}
    private int[] lastAttack = new int[2];
    private int numConsHits = 0;//remembers the number of hits in a row
    private Direction prevDirection;//remembers the previous direction
    private int orientation = 0;
    // 0 = n/a
    // 1 = vertical
    // -1 = horizontal

    private boolean multiHits = false;//if true, continue attack pattern

    public AiPlayer(String nameInput) {
        super(nameInput);
    }
   
     @Override
    public void buildGrid(){
       randomPlacement();
    }
    
    @Override
    public int[] attack(){
        int row = rand.nextInt(9);
        int col = rand.nextInt(9);
        int[] currentAttack = {row, col};
        
        if(multiHits)
            return lastAttack;
        else 
            return lastAttack = currentAttack;
    }
    
    @Override
   public boolean hitOrMiss(int row, int col) {
        boolean output = false;
        multiHits = false;
        char square = personalGrid.getSquare(row, col);
        
        if (square != '^' && square != 'H' && square != 'G'){
            numConsHits++;
            output = true;
            if(numConsHits >= 2)
                multiHits = true;
        } 

        return output;
    }
   
   private Direction choseRandomDirection(){
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
                default:{
                    return Direction.up;
                }
            }
    }
   
   private Direction inverse(Direction current){
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
                default:{
                    return current;
                }
            }
   }
    
   private void nextAttackDirection(int row, int col){
       if(multiHits){
           switch (prevDirection) {
                case up: {
                   row = lastAttack[0]--;
                   col = lastAttack[1];
                   prevDirection = Direction.up;
                }
                case down: {
                    row = lastAttack[0]++;
                    col = lastAttack[1];
                    prevDirection = Direction.down;
                }

                case left: {
                    row = lastAttack[0];
                    col = lastAttack[1]++;
                    prevDirection = Direction.left;
                }
                case right: {
                    row = lastAttack[0];
                    col = lastAttack[1]--;
                     prevDirection = Direction.right;
                }
            }
           lastAttack[0] = row;
           lastAttack[1] = col;
       }
       else{
           switch (choseRandomDirection()) {
                case up: {
                   row = lastAttack[0]--;
                   col = lastAttack[1];
                   prevDirection = Direction.up;
                }
                case down: {
                    row = lastAttack[0]++;
                    col = lastAttack[1];
                    prevDirection = Direction.down;
                }

                case left: {
                    row = lastAttack[0];
                    col = lastAttack[1]++;
                    prevDirection = Direction.left;
                }
                case right: {
                    row = lastAttack[0];
                    col = lastAttack[1]--;
                     prevDirection = Direction.right;
                }
                lastAttack[0] = row;
                lastAttack[1] = col;
            }  
       }
   }
  
}
