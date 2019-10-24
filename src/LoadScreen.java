
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.border.EmptyBorder;
/**
 * Load Saved Game that includes the following:
 * Load Game Label;
 * Load Game Buttons if there are any saved states;
 *
 * @author (Abdur Rahman Abul Hossain)
 * @version (Version 1.1)
 */
public class LoadScreen extends JFrame {
    private Container contentPane = getContentPane();
    private JPanel fullPanel, topPanel, bottomPanel;
    private JLabel loadGame;
    private JScrollPane scroll;
    private JMenuBar menu;
    private JMenu file,about;
    private JMenuItem reset,load,exit;




    public LoadScreen(){

        //Setting layouts of all the panel
        fullPanel=new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(50, 150, 50, 150));
        setupTopPanel();
        setupBottomPanel();
        contentPane.add(fullPanel);
        createMenu();
        setBounds(400, 200, 600, 500);
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
        load = new JMenuItem("Load");
        exit = new JMenuItem("Exit");
        menu.add(file);
        menu.add(about);
        file.add(load);
        file.add(exit);
        load.addActionListener(x->loadEvent());
        exit.addActionListener(x->exitEvent());
        
        about.addActionListener(x->aboutEvent());
        setJMenuBar(menu);
    }

    
    //Setting up Load Game label on top panel
    private void setupTopPanel(){
        topPanel=new JPanel(new FlowLayout(1));
        loadGame=new JLabel("Load Game!");
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Putting Load Game Label on Top Panel
        loadGame.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        loadGame.setAlignmentX(Font.CENTER_BASELINE);
        topPanel.add(loadGame);

        //Adding the Top panel to the fullPanel
        fullPanel.add(topPanel, BorderLayout.NORTH);
    }


    //Setting up Load Game buttons on bottom panel
    private void setupBottomPanel(){

        //Creating Load Game Buttons and adding them to bottom panel
        ArrayList<SavedState> save= SavedState.savedstates;
        int length=save.size();

        bottomPanel=new JPanel(new GridLayout(length,1));

        for(int r=0;r<length;r++){
                String bName=save.get(r).getName();
                String bDate=save.get(r).getDate();
                String bTime=save.get(r).getTime();
                String buttonText= "<html>"+"Name: "+bName+"<br>"+"Date: "+bDate+"<br>"+"Time: "+bTime+"</html>";
                JButton btn=new JButton(buttonText);
                bottomPanel.add(btn);
        }
    
        //Adding scrollbar to bottom panel
        scroll=new JScrollPane(bottomPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Adding scroll to fullPanel
        fullPanel.add(scroll,BorderLayout.CENTER);
    }

    /**
     * when exit button in file menu is pressed
     * exits
     */
    private void exitEvent(){
        System.exit(0);
    }

    private void aboutEvent(){
        System.out.println("Clicked on About menu button.");
    }
    private void loadEvent(){
        System.out.println("Clicked on load menu button.");
    }
    public static void main(String[] args)
    {
        for(int i = 0; i < 10; i++){
            new SavedState(Integer.toString(i));
        }
        loadSavedScreen screen = new loadSavedScreen();
    }
}

