import javax.swing.JButton;
import java.awt.Color;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SaveGame {

    public static void createSaveFile(String filename){

        List<String> lines = Arrays.asList(SaveGame.getOptions(), saveTurnSettings(), saveMainGrid(), savePlayerGrid());
        Path file = Paths.get("./SavedGames/" + filename);
        try {
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * maingrid: [(0,0);true;blue] , [(0,1);false;red],..]
     */
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

    /**
     * turn_index|alternateTurn
     */
    private static String saveTurnSettings() {
        return GameEngine.getCurrentTurn()+"|"+GameEngine.getAlternateTurn();
    }

    /**
     * isColorblind|difficulty|{1:color,2:color,key:value,player_number:value..}|number_of_players|number_of_AI
     */
    private static String getOptions(){
        String mapOfColorsString = "{1:"+colourToString(Options.getColor(1))+",2:"+colourToString(Options.getColor(2))+",3:"+colourToString(Options.getColor(3))+",4:"+colourToString(Options.getColor(4))+"}";
        return Options.getIsColorblind()+"|"+Options.getDifficulty()+"|"+Options.getScoringType()+"|"+mapOfColorsString+"|"+Options.getNumberOfPlayers()+"|"+Options.getNumberOfAI();
    }

    /**
     * PlayerNumber;isGridActive;[[(0,0)true],[(0,1)false],..]
     */


    private static String savePlayerGrid(){
        String toReturn = "";
        for (int i=1;i<5;i++){
            toReturn += savePlayerGridState(i)+"\n";
        }
        return toReturn;
    }

    private static String savePlayerGridState(int PlayerNumber){
        String toReturn = "Player" + PlayerNumber+";"+Player.getPlayer(PlayerNumber).getPlayerGrid().isActive()+";"+"[";
        JButton[][] grid = PlayerGrid.getPlayerGridButtons(PlayerNumber);
        for (int row=0;row<grid.length;row++){
            for (int col=0;col<grid[0].length;col++){
                toReturn += "[(" + row + "," + col + ");" + (grid[row][col].isEnabled()) + "],";
            }
        }
        return toReturn.substring(0,toReturn.length()-1) + "]";
    }

}
