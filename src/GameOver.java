import java.awt.*;
import java.util.Collections;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * Game Over Screen that includes the following:
 * Game Over Label;
 * Player Numbers and their Scores
 * Play Again button;
 * Exit button;
 *
 * @author (Abdur Rahman Abul Hossain)
 * @version (Version 1.2)
 */


public class GameOver extends JFrame{
    private Container contentPane = getContentPane();
    private JPanel fullPanel, gameOverPanel, playerScoresPanel, buttonPanel;
    private JLabel gameOver,paragraph;
    private JButton exitButton,playAgainButton;
    private String text;
    private HashMap<Integer, Integer> playerScores;


    public GameOver(HashMap<Integer, Integer> map){

        playerScores = map;

        setLayoutsOfAllPanels();
        setupGameOverPanel();
        setupPlayerScoresPanel();
        setupButtonPanel();

        contentPane.add(fullPanel);

        setBounds(400, 200, 600, 500);
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    /**
     * This function does the following:
     * Sets layout of the fullPanel
     * Sets layout of the gameOverPanel
     * Sets layout of the playerScoresPanel
     * Sets layout of the buttonPanel
     */
    private void setLayoutsOfAllPanels(){

        //Setting layout of the fullPanel
        fullPanel=new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(50, 150, 40, 150));

        //Setting layout of the gameOverPanel
        gameOverPanel =new JPanel(new FlowLayout(1));
        gameOver =new JLabel("Game Over");
        gameOverPanel.setBorder(new EmptyBorder(20, 0, 40, 0));

        //Setting layout of the playerScoresPanel
        playerScoresPanel = new JPanel();
        playerScoresPanel.setBorder(new EmptyBorder(0, 30, 20, 20));

        //Setting layout of the buttonPanel
        buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(200, 60));
    }


    /**
     * This function does the following:
     * Adds the gameOver label to gameOverPanel
     * Adds the gameOverPanel to fullPanel
     */
    private void setupGameOverPanel(){

        //Adding the gameOver label to gameOverPanel
        gameOver.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        gameOver.setAlignmentX(Font.CENTER_BASELINE);
        gameOverPanel.add(gameOver);

        //Adding the gameOverPanel to fullPanel
        fullPanel.add(gameOverPanel, BorderLayout.NORTH);
    }



    /**
     * This function does the following:
     * calls sortPlayerScores() which adds Player numbers and scores to String variable text
     * adds text to playerScoresPanel
     */
    private void setupPlayerScoresPanel(){

        // adds Player numbers and scores to String variable text
        sortPlayerScores();


        paragraph=new JLabel(text);
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 18));

        playerScoresPanel.add(paragraph);
        fullPanel.add(playerScoresPanel,BorderLayout.CENTER);
    }



    /**
     * This function does the following:
     * creates 2 buttons, Exit and Play Again
     * adds actionListeners to both buttons
     */
    private void setupButtonPanel() {

        Dimension btnSize = new Dimension(100, 50);


        exitButton = new JButton("Exit");
        playAgainButton=new JButton("Play Again");

        exitButton.setPreferredSize(btnSize);
        playAgainButton.setPreferredSize(btnSize);


        buttonPanel.add(playAgainButton);
        buttonPanel.add(exitButton);
        fullPanel.add(buttonPanel, BorderLayout.SOUTH);

        playAgainButton.addActionListener(x-> playAgainEvent());
        exitButton.addActionListener(x -> exitEvent());
    }



    /**
     * This function does the following:
     * sorts players by player scores.
     * adds Player numbers and scores to String variable text
     */
    private void sortPlayerScores(){
        text="<html>";
        for (int i = 1; i <= 4/*Options.getNumberOfPlayers()*/; i++){
            if(Collections.max(playerScores.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey() != null){
                Integer pl = Collections.max(playerScores.entrySet(), (entry1, entry2) -> entry1.getValue() - entry2.getValue()).getKey();
                if(pl!=null && playerScores.containsKey(pl)){
                    text +="<p>Player "+pl+" Scored : "+playerScores.get(pl)+" points </p></br>";
                    playerScores.remove(pl);
                }
            }
        }
        text+="</html>";
    }



    /**
     * when exit button is pressed
     * exits
     */
    private void exitEvent(){
        System.exit(0);
    }


    /**
     * when Play Again button is pressed
     * Opens CreateGame Window
     */
    private void playAgainEvent(){
        new CreateGame();
        this.dispose();
    }



    public static void main(String[] args) {
        HashMap<Integer, Integer> playerScores = new HashMap<>();
        playerScores.put( 1, 20);
        playerScores.put( 2, -8);
        playerScores.put( 3, -20);
        playerScores.put( 4, -24);
        new GameOver(playerScores);
    }


}
