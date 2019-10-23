
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
public class loadSavedScreen extends JFrame {
    private Container contentPane = getContentPane();
    private JPanel fullPanel, topPanel, bottomPanel;
    private JLabel loadGame;
    private JScrollPane scroll;
    // private ArrayList<ArrayList<JButton>> btns;



    public loadSavedScreen(){
        super("LOAD GAME!");

        //Setting layouts of all the panel
        fullPanel=new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(50, 150, 50, 150));
        setupTopPanel();
        setupBottomPanel();
        contentPane.add(fullPanel);
        setBounds(400, 200, 600, 500);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
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
                String buttonText= "Name: "+bName+"\n"+"Date: "+bDate+"\n"+"Time: "+bTime;
                JButton btn=new JButton(buttonText);
                bottomPanel.add(btn);
        }
    
        //Adding scrollbar to bottom panel
        scroll=new JScrollPane(bottomPanel,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Adding scroll to fullPanel
        fullPanel.add(scroll,BorderLayout.CENTER);



    }
    public static void main(String[] args)
    {
        for(int i = 0; i < 10; i++){
            new SavedState(Integer.toString(i));
        }
        loadSavedScreen screen = new loadSavedScreen();
    }
}

