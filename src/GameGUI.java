import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;

/**
 * Main GameGUI screen
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

public class GameGUI extends JFrame {

    private Container contentPane = getContentPane();
    private static JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPanel, bottomPanel;

    public GameGUI(JPanel GridPanel){

        leftPiecesPanel = new JPanel();
        rightPiecesPanel = new JPanel();
        topPanel = new JPanel();
        bottomPanel = new JPanel();
        mainGridPanel = new JPanel();
        mainGridPanel.add(GridPanel);

        //Used to make the grid centered in the window
        mainGridPanel.setLayout(new BoxLayout(mainGridPanel, BoxLayout.Y_AXIS));

        createMenu();

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
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem howTo = new JMenuItem("How To");

        menuBar.add(file);
        menuBar.add(about);
        menuBar.add(help);

        file.add(reset);
        file.add(load);
        file.add(newGame);
        file.add(exit);
        help.add(howTo);

        about.addMenuListener(aboutListener);

        newGame.addActionListener(actionEvent -> {
            GameGUI.this.dispose();
            new CreateGame();
        });
        exit.addActionListener(actionEvent -> System.exit(0));
        howTo.addActionListener(actionEvent -> new HelpDetails("game"));
        setJMenuBar(menuBar);
    }

    private void createPlayingPieces() {
        //int[] array = {3,0,2,1};

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        for(int i = 1; i <= Options.getNumberOfPlayers(); i++){

            JLabel tempLabel = new JLabel(Player.getPlayer(i).getPlayerName());
            tempLabel.setFont(new Font("Serif", Font.BOLD, 35));

            JPanel labelPanel = new JPanel();
            labelPanel.add(tempLabel);

            if( i == 1 || i == 3){
                leftPanel.add(labelPanel);
                leftPanel.add(PlayerGrid.getPlayerGridPanel(i));
            }

            else{
                rightPanel.add(labelPanel);
                rightPanel.add(PlayerGrid.getPlayerGridPanel(i));
            }

        }

        leftPiecesPanel.add(leftPanel);
        rightPiecesPanel.add(rightPanel);
    }

    /*public static void updateLabels(){
        int[] array = {3,0,2,1};
        for (int i = 0; i < 4; i++){
            playerLabels[i].setText(Player.getPlayer(GameEngine.getTurnOrder(array[i])).getPlayerName());
        }
    }*/

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
}