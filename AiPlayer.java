/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author cim217
 */
public class AiPlayer extends Player {

    private Scanner cin = new Scanner(System.in);
    private Random rand = new Random();
    private SeaGrid personalGrid;
    private SeaGrid guessGrid = new SeaGrid("Target grid");
    ;
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
    public int[] attack() {
        int row;
        int col;

        if (lastAttackHit()) {
            //to do logic here
            row = lastAttack[1] + 1;
            col = lastAttack[0];
        } else {
            row = rand.nextInt((10 - 1) + 1) + 1;
            col = rand.nextInt((10 - 1) + 1) + 1;
        }
        if (guessGrid.getSquare(row, col) == '^') {
            int output[] = {col, row};
            lastAttack = output;
            return output;
        } else {
            return attack();
        }
    }

    //--------------------------------------------
    //lastAttackHit() used to check if the last
    //shot fired was a hit.
    //if it was, apply different logic in attack()
    //--------------------------------------------

    private boolean lastAttackHit() {
        if (hitOrMiss(lastAttack[0], lastAttack[1])) {
            return true;
        } else {
            return false;
        }
    }

}
