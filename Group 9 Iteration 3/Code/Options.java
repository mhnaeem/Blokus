import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class Options {
    private static Boolean isColorblind = null;
    private static String difficulty = null;
    private static String scoringType = null;
    private static HashMap<Integer,Color> mapOfColors = null;
    private static Integer numberOfPlayers = null;
    private static Integer numberOfAI= null;
    private static Boolean hasAlternatePlayer = false;
    private static ArrayList<Integer> AI_indexList = new ArrayList<>();

    public static void setOptions(Boolean isColorblind, String difficulty, String scoringType, HashMap<Integer, Color> mapOfColors, Integer number_of_players, Integer number_of_computer){
        setColorblind(isColorblind);
        setDifficulty(difficulty);
        setScoringType(scoringType);
        setMap(mapOfColors);
        setNumberOfPlayers(number_of_players);
        setNumberOfAI(number_of_computer);
//        new Player(1);
//        new Player(2);
//        new Player(3);
//        new Player(4);
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

    public static ArrayList<Integer> getAI_indexList(){
        return AI_indexList;
    }

    public static boolean hasAlternatePlayer(){
        return hasAlternatePlayer;
    }
}
