/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package battleship;

import java.util.Scanner;

/**
 *
 * @author JM
 */
public class BattleShipGame {
    
    private Player player, ai;
    private Scanner cin = new Scanner(System.in);
    
    public BattleShipGame() {
        System.out.print("Initiating Battle Ship...\n"
                + "Please provide your name: ");
        String input = cin.next();
        
        player = new Player(input);
        ai = new Player("Marvin");
        System.out.println("Welcome to Battle Ship "+ player.getRank() + " " 
                + player.getName() + "! Prepare for combat.");
    }
    
    public void play() {
        player.buildGrid();
        ai.buildGrid();
        
        player.attack(ai);
    }
}
