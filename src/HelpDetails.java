import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;


/**
 * Help details displays the information to assist users in playing the Blokus game
 */
public class HelpDetails extends JFrame
{

    public HelpDetails(String type)
    {
        super("Instructions");
        setLayout(new BorderLayout());
        setSize(new Dimension(400,400));

        // makes the jpanel
        JPanel helpPanel = new JPanel();
        this.getContentPane().add(helpPanel);
        helpPanel.setLayout(new BorderLayout());

        // makes a new jpanel in the main jpanel's south
        JPanel helpPanelSouth = new JPanel();
        helpPanelSouth.setLayout(new BorderLayout());
        helpPanel.add(helpPanelSouth, BorderLayout.SOUTH);

        // makes a new jpanel in the main jpanel's centre
        JPanel helpPanelCenter = new JPanel();
        helpPanelCenter.setLayout(new BorderLayout());
        helpPanel.add(helpPanelCenter, BorderLayout.CENTER);

        // sets the empty space around the text
        helpPanel.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
        JLabel contentLabel = new JLabel("");
        String message = "";


        // called in the CreateGame
        if (type.equals("create"))
        {
            contentLabel.setText("Starting a Game: ");
            message += "To start a game, fill all drop-down boxes with game specifics, then press start!\n\n";
            message += "For quickest setup, select 4 players, 4 human players, easy bot, colourblind option, basic scoring, and random colours.\n\n";
            setTitle("Setup Instructions");

        }

        // called in the GameGUI
        if (type.equals("game"))
        {
            contentLabel.setText("Playing a Piece: ");
            message += "To play a piece, first select it from your tray. \n\n";
            message += "You can choose to rotate or flip the piece. \n\n";
            message += "You can also pass your turn.\n\n";
            message += "Once the piece is oriented correctly, select a location on the game board. \n\n";
            message += "This will play your piece. \n\n";
            message += "The first piece must be placed in the corner closest to your colour piece tray. \n\n";
            message += "Pieces must be placed with one corner touching another piece of the same colour, but no adjacent sides. \n\n";
            setTitle("Game Instructions");
        }

        // called on the MainScreen
        if (type.equals("blokus"))
        {
            contentLabel.setText("Description of the Game: ");
            message += "In Blokus, players place pieces onto a board, with the goal of having no (or the least number of) pieces at the end.\n\n";
            message += "Each player's first move must have a piece placed in a corner of the board.\n\n";
            message += "Pieces must be placed next to the corner of another piece of that colour, but with no two sides adjacent.\n\n";
            setTitle("Blokus Information ");
        }

        // called on the MainScreen
        if (type.equals("load"))
        {
            contentLabel.setText("Load Instructions: ");
            message += "Select a saved game to load. \n\n";
            setTitle("Load Game ");
        }

        // creates the parts of the jframe
        JTextArea howToText = new JTextArea(message);

        // sets attributes of the textarea
        howToText.setEditable(false);
        howToText.setLineWrap(true);
        howToText.setWrapStyleWord(true);

        // adds the label and textarea to the jframe
        helpPanel.add(contentLabel, BorderLayout.NORTH);
        helpPanelCenter.add(howToText, BorderLayout.CENTER);

        // makes back button
        JButton backButton = new JButton("Back");
        backButton.setSize(new Dimension(60,40));
        backButton.addActionListener(ev -> HelpDetails.this.dispose());
        helpPanelSouth.add(backButton, BorderLayout.EAST);

        // sets visible
        setVisible(true);
        if(Options.isDarkMode()){
            Options.setDarkModeColour(this);
        }
    }
}

