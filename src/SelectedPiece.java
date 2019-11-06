import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * Selected Piece Screen for the Blokus Game
 *
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

public class SelectedPiece{

    private JButton pass, rotate, flipUp, flipRight, back;
    private JPanel piece, buttons, main;
    private static JFrame frm;
    private String selectedButtonName;
    private int playerIndex;
    private JButton[][] selectedButtonGrid;

    SelectedPiece(int player_index, String selected_button_name, Component player_grid_panel){

        this.playerIndex = player_index;
        this.selectedButtonName = selected_button_name;

        frm = new JFrame("Selected Piece Window");

        main = new JPanel();
        frm.setSize(400,360);
        frm.setResizable(false);

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        ImageIcon flipUpIcon = new ImageIcon("./Assets/Icons/iconfinder_multimedia-26_2849810.png");
        ImageIcon flipRightIcon = new ImageIcon("./Assets/Icons/iconfinder_DoubleChevronLeftRight_1031523.png");
        ImageIcon closeIcon = new ImageIcon("./Assets/Icons/close.png");
        ImageIcon rotateIcon = new ImageIcon("./Assets/Icons/rotate.png");
        ImageIcon passIcon = new ImageIcon("./Assets/Icons/pass.png");
        this.piece = new JPanel();
        this.piece.setBorder(new EmptyBorder(5,5,5,5));
        this.piece.add(createGrid(5,5,50,50));

        main.add(this.piece);

        this.rotate = new JButton(rotateIcon);
        this.rotate.addActionListener(ev -> {
            Piece.setActionList(rotateCounterClock(Piece.getActionsList(GameEngine.getSelectedPiece())));
            displayPiece();
        });
        this.pass = new JButton(passIcon);
        this.pass.addActionListener(ev -> {
            GameEngine.updateCurrentTurn();
            frm.dispose();
        });
        this.flipUp = new JButton(flipUpIcon);
        this.flipUp.addActionListener(ev -> {
            Piece.setActionList(flipUp(Piece.getActionsList(GameEngine.getSelectedPiece())));
            displayPiece();
        });
        //this.flipUp.setDisabledIcon(icon);
        //this.flipUp.setIcon(icon);
        this.flipRight = new JButton(flipRightIcon);
        this.flipRight.addActionListener(ev -> {
            Piece.setActionList(flipRight(Piece.getActionsList(GameEngine.getSelectedPiece())));
            displayPiece();
        });

        this.back = new JButton(closeIcon);
        this.back.addActionListener(ev -> {
            GameEngine.setSelectedPiece(null);
            frm.dispose();
        });

        buttons = new JPanel();

        buttons.add(this.pass);
        buttons.add(this.rotate);
        buttons.add(this.flipUp);
        buttons.add(this.flipRight);
        buttons.add(this.back);

        main.add(buttons);
        frm.add(main);

        displayPiece();

        frm.setAlwaysOnTop(true);
        frm.setUndecorated(true);
        frm.setSize(player_grid_panel.getSize());
        frm.setLocationRelativeTo(player_grid_panel);
        frm.setResizable(false);
        frm.setVisible(true);
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
        selectedButtonGrid = buttons;
        return buttons;
    }


    private void displayPiece(){
        if(selectedButtonGrid != null){
            for (int i = 0; i < selectedButtonGrid.length; i++) {
                for (int j = 0; j < selectedButtonGrid[i].length; j++) {
                    selectedButtonGrid[i][j].setBackground(Color.white);
                    selectedButtonGrid[i][j].setIcon(null);
                    selectedButtonGrid[i][j].setDisabledIcon(null);
                }
            }
        }
        //These x and y represent the zero coordinates, all actions will be taken from these points
        int x = 2;
        int y = 2;
        Color color = Options.getColor(this.playerIndex);
        if (Piece.getPieceMap().containsKey(this.selectedButtonName)) {
            int key = Piece.getPieceMap().get(this.selectedButtonName);
            (Piece.getActionsList(key)).forEach(action -> {
                selectedButtonGrid[x + action[0]][y + action[1]].setBackground(color);
                if (Options.getIsColorblind()) {
                    ImageIcon icon = null;
                    switch (this.playerIndex) {
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
                    selectedButtonGrid[x + action[0]][y + action[1]].setDisabledIcon(icon);
                    selectedButtonGrid[x + action[0]][y + action[1]].setIcon(icon);
                }
            });

            //Resetting the piece panel
            this.piece.removeAll();
            this.piece.revalidate();
            this.piece.repaint();
            this.piece.setLayout(new GridBagLayout());
            this.piece.setBorder(new EmptyBorder(5, 5, 5, 5));

            GridBagConstraints gbc = new GridBagConstraints();

            for (int i = 0; i < selectedButtonGrid.length; i++) {
                gbc.gridx = i;
                for (int j = 0; j < selectedButtonGrid[i].length; j++) {
                    gbc.gridy = j;
                    selectedButtonGrid[i][j].setPreferredSize(new Dimension(50, 50));
                    this.piece.add(selectedButtonGrid[i][j], gbc);
                }
            }
        }

        this.piece.updateUI();
        this.main.updateUI();
    }
    public static void pieceHasBeenPlacedEvent(){
        frm.dispose();
    }

    private static ArrayList<int[]> rotateCounterClock(ArrayList<int[]> actionList){
        ArrayList<int[]> toReturn = new ArrayList<>();
        actionList.forEach(action -> toReturn.add(new int[]{-action[1], action[0]}));
        return toReturn;
    }

    private static ArrayList<int[]> flipUp(ArrayList<int[]> actionsList){
        ArrayList<int[]> toReturn = new ArrayList<>();
        actionsList.forEach(action -> {
            int y = action[1];
            if (y != 0) {
                y = -y;
            }
            toReturn.add(new int[]{action[0], y});
        });
        return toReturn;
    }

    private static ArrayList<int[]> flipRight(ArrayList<int[]> actionList){
        ArrayList<int[]> toReturn = new ArrayList<>();
        actionList.forEach(action -> {
            int x = action[0];
            int y = action[1];
            if (x != 0){
                x = -x;
            }
            toReturn.add(new int[]{x, y});
        });
        return toReturn;
    }
}


