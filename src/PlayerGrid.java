import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class PlayerGrid {

    private JPanel gridPanel;
    private int playerIndex;
    private static HashMap<Integer, JButton[][]> playerGridButtonMap = new HashMap<>();
    private static HashMap<Integer, JPanel> playerGridPanelMap = new HashMap<>();

    public PlayerGrid(int index) {
        this.playerIndex = index;
        createGrid(16, 17, 20, 20);
        playerGridPanelMap.put(this.playerIndex, this.gridPanel);
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
                btn.addActionListener(new PlayerGridListener());
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
        playerGridButtonMap.put(playerIndex,buttons);
        return buttons;
    }

    public static JButton[][] getPlayerGridButtons(int player_index){
        if(playerGridButtonMap.containsKey(player_index)){
            return playerGridButtonMap.get(player_index);
        }
        JOptionPane.showMessageDialog(null, "JButton array not found, error in PlayerGridMapButtons");
        return null;
    }


    public static JPanel getPlayerGridPanel(int player_index) {
        if (playerGridPanelMap.containsKey(player_index)){
            return playerGridPanelMap.get(player_index);
        }
        JOptionPane.showMessageDialog(null, "Error in getPlayerGridPanel, index not found");
        return null;
    }

    //Only to be called from the game engine class
    public static void disableOtherPlayerGrids(int player_index){
        playerGridButtonMap.forEach((k,v) -> {
            for (JButton[] jButtons : v) {
                for (JButton jButton : jButtons) {
                    if (k != player_index) {
                        jButton.setEnabled(false);
                    } else {
                        jButton.setEnabled(true);
                    }
                }
            }
        });
    }

    private void colorPieces() {
        HashSet<String> colourSpots = new HashSet<>(Arrays.asList("0,0", "0,1", "0,2", "0,3", "0,5", "0,6", "0,7", "0,8", "0,11", "0,12", "0,13", "0,14", "1,8", "1,13", "3,0", "3,3", "3,4", "3,6", "3,10", "3,11", "3,15", "4,0", "4,3", "4,4", "4,6", "4,11", "4,14", "4,15", "4,16", "5,0", "5,1", "5,3", "5,6", "5,7", "5,8", "5,11", "5,12", "5,15", "7,0", "7,3", "7,4", "7,6", "7,10", "7,11", "7,12", "7,14", "7,15", "8,0", "8,1", "8,3", "8,6", "8,7", "8,11", "8,15", "8,16", "9,0", "9,3", "9,4", "9,7", "9,8", "9,11", "9,15", "11,0", "11,1", "11,2", "11,5", "11,6", "11,9", "11,12", "11,13", "11,15", "12,2", "12,3", "12,6", "12,7", "12,9", "12,10", "12,12", "12,13", "14,0", "14,1", "14,2", "14,3", "14,4", "14,6", "14,7", "14,8", "14,10", "14,11"));
        Component[] c = gridPanel.getComponents();
        for (Component component : c) {
            String name = component.getName();
            if (colourSpots.contains(name)) {
                component.setBackground(Options.getColor(playerIndex));
                if (Options.getIsColorblind()) {
                    ImageIcon icon = null;
                    switch (playerIndex) {
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


    private class PlayerGridListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String btnName = ((JButton) e.getSource()).getName();

            //TODO: Player turn is being set after the player has already clicked on the grid, change this.
            //If player has clicked on it, then it is the player's turn
            GameEngine.setSelectedPiece(Piece.getPieceMap().get(btnName));
            new SelectedPiece(playerIndex, btnName, gridPanel);
        }
    }

    public static void removePieceEvent(int selectedPiece){
        JButton[][] playerGrid = PlayerGrid.getPlayerGridButtons(GameEngine.getCurrentTurn());
        Piece.getPieceDisplayCoordinates(selectedPiece).forEach(s->{
            String[] str = s.split(",");
            int x = Integer.parseInt(str[0]);
            int y = Integer.parseInt(str[1]);

            JButton btn = playerGrid[x][y];
            btn.setBackground(Color.white);
            btn.setIcon(null);
            btn.setDisabledIcon(null);
            btn.setEnabled(false);
        });
    }
}
