import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    @BeforeEach
    void main() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1, Color.RED);
        map.put(2, Color.BLUE);
        map.put(3, Color.YELLOW);
        map.put(4, Color.GREEN);
        Options.setOptions(true, "Medium", "Basic", map, 4, 3);
        GameGUI.previousFrame.dispose();
    }


    @Test
    void getPlayerIndex() {
        assertEquals(1, Player.getPlayer(1).getPlayerIndex());
    }

    @Test
    void getPlayerName() {
        assertEquals("Player 1", Player.getPlayer(1).getPlayerName());
    }

    @Test
    void getColor() {
        assertEquals(Color.RED, Player.getPlayer(1).getColor());
    }

    @Test
    void getAvailablePieces() {
        assertEquals(21, Player.getPlayer(1).getAvailablePieces().size());
        assertEquals(20, Player.getPlayer(2).getAvailablePieces().size());
    }

    @Test
    void getPlayerIcon() {
        assertEquals("./Assets/Shapes/iconfinder_star_216411.png", Player.getPlayer(1).getPlayerIcon(1).getDescription());
    }
}
