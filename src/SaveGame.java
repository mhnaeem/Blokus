import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SaveGame {

    private static void createSaveFile(String filename){
        List<String> lines = Arrays.asList("The first line", "The second line");
        Path file = Paths.get("./SavedGames/"+filename);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createSaveFile("hello.txt");
    }
}
