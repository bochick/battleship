
//GUI
package battleship;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;

public class SeaGridOutput extends JPanel {

    public static final int GRID_AMT = 4;

    private final String gridTitle[] = {"Player Target Grid",
                                        "AI Target Grid",
                                        "Player Personal Grid",
                                        "AI Personal Grid"};
    
    private JPanel gridPanel[] = new JPanel[GRID_AMT];
    private TitledBorder gridBorder[] = new TitledBorder[GRID_AMT];
    private JTextArea gridArea[] = new JTextArea[GRID_AMT];

    public SeaGridOutput() {
        for (int i = 0; i < GRID_AMT; i++) {
            gridPanel[i] = new JPanel();
            gridPanel[i].setPreferredSize(new Dimension(400,250));
            gridBorder[i] = new TitledBorder(gridTitle[i]);
            gridBorder[i].setTitleJustification(TitledBorder.LEFT);
            gridBorder[i].setBorder(new LineBorder(Color.BLACK, 5));
            
            gridArea[i] = new JTextArea(13,32);
            gridArea[i].setEditable(false);
            gridArea[i].setAlignmentX(TOP_ALIGNMENT);
            gridArea[i].setTabSize(3);
            
            gridPanel[i].setBorder(gridBorder[i]);
            gridPanel[i].add(gridArea[i]);
            
            add(gridPanel[i]);
            
            setPreferredSize(new Dimension(820,520));
            
        }
    }
    
    public void updatePlayerGrid(
            String plrTG, String plrPG, String aiTG, String aiPG) {
        gridArea[0].setText(plrTG);
        gridArea[1].setText(aiTG);
        gridArea[2].setText(plrPG);
        gridArea[3].setText(aiPG);
    }

}
