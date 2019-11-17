import javax.swing.*;
import java.awt.*;


/**
 * Main GameGUI screen
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

public class GameGUI extends JFrame {

    private static JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPanel, bottomPanel;
    private static JLabel[] playerLabels;
    private static GameGUI previousFrame;

    public GameGUI(JPanel GridPanel){

        // For Mac's look and feel
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        leftPiecesPanel = new JPanel();
        rightPiecesPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        mainGridPanel = new JPanel();
        mainGridPanel.add(GridPanel);

        //Used to make the grid centered in the window
        mainGridPanel.setLayout(new BoxLayout(mainGridPanel, BoxLayout.Y_AXIS));

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(leftPiecesPanel, BorderLayout.WEST);
        contentPane.add(rightPiecesPanel, BorderLayout.EAST);
        contentPane.add(topPanel, BorderLayout.NORTH);
        contentPane.add(bottomPanel, BorderLayout.SOUTH);
        contentPane.add(mainGridPanel, BorderLayout.CENTER);

        setTitle("Blokus");

        //Create the player pieces on the left and right
        createPlayingPieces();
        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Ensures that the JFrame opens in fullscreen
        setResizable(false);
        setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        if(Options.isDarkMode()){
            Options.setDarkModeColour(this);
        }
        if(previousFrame != null){
            previousFrame.dispose();
            previousFrame = null;
            System.gc();
        }
        previousFrame = this;

        setJMenuBar(new MenuCreator(new String[]{"endGame","newGame","saveGame","load","resetGame","exit","howTo","about"}, this, "game"));
    }

    private void createPlayingPieces() {
        int[] array = {3,0,2,1};
        playerLabels = new JLabel[]{null,null,null,null};

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        for(int i = 0; i < 4; i++){

            JLabel tempLabel = new JLabel(Player.getPlayer(Options.getTurnOrder(array[i])).getPlayerName());
            tempLabel.setFont(new Font("Serif", Font.BOLD, 35));
            playerLabels[i] = tempLabel;

            JPanel labelPanel = new JPanel();
            labelPanel.add(tempLabel);

            if( i == 0 || i == 2){
                leftPanel.add(labelPanel);
                leftPanel.add(Player.getPlayer(Options.getTurnOrder(array[i])).createGrid());
            }

            else{
                rightPanel.add(labelPanel);
                rightPanel.add(Player.getPlayer(Options.getTurnOrder(array[i])).createGrid());
            }

        }

        leftPiecesPanel.add(leftPanel);
        rightPiecesPanel.add(rightPanel);
    }

    public static void updateLabels(){
        int[] array = {3,0,2,1};
        for (int i = 0; i < 4; i++){
            playerLabels[i].setText(Player.getPlayer(Options.getTurnOrder(array[i])).getPlayerName());
        }
    }


}