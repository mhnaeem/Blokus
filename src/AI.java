import com.sun.security.auth.NTDomainPrincipal;

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
        HashMap<Integer,ArrayList<String[]>> possibleMoves = new HashMap<>();
        Player.getPlayer(currentTurn).getAvailablePieces().forEach(piece-> {
            GameEngine.setSelectedPiece(piece);
            possibleMoves.put(piece,GameEngine.getPossibleMoves(piece,currentTurn));
        });
        possibleMoves.entrySet().forEach(entry->{
            System.out.println(entry.getKey() + " " + entry.getValue());
        });
        int longestPiece = 0;
        for (int piece:longestPieceList){
            if (possibleMoves.containsKey(piece)){
                longestPiece = piece;
                break;
            }
        }
        //index = 0;
        String [] move = possibleMoves.get(longestPiece).get(0);
        String selectedPoint = move[0];
        int rotation = Integer.parseInt(move[1]);
        int flipRight = Integer.parseInt(move[2]);
        int flipUp = Integer.parseInt(move[3]);
        Color color = Player.getPlayer(currentTurn).getColor();
        for (int i=0;i<rotation;i++){
            SelectedPiece.rotateCounterClock(Piece.getActionsList(longestPiece,currentTurn));
        }
        for (int i=0;i<flipRight;i++){
            SelectedPiece.flipRight(Piece.getActionsList(longestPiece,currentTurn));
        }
        for (int i=0;i<flipUp;i++){
            SelectedPiece.flipUp(Piece.getActionsList(longestPiece,currentTurn));
        }
        //TODO MAKE MOVE WITH THESE
        GameEngine.setSelectedPiece(longestPiece);
        Player.getPlayer(currentTurn).pieceUsed(longestPiece);
        PlayerGrid.removePieceEvent(longestPiece);
        MainGrid.AIPlacingPiece(currentTurn,longestPiece,selectedPoint);
        Options.firstTurnCornerMoveEvent();
        GameEngine.updateCurrentTurn();
        //possible moves key is piece number, value is ArrayList Of String[]
        //String[] - [selectedPoint_On_Main_Grid,rotations,flipRights,flipUps]
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
