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
    private boolean quit = false;

    public BattleShipGame() {
        System.out.print("Initiating Battle Ship...\n"
                + "Please provide your name: ");
        String input = cin.next();

        player = new Player(input);
        ai = new AiPlayer("Marvin");
        
        System.out.println("Welcome to Battle Ship " + player 
                + "! Prepare for combat.");
    }

    public void play() {
        Player winner;
        player.buildGrid();
        ai.buildGrid();

        do {
            quit = playerTurn();
            aiTurn();
        } while (!player.fleetSunk() && !ai.fleetSunk() && !quit);
        
        if (player.fleetSunk() || quit) {
            if (quit) 
                System.out.println(player + " quit early and has forfeit!");
            winner = ai;
            ai.promote();
        }
        else {
            winner = player;
        }
        
        System.out.println(reportWinner(winner));
        System.out.print("Do you want to play another game? [Y] or [N] ");
        String input = cin.next();
        cin.nextLine();//clears the input
        
        if (input.equalsIgnoreCase("Y")) {
            play();
        }
    }

   private boolean playerTurn() {
        System.out.print(player.getPersonalGrid());
        System.out.println(new String(new char[82]).replace("\0", "-"));
        System.out.print(player.getTargetGrid());
        
        quit = playerQuit();
        
        if (!quit) {
            int[] coordinates = player.attack();
            boolean shot = ai.hit(coordinates[0], coordinates[1]);
            player.updateGuessGrid(coordinates[0], coordinates[1], shot);
            Ship.Enum ship = ai.updatePersonalGrid(coordinates[0], coordinates[1]);

            if (shot) {
                Toolkit.getDefaultToolkit().beep();
                System.out.println(player + ", you've landed a shot!");
                if (ship != null) {
                    System.out.println(player + " you have sunk " + ship);
                    player.promote();
                }
            } else {
                System.out.println(player + ", you've missed a shot.");
            }
        }
        return quit;
    }

    private void aiTurn() {
        System.out.print(ai.getPersonalGrid());
        System.out.println(new String(new char[82]).replace("\0", "-"));
        System.out.print(ai.getTargetGrid());
        
        int[] coordinates = ai.attack();
        boolean shot = player.hit(coordinates[0], coordinates[1]);
        ai.updateGuessGrid(coordinates[0], coordinates[1], shot);
        Ship.Enum ship = player.updatePersonalGrid(coordinates[0], coordinates[1]);
        
         if (shot) {
            ai.setLastAttackHit(true);
            ai.setNumConsHits(ai.getNumConsHits() + 1);
            Toolkit.getDefaultToolkit().beep();
            
            if (ship != null) {
                ai.reportSunkenShip();
                System.out.println(ai + " sunk " + ship);
            }
            else {
                    System.out.println("AI, " + ai + " hit one of your ships at " 
                        + "[row]" + (coordinates[0] + 1) 
                        + " [column]" + (coordinates[1] + 1) + "!");
            }
        } else {
            if(ai.hasMultiHits()){
                ai.changeDirection(!shot);
            }
            //else if(ai.getNumTriedAttacks() >= 4){
           //     ai.setNumConsHits(0);
           //     ai.setLastAttackHit(false);
           // }
                
            System.out.println("AI, " + ai + " missed your ships at "
                    + "[row]" + (coordinates[0] + 1) 
                    + " [column]" + (coordinates[1] + 1) + "!");
        }
        
    }
    
    private boolean playerQuit() {
        String input;
        System.out.print("Do you want to quit the game? [Y] or [N]: ");
        input = cin.next();
        boolean output = false;
        
        if (input.equalsIgnoreCase("N")) {
            output = false;
        }
        else if (input.equalsIgnoreCase("Y")) {
            output = true;
        }
        else {
            System.out.print("Unknown command... ");
            output = playerQuit();
        }
        cin.nextLine();//clears the input
        System.out.println();
        return output;
    }
    
    private String reportWinner(Player win) {
        String output = new String(new char[82]).replace("\0", "-") + "\n" 
                + win + " has won!\n(Final) " + win.getPersonalGrid();
        return output;
    }
}
