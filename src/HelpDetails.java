import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;


/**
 * Help details displays the information to assist users in playing the Blokus game
 */
public class HelpDetails extends JFrame
{
    private String text;

    public HelpDetails(String type)
    {
        super("Instructions");
        setLayout(new BorderLayout());
        setBounds(400, 200, 650, 550);
//        setSize(new Dimension(400,400));

        // makes the jpanel
        JPanel helpPanel = new JPanel();
        this.getContentPane().add(helpPanel);
        helpPanel.setBorder(new EmptyBorder(30, 50, 30, 40));
        helpPanel.setLayout(new BorderLayout());

        // makes a new jpanel in the main jpanel's south
        JPanel helpPanelSouth = new JPanel();
        helpPanelSouth.setLayout(new BorderLayout());
        helpPanelSouth.setBorder(new EmptyBorder(30, 210, 20, 210));
        helpPanel.add(helpPanelSouth, BorderLayout.SOUTH);

        // makes a new jpanel in the main jpanel's centre
        JPanel helpPanelCenter = new JPanel();
        helpPanelCenter.setLayout(new BorderLayout());
        helpPanelCenter.setBorder(new EmptyBorder(50, 20, 30, 20));
        helpPanel.add(helpPanelCenter, BorderLayout.CENTER);

        // sets the empty space around the text
        JLabel contentLabel = new JLabel("");


        // called in the CreateGame
        if (type.equals("create"))
        {
            setTitle("Setup Instructions");
            contentLabel.setText("Starting a Game: ");
            text = "<html><br><p style='font-size:30'>To start a game, fill all drop-down boxes with game specifics, then press start!<br>"+
            "For quickest setup, select 4 players, 4 human players, easy bot, colourblind option, basic scoring, and random colours.</p></html>";

        }

        // called in the GameGUI
        if (type.equals("game"))
        {
            setTitle("Game Instructions");
            contentLabel.setText("Playing a Piece: ");
            text = "<html><br><p style='font-size:20'>To play a piece, first select it from your tray. "+
                "You can choose to rotate or flip the piece. "+
                "You can also pass your turn. "+
                "Once the piece is oriented correctly, select a location on the game board. "+
                "This will play your piece. "+
                "The first piece must be placed in the corner closest to your colour piece tray. "+
                "Pieces must be placed with one corner touching another piece of the same colour, but no adjacent sides. </p></html>";
        }

        // called on the MainScreen
        if (type.equals("blokus"))
        {
            setTitle("Blokus Information ");
            contentLabel.setText("Description of the Game: ");
            text = "<html><br><p style='font-size:20'>In Blokus, players place pieces onto a board, with the goal of having no (or the least number of) pieces at the end. "+
            "Each player's first move must have a piece placed in a corner of the board. "+
            "Pieces must be placed next to the corner of another piece of that colour, but with no two sides adjacent. </p></html>";
        }

        // called on the MainScreen
        if (type.equals("load"))
        {
            setTitle("Load Game ");
            contentLabel.setText("Load Instructions: ");
            text = "<html><br><p style='font-size:30'>Select a saved game to load.</p></html>";
        }

        contentLabel.setFont(new Font(Font.SERIF, Font.BOLD, 40));


        JEditorPane paragraph = new JEditorPane();
        paragraph.setContentType("text/html");//set content as html
        paragraph.setText(text);

        paragraph.setEditable(false);//so its not editable
        paragraph.setOpaque(false);//so we dont see white background



        // adds the label and paragraph to the jframe
        helpPanelCenter.add(contentLabel, BorderLayout.NORTH);
        helpPanelCenter.add(paragraph, BorderLayout.CENTER);

        // makes back button
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100,50));
        backButton.addActionListener(ev -> HelpDetails.this.dispose());
        helpPanelSouth.add(backButton, BorderLayout.CENTER);

        // sets visible
        setVisible(true);
        if(Options.isDarkMode()){
            Options.setDarkModeColour(this);
        }
    }
}

