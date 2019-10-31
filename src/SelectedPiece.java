import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Selected Piece Screen for the Blokus Game
 *
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

public class SelectedPiece extends JFrame{

    private JButton pass, rotate, flip, back;
    private JPanel piece, buttons, main;
    private Piece selectedPiece;
    private JButton[][] selectedButtonGrid;
    private String playerName;
    private GameGUI gameGUI;

    SelectedPiece(GameGUI gui, Color color, Piece piece, String player_name, Component c){

        super("Selected Piece Window");
        setSize(400,360);
        setResizable(false);

        this.selectedPiece = piece;
        this.playerName = player_name;
        this.gameGUI = gui;
        this.selectedButtonGrid = GameGUI.getSelectedPieceButtons();

        main = new JPanel();

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));


        this.piece = new JPanel();
        this.piece.setBorder(new EmptyBorder(5,5,5,5));
        this.piece.add(gui.createGrid(5,5,50,50, "selected"));

        main.add(this.piece);

        this.pass = new JButton("Pass");
        this.pass.addActionListener(ev -> System.out.println("Pass button was pressed"));
        this.rotate = new JButton("Rotate");
        this.rotate.addActionListener(ev -> System.out.println("Rotate button was pressed"));
        this.flip = new JButton("Flip");
        this.flip.addActionListener(ev -> System.out.println("Flip button was pressed"));
        this.back = new JButton("Back");
        this.back.addActionListener(ev -> SelectedPiece.this.dispose());

        buttons = new JPanel();

        buttons.add(this.pass);
        buttons.add(this.rotate);
        buttons.add(this.flip);
        buttons.add(this.back);

        main.add(buttons);
        add(main);

        displayPiece(color, this.selectedPiece);

        setAlwaysOnTop(true);
        setUndecorated(true);
        setSize(c.getSize());
        setLocationRelativeTo(c);
        setResizable(false);
        setVisible(true);
    }

    private void displayPiece(Color color, Piece piece){
        //These x and y represent the zero coordinates, all actions will be taken from these points
        int x = 2;
        int y = 2;


        piece.getPieceActions().forEach(action -> {
            selectedButtonGrid[x+action[0]][y+action[1]].setBackground(color);
            if (this.gameGUI.isColourBlind()){
                ImageIcon icon = null;
                switch (this.playerName){
                    case "Player 1":
                        icon = new ImageIcon("./Assets/Shapes/iconfinder_star_216411.png");
                        break;
                    case "Player 2":
                        icon = new ImageIcon("./Assets/Shapes/iconfinder_times_216465.png");
                        break;
                    case "Player 3":
                        icon = new ImageIcon("./Assets/Shapes/iconfinder_media-record_216317.png");
                        break;
                    case "Player 4":
                        icon = new ImageIcon("./Assets/Shapes/iconfinder_media-stop_216325.png");
                        break;
                }
                selectedButtonGrid[x+action[0]][y+action[1]].setDisabledIcon(icon);
                selectedButtonGrid[x+action[0]][y+action[1]].setIcon(icon);
            }
        });

        //Resetting the piece panel
        this.piece.removeAll();
        this.piece.revalidate();
        this.piece.repaint();
        this.piece.setLayout(new GridBagLayout());
        this.piece.setBorder(new EmptyBorder(5,5,5,5));

        GridBagConstraints gbc = new GridBagConstraints();

        for (int i = 0; i < selectedButtonGrid.length; i++) {
            gbc.gridx = i;
            for (int j = 0; j < selectedButtonGrid[i].length; j++) {
                gbc.gridy = j;
                selectedButtonGrid[i][j].setPreferredSize(new Dimension(50,50));
                this.piece.add(selectedButtonGrid[i][j],gbc);
            }
        }

        this.piece.updateUI();
        this.main.updateUI();
    }
}
