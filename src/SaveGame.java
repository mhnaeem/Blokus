import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SaveGame {

    public static void createSaveFile(String filename){
        List<String> lines = Arrays.asList("The first line", saveMainGrid());
        Path file = Paths.get("./SavedGames/"+filename);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String saveMainGrid(){
        JButton[][] buttonsToSave = MainGrid.getMainGridButtons();

        String toReturn = "main_grid:[";

        for (int r = 0; r < buttonsToSave.length; r++) {
            for (int c = 0; c < buttonsToSave[r].length; c++) {
                JButton currButton = buttonsToSave[r][c];
                toReturn += "[" + "(" + currButton.getName() + ")" + ";" + currButton.isEnabled() + ";" + colourToString(currButton.getBackground()) + "]";
            }
        }

        toReturn += "]";

        return toReturn;
    }

    private static String colourToString(Color colour){
        if(colour == Color.BLUE){
            return "blue";
        }
        if(colour == Color.red){
            return "red";
        }
        if(colour == Color.green){
            return "green";
        }
        if(colour == Color.yellow){
            return "yellow";
        }
        return "white";
    }

    public static void main(String[] args) {
        createSaveFile("hello.txt");
    }
}
