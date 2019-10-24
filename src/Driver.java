import java.awt.*;
import java.util.HashMap;

public class Driver {
    public static void main(String[] args) {
        new MainScreen();

        for(int i = 0; i < 10; i++){
            new SavedState(Integer.toString(i));
        }
        new LoadScreen();

        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1, Color.blue);
        map.put(2, Color.green);
        map.put(3, Color.red);
        map.put(4, Color.yellow);

        new GameGUI(2,map, false);
    }
}
