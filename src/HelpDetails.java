import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

/**
 * Help details displays the information to assist users in playing the Blokus game
 */
public class HelpDetails extends JFrame 
{
    public HelpDetails()
    {
        // creates how to function for the help menu 
        super("Instructions");
        setLayout(new FlowLayout());
        setSize(new Dimension(200,200));

        JTextArea howToText = new JTextArea("To start a game, fill all drop-down boxes with game specifics, then press start!");
        JScrollPane scrollPane = new JScrollPane(howToText);
        howToText.setEditable(false);
        howToText.setLineWrap(true);
        howToText.setWrapStyleWord(true);
        getContentPane().add(howToText);
        setVisible(true);
    }

}

