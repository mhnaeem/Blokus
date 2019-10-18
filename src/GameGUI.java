import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameGUI extends JFrame {

    private Container contentPane = getContentPane();
    private JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPiecesPanel, bottomPiecesPanel;
    private ArrayList<ButtonGroup> pieces;

    public GameGUI(){

        //Initializing all the panels
        mainGridPanel = new JPanel();
        leftPiecesPanel = new JPanel();
        rightPiecesPanel = new JPanel();
        topPiecesPanel = new JPanel();
        bottomPiecesPanel = new JPanel();

        //Setting layouts of all the panel
        contentPane.setLayout(new BorderLayout());

        //Adding the panels to the main frame
        contentPane.add(leftPiecesPanel, BorderLayout.WEST);
        contentPane.add(rightPiecesPanel, BorderLayout.EAST);
        contentPane.add(topPiecesPanel, BorderLayout.NORTH);
        contentPane.add(bottomPiecesPanel, BorderLayout.SOUTH);
        contentPane.add(mainGridPanel, BorderLayout.CENTER);

        mainGridPanel.add(createGrid(20,20,40,40));
        leftPiecesPanel.add(createPlayingPieces());
        rightPiecesPanel.add(createPlayingPieces());

        mainGridPanel.updateUI();

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Ensures that the JFrame opens in fullscreen
        setVisible(true);
    }

    private JPanel createGrid(int gridRows, int gridColumns, int buttonWidth, int buttonHeight){
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridBagLayout());

        ArrayList<ArrayList<JButton>> buttons = createButtonsList(gridRows, gridColumns);

        GridBagConstraints gbc = new GridBagConstraints();

        for (int r = 0; r < gridRows; r++){
            gbc.gridy = r;
            for (int c = 0; c < gridColumns; c++){
                gbc.gridx = c;
                JButton btn =  buttons.get(r).get(c);
                btn.setForeground(Color.white);
                btn.setBackground(Color.white);
                btn.setPreferredSize(new Dimension(buttonWidth,buttonHeight));
                btn.setFocusable(false);
                btn.addActionListener(new gridListener());
                tempPanel.setBorder(new EmptyBorder(1,1,1,1));
                tempPanel.add(btn,gbc);
            }
        }
        return tempPanel;
    }

    private ArrayList<ArrayList<JButton>> createButtonsList(int gridRows, int gridColumns){
        ArrayList<ArrayList<JButton>> buttons = new ArrayList<>();
        ArrayList<String> cubesNotEaten = new ArrayList<>();

        for (int r = 0; r < gridRows; r++) {
            buttons.add(new ArrayList<>());
            for (int c = 0; c < gridColumns; c++) {
                JButton btn = new JButton();
                String nameStr = r + "," + c;
                btn.setName(nameStr);
                cubesNotEaten.add(nameStr);
                buttons.get(r).add(btn);
            }
        }
        return buttons;
    }

    private JPanel createPlayingPieces(){
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel pnl1 = createGrid(17,17, 20,20);
        pnl1.setBorder(new EmptyBorder(30,30,30,30));

        JPanel pnl2 = createGrid(17,17, 20,20);
        pnl2.setBorder(new EmptyBorder(30,30,30,30));

        main.add(pnl1);
        main.add(pnl2);

        return main;
    }

    private class gridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String st = ((JButton) e.getSource()).getName();
            System.out.print("The button on location ");
            System.out.print(st);
            System.out.println(" was pressed.");
        }
    }
}