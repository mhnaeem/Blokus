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
 * @version (Version 1.4)
 */
public class MainScreen extends JFrame{
    
   private JPanel mainPanel;
   private Container contentPane = getContentPane();
   private JLabel mainLbl;
   private JButton startButton, loadButton, exitButton;
   
   public MainScreen(){
       setFrame();
       createMenu();
       createLabel();
       createButtons();
       addDetails();
       buttonAction();
       this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
       setVisible(true);
       setDefaultCloseOperation(DISPOSE_ON_CLOSE);
   }
   
   public void setFrame(){
       
//       setPreferredSize(new Dimension(500,300));
//       setBounds(450, 200, 500, 300);
       setSize(500,300);
       setLocationRelativeTo(null);
       setTitle("Welcome to BLOKUS!");
       mainPanel = new JPanel();
       contentPane.add(mainPanel);
       mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
       setResizable(false);
   }

    private void createMenu()
    {
        JMenuBar menuBar = new JMenuBar();

        JMenu file = new JMenu("File");
        JMenu help = new JMenu("Help");

        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem howTo = new JMenuItem("How To");
        JMenuItem about = new JMenuItem("About");

        JMenuItem darkMode = new JMenuItem("Dark Mode");

        menuBar.add(file);
        menuBar.add(help);

        file.add(darkMode);
        file.add(exit);
        help.add(howTo);
        help.add(about);


        darkMode.addActionListener(actionEvent -> {
            int dialogResult = JOptionPane.showConfirmDialog (null, "Changing to dark mode is permanent, you will have to exit the window to change it back? Do you still want to continue with dark mode?","Warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if(dialogResult == JOptionPane.YES_OPTION) {
                Options.switchDarkMode();
            }
            if(Options.isDarkMode()){
                Options.setDarkModeColour(MainScreen.this);
            }
        });

        exit.addActionListener(actionEvent -> System.exit(0));
        howTo.addActionListener(actionEvent -> new HelpDetails("blokus"));
        about.addActionListener(actionEvent -> new About());
        setJMenuBar(menuBar);
    }
   
   public void createLabel(){
       String text="<html><span style='color: #4285f4'>B</span>" +  //BLUE
               "  <span style='color: #ea4335'>L</span>" +          //RED
               "  <span style='color: #fbbc05'>O</span>" +            //YELLOW
               "  <span style='color: #4285f4'>K</span>" +             //BLUE
               "  <span style='color: #34a853'>U</span>" +           //GREEN
               "  <span style='color: #ea4335'>S</span></html>";        //RED
        mainLbl = new JLabel(text);
   }
   
   public void createButtons(){
       startButton = new JButton("Start");
       loadButton = new JButton("Load");
       exitButton = new JButton("Exit");
   }
   
   public void addDetails(){
       Dimension labelPanelSize = new Dimension(300, 150);
       Dimension BtnPanelsize = new Dimension(200, 50);
       Dimension btnSize = new Dimension(100, 50);
       
       

       
       JPanel mainLblPan = new JPanel();
       mainLblPan.setPreferredSize(labelPanelSize); 
       mainLblPan.setLayout(new FlowLayout());
       mainLbl.setFont(new Font(Font.SERIF, Font.BOLD, 60));

       

       JPanel buttonPan = new JPanel();
       buttonPan.setPreferredSize(BtnPanelsize);
       buttonPan.setLayout(new FlowLayout());
      
       
       startButton.setPreferredSize(btnSize);
       loadButton.setPreferredSize(btnSize); 
       exitButton.setPreferredSize(btnSize); 
       
        buttonPan.add(startButton);
        buttonPan.add(Box.createRigidArea(new Dimension(5,0)));
        buttonPan.add(loadButton);
        buttonPan.add(Box.createRigidArea(new Dimension(5,0)));
        buttonPan.add(exitButton);
        mainPanel.add(buttonPan);
        
        mainLblPan.add(Box.createRigidArea(new Dimension(0,150)));
        mainLblPan.add(mainLbl);
        
       mainPanel.add(mainLblPan);
       mainPanel.add(buttonPan);
   }
   
   private void buttonAction(){
        exitButton.addActionListener((ActionEvent ev) -> {
            System.exit(0);
        });
        loadButton.addActionListener((ActionEvent ev) -> {
            new LoadScreen();// This button will load saved files
        });
        startButton.addActionListener((ActionEvent ev) -> {
            dispose();
            new CreateGame(); // This Button will bring you to the game setup
            //screen
        });
    }


}
