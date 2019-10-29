import java.awt.*;
import java.util.HashMap;

public class Driver {
    public static void main(String[] args) {
        new Player("Player 1", Color.BLUE, Piece.getPieces().get(0));
        new Player("Player 2", Color.RED, Piece.getPieces().get(1));
        new Player("Player 3", Color.GREEN, Piece.getPieces().get(2));
        new Player("Player 4", Color.YELLOW, Piece.getPieces().get(3));

        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.BLUE);
        map.put(2,Color.RED);
        map.put(3,Color.GREEN);
        map.put(4,Color.YELLOW);
        new GameGUI(4, map, true, Player.getPlayers());

        //new MainScreen();
    }
}
