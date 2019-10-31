import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;

public class PlayerGrid {

    private JPanel gridPanel;
    private Color color;
    private int index;

    public PlayerGrid(int index) {
        super();
        this.index = index;
        this.color = Options.getColor(index);
    }

    private JPanel createGrid(int gridRows, int gridColumns, int buttonWidth, int buttonHeight) {
        JPanel tempPanel = new JPanel();
        tempPanel.setLayout(new GridBagLayout());
        JButton[][] buttons = createButtonsList(gridRows, gridColumns);
        GridBagConstraints gbc = new GridBagConstraints();

        for (int r = 0; r < gridRows; r++) {
            gbc.gridy = r;
            for (int c = 0; c < gridColumns; c++) {
                gbc.gridx = c;
                JButton btn = buttons[r][c];
                btn.setForeground(Color.white);
                btn.setBackground(Color.white);
                btn.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                btn.setFocusable(false);
                btn.addActionListener(new playerListener());
                tempPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
                tempPanel.add(btn, gbc);
            }
        }
        gridPanel = tempPanel;
        colorPieces();
        return gridPanel;
    }

    private JButton[][] createButtonsList(int gridRows, int gridColumns) {
        JButton[][] buttons = new JButton[gridRows][gridColumns];
        for (int r = 0; r < gridRows; r++) {
            for (int c = 0; c < gridColumns; c++) {
                JButton btn = new JButton();
                String nameStr = r + "," + c;
                btn.setName(nameStr);
                buttons[r][c] = btn;
            }
        }
        Piece.getPlayerGridMap().put(index,buttons);
        return buttons;
    }


    public JPanel getPlayerGridPanel() {
        return createGrid(16, 17, 20, 20);
    }

    private void colorPieces() {
        HashSet<String> colourSpots = new HashSet<>(Arrays.asList("0,0", "0,1", "0,2", "0,3", "0,5", "0,6", "0,7", "0,8", "0,11", "0,12", "0,13", "0,14", "1,8", "1,13", "3,0", "3,3", "3,4", "3,6", "3,10", "3,11", "3,15", "4,0", "4,3", "4,4", "4,6", "4,11", "4,14", "4,15", "4,16", "5,0", "5,1", "5,3", "5,6", "5,7", "5,8", "5,11", "5,12", "5,15", "7,0", "7,3", "7,4", "7,6", "7,10", "7,11", "7,12", "7,14", "7,15", "8,0", "8,1", "8,3", "8,6", "8,7", "8,11", "8,15", "8,16", "9,0", "9,3", "9,4", "9,7", "9,8", "9,11", "9,15", "11,0", "11,1", "11,2", "11,5", "11,6", "11,9", "11,12", "11,13", "11,15", "12,2", "12,3", "12,6", "12,7", "12,9", "12,10", "12,12", "12,13", "14,0", "14,1", "14,2", "14,3", "14,4", "14,6", "14,7", "14,8", "14,10", "14,11"));
        Component[] c = gridPanel.getComponents();
        for (Component component : c) {
            String name = component.getName();
            //This is extremely inefficient however we will change it later
            if (colourSpots.contains(name)) {
                component.setBackground(color);
                if (Options.getIsColorblind()) {
                    ImageIcon icon = null;
                    switch (index) {
                        case 1:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_star_216411.png");
                            break;
                        case 2:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_times_216465.png");
                            break;
                        case 3:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_media-record_216317.png");
                            break;
                        case 4:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_media-stop_216325.png");
                            break;
                    }
                    ((JButton) component).setIcon(icon);
                    ((JButton) component).setDisabledIcon(icon);
                }
            } else {
                component.setBackground(Color.white);
                ((JButton) component).setDisabledIcon(null);
                ((JButton) component).setIcon(null);
                component.setEnabled(false);
            }
        }
    }


    private class playerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String btnName = ((JButton) e.getSource()).getName();
            PiecesMonitor.setSelectedPiece(index,Piece.getPieceMap().get(btnName));
            new SelectedPiece(index,btnName,gridPanel);
        }
        }
}
