import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.*;
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
    private JPanel fullPanel, topPanel, bottomPanel, exitPanel;
    private JLabel loadGame;
    private JScrollPane scroll;
    private JMenuBar menu;
    private JMenu file,about;
    private JMenuItem deleteLoad,load,exit;
    private JButton exitButton;




    public LoadScreen(){

        //Setting layouts of all the panel
        fullPanel=new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(50, 150, 50, 150));
        setupTopPanel();
        setupBottomPanel();
        setupExitPanel();
        contentPane.add(fullPanel);
        createMenu();
        setBounds(400, 200, 600, 500);
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
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
        deleteLoad=new JMenuItem("Delete Load State");
        exit = new JMenuItem("Exit");

        file.add(load);
        file.add(deleteLoad);
        file.add(exit);
        load.addActionListener(x->loadEvent());
        exit.addActionListener(x->exitEvent());
        deleteLoad.addActionListener(x->deleteLoadEvent());
        about.addActionListener(x->aboutEvent());
        menu.add(file);
        menu.add(about);
        setJMenuBar(menu);
    }

    
    //Setting up Load Game label on top panel
    private void setupTopPanel(){
        topPanel=new JPanel(new FlowLayout(1));
        loadGame=new JLabel("Load Screen");
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

    private void setupExitPanel(){

        exitPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        exitButton=new JButton("Exit");

        exitPanel.add(exitButton);
        fullPanel.add(exitPanel,BorderLayout.SOUTH);

        exitButton.addActionListener(x->exitEvent());
        
        
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
    private void deleteLoadEvent(){
        System.out.println("Clicked on delete load menu button.");
    }

}


