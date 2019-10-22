import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameGUI extends JFrame {

    private Container contentPane = getContentPane();
    private JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPiecesPanel, bottomPiecesPanel;

    public GameGUI(){

        //Initializing all the panels
        mainGridPanel = new JPanel();
        leftPiecesPanel = new JPanel();
        rightPiecesPanel = new JPanel();
        topPiecesPanel = new JPanel();
        bottomPiecesPanel = new JPanel();

        //Setting layouts of all the panel
        contentPane.setLayout(new BorderLayout());

        //Adding the panels to the main frame
        contentPane.add(leftPiecesPanel, BorderLayout.WEST);
        contentPane.add(rightPiecesPanel, BorderLayout.EAST);
        contentPane.add(topPiecesPanel, BorderLayout.NORTH);
        contentPane.add(bottomPiecesPanel, BorderLayout.SOUTH);
        contentPane.add(mainGridPanel, BorderLayout.CENTER);

        mainGridPanel.add(createGrid(20,20,35,35));
        leftPiecesPanel.add(createPlayingPieces());
        rightPiecesPanel.add(createPlayingPieces());

        colourPieces(); 

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Ensures that the JFrame opens in fullscreen
        setVisible(true);
    }

    private JPanel createGrid(int gridRows, int gridColumns, int buttonWidth, int buttonHeight){
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridBagLayout());

        ArrayList<ArrayList<JButton>> buttons = createButtonsList(gridRows, gridColumns);

        GridBagConstraints gbc = new GridBagConstraints();

        for (int r = 0; r < gridRows; r++){
            gbc.gridy = r;
            for (int c = 0; c < gridColumns; c++){
                gbc.gridx = c;
                JButton btn =  buttons.get(r).get(c);
                btn.setForeground(Color.white);
                btn.setBackground(Color.white);
                btn.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
                btn.setFocusable(false);
                btn.addActionListener(new gridListener());
                tempPanel.setBorder(new EmptyBorder(1,1,1,1));
                tempPanel.add(btn,gbc);
            }
        }
        return tempPanel;
    }

    private ArrayList<ArrayList<JButton>> createButtonsList(int gridRows, int gridColumns){
        ArrayList<ArrayList<JButton>> buttons = new ArrayList<>();

        for (int r = 0; r < gridRows; r++) {
            buttons.add(new ArrayList<>());
            for (int c = 0; c < gridColumns; c++) {
                JButton btn = new JButton();
                String nameStr = r + "," + c;
                btn.setName(nameStr);
                buttons.get(r).add(btn);
            }
        }
        return buttons;
    }

    private JPanel createPlayingPieces(){
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel pnl1 = createGrid(17,17, 20,20);
        pnl1.setBorder(new EmptyBorder(30,30,30,30));

        JPanel pnl2 = createGrid(17,17, 20,20);
        pnl2.setBorder(new EmptyBorder(30,30,30,30));

        main.add(pnl1);
        main.add(pnl2);

        return main;
    }

    private void colourPieces(){
        Component[] leftComponents = leftPiecesPanel.getComponents();
        Component firstPanel = ((JPanel) leftComponents[0]).getComponents()[0];
        Component secondPanel = ((JPanel) leftComponents[0]).getComponents()[1];

        Component[] rightComponenets = rightPiecesPanel.getComponents();
        Component thirdPanel = ((JPanel) rightComponenets[0]).getComponents()[0];
        Component fourthPanel = ((JPanel) rightComponenets[0]).getComponents()[1];

        for (int j = 0; j < 4; j++){
            Component[] c = new Component[4];
            Color color = Color.white;

            if (j == 0) {
                c = ((JPanel) firstPanel).getComponents();
                color = Color.blue;
            }
            if (j == 1) {
                c = ((JPanel) secondPanel).getComponents();
                color = Color.green;
            }
            if (j == 2) {
                c = ((JPanel) thirdPanel).getComponents();
                color = Color.yellow;
            }
            if (j == 3) {
                c = ((JPanel) fourthPanel).getComponents();
                color = Color.red;
            }

            for(int i = 0; i < c.length; i++){
                String name = c[i].getName();

                //This is extremely inefficient however we will change it later
                if(name.equals("0,0") || name.equals("0,1") || name.equals("0,2") || name.equals("0,3") || name.equals("0,5") || name.equals("0,6") || name.equals("0,7") || name.equals("0,8")
                        || name.equals("0,11") || name.equals("0,12") || name.equals("0,13") || name.equals("0,14") || name.equals("1,8") || name.equals("1,13") || name.equals("3,0")
                        || name.equals("3,3") || name.equals("3,4") || name.equals("3,6") || name.equals("3,10") || name.equals("3,11") || name.equals("3,15") || name.equals("4,0")
                        || name.equals("4,3") || name.equals("4,4") || name.equals("4,6") || name.equals("4,11") || name.equals("4,14") || name.equals("4,15") || name.equals("4,16")
                        || name.equals("5,0") || name.equals("5,1") || name.equals("5,3") || name.equals("5,6") || name.equals("5,7") || name.equals("5,8") || name.equals("5,11")
                        || name.equals("5,12") || name.equals("5,15") || name.equals("7,0") || name.equals("7,3") || name.equals("7,4") || name.equals("7,6") || name.equals("7,10")
                        || name.equals("7,11") || name.equals("7,12") || name.equals("7,14") || name.equals("7,15") || name.equals("8,0") || name.equals("8,1") || name.equals("8,3")
                        || name.equals("8,6") || name.equals("8,7") || name.equals("8,11") || name.equals("8,15") || name.equals("8,16") || name.equals("9,0") || name.equals("9,3")
                        || name.equals("9,4") || name.equals("9,7") || name.equals("9,8") || name.equals("9,11") || name.equals("9,15") || name.equals("11,0") || name.equals("11,1")
                        || name.equals("11,2") || name.equals("11,5") || name.equals("11,6") || name.equals("11,9") || name.equals("11,12") || name.equals("11,13") || name.equals("11,15")
                        || name.equals("12,2") || name.equals("12,3") || name.equals("12,6") || name.equals("12,7") || name.equals("12,9") || name.equals("12,10") || name.equals("12,12")
                        || name.equals("12,13") || name.equals("14,0") || name.equals("14,1") || name.equals("14,2") || name.equals("14,3") || name.equals("14,4") || name.equals("14,6")
                        || name.equals("14,7") || name.equals("14,8") || name.equals("14,10") || name.equals("14,11"))
                {

                    c[i].setBackground(color);
                    //TODO: Can Set Name and other things here later
                }
            }
        }
    }

    private class gridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String st = ((JButton) e.getSource()).getName();
            System.out.print("The button on location ");
            System.out.print(st);
            System.out.println(" was pressed.");
        }
    }
}