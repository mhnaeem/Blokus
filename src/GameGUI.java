import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main GameGUI screen
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

public class GameGUI extends JFrame {

    private Container contentPane = getContentPane();
    private JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPanel, bottomPanel;
    private int numberOfPlayers;

    /* The panels are set based on indexes, in the following order
       2 Players = [Player 1, Player 2]
       3 Players = [Player 1, Player 2, Player 3]
       4 Players = [Player 1, Player 2, Player 3, Player 4]

       [1]   [Main Grid]    [2]
       [3]   [Main Grid]    [4]
     */
    private ArrayList<JPanel> listOfPiecesPanels;

    // Meant to hold the player number and the colours
    // For example: { 1 : Color.blue, 2 : Color.green}
    private HashMap<Integer, Color> mapOfColours;

    private boolean colour_blind;

    public GameGUI(int number_of_players, HashMap<Integer, Color> map_of_colours, boolean colour_blind){

        this.numberOfPlayers = number_of_players;
        this.mapOfColours = map_of_colours;
        this.colour_blind = colour_blind;

        createMainPanels();

        //Create the main grid
        mainGridPanel.add(createGrid(20,20,35,35));
        //Used to make the grid centered in the window
        mainGridPanel.setLayout(new BoxLayout(mainGridPanel, BoxLayout.Y_AXIS));

        setTitle("Blokus");

        //Create the player pieces on the left and right, depending on the number of players
        createPlayingPieces();

        //Colour the pieces for the players
        colourPieces();
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Ensures that the JFrame opens in fullscreen
        setVisible(true);
    }

    private void createMainPanels(){
        //Initializing all the panels
        mainGridPanel = new JPanel();
        leftPiecesPanel = new JPanel();
        rightPiecesPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();

        //Setting layouts of all the panels
        contentPane.setLayout(new BorderLayout());

        //Adding the panels to the main frame
        contentPane.add(leftPiecesPanel, BorderLayout.WEST);
        contentPane.add(rightPiecesPanel, BorderLayout.EAST);
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.add(mainGridPanel, BorderLayout.CENTER);
    }

    public JPanel createGrid(int gridRows, int gridColumns, int buttonWidth, int buttonHeight){
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridBagLayout());

        JButton[][] buttons = createButtonsList(gridRows, gridColumns);

        GridBagConstraints gbc = new GridBagConstraints();

        for (int r = 0; r < gridRows; r++){
            gbc.gridy = r;
            for (int c = 0; c < gridColumns; c++){
                gbc.gridx = c;
                JButton btn =  buttons[r][c];
                btn.setForeground(Color.white);
                btn.setBackground(Color.white);
                btn.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
                btn.setFocusable(false);
                btn.addActionListener(new gridListener());
                tempPanel.setBorder(new EmptyBorder(0,0,0,0));
                tempPanel.add(btn,gbc);
            }
        }
        return tempPanel;
    }

    private JButton[][] createButtonsList(int gridRows, int gridColumns){
        JButton[][] buttons = new JButton[gridRows][gridColumns];

        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridColumns; c++) {
                JButton btn = new JButton();
                String nameStr = r + "," + c;
                btn.setName(nameStr);
                buttons[r][c] = btn;
            }
        }
        return buttons;
    }

    private void createPlayingPieces() {

        listOfPiecesPanels = new ArrayList<>();

        for (int i = 0; i < 4; i++){
            JPanel tempPnl = createGrid(16, 17, 20, 20);
            tempPnl.setBorder(new EmptyBorder(10, 30, 30, 30));
            //This is naming every panel of pieces for its player
            tempPnl.setName("Player " + Integer.toString(i+1));
            listOfPiecesPanels.add(tempPnl);
        }

        JLabel[] playerLabels = new JLabel[4];
        for (int i = 0; i < 4; i++){
            JLabel tempLabel = new JLabel("Player " + Integer.toString(i+1));
            tempLabel.setFont(new Font("Serif", Font.BOLD, 35));
            playerLabels[i] = tempLabel;
        }

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        leftPanel.add(playerLabels[0]);
        leftPanel.add(listOfPiecesPanels.get(0));
        rightPanel.add(playerLabels[1]);
        rightPanel.add(listOfPiecesPanels.get(1));

        if (numberOfPlayers == 3){
            //TODO: Fix later, alternating player
            leftPanel.add(listOfPiecesPanels.get(2));
        }
        if (numberOfPlayers == 4){
            leftPanel.add(playerLabels[2]);
            leftPanel.add(listOfPiecesPanels.get(2));
            rightPanel.add(playerLabels[3]);
            rightPanel.add(listOfPiecesPanels.get(3));
        }

        leftPiecesPanel.add(leftPanel);
        rightPiecesPanel.add(rightPanel);
    }

    private void colourPieces(){
        for (int j = 1; j <= this.numberOfPlayers; j++) {
            Color color = mapOfColours.get(j);
            Component[] c = listOfPiecesPanels.get(j-1).getComponents();
            for (int i = 0; i < c.length; i++) {
                String name = c[i].getName();

                //This is extremely inefficient however we will change it later
                if (name.equals("0,0") || name.equals("0,1") || name.equals("0,2") || name.equals("0,3") || name.equals("0,5") || name.equals("0,6") || name.equals("0,7") || name.equals("0,8")
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
                        || name.equals("14,7") || name.equals("14,8") || name.equals("14,10") || name.equals("14,11")) {

                    c[i].setBackground(color);
                    //TODO: Can Set Name and other things here later
                } else {
                    c[i].setEnabled(false);
                }
            }
        }
    }

    private class gridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(((JButton) e.getSource()).getParent().getName());
            String st = ((JButton) e.getSource()).getName();
            System.out.print("The button on location ");
            System.out.print(st);
            System.out.println(" was pressed.");
            if(((JButton) e.getSource()).getParent().getName().equals("Player 1") || ((JButton) e.getSource()).getParent().getName().equals("Player 2")
            || ((JButton) e.getSource()).getParent().getName().equals("Player 3") || ((JButton) e.getSource()).getParent().getName().equals("Player 4")) {
                new SelectedPiece(GameGUI.this, ((JButton) e.getSource()).getBackground());
            }
        }
    }
}