import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//TODO restructure and move code to appropriate class
abstract class AI {
    private static int currentTurn = -1;
    private static ArrayList<Integer> longestPieceList = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));//in order of longest piece

    public static void makeMove(int current_turn){
        currentTurn = current_turn;
        switch (Options.getDifficulty()){
            case "Easy":
                AI.easyMove();
                break;
            case "Medium":
                AI.mediumMove();
            case "Hard":
                AI.hardMove();
        }
    }

    private static void easyMove(){
    }

    private static void mediumMove(){
    }

    private static void hardMove(){
    }


}
