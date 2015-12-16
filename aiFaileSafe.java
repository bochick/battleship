/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package battleship;

import java.util.Random;

/**
 *
 * @author Anthony
 */
public class aiFailSafe extends Player {
    
     private Random rand = new Random();
    
     public aiFailSafe(String nameInput) {
        super(nameInput);
    }
    
      public void buildGrid() {
        randomPlacement();
    }
      
     @Override
      public int[] attack(){
        int row = rand.nextInt(9);
        int col = rand.nextInt(9);
        int[] currentAttack = {row, col};
        
        if(targetGrid.getSquare(currentAttack[0], currentAttack[1]) == 'G' ||
           targetGrid.getSquare(currentAttack[0], currentAttack[1]) == 'H' ||
           targetGrid.getSquare(currentAttack[0], currentAttack[1]) == 'X')
            return attack();
        else
            return currentAttack;
    }
}
