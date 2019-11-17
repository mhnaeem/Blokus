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
    private static ArrayList<Integer> AI_indexList = new ArrayList<>();
    private static int[] turnOrder;
    private static HashMap<Integer, int[]> firstTurnMap = new HashMap<>();
    private static boolean darkMode = false;

    public static void setOptions(Boolean isColorblind, String difficulty, String scoringType, HashMap<Integer, Color> mapOfColors, Integer number_of_players, Integer number_of_computer){
        AI_indexList = new ArrayList<>();
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
        if (numberOfPlayers==3){
            hasAlternatePlayer = true;
        }
        setAIPlayer();
        setPlayerNames();
        setAlternatePlayer();
        calculateTurnOrder();



        JPanel mainGridPanel = new MainGrid().getMainGridPanel();
        new GameGUI(mainGridPanel);
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

    public static Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    private static void setAlternatePlayer(){
        if (hasAlternatePlayer()){
            Player.getPlayer(4).setName("Alternate");
        }
    }

    private static void setPlayerNames(){
        if (numberOfPlayers==2){
            Player.getPlayer(3).setName("Player 1");
            Player.getPlayer(4).setName("Player 2");
        }
    }

    private static void setAIPlayer(){
        AI_indexList.clear();
        if (Options.hasAlternatePlayer()){
            if (numberOfAI==2){
                Player.getPlayer(2).setName("AI Player 1");
                AI_indexList.add(2);
                Player.getPlayer(3).setName("AI Player 2");
                AI_indexList.add(3);
            }
            else if (numberOfAI==1){
                AI_indexList.add(3);
                Player.getPlayer(3).setName("AI Player");
            }
        }
        else{
            if (numberOfAI==3){
                Player.getPlayer(1).setName("AI Player 1");
                AI_indexList.add(1);
                Player.getPlayer(2).setName("AI Player 2");
                AI_indexList.add(2);
                Player.getPlayer(3).setName("AI Player 3");
                AI_indexList.add(3);
            }
            if (numberOfAI==2){
                Player.getPlayer(3).setName("AI Player 1");
                AI_indexList.add(3);
                Player.getPlayer(4).setName("AI Player 2");
                AI_indexList.add(4);
            }
            else if (numberOfAI==1){
                Player.getPlayer(4).setName("AI Player");
                AI_indexList.add(4);
            }
        }

    }

    private static void calculateTurnOrder() {
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
        firstTurnMap.put(first, (new int[]{0, 19}));
        firstTurnMap.put(second, (new int[]{19, 19}));
        firstTurnMap.put(third, (new int[]{19, 0}));
        firstTurnMap.put(forth, (new int[]{0, 0}));
        turnOrder =  (new int[]{first, second, third, forth});
    }

    public static HashMap<Integer,int[]> getFirstTurnMap(){
        return firstTurnMap;
    }


    public static int getTurnOrder(int index){
        return turnOrder[index];
    }

    public static void cornerMoveEvent() {
        firstTurnMap.remove(GameEngine.getCurrentTurn());
    }

    public static void clearFirstTurnMap(int current_turn){
        firstTurnMap.remove(current_turn);
    }

    public static ArrayList<Integer> getAI_indexList(){
        return AI_indexList;
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
