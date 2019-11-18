import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//TODO restructure and move code to appropriate class
abstract class AI {
    private static int currentTurn = -1;
    private static ArrayList<Integer> longestPieceList = new ArrayList<>(Arrays.asList(2,3));//in order of longest piece

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
        HashMap<Integer,ArrayList<String[]>> possibleMoves = new HashMap<>();
        Player.getPlayer(currentTurn).getAvailablePieces().forEach(piece-> {
            possibleMoves.put(piece,GameEngine.getPossibleMoves(piece,currentTurn));
        });
        //possible moves key is piece number, value is ArrayList Of String[]
        //String[] - [selectedPoint_On_Main_Grid,rotations,flipRights,flipLefts]
        //TODO please do a function that allows the AI to place a piece, function in MainGrid that takes the selected Piece and selectedPoint as parameters
    }

    private static void mediumMove(){
        HashMap<Integer,ArrayList<String[]>> possibleMoves = new HashMap<>();
        Player.getPlayer(currentTurn).getAvailablePieces().forEach(piece-> {
            possibleMoves.put(piece,GameEngine.getPossibleMoves(piece,currentTurn));
        });
        HashMap<Integer,ArrayList<String[]>> mediumMoves = GameEngine.moveThatBlockOtherPlayerEdges(Player.getPlayer(currentTurn).getAvailablePieces(),possibleMoves,currentTurn);
        //medium moves is empty if no possibleMoves can be placed to block another player Edge

    }

    private static void hardMove(){
    }


}
