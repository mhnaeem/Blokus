import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Driver {

    private Player Player1,Player2,Player3,Player4;

    public Driver(HashMap<Integer,Color> map, int count, int computer, int human,String difficulty, String scoring, Boolean isColorblind)
    {
        Options ONE = new Options(isColorblind,difficulty,scoring,map);
        Player1 = new Player(1);
        Player2 = new Player(2);
        Player3 = new Player(3);
        Player4 = new Player(4);
        new MainGrid();
        JPanel mainGridPanel = MainGrid.getMainGridPanel();
        new GameEngine();
        new GameGUI(Player1.createGrid(),Player2.createGrid(),Player3.createGrid(),Player4.createGrid(),mainGridPanel);
    }

    public Driver()
    {
        HashMap<Integer,Color> map = new HashMap();
        map.put(1,Color.BLUE);
        map.put(2,Color.GREEN);
        map.put(3,Color.RED);
        map.put(4,Color.YELLOW);
        Options ONE = new Options(true,"easy","String",map);
        Player1 = new Player(1);
        Player2 = new Player(2);
        Player3 = new Player(3);
        Player4 = new Player(4);
        new MainGrid();
        JPanel mainGridPanel = MainGrid.getMainGridPanel();
        new GameEngine();
        new GameGUI(Player1.createGrid(),Player2.createGrid(),Player3.createGrid(),Player4.createGrid(),mainGridPanel);
    }
    public static void main(String[] args) {
        new Driver();
    }
}
