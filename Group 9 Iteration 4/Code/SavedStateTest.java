import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class SavedStateTest {

    ArrayList<SavedState> testState = SavedState.getSavedStates();

    @BeforeEach
    void main() {
        HashMap<Integer, Color> map = new HashMap<>();
        map.put(1, Color.RED);
        map.put(2, Color.BLUE);
        map.put(3, Color.YELLOW);
        map.put(4, Color.GREEN);
        Options.setOptions(true, "Medium", "Basic", map, 4, 3);

        SaveGame.createSaveFile("14-Nov-2019211234test.txt");
        Path file = Paths.get("./SavedGames/" + "14-Nov-2019211234test.txt");

        GameGUI.previousFrame.dispose();
    }

    @Test
    void getName() {
        final boolean[] test = {false};
        testState.forEach(save -> {
                save.getName().equals("test");
                if(save.getName().equals("test")){
                    test[0] = true;
                }
        });
        assertFalse(test[0]);
    }

    @Test
    void getDate() {
        final boolean[] test = {false};
        testState.forEach(save -> {
            save.getDate().equals("14-Nov-2019");
            if(save.getDate().equals("14-Nov-2019")){
                test[0] = true;
            }
        });
        assertFalse(test[0]);
    }

    @Test
    void getTime() {
        final boolean[] test = {false};
        testState.forEach(save -> {
            save.getTime().equals("211234");
            if(save.getTime().equals("211234")){
                test[0] = true;
            }
        });
        assertFalse(test[0]);
    }

    @Test
    void getPath() {
        final boolean[] test = {false};
        testState.forEach(save -> {
            save.getPath().equals("./SavedGames/14-Nov-2019211234test.txt");
            if(save.getPath().equals("./SavedGames/14-Nov-2019211234test.txt")){
                test[0] = true;
            }
        });
        assertFalse(test[0]);

    }
}