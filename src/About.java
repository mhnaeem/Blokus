import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.HyperlinkEvent;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

/**
 * About Screen that includes the following:
 * About Label;
 * Play Again button;
 * Exit button;
 *
 * @author (Abdur Rahman Abul Hossain)
 * @version (Version 1.2)
 */

public class About extends JFrame {

    private JPanel fullPanel, topPanel, exitPanel, paragraphPanel, topLeftButtonPanel, topRightAboutPanel;
    private JEditorPane paragraph;
    private JButton exitButton, projectInfoButton, gameInfoButton, disclaimerButton, sourcesButton;
    private String text;

    public About(){

        createButtons();
        setLayoutsOfAllPanels();
        setupTopPanel();
        setupParagraphPanel();
        setupExitPanel();

        getContentPane().add(fullPanel);

        setTitle("About");
        setBounds(400, 200, 650, 550);
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setResizable(false);
        setAlwaysOnTop(true);
    }


    /**
     * This function does the following:
     * Creates gameInfoButton and ProjectInfoButton for topLeftButtonPanel
     * Creates disclaimerButton and exitButton for exitPanel
     */
    private void createButtons(){

        //for topLeftButtonPanel
        gameInfoButton = new JButton("Game Information");
        projectInfoButton = new JButton("Project Information");

        //for exitPanel
        disclaimerButton=new JButton("Disclaimer");
        exitButton = new JButton("Exit");

        sourcesButton=new JButton("Sources");

    }

    /**
     * This function does the following:
     * Sets layout of the fullPanel
     * Sets layout of the topLeftButtonPanel
     * Sets layout of the topRightAboutPanel
     * Sets layout of the topPanel
     * Sets layout of the paragraphPanel
     * Sets layout of the exitPanel
     */
    private void setLayoutsOfAllPanels(){

        //Adding layout of the fullPanel
        fullPanel = new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(30, 10, 30, 10));

        //Adding layout of the topLeftButtonPanel
        topLeftButtonPanel = new JPanel();
        topLeftButtonPanel.setPreferredSize(new Dimension(200, 120));
        topLeftButtonPanel.setBorder(new EmptyBorder(0, 0, 0, 30));

        //Adding layout of the topRightAboutPanel
        topRightAboutPanel = new JPanel();
        topRightAboutPanel.setBorder(new EmptyBorder(40, 0, 0, 20));

        //Adding layout of the topPanel
        topPanel = new JPanel();
        topPanel.setBorder(new EmptyBorder(0, 10, 20, 10));
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        //Adding layout of the paragraphPanel
        paragraphPanel = new JPanel(new BorderLayout());
        paragraphPanel.setBorder(new EmptyBorder(0, 40, 0, 30));

