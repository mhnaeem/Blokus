import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

class LoadGame {
    private String path;
    private static HashMap<String,Integer> color_to_index_map; //this map is used to draw shapes on objects

    LoadGame(String filePath){
        this.path = filePath;
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
        loadOptions(lines.get(0));
        loadTurn(lines.get(1));
        loadMainGrid(lines.get(2).substring(10));
        loadPlayerGrids(new String[]{lines.get(3),lines.get(4),lines.get(5),lines.get(6)});
    }

    private void loadOptions(String line) {
        String[] options = line.split("\\|");
        Boolean isColorblind = Boolean.valueOf(options[0]);
        String difficulty = options[1];
        String scoringType = options[2];
        String[] mapString = options[3].replace("{", "").replace("}", "").split(",");
        HashMap<Integer, Color> map_of_colors = new HashMap<>();
        color_to_index_map = new HashMap<>();
        for (int i = 0; i < 4; i++) {
            String[] key_value_pair = mapString[i].split(":");
            String color = key_value_pair[1];
            map_of_colors.put(i + 1, stringToColor(color));
            color_to_index_map.put(color,i+1);
        }
        int number_of_players = Integer.parseInt(options[4]);
        int number_of_computer = Integer.parseInt(options[5]);
        Options.setOptions(isColorblind, difficulty, scoringType, map_of_colors, number_of_players, number_of_computer);
    }

    private void loadTurn(String line){
        String[] turn = line.split("\\|");
        int turn_index = Integer.parseInt(turn[0]);
        int alternate_turn = Integer.parseInt(turn[1]);
        //TODO implement later if game was save during AI turn disable ALL GRIDS AND LET AI MAKE MOVE
        GameEngine.setCurrentTurn(turn_index);
        if (Options.hasAlternatePlayer()){
            GameEngine.setAlternateTurn(alternate_turn);
        }
    }

    private void loadMainGrid(String line){
        //Add loading main grid part here
        String[] mainGridState = line.replace("[","").split("]");
        JButton[][] mainGridButtons = MainGrid.getMainGridButtons();
        for (String s : mainGridState) {
            String[] button_state = s.split(";");
            String[] button_coordinate = button_state[0].replace("(", "").replace(")", "").split(",");
            int button_row = Integer.parseInt(button_coordinate[0]);
            int button_col = Integer.parseInt(button_coordinate[1]);
            boolean isEnabled = Boolean.parseBoolean(button_state[1]);
            String color = button_state[2];
            if (!isEnabled) {
                int turn = color_to_index_map.get(color);
                if (Options.getFirstTurnMap().containsKey(turn)) {
                    Options.clearFirstTurnMap(turn);
                }
                JButton btn = mainGridButtons[button_row][button_col];
                btn.setBackground(stringToColor(color));
                btn.setEnabled(false);
                if (Options.getIsColorblind()) {
                    ImageIcon icon = null;
                    switch (turn) {
                        case 1:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_star_216411.png");
                            break;
                        case 2:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_times_216465.png");
                            break;
                        case 3:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_media-record_216317.png");
                            break;
                        case 4:
                            icon = new ImageIcon("./Assets/Shapes/iconfinder_media-stop_216325.png");
                            break;
                    }
                    btn.setDisabledIcon(icon);
                    btn.setIcon(icon);
                }
            }
        }
    }

    private void loadPlayerGrids(String[] playerLineArray){
        for (int i=0;i<4;i++) {
            String[] playerState = playerLineArray[i].split(":");
            Player.getPlayer(i+1).setName(playerState[0]);
            String[] playerGridState = playerState[2].replace("[","").split("]");
            Color color = Options.getColor(i+1);
            JButton[][] playerGridButtons = PlayerGrid.getPlayerGridButtons(i+1);
            for (String s : playerGridState) {
                String[] button_state = s.split(";");
                String button_name = button_state[0].replace("(", "").replace(")", "");
                String[] button_coordinate = button_name.split(",");
                int button_row = Integer.parseInt(button_coordinate[0]);
                int button_col = Integer.parseInt(button_coordinate[1]);
                boolean isEnabled = Boolean.parseBoolean(button_state[1]);
                if (!isEnabled) {
                    assert playerGridButtons != null;
                    if (Piece.getPieceMap().containsKey(button_name)) {
                        Player.getPlayer(i + 1).pieceUsed(Piece.getPieceMap().get(button_name));
                    }
                    JButton btn = playerGridButtons[button_row][button_col];
                    btn.setBackground(Color.white);
                    btn.setIcon(null);
                    btn.setDisabledIcon(null);
                    btn.setEnabled(false);
                }
            }
        }
        GameGUI.updateLabels();
    }


    private static Color stringToColor(String colour){
        if(colour.equals("blue")){
            return Color.BLUE;
        }
        if(colour.equals("red")){
            return Color.RED;
        }
        if(colour.equals("yellow")){
            return Color.YELLOW;
        }
        if(colour.equals("green")){
            return Color.GREEN;
        }
        return Color.WHITE;
    }
}
