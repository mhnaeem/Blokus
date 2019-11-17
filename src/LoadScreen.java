import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Load Saved Game that includes the following:
 * Load Game Label;
 * Load Game Buttons if there are any saved states;
 *
 * @author (Abdur Rahman Abul Hossain)
 * @version (Version 1.1)
 */
class LoadScreen extends JFrame {
    private JPanel mainPanel, topPanel, savedStatesPanel, goBackPanel;


    public LoadScreen(){

        SavedState.updateForLoad();

        //Setting layouts of all the panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(30, 110, 30, 110));
        getContentPane().add(mainPanel);

        setMainLoadGameLabel();
        setupSavedGameButtons();
        setupTheBackButton();
        setFrameSettings();

        if(Options.isDarkMode()){
            Options.setDarkModeColour(this);
        }
    }

    private void setFrameSettings(){
        setTitle("Load Game");
        setJMenuBar(new MenuCreator(new String[]{"deleteLoadState","exit","howTo","about"}, "load"));
        setBounds(400, 200, 600, 500);
        this.setIconImage(new ImageIcon("./Assets/Icons/tetris.png").getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setVisible(true);
    }

    //Setting up Load Game label on top panel
    private void setMainLoadGameLabel(){
        topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel loadGame = new JLabel("Load Screen");
        topPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        // Putting Load Game Label on Top Panel
        loadGame.setFont(new Font(Font.SERIF, Font.BOLD, 50));
        loadGame.setAlignmentX(Font.CENTER_BASELINE);
        topPanel.add(loadGame);

        //Adding the Top panel to the fullPanel
        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    //Setting up Load Game buttons on bottom panel
    private void setupSavedGameButtons(){

        //Creating Load Game Buttons and adding them to bottom panel
        ArrayList<SavedState> save = SavedState.getSavedStates();

        savedStatesPanel = new JPanel();
        savedStatesPanel.setLayout(new BoxLayout(savedStatesPanel, BoxLayout.Y_AXIS));

        for (SavedState savedState : save) {
            String bName = savedState.getName();
            String bDate = savedState.getDate();
            String bTime = savedState.getTime();
            String buttonText = "<html>" + "Name: " + bName + "<br>" + "Date: " + bDate + "<br>" + "Time: " + bTime + "</html>";
            JButton btn = new JButton(buttonText);
            btn.setName(savedState.getPath());
            btn.addActionListener(actionEvent -> new LoadGame(((JButton) actionEvent.getSource()).getName()));
            savedStatesPanel.add(btn);
        }
    
        //Adding scrollbar to bottom panel
        JScrollPane scroll = new JScrollPane(savedStatesPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Adding scroll to fullPanel
        mainPanel.add(scroll,BorderLayout.CENTER);
    }

    private void setupTheBackButton(){

        goBackPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back");

        backButton.setPreferredSize(new Dimension(100,50));
        goBackPanel.add(backButton);
        mainPanel.add(goBackPanel,BorderLayout.SOUTH);

        backButton.addActionListener(actionEvent -> this.dispose());
    }

}


