package battleship;
import java.util.Scanner;

/**
 *
 * @author cim114
 */
public class Driver {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        BattleShipGame bsGame = new BattleShipGame();
        Scanner cin = new Scanner(System.in);
        String input;
        
        do {
            bsGame.play();
            
            System.out.print("New Player? [Y] or [N] ");
            input = cin.next();
            cin.nextLine();//clears the input
            
        } while (input.equalsIgnoreCase("Y"));
    }
}
