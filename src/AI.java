import com.sun.security.auth.NTDomainPrincipal;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//TODO restructure and move code to appropriate class
abstract class AI {
    private static int currentTurn = -1;
    private static ArrayList<Integer> longestPieceList = new ArrayList<>(Arrays.asList(1,2,4,5,6,7,9,10,11,12,13,18,0,3,8,14,16,15,19,20,17));//in order of longest piece

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
                System.out.println(entry.getKey() + " " );
                entry.getValue().forEach(v->{
                    System.out.println("selectedPoint"+v[0]);
                    System.out.println("rotation"+v[1]);
                    System.out.println("flipUp"+v[2]);
                    System.out.println("flipRight"+v[3]);
                });
        });
        int longestPiece = 0;
        for (int piece:longestPieceList){
            if (possibleMoves.containsKey(piece)){
                longestPiece = piece;
                System.out.println("longestPiece"+longestPiece);
                break;
            }
        }
        int index = (int) (Math.random()*possibleMoves.get(longestPiece).size());
        String [] move = possibleMoves.get(longestPiece).get(0);
        String selectedPoint = move[0];
        int rotation = Integer.parseInt(move[1]);
        int flipUp = Integer.parseInt(move[2]);
        int flipRight = Integer.parseInt(move[3]);
        Color color = Player.getPlayer(currentTurn).getColor();
        GameEngine.setSelectedPiece(longestPiece);
        Piece.resetActionList();
        for (int i=0;i<flipUp;i++){
            System.out.println("flipup");
            Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(longestPiece,currentTurn)),currentTurn);
        }
        for (int i=0;i<flipRight;i++){
            System.out.println("flipright");
            Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(longestPiece,currentTurn)),currentTurn);
        }
        for (int i=0;i<rotation;i++){
            System.out.println("rotation");
            Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(longestPiece,currentTurn)),currentTurn);
        }


        String[] b = selectedPoint.split(",");
        int brow = Integer.parseInt(b[0]);
        int bcol = Integer.parseInt(b[1]);
        System.out.println(brow+""+bcol);
        System.out.println("Selected piece"+GameEngine.getSelectedPiece()+"br,bcol"+brow+","+bcol);
        Piece.getActionsList(longestPiece,GameEngine.getCurrentTurn()).forEach(actions -> {
            System.out.println("row"+(brow+actions[1])+",col"+(bcol+actions[0]));
        });
        //TODO MAKE MOVE WITH THESE
        MainGrid.AI_Placing_Piece(selectedPoint);
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
