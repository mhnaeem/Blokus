import java.awt.*;
import java.util.HashMap;

public class Options {
    private static Boolean isColorblind =null;
    private static String difficulty = null;
    private static String scoringType = null;
    private static HashMap<Integer,Color> mapOfColors = null;

    public Options(Boolean isColorblind, String difficulty, String scoringType, HashMap<Integer, Color> mapOfColors){
        this.setColorblind(isColorblind);
        this.setDifficulty(difficulty);
        this.setScoringType(scoringType);
        this.setMap(mapOfColors);
    }

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
}
