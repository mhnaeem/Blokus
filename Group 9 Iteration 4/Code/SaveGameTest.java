import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SaveGameTest {

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
    void createSaveFile() {
        SaveGame.createSaveFile("14-Nov-2019211234test.txt");
        Path file = Paths.get("./SavedGames/" + "14-Nov-2019211234test.txt");
        assertNotNull(file);
    }
}