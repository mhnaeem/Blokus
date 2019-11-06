import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class Driver {

    private Player Player1,Player2,Player3,Player4;

    public Driver(HashMap<Integer,Color> map, int count, int computer, int human,String difficulty, String scoring, Boolean isColorblind)
    {
        new Options(true,"Easy","Basic",map, count,computer);
        Player1 = new Player(1);
        Player2 = new Player(2);
        Player3 = new Player(3);
        Player4 = new Player(4);
        Options.setOptions();
        JPanel mainGridPanel = new MainGrid().getMainGridPanel();
        new Piece();
        new GameGUI(mainGridPanel);
        new GameEngine();
    }


    public static void main(String[] args) {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        new Driver(map,4,0,4,"Easy","Basic",true);
    }
}
