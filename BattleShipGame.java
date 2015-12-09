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
        
        
        
        do
        {
           
            playerTurn();
            aiTurn();
       
        }while(!player.fleetSunk() && !ai.fleetSunk());
    }
    
    private void playerTurn()
    {
       System.out.println(player.getName() + "'s Personal Grid:" 
               + player.getPersonal() );
       System.out.println("\n--------------------------------------------------"
               + "----------\n\n" + player.getName() + "'s Target Grid:"
       + player.getGuess());
       int[] coordinates = player.attack();
       char shot = ai.hitOrMiss(coordinates[0], coordinates[1]);
        if( shot != '^' )
        {
            player.GoodHit(coordinates[0], coordinates[1]);
            ai.BadHit(coordinates[0], coordinates[1]);
            System.out.println("You've landed a shot!");
            
        }
        
    }
    
    private void aiTurn()
    {
        int[] coordinates = ai.attack();
        char shot = ai.hitOrMiss(coordinates[0], coordinates[1]);
        
        if( shot != '^')
        {
            ai.GoodHit(coordinates[0], coordinates[1]);
            player.BadHit(coordinates[0], coordinates[1]);
        }
    }
}
