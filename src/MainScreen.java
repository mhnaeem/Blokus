import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Main Screen for Blokus Game that includes the following:
 * Window title;
 * Blokus title on window; and
 * 3 Buttons.
 *
 *
 * @author (Zachary Cheema)
 * @version (Version 1.1)
 */
public class MainScreen extends JFrame{

    private JPanel mainPanel;
    private Container contentPane = getContentPane();
    private JLabel mainLbl;
    private JButton b1, b2, b3;

    public MainScreen(){
        setFrame();
        createDetails();
        addDetails();
        buttonAction();
        setVisible(true);
    }

    public void setFrame(){
        setSize(500,300);
        setPreferredSize(new Dimension(500,300));
        setTitle("Welcome to BLOCKUS!");
        mainPanel = new JPanel();
        contentPane.add(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        setResizable(false);
    }

    public void createDetails(){
        mainLbl = new JLabel("BLOKUS");
        b1 = new JButton("Start");
        b2 = new JButton("Load");
        b3 = new JButton("Exit");
    }

    public void addDetails(){
        Dimension size = new Dimension(500, 150);

        JPanel mainLblPan = new JPanel();
        mainLblPan.setPreferredSize(size);
        mainLblPan.setLayout(new FlowLayout());

        JPanel buttonPan = new JPanel();
        buttonPan.setPreferredSize(size);
        buttonPan.setLayout(new FlowLayout());

        buttonPan.add(b1);
        buttonPan.add(b2);
        buttonPan.add(b3);
        mainPanel.add(buttonPan);

        mainPanel.add(mainLblPan);
        mainPanel.add(buttonPan);
    }

    private void buttonAction(){
        b3.addActionListener((ActionEvent ev) -> {
            System.exit(0);
        });
    }
}

