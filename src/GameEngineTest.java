import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @BeforeEach
    void main() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1, Color.RED);
        map.put(2, Color.BLUE);
        map.put(3, Color.YELLOW);
        map.put(4, Color.GREEN);
        Options.setOptions(true, "Medium", "Basic", map, 4, 0);
        GameGUI.previousFrame.dispose();
    }

    @Test
    void isLegal() {
        GameEngine.setSelectedPiece(17);
        assertTrue(GameEngine.isLegal("0,19"));
        GameEngine.setSelectedPiece(8);
        assertFalse(GameEngine.isLegal("0,19"));
        GameEngine.setSelectedPiece(12);
        assertFalse(GameEngine.isLegal("0,19"));
    }

    @Test
    void getCurrentTurn() {
        assertEquals(2, GameEngine.getCurrentTurn());
    }

    @Test
    void getSelectedPiece() {
        assertNull(GameEngine.getSelectedPiece());
    }

    @Test
    void getTurnIndex() {
        assertEquals(0, GameEngine.getTurnIndex());
    }

    @Test
    void getAlternateTurn() {
        assertEquals(1, GameEngine.getAlternateTurn());
    }

    @Test
    void isAITurn() {
        assertFalse(GameEngine.isAITurn(1));
    }

    @Test
    void getEasyPlayableMap() {
        HashMap<Integer, Integer> testMap = new HashMap<Integer, Integer>();
        testMap.put(1,17);
        assertFalse(GameEngine.getEasyPlayableMap().isEmpty());
    }

    @Test
    void isWithinGrid() {
        assertEquals(true, GameEngine.isWithinGrid("0,0", new int[]{2,5},MainGrid.getMainGridButtons()));
    }
}