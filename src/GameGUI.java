import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
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

        ArrayList<String> colourSpots = new ArrayList<>(Arrays.asList("0,0","0,1","0,2","0,3","0,5","0,6","0,7","0,8","0,11","0,12","0,13","0,14","1,8","1,13","3,0","3,3","3,4","3,6","3,10","3,11","3,15","4,0","4,3","4,4","4,6","4,11","4,14","4,15","4,16","5,0","5,1","5,3","5,6","5,7","5,8","5,11","5,12","5,15","7,0","7,3","7,4","7,6","7,10","7,11","7,12","7,14","7,15","8,0","8,1","8,3","8,6","8,7","8,11","8,15","8,16","9,0","9,3","9,4","9,7","9,8","9,11","9,15","11,0","11,1","11,2","11,5","11,6","11,9","11,12","11,13","11,15","12,2","12,3","12,6","12,7","12,9","12,10","12,12","12,13","14,0","14,1","14,2","14,3","14,4","14,6","14,7","14,8","14,10","14,11"));

        for (int j = 1; j <= this.numberOfPlayers; j++) {
            Color color = mapOfColours.get(j);
            Component[] c = listOfPiecesPanels.get(j-1).getComponents();
            for (int i = 0; i < c.length; i++) {
                String name = c[i].getName();

                //This is extremely inefficient however we will change it later
                if(colourSpots.contains(name)) {
                    c[i].setBackground(color);
                    //TODO: Can Set Name and other things here later
                }
                else {
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