import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Help details displays the information to assist users in playing the Blokus game
 */
public class HelpDetails extends JFrame
{
    public HelpDetails(String type)
    {
        super("Instructions");
        setLayout(new BorderLayout());
        setSize(new Dimension(600,600));

        JLabel titleText = new JLabel("Instructions");

        JLabel contentText = new JLabel("");
        String message = "";


        if (type.equals("create"))
        {
            contentText.setText("Starting a Game: ");
            message += "To start a game, fill all drop-down boxes with game specifics, then press start!\n";
            setTitle("Setup Instructions");

        }

        if (type.equals("game"))
        {
            contentText.setText("Playing a Piece: ");
            message += "To play a piece, first select it from your tray. \n";
            message += "You can choose to rotate or flip the piece. \n";
            message += "You can also pass your turn.\n";
            message += "Once the piece is oriented correctly, select a location on the game board. \n";
            message += "This will play your piece. \n";
            setTitle("Game Instructions");
        }

        if (type.equals("blokus"))
        {
            contentText.setText("Description of the Game: ");
            message += "In Blokus, players place pieces onto a board, with the goal of having no (or the least number of) pieces at the end.\n";
            message += "Each player's first move must have a piece placed in a corner of the board.\n";
            message += "Pieces must be placed next to the corner of another piece of that colour, but with no two sides adjacent.\n";
            setTitle("Blokus info");
        }

        JTextArea howToText = new JTextArea(message);
        JScrollPane scrollPane = new JScrollPane(howToText);
        howToText.setEditable(false);
        howToText.setLineWrap(true);
        howToText.setWrapStyleWord(true);
        add(titleText, BorderLayout.NORTH);
        add(howToText, BorderLayout.CENTER);


        // makes back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(ev -> HelpDetails.this.dispose());
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

//    public static void main(String[] args){
//        new HelpDetails("blokus");
//    }

}

