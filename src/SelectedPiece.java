import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;

/**
 * Selected Piece Screen for the Blokus Game
 *
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

class SelectedPiece{

    private JPanel piece, main;
    private static JFrame frm;
    private String selectedButtonName;
    private int playerIndex;
    private JButton[][] selectedButtonGrid;

    SelectedPiece(int player_index, String selected_button_name, Component player_grid_panel){

        // For Mac's look and feel
        try {
            UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName() );
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.playerIndex = player_index;
        this.selectedButtonName = selected_button_name;

        frm = new JFrame("Selected Piece Window");

        main = new JPanel();
        frm.setSize(400,360);
        frm.setResizable(false);

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        this.piece = new JPanel();
        this.piece.setBorder(new EmptyBorder(5,5,5,5));
        this.piece.add(createGrid(5,5,50,50));

        main.add(this.piece);

        createButtons();

        frm.add(main);

        displayPiece();

        frm.setAlwaysOnTop(true);
        frm.setUndecorated(true);
        frm.setSize(player_grid_panel.getSize());
        frm.setLocationRelativeTo(player_grid_panel);
        frm.setResizable(false);
        frm.setVisible(true);
        if(Options.isDarkMode()){
            Options.setDarkModeColour(frm);
        }

        //TODO: this will need to be changed later, just for testing purposes.
        System.out.println(GameEngine.checkValidForEachPlayer());
    }

    private void createButtons(){
        JButton pass, rotate, flipUp, flipRight, back;

        //Set the icons for each button
        ImageIcon flipUpIcon = new ImageIcon("./Assets/Icons/iconfinder_multimedia-26_2849810.png");
        ImageIcon flipRightIcon = new ImageIcon("./Assets/Icons/iconfinder_DoubleChevronLeftRight_1031523.png");
        ImageIcon closeIcon = new ImageIcon("./Assets/Icons/close.png");
        ImageIcon rotateIcon = new ImageIcon("./Assets/Icons/rotate.png");
        ImageIcon passIcon = new ImageIcon("./Assets/Icons/pass.png");

        //Set the actionListeners for each button
        rotate = new JButton(rotateIcon);
        rotate.addActionListener(ev -> {
            Piece.setActionList(rotateCounterClock(Piece.getActionsList(GameEngine.getSelectedPiece())));
            displayPiece();
        });

        pass = new JButton(passIcon);
        pass.addActionListener(ev -> {
            GameEngine.updateCurrentTurn();
            frm.dispose();
        });

        flipUp = new JButton(flipUpIcon);
        flipUp.addActionListener(ev -> {
            Piece.setActionList(flipUp(Piece.getActionsList(GameEngine.getSelectedPiece())));
            displayPiece();
        });

        flipRight = new JButton(flipRightIcon);
        flipRight.addActionListener(ev -> {
            Piece.setActionList(flipRight(Piece.getActionsList(GameEngine.getSelectedPiece())));
            displayPiece();
        });

        back = new JButton(closeIcon);
        back.addActionListener(ev -> {
            GameEngine.setSelectedPiece(null);
            frm.dispose();
        });

        JPanel buttons = new JPanel();
        buttons.add(pass);
        buttons.add(rotate);
        buttons.add(flipUp);
        buttons.add(flipRight);
        buttons.add(back);

        main.add(buttons);
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
            for (JButton[] jButtons : selectedButtonGrid) {
                for (JButton jButton : jButtons) {
                    jButton.setBackground(Color.white);
                    jButton.setIcon(null);
                    jButton.setDisabledIcon(null);
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
                    ImageIcon icon = Player.getPlayerIcon(this.playerIndex);
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
        actionsList.forEach(action -> toReturn.add(new int[]{action[0], action[1] * -1}));
        return toReturn;
    }

    private static ArrayList<int[]> flipRight(ArrayList<int[]> actionList){
        ArrayList<int[]> toReturn = new ArrayList<>();
        actionList.forEach(action -> toReturn.add(new int[]{action[0] * -1, action[1]}));
        return toReturn;
    }
}


