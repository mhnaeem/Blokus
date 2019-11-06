

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
    private JButton exitButton, projectInfoButton, gameInfoButton;
    private String text;



    public About(){

        //Setting layouts of all the panel
        fullPanel = new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(30, 10, 30, 10));

        setupTopPanel();
        setupMiddlePanel();
        setupBottomPanel();

        contentPane.add(fullPanel);

        setTitle("About");
        setBounds(400, 200, 600, 500);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setResizable(false);
    }



    private void setupTopLeftButtonPanel(){
        Dimension btnSize = new Dimension(180, 50);

        JPanel buttonPan = new JPanel();
        buttonPan.setPreferredSize(new Dimension(200, 120));

        gameInfoButton = new JButton("Game Information");
        projectInfoButton = new JButton("Project Information");

        // Added functionality to buttons
        gameInfoButton.addActionListener(x -> gameInfoButtonEvent());
        projectInfoButton.addActionListener(x -> projectInfoButtonEvent());

        gameInfoButton.setPreferredSize(btnSize);
        projectInfoButton.setPreferredSize(btnSize);

        buttonPan.add(gameInfoButton);
        buttonPan.add(projectInfoButton);
        buttonPan.setBorder(new EmptyBorder(0, 0, 0, 30));

        topPanel.add(buttonPan);
    }

    private void setupTopRightAboutPanel(){
        JPanel aboutPanel = new JPanel();

        about = new JLabel("About");
        about.setFont(new Font(Font.SERIF, Font.BOLD, 50));

        aboutPanel.add(about);
        aboutPanel.setBorder(new EmptyBorder(40, 0, 0, 20));

        topPanel.add(aboutPanel);
        fullPanel.add(topPanel, BorderLayout.NORTH);
    }



    private void setupTopPanel() {
        topPanel = new JPanel();
        topPanel.setBorder(new EmptyBorder(0, 10, 20, 10));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        setupTopLeftButtonPanel();
        setupTopRightAboutPanel();

    }

    private void setupMiddlePanel(){
        middlePanel = new JPanel(new BorderLayout());
        middlePanel.setBorder(new EmptyBorder(0, 40, 0, 30));

        text = "<html><p>Blokus is an abstract strategy board game for two to four players, where players try to score points by occupying most of the board with pieces of their colour. " +
                "It was invented by Bernard Tavitian and first released in 2000 by Sekkoïa, a French company. " +
                "In 2009, the game was sold to Mattel. </p></html>";

        paragraph=new JLabel(text);
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        middlePanel.add(paragraph,BorderLayout.CENTER);
        fullPanel.add(middlePanel, BorderLayout.CENTER);

    }

    private void setupBottomPanel() {

        Dimension btnSize = new Dimension(100, 50);
        bottomPanel = new JPanel(new FlowLayout());


        exitButton = new JButton("Exit");
        exitButton.setPreferredSize(btnSize);


        JPanel buttonPan = new JPanel();
        buttonPan.setPreferredSize(new Dimension(200, 50));
        buttonPan.setLayout(new FlowLayout());

        bottomPanel.add(exitButton);
        fullPanel.add(bottomPanel, BorderLayout.SOUTH);

        exitButton.addActionListener(x -> exitEvent());
    }


    /**
     * when exit button in file menu is pressed
     * exits
     */
    private void exitEvent(){
        this.dispose();
    }


    private void projectInfoButtonEvent(){
        text="<html>" +"This is the Blokus game implemented for a software engineering course."+
                "<p>\n" +
                "Project Name: Blokus Game </br>\n" +
                "Project Type: Group Project </br>\n" +
                "Group Number: Group 9 </br>\n" +
                "Course: COMP 2005 - Software Engineering </br>\n" +
                "Instructor: Mark Hatcher as [mhatcher] </br>\n" +
                "Teaching Assistants: </br>\n" +
                "<ol>\n" +
                "  <li>Ali Mohammad Saheb Alfosool as [alfosool] </li>\n" +
                "  <li>Samira Saki as [Samira63] </li>\n" +
                "</ol>\n" +
                "</br>\n" +
                "Member Names: </p>\n" +
                "<ol>\n" +
                "  <li>Muhammad Hammad as [mhnaeem]</li>\n" +
                "  <li>Abdur Rahman Abul Hossain as [Rifat1]</li>\n" +
                "  <li>Muhammad Uwais Jahmeerbacus as [uwaisj241299]</li>\n" +
                "  <li>Zachary S. Cheema as [Sakif-Max-Flex]</li>\n" +
                "  <li>Zoe S. Collins as [zscollins]</li>\t\n" +
                "</ol></html>\n";

        paragraph.setText(text);
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }

    private void gameInfoButtonEvent(){
        text = "<html><p>Blokus is an abstract strategy board game for two to four players, where players try to score points by occupying most of the board with pieces of their colour. " +
                "It was invented by Bernard Tavitian and first released in 2000 by Sekkoïa, a French company. " +
                "In 2009, the game was sold to Mattel. </p></html>";
        paragraph.setText(text);
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    }

//    public static void main(String[] args) {
//        new About();
//    }


}
