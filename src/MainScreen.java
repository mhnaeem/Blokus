import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Main Screen for the Blokus Game that includes the following:
 * Window title;
 * Blokus title on window; and
 * 3 Buttons.
 * 
 *
 * @author (Zachary Cheema)
 * @version (Version 1.3)
 */
public class MainScreen extends JFrame{
    
   private JPanel mainPanel;
   private Container contentPane = getContentPane();
   private JLabel mainLbl;
   private JButton startButton, loadButton, exitButton;
   
   public MainScreen(){
       setFrame();
       createLabel();
       createButtons();
       addDetails();
       buttonAction();
       setVisible(true);
   }
   
   public void setFrame(){
       setSize(500,300);
       setPreferredSize(new Dimension(500,300));
       setTitle("Welcome to BLOCKUS!");
       mainPanel = new JPanel();
       contentPane.add(mainPanel);
       mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
       setResizable(false);
   }
   
   public void createLabel(){
        mainLbl = new JLabel("BLOKUS");
   }
   
   public void createButtons(){
       startButton = new JButton("Start");
       loadButton = new JButton("Load");
       exitButton = new JButton("Exit");
   }
   
   public void addDetails(){
       Dimension labelPanelSize = new Dimension(500, 150);
       Dimension BtnPanelsize = new Dimension(200, 50);
       Dimension btnSize = new Dimension(100, 50);
       
       
       JPanel mainLblPan = new JPanel();
       mainLblPan.setPreferredSize(labelPanelSize); 
       mainLblPan.setLayout(new FlowLayout());

       JPanel buttonPan = new JPanel();
       buttonPan.setPreferredSize(BtnPanelsize);
       buttonPan.setLayout(new FlowLayout());
      
       
       startButton.setPreferredSize(btnSize);
       loadButton.setPreferredSize(btnSize); 
       exitButton.setPreferredSize(btnSize); 
       
        buttonPan.add(startButton);
        buttonPan.add(loadButton);
        buttonPan.add(exitButton);
        mainPanel.add(buttonPan);
        mainLblPan.add(mainLbl);
       mainPanel.add(mainLblPan);
       mainPanel.add(buttonPan);
   }
   
   private void buttonAction(){
        exitButton.addActionListener((ActionEvent ev) -> {
            System.exit(0);
        });
        loadButton.addActionListener((ActionEvent ev) -> {
            dispose();
            new loadSavedScreen();// This button will load saved files 
        });
        startButton.addActionListener((ActionEvent ev) -> {
            dispose();
            new CreateGame(); // This Button will bring you to the game setup
            //screen
        });
    }
    
   public static void main(String[] args) {
        new MainScreen();
   }
}


