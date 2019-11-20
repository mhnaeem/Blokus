import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

class MainGrid {
    private static JPanel mainGridPanel;
    private static JButton[][] mainGridButtons;

    public MainGrid() {

        // For Mac's look and feel
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }
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

                // To account for different screen sizes
                btn.setMinimumSize(new Dimension(buttonWidth-5, buttonHeight-5));
                btn.setMaximumSize(new Dimension(buttonWidth+5,buttonHeight+5));

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
        buttons[0][0].setBorder(BorderFactory.createLineBorder(Color.GREEN,2));
        buttons[0][19].setBorder(BorderFactory.createLineBorder(Color.BLUE,2));
        buttons[19][0].setBorder(BorderFactory.createLineBorder(Color.RED,2));
        buttons[19][19].setBorder(BorderFactory.createLineBorder(Color.YELLOW,2));
        mainGridButtons = buttons;
        return buttons;
    }

    private static class MainGridListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedPoint = ((JButton) e.getSource()).getName();
            Integer selectedPiece = GameEngine.getSelectedPiece();
                if (selectedPiece != null) {
                    if(GameEngine.isLegal(selectedPoint)) {
                        int turn = GameEngine.getCurrentTurn();
                        placingPiece(turn, selectedPiece, selectedPoint);
                        Player.getPlayer(turn).pieceUsed(selectedPiece);
                        SelectedPiece.pieceHasBeenPlacedEvent(); //this closes selectedPieceWindow
                        PlayerGrid.removePieceEvent(selectedPiece); //this should remove piece from PlayerGrid for the current player
                        Options.firstTurnCornerMoveEvent();
                        GameEngine.hasGameEndedEvent();
                        GameEngine.setSelectedPiece(null);
                        GameEngine.updateCurrentTurn();
                    }
                else {
                    JOptionPane.showMessageDialog(null, "Error, No piece was selected. MainGridListener", "Illegal move!",JOptionPane.ERROR_MESSAGE);
                }
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
                    btn.setDisabledIcon(Player.getPlayerIcon(turn));
                    btn.setIcon(Player.getPlayerIcon(turn));
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
        else{
            Piece.getActionsList(selectedPieceIndex).forEach( actions -> {
                if ((brow +actions[1]<20)&&(brow +actions[1]>=0)&&(bcol + actions[0]<20)&&(bcol + actions[0]>=0)){
                    JButton btn = mainGridButtons[brow + actions[1]][bcol + actions[0]];

                    if(btn.isEnabled() && hover) {
                        btn.setBackground(Color.BLACK);
                        btn.setForeground(Color.white);
                        btn.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
                        btn.setText("X");
                        btn.setMargin(new Insets(0, 0, 0, 0));
                    }
                    if(!hover && btn.isEnabled()){
                        btn.setBackground(Color.white);
                        btn.setIcon(null);
                        btn.setDisabledIcon(null);
                        btn.setFont(null);
                        btn.setText("");
                    }
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
