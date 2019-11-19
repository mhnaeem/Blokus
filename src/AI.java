//import com.sun.security.auth.NTDomainPrincipal;
//
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//
////TODO restructure and move code to appropriate class
//abstract class AI {
//    private static int currentTurn = -1;
//    private static ArrayList<Integer> longestPieceList = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));//in order of longest piece
//
//    public static void makeMove(int current_turn){
//        currentTurn = current_turn;
//        switch (Options.getDifficulty()){
//            case "Easy":
//                AI.easyMove();
//                break;
//            case "Medium":
//                AI.mediumMove();
//            case "Hard":
//                AI.hardMove();
//        }
//    }
//
//    private static void easyMove(){
//        HashMap<Integer,ArrayList<String[]>> possibleMoves = new HashMap<>();
//        Player.getPlayer(currentTurn).getAvailablePieces().forEach(piece-> {
//            GameEngine.setSelectedPiece(piece);
//            possibleMoves.put(piece,GameEngine.getPossibleMoves(piece,currentTurn));
//        });
//        possibleMoves.entrySet().forEach(entry->{
//                System.out.println("Player "+currentTurn+entry.getKey() + " " );
//                entry.getValue().forEach(v->{
//                    System.out.print("selectedPoint "+v[0]);
//                    System.out.print("rotation "+v[1]);
//                    System.out.print("flipUp "+v[2]);
//                    System.out.println("flipRight "+v[3]);
//                });
//        });
//        int longestPiece = 0;
//        for (int piece:longestPieceList){
//            if (possibleMoves.containsKey(piece)){
//                longestPiece = piece;
//                //System.out.println("longestPiece"+longestPiece);
//                break;
//            }
//        }
//        int index = (int) (Math.random()*possibleMoves.get(longestPiece).size());
//        String [] move = possibleMoves.get(longestPiece).get(index);
//        String selectedPoint = move[0];
//        int rotation = Integer.parseInt(move[1]);
//        int flipUp = Integer.parseInt(move[2]);
//        int flipRight = Integer.parseInt(move[3]);
//        Color color = Player.getPlayer(currentTurn).getColor();
//        GameEngine.setSelectedPiece(longestPiece);
//        Piece.resetActionList();
//        for (int i=1;i<=rotation;i++){
//            //System.out.println("rotation");
//            Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(longestPiece)));
//        }
//        for (int i=1;i<=flipRight;i++){
//            //System.out.println("flipright");
//            Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(longestPiece)));
//        }
//        for (int i=1;i<=flipUp;i++){
//            //System.out.println("flipup");
//            Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(longestPiece)));
//        }
//        String[] b = selectedPoint.split(",");
//        int brow = Integer.parseInt(b[0]);
//        int bcol = Integer.parseInt(b[1]);
//        //System.out.println(brow+""+bcol);
//        //System.out.println("Selected piece"+GameEngine.getSelectedPiece()+"br,bcol"+brow+","+bcol);
//        Piece.getActionsList(longestPiece).forEach(actions -> {
//            //System.out.println("row"+(brow+actions[1])+",col"+(bcol+actions[0]));
//        });
//        //TODO MAKE MOVE WITH THESE
//        //GameEngine.updateCurrentTurn();
//    }
//
//    private static void mediumMove(){
//        HashMap<Integer,ArrayList<String[]>> possibleMoves = new HashMap<>();
//        Player.getPlayer(currentTurn).getAvailablePieces().forEach(piece-> {
//            possibleMoves.put(piece,GameEngine.getPossibleMoves(piece,currentTurn));
//        });
//        HashMap<Integer,ArrayList<String[]>> mediumMoves = GameEngine.moveThatBlockOtherPlayerEdges(Player.getPlayer(currentTurn).getAvailablePieces(),possibleMoves,currentTurn);
//        //medium moves is empty if no possibleMoves can be placed to block another player Edge
//
//    }
//
//    private static void hardMove(){
//    }
//
//
//}
