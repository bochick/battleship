package battleship;

import java.util.Scanner;
import java.awt.*;

/**
 *
 * @author
 */
public class BattleShipGame {

    private Player player, ai;
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

        do {
            playerTurn();
            aiTurn();
        } while (!player.fleetSunk() && !ai.fleetSunk());
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
        boolean shot = ai.hitOrMiss(coordinates[0], coordinates[1]);
        ai.updateGuessGrid(coordinates[0], coordinates[1], shot);
        
        if (shot) {
            Toolkit.getDefaultToolkit().beep();
            System.out.println("AI, " + ai + " hit one of your ships at " 
                    + (coordinates[0] + 1) + " " + (coordinates[0] + 1) + "!");
        } else {
            System.out.println("AI, " + ai + " missed your ships at "
                    + (coordinates[0] + 1) + " " + (coordinates[0] + 1) + "!");
        }
        
        player.updatePersonalGrid(coordinates[0], coordinates[1]);
    }
}
