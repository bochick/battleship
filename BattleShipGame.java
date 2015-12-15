package battleship;

import java.util.Scanner;
import java.awt.*;

/**
 *
 * @author
 */
public class BattleShipGame {

    private Player player;
    private AiPlayer ai;
    private Scanner cin = new Scanner(System.in);

    public BattleShipGame() {
        System.out.print("Initiating Battle Ship...\n"
                + "Please provide your name: ");
        String input = cin.next();

        player = new Player(input);
        ai = new AiPlayer("Marvin");
        
        System.out.println("Welcome to Battle Ship " + player.getRank() + " "
                + player + "! Prepare for combat.");
    }

    public void play() {
        player.buildGrid();
        ai.buildGrid();

        boolean quit = false;

        do {
            
            playerTurn();
            aiTurn();
        } while (!player.fleetSunk() && !ai.fleetSunk() || quit);
    }

    private void playerTurn() {
        System.out.print(player + "'s Personal Grid:" + player.getPersonalGrid());
        System.out.println("--------------------------------------------------"
                + "--------------------------------------\n" + player 
                + "'s Target Grid: " + player.getTargetGrid());
        
        int[] coordinates = player.attack();
        boolean shot = ai.hitOrMiss(coordinates[0], coordinates[1]);
        player.updateGuessGrid(coordinates[0], coordinates[1], shot);
        
        if (shot) {
            Toolkit.getDefaultToolkit().beep();
            System.out.println(player + ", you've landed a shot!");
        } else {
            System.out.println(player + ", you've missed a shot.");
        }
        
        ai.updatePersonalGrid(coordinates[0], coordinates[1]);
    }

    private void aiTurn() {
        int[] coordinates = ai.attack();
        boolean shot = player.hitOrMiss(coordinates[0], coordinates[1]);
        ai.updateGuessGrid(coordinates[0], coordinates[1], shot);
        
        if (shot) {
            ai.setLastAttackHit(true);
            Toolkit.getDefaultToolkit().beep();
            System.out.println("AI, " + ai + " hit one of your ships at " 
                    + "[row]:" + (coordinates[0] + 1) 
                    + " [column]:" + (coordinates[1] + 1) + "!");
        } else {
            System.out.println("AI, " + ai + " missed your ships at "
                    + "[row]:" + (coordinates[0] + 1) 
                    + " [column]:" + (coordinates[1] + 1) + "!");
        }
        
        player.updatePersonalGrid(coordinates[0], coordinates[1]);
    }
    
    private boolean playerQuit() {
        String input;
        System.out.print("Do you want to continue? [Y] or [N] ");
        input = cin.next();
        cin.nextLine();//clears the input
        boolean output = false;
        
        if (input.equalsIgnoreCase("N")) {
            output = true;
        }
        else if (input.equalsIgnoreCase("Y")) {
            output = false;
        }
        else {
            System.out.print("Unknown command... ");
            output = playerQuit();
        }
        return output;
    }
}
