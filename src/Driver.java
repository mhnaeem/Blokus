import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Driver {

    public Player Player1,Player2,Player3,Player4;

    public Driver(HashMap<Integer,Color> map, int count, int computer, int human,String difficulty, String scoring, Boolean isColorblind)
    {
        new Options(isColorblind,difficulty,scoring,map);
        Player1 = new Player(1);
        Player2 = new Player(2);
        Player3 = new Player(3);
        Player4 = new Player(4);
        JPanel mainGridPanel = new MainGrid().getMainGridPanel();
        new Piece();
        new PiecesMonitor();
        new GameEngine();
        new GameGUI(Player1.createGrid(),Player2.createGrid(),Player3.createGrid(),Player4.createGrid(),mainGridPanel);
    }
}
