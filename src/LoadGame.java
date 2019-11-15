import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class LoadGame {

    String path;

    LoadGame(String filePath){
        this.path = filePath;
        System.out.println(filePath);
        ArrayList<String> lines = new ArrayList<>();


        //TODO: read comments if any in LoadScreen, SavedState, SaveGame, and LoadGame.
        // To make the thing run just save a game during the actual game play and then you should see the saved thing on the load screen. Click on the name of the game you want to load (From LoadScreen) and this class will be called. Just edit this class to load the game and change settings then we are done with load and save.

        //This thing can read the files, the line will return every line in the file, look at the output to see more.
        try {
            Files.lines(Paths.get(this.path)).forEach(line -> {
                System.out.println(line);
                lines.add(line);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        loadOptions();
        loadTurn();
        loadMainGrid();
        loadPlayerGrids();
        loadAvailablePieces();
         */
    }

    private void loadOptions(){
        //Add loading options part here
    }

    private void loadTurn(){
        //Add loading turn part here
        //Current turn, alternate turn and turn index
    }

    private void loadMainGrid(){
        //Add loading main grid part here
    }

    private void loadPlayerGrids(){
        //Add loading player grid part here
    }

    private void loadAvailablePieces(){
        //Add loading available pieces part here
    }
}
