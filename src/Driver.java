import java.awt.*;
import java.util.HashMap;

public class Driver {
    public static void main(String[] args) {
        new MainScreen();

        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1, Color.blue);
        map.put(2, Color.green);
        map.put(3, Color.red);
        map.put(4, Color.yellow);

        new GameGUI(2,map);
    }
}