        //Adding layout of the exitPanel
        exitPanel = new JPanel(new FlowLayout());
    }

    /**
     * This function does the following:
     * adds actionListeners to gameInfoButton and projectInfoButton buttons
     * adds both buttons to topLeftButtonPanel
     * adds topLeftButtonPanel to topPanel
     */
    private void setupTopLeftButtonPanel(){

        Dimension btnSize = new Dimension(180, 50);

        // Added functionality to buttons
        gameInfoButton.addActionListener(actionEvent -> gameInfoButtonEvent());
        projectInfoButton.addActionListener(actionEvent -> projectInfoButtonEvent());

        gameInfoButton.setPreferredSize(btnSize);
        projectInfoButton.setPreferredSize(btnSize);

        topLeftButtonPanel.add(gameInfoButton);
        topLeftButtonPanel.add(projectInfoButton);

        topPanel.add(topLeftButtonPanel);
    }

    /**
     * This function does the following:
     * adds About label to topRightAboutPanel
     * adds topRightAboutPanel to topPanel
     */
    private void setupTopRightAboutPanel(){

        JLabel about = new JLabel("About");
        about.setFont(new Font(Font.SERIF, Font.BOLD, 50));

        topRightAboutPanel.add(about);

        topPanel.add(topRightAboutPanel);
    }

    /**
     * This function does the following:
     * adds topLeftButtonPanel and topRightAboutPanel to topPanel by
     * calling setupTopLeftButtonPanel() and setupTopRightAboutPanel()
     * adds topPanel to fullPanel
     */
    private void setupTopPanel() {

        //Adds Game Information button and Project Information Button to topLeftButtonPanel
        setupTopLeftButtonPanel();

        //Adds About label to topLeftAboutPanel
        setupTopRightAboutPanel();

        //adding topPanel to full Panel
        fullPanel.add(topPanel, BorderLayout.NORTH);
    }

    /**
     * This function does the following:
     * adds Game Information to text variable
     * adds text variable to paragraph label
     */
    private void setupParagraphPanel(){

        text = "<html><p>Blokus is an abstract strategy board game for two to four players, where players try to score points by occupying most of the board with pieces of their colour. " +
                "It was invented by Bernard Tavitian and first released in 2000 by Sekkoïa, a French company. " +
                "In 2009, the game was sold to Mattel. </p></html>";

        paragraph=new JEditorPane();
        paragraph.setContentType("text/html");//set content as html
        paragraph.setText(text);

        paragraph.addHyperlinkListener(actionEvent -> {
            if (HyperlinkEvent.EventType.ACTIVATED.equals(actionEvent.getEventType())) {
                System.out.println(actionEvent.getURL());
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(actionEvent.getURL().toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        paragraph.setEditable(false);//so its not editable
        paragraph.setOpaque(false);//so we dont see whit background
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));

        paragraphPanel.add(paragraph,BorderLayout.CENTER);
        fullPanel.add(paragraphPanel, BorderLayout.CENTER);
    }

    /**
     * This function does the following:
     * adds Disclaimer button and Exit button to exitPanel
     * adds exitPanel to fullPanel
     * adds actionListener to both buttons
     */
    private void setupExitPanel() {

        Dimension btnSize = new Dimension(100, 50);

        sourcesButton.setPreferredSize(btnSize);
        disclaimerButton.setPreferredSize(btnSize);
        exitButton.setPreferredSize(btnSize);

        //adding Disclaimer button and Exit button to exitPanel
        exitPanel.add(sourcesButton);
        exitPanel.add(disclaimerButton);
        exitPanel.add(exitButton);

        //adding exitPanel to fullPanel
        fullPanel.add(exitPanel, BorderLayout.SOUTH);

        //adding actionListener to both buttons
        sourcesButton.addActionListener(actionEvent -> sourcesButtonEvent());
        disclaimerButton.addActionListener(actionEvent -> disclaimerButtonEvent());
        exitButton.addActionListener(actionEvent -> About.this.dispose());
    }

    private void sourcesButtonEvent(){
        text="<html><p>Link to our project in GitHub : <a href=\"https://github.com/mhnaeem/comp2005-fall19-group9\">comp2005-fall19-group9</a><br>" +
                "Link to original owners of the game we created for this project: <a href=\"https://www.mattelgames.com/en-ca/blokus\">Mattel Games</a><br>" +
                "Link to Wikipedia for more information about Blokus: <a href=\"https://en.wikipedia.org/wiki/Blokus\">WIKIPEDIA</a><br>" +
                "Link to the source where we got our icon from: <a href=\"https://www.flaticon.com/free-icon/tetris_566312\">FLATICON</a><br></p></html>";

        paragraph.setText(text);
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    }

    /**
     * when Disclaimer button is pressed
     * Modifies text variable
     * adds copyright information to text variable
     * adds text variable to paragraph label
     */
    private void disclaimerButtonEvent(){
        text="<html><p>©2013 Mattel. All Rights Reserved. " +
                "Mattel, Inc., 636 Girard Avenue, East Aurora, NY 14052, U.S.A. Consumer Relations 1-800-524-8697.  " +
                "Mattel  U.K.  Ltd.,  Vanwall  Business  Park,  Maidenhead  SL6  4UB.  " +
                "Helpline  01628  500303.  Mattel  Australia Pty., Ltd., Richmond, Victoria. 3121.Consumer Advisory Service - 1300 135 312. " +
                "Mattel East Asia Ltd., Room 503-09, North Tower, World Finance Centre, Harbour City, Tsimshatsui, HK, China. " +
                "Diimport & Diedarkan Oleh: Mattel SEA  Ptd  Ltd.(993532-P)  Lot  13.5,  13th  Floor,  Menara  Lien  Hoe,  Persiaran  Tropicana  Golf  Country  Resort,  47410  PJ. Malaysia. Tel:03-78803817, Fax:03-78803867. " +
                "Mattel Europa, B.V., Gondel 1, 1186 MJ Amstelveen, Nederland.\n" +
                "Blokus® is based on the original concept by Bernard Tavitia\n</p></html>";
        paragraph.setText(text);
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 11));
    }

    /**
     * when Project Information button is pressed
     * Modifies text variable
     * adds project information to text variable
     * adds text variable to paragraph label
     */
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

    /**
     * when Game Information button is pressed
     * Modifies text variable
     * adds Game Information to text variable
     */
    private void gameInfoButtonEvent(){
        text = "<html><p>Blokus is an abstract strategy board game for two to four players, where players try to score points by occupying most of the board with pieces of their colour. " +
                "It was invented by Bernard Tavitian and first released in 2000 by Sekkoïa, a French company. " +
                "In 2009, the game was sold to Mattel. </p></html>";
        paragraph.setText(text);
        paragraph.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 14));
    }
}
