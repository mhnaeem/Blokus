import javax.imageio.ImageWriter;
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

    private JButton pass, rotate, flip, back;
    private JPanel piece, buttons, main;
    private static JFrame frm = new JFrame("Selected Piece Window");;
    private String btn;
    private int index;
    private JButton[][] selectedButtonGrid;
    private String playerName;
    private boolean colourBlind;

    SelectedPiece(int index,String btnName, Component c){
        this.index = index;
        btn = btnName;

        main = new JPanel();
        frm.setSize(400,360);
        frm.setResizable(false);

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));


        this.piece = new JPanel();
        this.piece.setBorder(new EmptyBorder(5,5,5,5));
        this.piece.add(createGrid(5,5,50,50));


        main.add(this.piece);

        this.pass = new JButton("Pass");
        this.pass.addActionListener(ev -> System.out.println("Pass button was pressed"));
        this.rotate = new JButton("Rotate");
        this.rotate.addActionListener(ev -> System.out.println("Rotate button was pressed"));
        this.flip = new JButton("Flip");
        this.flip.addActionListener(ev -> System.out.println("Flip button was pressed"));
        this.back = new JButton("Back");
        this.back.addActionListener(ev -> {PiecesMonitor.setSelectedPiece(index,-1);frm.dispose();});

        buttons = new JPanel();

        buttons.add(this.pass);
        buttons.add(this.rotate);
        buttons.add(this.flip);
        buttons.add(this.back);

        main.add(buttons);
        frm.add(main);
        System.out.println(btn);
        displayPiece(Options.getColor(index),btn);

        frm.setAlwaysOnTop(true);
        frm.setUndecorated(true);
        frm.setSize(c.getSize());
        frm.setLocationRelativeTo(c);
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


    private void displayPiece(Color color, String btn){
        //These x and y represent the zero coordinates, all actions will be taken from these points
        int x = 2;
        int y = 2;
        if (Piece.getPieceMap().containsKey(btn)) {
            int key = Piece.getPieceMap().get(btn);
            (Piece.getActionsListMap().get(key)).forEach(action -> {
                selectedButtonGrid[x + action[0]][y + action[1]].setBackground(color);
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
    public static void pieceHasBeenPlacedEvent(int index){
        frm.dispose();
    }
}