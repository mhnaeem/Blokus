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


    SelectedPiece(GameGUI gui, Color color){

        main = new JPanel();
        frm = new JFrame("Selected Piece Window");
        frm.setSize(400,360);

        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));


        this.piece = new JPanel();
        this.piece.setBorder(new EmptyBorder(5,5,5,5));
        this.piece.add(gui.createGrid(5,5,50,50));

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
        randomButton(color);
        frm.setVisible(true);
    }
    public JPanel returnPanel(){
        return main;
    }

    private void randomButton(Color color){
        ((JPanel) this.piece.getComponents()[0]).getComponents()[6].setBackground(color);
        ((JPanel) this.piece.getComponents()[0]).getComponents()[11].setBackground(color);
        ((JPanel) this.piece.getComponents()[0]).getComponents()[12].setBackground(color);
        ((JPanel) this.piece.getComponents()[0]).getComponents()[17].setBackground(color);
        ((JPanel) this.piece.getComponents()[0]).getComponents()[18].setBackground(color);
    }
}
