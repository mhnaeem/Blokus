import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

abstract class Options {
    private static Boolean isColorblind = null;
    private static String difficulty = null;
    private static String scoringType = null;
    private static HashMap<Integer,Color> mapOfColors = null;
    private static Integer numberOfPlayers = null;
    private static Integer numberOfAI= null;
    private static Boolean hasAlternatePlayer = false;
    private static ArrayList<Integer> AI_player_index_List = new ArrayList<>();
    private static int[] turn_order_according_to_color;
    private static HashMap<Integer, int[]> isFirstTurnMap = new HashMap<>();
    private static boolean darkMode = false;
    private static GameGUI gameGUIObject;
    private static MainGrid mainGridObject;

    public static void setOptions(Boolean isColorblind, String difficulty, String scoringType, HashMap<Integer, Color> mapOfColors, Integer number_of_players, Integer number_of_computer){
        AI_player_index_List = new ArrayList<>();
        setColorblind(isColorblind);
        setDifficulty(difficulty);
        setScoringType(scoringType);
        setMap(mapOfColors);
        setNumberOfPlayers(number_of_players);
        setNumberOfAI(number_of_computer);
        new Player(1);
        new Player(2);
        new Player(3);
        new Player(4);
        calculateTurnOrderAccordingToColors();
        if (numberOfPlayers==3){
            hasAlternatePlayer = true;
        }
        else {
            hasAlternatePlayer = false;
        }
        setAIPlayerOptions();
        setPlayerNames();
        setNameOfAlternatePlayer();

        mainGridObject= new MainGrid();

        JPanel mainGridPanel = MainGrid.getMainGridPanel();
        gameGUIObject = new GameGUI(mainGridPanel);
        new GameEngine();
    }

    private static void setNumberOfAI(Integer number_of_computer){
        numberOfAI = number_of_computer;
    }

    public static int getNumberOfAI(){return numberOfAI;}

    private static void setColorblind(Boolean is_Color_blind){
        isColorblind = is_Color_blind;
    }

    private static void setDifficulty(String difficultyLevel){
        difficulty = difficultyLevel;
    }

    private static void setScoringType(String scoring_type){
        scoringType = scoring_type;
    }

    private static void setMap(HashMap<Integer,Color> map_Of_Colors){
        mapOfColors = map_Of_Colors;
    }

    private static void setNumberOfPlayers(Integer number_of_players){ numberOfPlayers = number_of_players;}

    public static Boolean getIsColorblind(){
        return isColorblind;
    }

    public static String getDifficulty(){
        return difficulty;
    }

    public static String getScoringType(){
        return scoringType;
    }

    public static Color getColor(int key){
        return mapOfColors.get(key);
    }

    /**
     * returns the number of players in the game
     * @return int number_of players
     */
    public static Integer getNumberOfPlayers() {
        return 4;
        //return numberOfPlayers;
    }

    /**
     * this function sets the name of the alternate player from player_number to alternate player
     * if there is an alternate player in the game
     */
    private static void setNameOfAlternatePlayer(){
        if (hasAlternatePlayer()){
            Player.getPlayer(4).setName("Alternate");
        }
    }

    /**
     * this function sets the player names on top of their selected colors
     * by default players are name 1,2,3,4 regardless of the amount of players playing
     */
    private static void setPlayerNames(){
        if (numberOfPlayers==2 && numberOfAI==0){
            Player.getPlayer(3).setName("Player 1");
            Player.getPlayer(4).setName("Player 2");
        }
        else if (numberOfPlayers==2 && numberOfAI==1) {
            Player.getPlayer(2).setName("AI Player");
            Player.getPlayer(3).setName("Player 1");
            Player.getPlayer(4).setName("AI Player");
        }
    }

    /**
     * this function sets the name of AI players
     * and also has an AI_indexList which has the player index of the AI players
     */
    private static void setAIPlayerOptions(){
        AI_player_index_List.clear();
        if (Options.hasAlternatePlayer()){
            if (numberOfAI==2){
                Player.getPlayer(2).setName("AI Player 1");
                AI_player_index_List.add(2);
                Player.getPlayer(3).setName("AI Player 2");
                AI_player_index_List.add(3);
            }
            else if (numberOfAI==1){
                AI_player_index_List.add(3);
                Player.getPlayer(3).setName("AI Player");
            }
        }
        else if (numberOfPlayers==4) {
            if (numberOfAI == 3) {
                Player.getPlayer(2).setName("AI Player 1");
                AI_player_index_List.add(2);
                Player.getPlayer(3).setName("AI Player 2");
                AI_player_index_List.add(3);
                Player.getPlayer(4).setName("AI Player 3");
                AI_player_index_List.add(4);
            }
            else if (numberOfAI == 2) {
                Player.getPlayer(3).setName("AI Player 1");
                AI_player_index_List.add(3);
                Player.getPlayer(4).setName("AI Player 2");
                AI_player_index_List.add(4);
            } else if (numberOfAI == 1) {
                Player.getPlayer(4).setName("AI Player");
                AI_player_index_List.add(4);
            }
        }
        else if (numberOfPlayers==2){
            if (numberOfAI==1){
                Player.getPlayer(2).setName("AI Player");
                AI_player_index_List.add(2);
                Player.getPlayer(4).setName("AI Player");
                AI_player_index_List.add(4);
            }
        }

    }


    private static void calculateTurnOrderAccordingToColors() {
        int first = 0, second = 0, third = 0, forth = 0;
        for (int i = 1; i < 5; i++) {
            Color color = Options.getColor(i);
            if (color == Color.BLUE) {
                first = i;
            } else if (color == Color.YELLOW) {
                second = i;
            } else if (color == Color.RED) {
                third = i;
            } else if (color == Color.GREEN) {
                forth = i;
            }
        }
        isFirstTurnMap.put(first, (new int[]{0, 19}));
        isFirstTurnMap.put(second, (new int[]{19, 19}));
        isFirstTurnMap.put(third, (new int[]{19, 0}));
        isFirstTurnMap.put(forth, (new int[]{0, 0}));
        turn_order_according_to_color =  (new int[]{first, second, third, forth});
    }

    public static HashMap<Integer,int[]> getIsFirstTurnMap(){
        return isFirstTurnMap;
    }


    public static int getTurnOrderAccordingToColors(int index){
        return turn_order_according_to_color[index];
    }

    public static void firstTurnCornerMoveEvent() {
        isFirstTurnMap.remove(GameEngine.getCurrentTurn());
    }

    public static void clearIsFirstTurnMap(int current_turn){
        isFirstTurnMap.remove(current_turn);
    }

    public static ArrayList<Integer> getAI_player_index_List(){
        return AI_player_index_List;
    }

    public static boolean hasAlternatePlayer(){
        return hasAlternatePlayer;
    }

    public static boolean isDarkMode(){
        return darkMode;
    }

    public static void switchDarkMode(){
        darkMode = !darkMode;
    }

    public static void setDarkModeColour(Container parent)
    {
        for(Component c : parent.getComponents())
        {
            if(c instanceof Container)
            {

                if(c instanceof JPanel)
                {
                    c.setBackground(new Color(48,48,48));
                }
                if(c instanceof JLabel)
                {
                    c.setForeground(new Color(255,255,255));
                }
                setDarkModeColour((Container)c);
            }
        }
    }
}
