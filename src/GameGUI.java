import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;


/**
 * Main GameGUI screen
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

class GameGUI extends JFrame {

    private static JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPanel, bottomPanel, playerTurnPanel;
    private static JLabel[] playerLabels;
    private static GameGUI previousFrame;
    public static int currentTurn;
    private static String text;
    private static JEditorPane paragraph = new JEditorPane();

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
        playerTurnPanel = new JPanel();
        mainGridPanel.add(playerTurnPanel);
        mainGridPanel.add(GridPanel);

        TitledBorder title = BorderFactory.createTitledBorder("Turn");
        title.setTitleFont(new Font(Font.SANS_SERIF, Font.PLAIN,25));
        title.setTitleJustification(TitledBorder.CENTER);
        playerTurnPanel.setBorder(title);
        playerTurnPanel.setSize(100,50);

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

        setPlayerTurnTopPanel(currentTurn);

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

    public static void setPlayerTurnTopPanel(int currentTurn){

        if(currentTurn == 0){
            currentTurn=Options.getTurnOrderAccordingToColors(0);
        }

        text = "<html><p style='font-size:40px'>" + Player.getPlayer(currentTurn).getPlayerName()+
                "</p></html>";



        paragraph.setContentType("text/html");//set content as html
        paragraph.setText(text);

        paragraph.setEditable(false);//so its not editable
        paragraph.setOpaque(false);//so we dont see whit background

        playerTurnPanel.add(paragraph);
//        System.out.println("Player "+ currentTurn +"'s turn.");

    }

    private void createPlayingPieces() {
        int[] array = {3,0,2,1};
        playerLabels = new JLabel[]{null,null,null,null};

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        for(int i = 0; i < 4; i++){

            JLabel tempLabel = new JLabel(Player.getPlayer(Options.getTurnOrderAccordingToColors(array[i])).getPlayerName());
            tempLabel.setFont(new Font("Serif", Font.BOLD, 35));
            playerLabels[i] = tempLabel;

            JPanel labelPanel = new JPanel();
            labelPanel.add(tempLabel);

            if( i == 0 || i == 2){
                leftPanel.add(labelPanel);
                leftPanel.add(Player.getPlayer(Options.getTurnOrderAccordingToColors(array[i])).createGrid());
            }

            else{
                rightPanel.add(labelPanel);
                rightPanel.add(Player.getPlayer(Options.getTurnOrderAccordingToColors(array[i])).createGrid());
            }

        }

        leftPiecesPanel.add(leftPanel);
        rightPiecesPanel.add(rightPanel);
    }

    public static void updateLabels(){
        int[] array = {3,0,2,1};
        for (int i = 0; i < 4; i++){
            playerLabels[i].setText(Player.getPlayer(Options.getTurnOrderAccordingToColors(array[i])).getPlayerName());
        }
    }


}