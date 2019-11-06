import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Options {
    private static Boolean isColorblind = null;
    private static String difficulty = null;
    private static String scoringType = null;
    private static HashMap<Integer,Color> mapOfColors = null;
    private static Integer numberOfPlayers = null;
    private static Integer numberOfAI= null;
    private static Boolean hasAlternatePlayer = false;
    private static ArrayList<Integer> AI_indexList = new ArrayList<>();

    public Options(Boolean isColorblind, String difficulty, String scoringType, HashMap<Integer, Color> mapOfColors, Integer number_of_players, Integer number_of_computer){
        this.setColorblind(isColorblind);
        this.setDifficulty(difficulty);
        this.setScoringType(scoringType);
        this.setMap(mapOfColors);
        this.setNumberOfPlayers(number_of_players);
        this.setNumberOfAI(number_of_computer);
        if (numberOfPlayers==3){
            hasAlternatePlayer = true;
        }
    }

    private void setNumberOfAI(Integer number_of_computer){
        numberOfAI = number_of_computer;
    }

    public static int getNumberOfAI(){return numberOfAI;}

    private void setColorblind(Boolean isColorblind){
        this.isColorblind = isColorblind;
    }

    private void setDifficulty(String difficulty){
        this.difficulty = difficulty;
    }

    private void setScoringType(String scoringType){
        this.scoringType = scoringType;
    }

    private void setMap(HashMap<Integer,Color> mapOfColors){
        this.mapOfColors = mapOfColors;
    }


    private void setNumberOfPlayers(Integer number_of_players){ numberOfPlayers = number_of_players;}

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

    public static void setOptions(){
        setAIPlayer();
        setPlayerNames();
        setAlternatePlayer();
    }

    public static boolean hasAlternatePlayer(){
        return hasAlternatePlayer;
    }
}
