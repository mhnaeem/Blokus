import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.ByteOrder;
import java.util.ArrayList;

public class GameGUI extends JFrame {

    private int columns, rows;
    private Container contentPane = getContentPane();
    private JPanel mainGridPanel, leftPiecesPanel, rightPiecesPanel, topPiecesPanel, bottomPiecesPanel;
    private ArrayList<ArrayList<JButton>> btns;
    private ArrayList<String> cubesNotEaten;
    private ArrayList<ButtonGroup> pieces;

    public GameGUI(Integer w, Integer h){
        columns = w;
        rows = h;

        //Initializing all the panels
        mainGridPanel = new JPanel();
        leftPiecesPanel = new JPanel();
        rightPiecesPanel = new JPanel();
        topPiecesPanel = new JPanel();
        bottomPiecesPanel = new JPanel();

        //Setting layouts of all the panels
        mainGridPanel.setLayout(new GridBagLayout());
        leftPiecesPanel.setLayout(new GridLayout(11,2));
        rightPiecesPanel.setLayout(new GridLayout(11,2));
        topPiecesPanel.setLayout(new GridLayout(2,11));
        bottomPiecesPanel.setLayout(new GridLayout(2,11));
        contentPane.setLayout(new BorderLayout());

        //Adding the panels to the main frame
        contentPane.add(leftPiecesPanel, BorderLayout.WEST);
        contentPane.add(rightPiecesPanel, BorderLayout.EAST);
        contentPane.add(topPiecesPanel, BorderLayout.NORTH);
        contentPane.add(bottomPiecesPanel, BorderLayout.SOUTH);
        contentPane.add(mainGridPanel, BorderLayout.CENTER);

        createGrid();
        createPlayingPieces();
        mainGridPanel.updateUI();

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH); //Ensures that the JFrame opens in fullscreen
        setVisible(true);
    }

    private void createGrid(){
        createButtonsList();
        GridBagConstraints gbc = new GridBagConstraints();
        for (int r = 0; r < rows; r++){
            gbc.gridy = r;
            for (int c = 0; c < columns; c++){
                gbc.gridx = c;
                JButton btn =  btns.get(r).get(c);
                btn.setForeground(Color.white);
                btn.setBackground(Color.white);
                btn.setPreferredSize(new Dimension(32,32));
                btn.setFocusable(false);
                btn.addActionListener(new gridListener());
                mainGridPanel.add(btn,gbc);
            }
        }
        mainGridPanel.updateUI();
    }

    private void createButtonsList(){
        btns = new ArrayList<>();
        cubesNotEaten = new ArrayList<>();

        for (int r = 0; r < rows; r++){
            btns.add(new ArrayList<>());
            for (int c = 0; c < columns; c++){
                JButton btn = new JButton();
                String nameStr = r+","+c;
                btn.setName(nameStr);
                cubesNotEaten.add(nameStr);
                btns.get(r).add(btn);
            }
        }
        mainGridPanel.updateUI();
    }

    private void createPlayingPieces(){
        pieces = new ArrayList<>();

        ArrayList<JPanel> pnls = new ArrayList<>();
        pnls.add(rightPiecesPanel);
        pnls.add(leftPiecesPanel);
        pnls.add(topPiecesPanel);
        pnls.add(bottomPiecesPanel);

        for (int i = 0; i < 4; i++){
            pieces.add(new ButtonGroup());
            ButtonGroup currPlayer = pieces.get(i);
            for (int j = 1; j <= 21; j++){
                JRadioButton tempBtn = new JRadioButton(Integer.toString(j));
                tempBtn.addActionListener(new piecesManager());
                tempBtn.setName(Integer.toString(j));
                currPlayer.add(tempBtn);
                pnls.get(i).add(tempBtn);
            }
        }
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

    private class piecesManager implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e){
            String st = ((JRadioButton) e.getSource()).getName();
            System.out.print("Piece ");
            System.out.print(st);
            System.out.println(" was selected.");
        }
    }
}