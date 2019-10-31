
import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * Game Over Screen that includes the following:
 * Game Over Label;
 * Play Again button;
 * Exit button;
 *
 * @author (Abdur Rahman Abul Hossain)
 * @version (Version 1.1)
 */


public class GameOver extends JFrame{
    private Container contentPane = getContentPane();
    private JPanel fullPanel, topPanel, bottomPanel;
    private JLabel gameOver;
    private JMenuBar menu;
    private JMenu file,about;
    private JMenuItem playAgain,exit;
    private JButton exitButton,playAgainButton;



    public GameOver(){

        //Setting layouts of all the panel
        fullPanel=new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(50, 150, 50, 150));
        setupTopPanel();
        setupBottomPanel();
        contentPane.add(fullPanel);
        createMenu();
        setBounds(400, 200, 600, 500);
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }

    /**
     * creates Menu Bor with its components
     * adds Menu to JFrame
     */
    private void createMenu()
    {
        menu = new JMenuBar();
        file = new JMenu("File");
        about = new JMenu("About");
        playAgain = new JMenuItem("Play Again");
        exit = new JMenuItem("Exit");

        file.add(playAgain);
        file.add(exit);
        playAgain.addActionListener(x->playAgain());
        exit.addActionListener(x->exitEvent());
        about.addActionListener(x->aboutEvent());
        menu.add(file);
        menu.add(about);
        setJMenuBar(menu);
    }

    private void setupTopPanel(){
        topPanel=new JPanel(new FlowLayout(1));
        gameOver =new JLabel("Game Over");
        topPanel.setBorder(new EmptyBorder(20, 0, 50, 0));

        // Putting Load Game Label on Top Panel
        gameOver.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        gameOver.setAlignmentX(Font.CENTER_BASELINE);
        topPanel.add(gameOver);

        //Adding the Top panel to the fullPanel
        fullPanel.add(topPanel, BorderLayout.NORTH);
    }


    private void setupBottomPanel() {

        Dimension btnSize = new Dimension(100, 50);
        bottomPanel = new JPanel(new FlowLayout());


        exitButton = new JButton("Exit");
        playAgainButton=new JButton("Play Again");

        exitButton.setPreferredSize(btnSize);
        playAgainButton.setPreferredSize(btnSize);


        JPanel buttonPan = new JPanel();
        buttonPan.setPreferredSize(new Dimension(200, 50));
        buttonPan.setLayout(new FlowLayout());

        bottomPanel.add(playAgainButton);
        bottomPanel.add(exitButton);
        fullPanel.add(bottomPanel, BorderLayout.CENTER);

        exitButton.addActionListener(x -> exitEvent());
    }


    /**
     * when exit button in file menu is pressed
     * exits
     */
    private void exitEvent(){
        System.exit(0);
    }

    private void playAgain(){
        System.out.println("Clicked on Play Again menu button.");
    }

    private void aboutEvent(){
        System.out.println("Clicked on About menu button.");
    }


    public static void main(String[] args) {
        new GameOver();
    }


}
