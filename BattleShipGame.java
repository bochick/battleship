package battleship;

import java.util.Scanner;

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
                + player.getName() + "! Prepare for combat.");
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
        System.out.println(player.getName() + "'s Personal Grid:"
                + player.getPersonal());
        System.out.println("\n--------------------------------------------------"
                + "----------\n\n" + player.getName() + "'s Target Grid:"
                + player.getGuess());
        int[] coordinates = player.attack();
        char shot = ai.hitOrMiss(coordinates[0], coordinates[1]);
        player.updateGuessGrid(coordinates[0], coordinates[1]);
        ai.updatePersonalGrid(coordinates[0], coordinates[1]);
        if (shot != '^') {
            System.out.println(player + ", you've landed a shot!");
        } else {
            System.out.println(player + ", you've missed a shot.");
        }

    }

    private void aiTurn() {
        int[] coordinates = ai.attack();
        char shot = ai.hitOrMiss(coordinates[0], coordinates[1]);
        ai.updateGuessGrid(coordinates[0], coordinates[1]);
        player.updatePersonalGrid(coordinates[0], coordinates[1]);
        if (shot != '^') {
            System.out.println("AI, " + ai + " hit one of your ships!");
        } else {
            System.out.println("AI, " + ai + " missed one of your ships!");
        }
    }
}
