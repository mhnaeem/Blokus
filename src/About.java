

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
    private JLabel about, paragraph;
    private JButton exitButton,playButton;
    private String text;



    public About(){

        //Setting layouts of all the panel
        fullPanel=new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(50, 150, 50, 150));
        setupTopPanel();
        setupMiddlePanel();
        setupBottomPanel();
        contentPane.add(fullPanel);
        setBounds(400, 200, 600, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
    }




    private void setupTopPanel(){
        topPanel=new JPanel(new FlowLayout(1));
        about =new JLabel("About");
        topPanel.setBorder(new EmptyBorder(20, 0, 50, 0));

        // Putting Load Game Label on Top Panel
        about.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        about.setAlignmentX(Font.CENTER_BASELINE);
        topPanel.add(about);

        //Adding the Top panel to the fullPanel
        fullPanel.add(topPanel, BorderLayout.NORTH);
    }


    private void setupMiddlePanel(){
        middlePanel = new JPanel();

        String text = "<html><p>This program is........ DOPE! LEGENDARY</p></html>";
        paragraph=new JLabel(text);

        middlePanel.add(paragraph);
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

        exitButton.addActionListener(x -> exitEvent());
    }


    /**
     * when exit button in file menu is pressed
     * exits
     */
    private void exitEvent(){
        System.exit(0);
    }



    public static void main(String[] args) {
        new About();
    }


}
