import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class OptionsTest {

    @Test
    void getIsColorblind() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);
        GameGUI.previousFrame.dispose();
        assertEquals(true, Options.getIsColorblind());
    }

    @Test
    void getNumberOfAI() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);
        GameGUI.previousFrame.dispose();
        assertEquals(3, Options.getNumberOfAI());
    }

    @Test
    void getDifficulty() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);
        GameGUI.previousFrame.dispose();
        assertEquals("Medium", Options.getDifficulty());
    }

    @Test
    void getScoringType() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);
        GameGUI.previousFrame.dispose();
        assertEquals("Basic", Options.getScoringType());
    }

    @Test
    void getColor() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);
        GameGUI.previousFrame.dispose();
        assertEquals(Color.RED, Options.getColor(1));
        assertEquals(Color.BLUE, Options.getColor(2));
        assertEquals(Color.YELLOW, Options.getColor(3));
        assertEquals(Color.GREEN, Options.getColor(4));
    }

    @Test
    void getNumberOfPlayers() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);
        GameGUI.previousFrame.dispose();
        assertEquals(4, Options.getNumberOfPlayers());
    }


    @Test
    void getTurnOrderAccordingToColors() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);
        //assertEquals(2, Options.getTurnOrderAccordingToColors(2));
    }

    @Test
    void getIsFirstTurnMap() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1,Color.RED);
        map.put(2,Color.BLUE);
        map.put(3,Color.YELLOW);
        map.put(4,Color.GREEN);
        Options.setOptions(true,"Medium","Basic",map,4,3);

    }

    @Test
    void getAI_player_index_List() {
    }

    @Test
    void isDarkMode() {
    }

    @Test
    void switchDarkMode() {
    }

}