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

     private Ship[] fleet;
    private Random rand = new Random();
    private SeaGrid personalGrid;
    private SeaGrid targetGrid = new SeaGrid("Target grid");

   public enum Direction {up, down, left, right}
    private int[] lastAttack = new int[2];
    private int numConsHits = 0;//remembers the number of hits in a row
    private Direction prevDirection;//remembers the previous direction
    private int orientation = 0;
    // 0 = n/a
    // 1 = vertical
    // -1 = horizontal

    private boolean multiHits = false;//if true, continue attack pattern
    private boolean lastAttackHit;

    public AiPlayer(String nameInput) {
        super(nameInput);
        personalGrid = new SeaGrid(nameInput + "\'s grid");
        lastAttackHit = false;
        fleet = new Ship[5];
        fleet[0] = new Ship("Destroyer", 2);
        fleet[1] = new Ship("Submarine", 3);
        fleet[2] = new Ship("Frigate", 3);
        fleet[3] = new Ship("Cruiser", 4);
        fleet[4] = new Ship("Aircraft Carrier", 5);
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
        
        if(lastAttackHit){
            System.out.println("ai got into lastattackhit, multihits is " + multiHits);
            return nextAttack();
        }
        else{
            System.out.println("ai did not get into lastattackhit");
            return lastAttack = currentAttack;
        }
    }
    
    @Override
   public boolean hitOrMiss(int row, int col) {
        boolean output = false;
        char square = targetGrid.getSquare(row, col);
        
        if (square != '^' && square != 'H' && square != 'G'){
            numConsHits++;
            output = true;
            lastAttackHit = true;
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
    
   private int[] nextAttack(){
       if(multiHits){
           switch (prevDirection) {
                case up: {
                   lastAttack[0]--;
                   prevDirection = Direction.up;
                }
                case down: {
                   lastAttack[0]++;
                    prevDirection = Direction.down;
                }

                case left: {
                    lastAttack[1]++;
                    prevDirection = Direction.left;
                }
                case right: {
                    lastAttack[1]--;
                     prevDirection = Direction.right;
                }
            }

           return lastAttack;
       }
       else{
           Direction temp = choseRandomDirection();
           switch (temp) {
                case up: {
                   lastAttack[0]--;
                   prevDirection = Direction.up;
                }
                case down: {
                    lastAttack[0]++;
                    prevDirection = Direction.down;
                }

                case left: {
                    lastAttack[1]++;
                    prevDirection = Direction.left;
                }
                case right: {
                    lastAttack[1]--;
                     prevDirection = Direction.right;
                }
            } 
           return lastAttack; 
       }
   }
  
   private void changeDirection(){
       if(!hitOrMiss(lastAttack[0], lastAttack[1]) && multiHits)
           prevDirection = inverse(prevDirection);
   }
   
   public void setLastAttackHit(boolean hit){
       lastAttackHit = hit;
   }
   
   
   
}
