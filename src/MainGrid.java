import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGrid {
    private JPanel mainGridPanel;
    private static JButton[][] mainGridButtons;

    public MainGrid() {
    }

    public JPanel getMainGridPanel() {
        mainGridPanel = createGrid(20, 20, 35, 35);
        return mainGridPanel;
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
                btn.addActionListener(new gridListener());
                tempPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
                tempPanel.add(btn, gbc);
            }
        }
        return tempPanel;
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
        mainGridButtons = buttons;
        return buttons;
    }

    public static JButton[][] getMainGridButtons(){
        return mainGridButtons;
    }

    private class gridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String btnName = ((JButton) e.getSource()).getName();

            String selectedPoint = btnName;

            System.out.print("The button on location ");
            System.out.print(btnName);
            System.out.println(" was pressed on main Grid.");

            if (PiecesMonitor.getSelectedPiece() != -1) {//player turn would go here
                int selectedPiece = PiecesMonitor.getSelectedPiece();
                if(GameEngine.isLegal(MainGrid.getMainGridButtons(),selectedPoint)) {
                    int turn = 1;//player 1 will always remove for testing // turn represents player index
                    placingPiece(turn, selectedPiece, selectedPoint);
                }
            }
        }

        private void placingPiece(int turn, int selectedPiece, String button) {
            String[] b = button.split(",");
            int brow = Integer.parseInt(b[0]);
            int bcol = Integer.parseInt(b[1]);
            Piece.getActionsListMap().get(selectedPiece).forEach(IntegerArray -> {
                JButton btn = mainGridButtons[brow + IntegerArray[1]][bcol + IntegerArray[0]];
                btn.setBackground(Options.getColor(turn));
                btn.setEnabled(false);
                if (Options.getIsColorblind()) {
                    ImageIcon icon = null;
                    switch (turn) {
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
                    btn.setDisabledIcon(icon);
                    btn.setIcon(icon);
                }
            });
            PiecesMonitor.removePiece(turn,selectedPiece);
            SelectedPiece.pieceHasBeenPlacedEvent(turn); //this closes selectedPieceWindow
            PlayerGrid.removePieceEvent(turn,selectedPiece); //this should remove piece from PlayerGrid

        }
    }
}
