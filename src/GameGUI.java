import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

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

    private Player[] listOfPlayers;

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

    private static JButton[][] mainGridButtons;
    private static ArrayList<JButton[][]> playerButtons = new ArrayList<>(Arrays.asList());
    private static JButton[][] selectedPieceButtons;

    public GameGUI(int number_of_players, HashMap<Integer, Color> map_of_colours, boolean colour_blind, Player[] player_list){

        this.numberOfPlayers = number_of_players;
        this.mapOfColours = map_of_colours;
        this.colour_blind = colour_blind;
        this.listOfPlayers = player_list;

        createMainPanels();

        //Create the main grid
        mainGridPanel.add(createGrid(20,20,35,35, "main"));
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

    public JPanel createGrid(int gridRows, int gridColumns, int buttonWidth, int buttonHeight, String type){
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

        if(type.equals("main")){
            mainGridButtons = buttons;
        }
        if(type.equals("player")){
            playerButtons.add(buttons);
        }
        if(type.equals("selected")){
            selectedPieceButtons = buttons;
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
            JPanel tempPnl = createGrid(16, 17, 20, 20, "player");
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
        for (int j = 1; j <= listOfPlayers.length; j++) {
            HashSet<String> colourSpots = this.listOfPlayers[j-1].getDisplayPiecesCoordinates();
            Color color = mapOfColours.get(j);
            Component[] c = listOfPiecesPanels.get(j-1).getComponents();
            for (Component component : c) {
                String name = component.getName();

                //This is extremely inefficient however we will change it later
                if (colourSpots.contains(name)) {
                    component.setBackground(color);
                } else {
                    component.setBackground(Color.white);
                    component.setEnabled(false);
                }
            }
        }
    }

    private class gridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String btnName = ((JButton) e.getSource()).getName();
            String playerName = ((JButton) e.getSource()).getParent().getName();
            Color color = ((JButton) e.getSource()).getBackground();
            int playerIndex = 0;

            System.out.print("The button on location ");
            System.out.print(btnName);
            System.out.println(" was pressed.");

            switch (playerName) {
                case "Player 1":
                    playerIndex = 0;
                    break;
                case "Player 2":
                    playerIndex = 1;
                    break;
                case "Player 3":
                    playerIndex = 2;
                    break;
                case "Player 4":
                    playerIndex = 3;
                    break;
            }
            if (playerName.equals("Player 1") || playerName.equals("Player 2") || playerName.equals("Player 3") || playerName.equals("Player 4")){
                Piece piece = Piece.getPiece(btnName, playerName);
                new SelectedPiece(GameGUI.this, color, piece, selectedPieceButtons);
                listOfPlayers[playerIndex].removeDisplayPieceCoordinates(piece.getDisplayCoordinates());
            }
            colourPieces();
        }
    }
}