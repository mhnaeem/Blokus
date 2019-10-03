import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GameGUI extends JFrame {

    private int columns, rows;
    private Container contentPane = getContentPane();
    private JPanel pnl;
    private ArrayList<ArrayList<JButton>> btns;
    private ArrayList<String> cubesNotEaten;

    public GameGUI(Integer w, Integer h){
        columns = w;
        rows = h;

        pnl = new JPanel();
        pnl.setLayout(new GridBagLayout());
        contentPane.add(pnl);

        createGrid();
        pnl.updateUI();

        pack();
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
                btn.setForeground(new Color(139,69,19));
                btn.setBackground(new Color(139,69,19));
                btn.setPreferredSize(new Dimension(32,32));
                btn.setFocusable(false);
                btn.addActionListener(new gridListener());
                pnl.add(btn,gbc);
            }
        }
        pnl.updateUI();
    }

    private void createButtonsList(){
        btns = new ArrayList<>();
        cubesNotEaten = new ArrayList<>();

        for (int r = 0; r < rows; r++){
            btns.add(new ArrayList<>());
            for (int c = 0; c < columns; c++){
                JButton btn = new JButton();
                String nameStr = Integer.toString(r)+","+Integer.toString(c);
                btn.setName(nameStr);
                cubesNotEaten.add(nameStr);
                btns.get(r).add(btn);
            }
        }
        pnl.updateUI();
    }
    private class gridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String st = ((JButton) e.getSource()).getName();
            System.out.println(st);
        }
    }

    public static void main(String[] args) {
        new GameGUI(20,20);
    }
}