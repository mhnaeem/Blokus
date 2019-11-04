

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;


/**
 * About Screen that includes the following:
 * About Label;
 * Play Again button;
 * Exit button;
 *
 * @author (Abdur Rahman Abul Hossain)
 * @version (Version 1.1)
 */


public class About extends JFrame {
    private Container contentPane = getContentPane();
    private JPanel fullPanel, topPanel, bottomPanel,middlePanel;
    private JLabel about,paragraph;
    private JButton exitButton,playButton, projectInfoButton, gameInfoButton;
    private String text;



    public About(){

        //Setting layouts of all the panel
        fullPanel = new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(20, 10, 50, 10));

        setupTopPanel();
        setupMiddlePanel();
        setupBottomPanel();

        contentPane.add(fullPanel);

        setTitle("About");
        setBounds(400, 200, 600, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setResizable(false);
    }




    private void setupTopPanel() {
        topPanel = new JPanel();
        about = new JLabel("About");
        topPanel.setBorder(new EmptyBorder(0, 10, 20, 10));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        // Putting Load Game Label on Top Panel
        about.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        JPanel aboutPanel = new JPanel();
        aboutPanel.add(about);


//        buttonPanel for projectInfo and gameInfo
        Dimension btnSize = new Dimension(180, 50);
        projectInfoButton = new JButton("Project Information");
        gameInfoButton = new JButton("Game Information");

        projectInfoButton.addActionListener(x -> projectInfoButtonEvent());
        gameInfoButton.addActionListener(x -> gameInfoButtonEvent());



        JPanel buttonPan = new JPanel();
        buttonPan.setPreferredSize(new Dimension(200, 120));
        projectInfoButton.setPreferredSize(btnSize);
        gameInfoButton.setPreferredSize(btnSize);
        buttonPan.add(projectInfoButton);
        buttonPan.add(gameInfoButton);
        buttonPan.setBorder(new EmptyBorder(0, 0, 0, 50));


        topPanel.add(buttonPan);
        topPanel.add(aboutPanel);
        fullPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void setupMiddlePanel(){
//        Dimension panelSize = new Dimension(500, 200);
        middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBorder(new EmptyBorder(0, 10, 0, 10));
//        middlePanel.setSize(panelSize);

        text = "<html><p>Blokus is an abstract strategy board game for two to four players, where players try to score points by occupying most of the board with pieces of their colour. " +
                "It was invented by Bernard Tavitian and first released in 2000 by Sekkoïa, a French company. " +
                "It has won several awards, including the Mensa Select award and the 2004 Teacher's Choice Award. </p></html>" +
                "In 2009, the game was sold to Mattel.";
        paragraph=new JLabel(text);
//        paragraph.setEditable(false);
//        paragraph.setLineWrap(true);
        middlePanel.add(paragraph,BorderLayout.CENTER);
        fullPanel.add(middlePanel, BorderLayout.CENTER);

    }

    private void setupBottomPanel() {

        Dimension btnSize = new Dimension(100, 50);
        bottomPanel = new JPanel(new FlowLayout());


        exitButton = new JButton("Exit");
        playButton=new JButton("Play Again");

        exitButton.setPreferredSize(btnSize);
        playButton.setPreferredSize(btnSize);


        JPanel buttonPan = new JPanel();
        buttonPan.setPreferredSize(new Dimension(200, 50));
        buttonPan.setLayout(new FlowLayout());

        bottomPanel.add(playButton);
        bottomPanel.add(exitButton);
        fullPanel.add(bottomPanel, BorderLayout.SOUTH);

        playButton.addActionListener(x -> playEvent());
        exitButton.addActionListener(x -> exitEvent());
    }


    /**
     * when exit button in file menu is pressed
     * exits
     */
    private void exitEvent(){
        System.exit(0);
    }

    private void playEvent(){
        System.out.println("Clicked Play Button");
    }

    private void projectInfoButtonEvent(){
        System.out.println("Clicked Project Information Button!");
    }

    private void gameInfoButtonEvent(){
        System.out.println("Clicked Game Information Button!");
    }

    public static void main(String[] args) {
        new About();
    }


}
