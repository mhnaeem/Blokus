import org.ietf.jgss.GSSManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainGrid {
    private static JPanel mainGridPanel;
    private static JButton[][] mainGridButtons;

    public MainGrid() {
        mainGridPanel = createGrid(20, 20, 35, 35);
    }

    public static JPanel getMainGridPanel() {
        return mainGridPanel;
    }

    public static JButton[][] getMainGridButtons(){
        return mainGridButtons;
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
                btn.addActionListener(new MainGridListener());
                btn.addMouseListener(new MainGridHoverListener());
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

    private class MainGridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedPoint = ((JButton) e.getSource()).getName();
            Integer selectedPiece = GameEngine.getSelectedPiece();

            if (selectedPiece != null) {//player turn would go here
                if(GameEngine.isLegal(selectedPoint)) {
                    int turn = GameEngine.getCurrentTurn();
                    placingPiece(turn, selectedPiece, selectedPoint);
                    Player.getPlayer(turn).pieceUsed(selectedPiece);
                    SelectedPiece.pieceHasBeenPlacedEvent(); //this closes selectedPieceWindow
                    PlayerGrid.removePieceEvent(selectedPiece); //this should remove piece from PlayerGrid for the current player
                    GameEngine.updateCurrentTurn();
                }
            }
            else {
                JOptionPane.showMessageDialog(null, "Error, No piece was selected. MainGridListener", "Illegal move!",JOptionPane.ERROR_MESSAGE);
            }
        }

        private void placingPiece(int turn, int selectedPiece, String button) {
            String[] b = button.split(",");
            int brow = Integer.parseInt(b[0]);
            int bcol = Integer.parseInt(b[1]);

            Piece.getActionsList(selectedPiece).forEach(actions -> {
                JButton btn = mainGridButtons[brow + actions[1]][bcol + actions[0]];
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
        }
    }

    private void mouseHoverDisplay(String buttonName, boolean hover){

        String[] b = buttonName.split(",");
        int brow = Integer.parseInt(b[0]);
        int bcol = Integer.parseInt(b[1]);
        Integer selectedPieceIndex = GameEngine.getSelectedPiece();

        if (GameEngine.isLegal(buttonName)){

            Piece.getActionsList(selectedPieceIndex).forEach( actions -> {

                JButton btn = mainGridButtons[brow + actions[1]][bcol + actions[0]];

                if(btn.isEnabled() && hover) {
                    btn.setBackground(Options.getColor(GameEngine.getCurrentTurn()));

                    if (Options.getIsColorblind()) {
                        ImageIcon icon = null;
                        switch (GameEngine.getCurrentTurn()) {
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
                }
                if(!hover && btn.isEnabled()){
                    btn.setBackground(Color.white);
                    btn.setIcon(null);
                    btn.setDisabledIcon(null);
                }
            });
        }
    }

    private class MainGridHoverListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (GameEngine.getSelectedPiece() != null) {
                mouseHoverDisplay(((JButton) e.getSource()).getName(), true);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            if (GameEngine.getSelectedPiece() != null) {
                mouseHoverDisplay(((JButton) e.getSource()).getName(), false);
            }
        }

    }
}
