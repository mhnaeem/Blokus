import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.util.*;

/**
 * Main GameGUI screen
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

public class GameGUI extends JFrame {

    private Container contentPane = getContentPane();
    private static JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPanel, bottomPanel;
    private int numberOfPlayers;

    private Player[] listOfPlayers;

    /* The panels are set based on indexes, in the following order
       2 Players = [Player 1, Player 2]
       3 Players = [Player 1, Player 2, Player 3]
       4 Players = [Player 1, Player 2, Player 3, Player 4]

       [1]   [Main Grid]    [2]
       [3]   [Main Grid]    [4]
     */
    private ArrayList<JPanel> listOfPiecesPanels = new ArrayList<>();

    // Meant to hold the player number and the colours
    // For example: { 1 : Color.blue, 2 : Color.green}
    private HashMap<Integer, Color> mapOfColours;
    private boolean colour_blind;

    private static JButton[][] mainGridButtons;
    private static ArrayList<JButton[][]> playerButtons = new ArrayList<>(Arrays.asList());
    private static JButton[][] selectedPieceButtons;
    private static JLabel[] playerLabels;

    private static String selectedPoint;

    private HashMap<Color,Component> componentMap = new HashMap<>();

    private MenuListener aboutListener = new MenuListener() {
        @Override
        public void menuSelected(MenuEvent e) {
            new About();
        }
        @Override
        public void menuDeselected(MenuEvent e) {
        }
        @Override
        public void menuCanceled(MenuEvent e) {
        }
    };


    public GameGUI(JPanel GridPanel){

        listOfPiecesPanels.add(Player.getPlayer(GameEngine.getTurnOrder(3)).createGrid());
        listOfPiecesPanels.add(Player.getPlayer(GameEngine.getTurnOrder(0)).createGrid());
        listOfPiecesPanels.add(Player.getPlayer(GameEngine.getTurnOrder(2)).createGrid());
        listOfPiecesPanels.add(Player.getPlayer(GameEngine.getTurnOrder(1)).createGrid());

        leftPiecesPanel = new JPanel();
        rightPiecesPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        mainGridPanel = new JPanel();
        mainGridPanel.add(GridPanel);


        //Used to make the grid centered in the window
        mainGridPanel.setLayout(new BoxLayout(mainGridPanel, BoxLayout.Y_AXIS));

        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPiecesPanel, BorderLayout.WEST);
        contentPane.add(rightPiecesPanel, BorderLayout.EAST);
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.add(mainGridPanel, BorderLayout.CENTER);

        setTitle("Blokus");

        //Create the player pieces on the left and right
        createPlayingPieces();
        createMenu();

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Ensures that the JFrame opens in fullscreen
        setResizable(false);
        setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setVisible(true);
    }

    private void createMenu()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");
        JMenu help = new JMenu("Help");

        JMenuItem reset = new JMenuItem("Reset");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem newGame = new JMenuItem("New Game");
        JMenuItem howTo = new JMenuItem("How To");

        menuBar.add(file);
        menuBar.add(about);
        menuBar.add(help);

        file.add(reset);
        file.add(load);
        file.add(newGame);
        help.add(howTo);

        about.addMenuListener(aboutListener);

        newGame.addActionListener(x -> newGameEvent());
        howTo.addActionListener(x -> howToEvent());
        setJMenuBar(menuBar);
    }

    private void howToEvent()
    {
        new HelpDetails("game");
    }

    private void newGameEvent(){
        this.dispose();
        new CreateGame();
    }



    private void createPlayingPieces() {
        int[] array = {3,0,2,1};
        //TODO: what happens when there are two players or one player.
        for (int i = 0; i < 4; i++){
            JPanel tempPnl = listOfPiecesPanels.get(i);
            tempPnl.setBorder(new EmptyBorder(10, 30, 30, 30));
            //This is naming every panel of pieces for its player
            tempPnl.setName(Player.getPlayer(GameEngine.getTurnOrder(array[i])).getPlayerName());
        }

        playerLabels = new JLabel[4];
        for (int i = 0; i < 4; i++){
            JLabel tempLabel = new JLabel(Player.getPlayer(GameEngine.getTurnOrder(array[i])).getPlayerName());
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
        leftPanel.add(playerLabels[2]);
        leftPanel.add(listOfPiecesPanels.get(2));
        rightPanel.add(playerLabels[3]);
        rightPanel.add(listOfPiecesPanels.get(3));

        leftPiecesPanel.add(leftPanel);
        rightPiecesPanel.add(rightPanel);
    }

    public static void updateLabels(){
        int[] array = {3,0,2,1};
        for (int i = 0; i < 4; i++){
            playerLabels[i].setText(Player.getPlayer(GameEngine.getTurnOrder(array[i])).getPlayerName());
        }
    }

}