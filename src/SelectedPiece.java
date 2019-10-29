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

public class SelectedPiece{

    private JButton pass, rotate, flip;
    private JPanel piece, buttons, main;
    private JFrame frm;
    private Piece selectedPiece;
    private JButton[][] selectedButtonGrid;

    SelectedPiece(GameGUI gui, Color color, Piece piece, JButton[][] buttonGrid){

        this.selectedPiece = piece;
        this.selectedButtonGrid = buttonGrid;

        main = new JPanel();
        frm = new JFrame("Selected Piece Window");
        frm.setSize(400,360);
        frm.setResizable(false);

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

        buttons = new JPanel();

        buttons.add(this.pass);
        buttons.add(this.rotate);
        buttons.add(this.flip);

        main.add(buttons);
        frm.add(main);

        displayPiece(color, this.selectedPiece);

        frm.setVisible(true);
    }

    public JPanel returnPanel(){
        return main;
    }

    private void displayPiece(Color color, Piece piece){
        //These x and y represent the zero coordinates, all actions will be taken from these points
        int x = 2;
        int y = 2;


        piece.getPieceActions().forEach(action -> {
            selectedButtonGrid[x+action[0]][y+action[1]].setBackground(color);
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
