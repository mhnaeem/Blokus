import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
public class LoadScreen extends JFrame {
    private JPanel fullPanel, topPanel, bottomPanel, exitPanel;


    public LoadScreen(){

        //Setting layouts of all the panel
        fullPanel=new JPanel(new BorderLayout());
        fullPanel.setBorder(new EmptyBorder(50, 150, 50, 150));
        setupTopPanel();
        setupBottomPanel();
        setupExitPanel();
        Container contentPane = getContentPane();
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
        JMenuBar menu = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenu about = new JMenu("About");
        JMenu help = new JMenu("Help");
        JMenuItem load = new JMenuItem("Load");
        JMenuItem deleteLoad = new JMenuItem("Delete Load State");
        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem howTo = new JMenuItem("How To");

        file.add(load);
        file.add(deleteLoad);
        file.add(exit);
        help.add(howTo);

        load.addActionListener(actionEvent -> loadEvent());
        exit.addActionListener(actionEvent -> System.exit(0));
        deleteLoad.addActionListener(actionEvent -> deleteLoadEvent());
        howTo.addActionListener(actionEvent -> new HelpDetails("load"));
        about.addMouseListener(new AboutListener());

        menu.add(file);
        menu.add(about);
        menu.add(help);
        setJMenuBar(menu);
    }

    
    //Setting up Load Game label on top panel
    private void setupTopPanel(){
        topPanel=new JPanel(new FlowLayout(1));
        JLabel loadGame = new JLabel("Load Screen");
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
        JScrollPane scroll = new JScrollPane(bottomPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //Adding scroll to fullPanel
        fullPanel.add(scroll,BorderLayout.CENTER);
    }

    private void setupExitPanel(){

        exitPanel=new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton exitButton = new JButton("Exit");

        exitPanel.add(exitButton);
        fullPanel.add(exitPanel,BorderLayout.SOUTH);

        exitButton.addActionListener(actionEvent -> System.exit(0));
    }

    private void loadEvent(){
        System.out.println("Clicked on load menu button.");
    }
    private void deleteLoadEvent(){
        System.out.println("Clicked on delete load menu button.");
    }

    private class AboutListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            new About();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}


