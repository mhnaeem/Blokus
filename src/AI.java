import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

//TODO restructure and move code to appropriate class
abstract class AI {
    private static int currentTurn;
    private static ArrayList<Integer> longestPieceList = new ArrayList<>(Arrays.asList(18,10,4,12,5,7,9,6,1,2,11,13,0,3,8,16,14,15,19,20,17));//in order of longest piece
    private static HashMap<Integer, HashMap<String, ArrayList<Integer>>> longestPieceListMap = makeLongestPieceListMap();

    public static void makeMove(){
        PlayerGrid.disableAllPlayerGrids();
        currentTurn = GameEngine.getCurrentTurn();
        switch (Options.getDifficulty()){
            case "Easy":
                AI.easyMove();
                break;
            case "Medium":
                AI.mediumMove();
                break;
            case "Hard":
                AI.hardMove();
                break;
        }
    }

    //TODO: Trying to pick a better piece
    public static HashMap<Integer,HashMap<String, ArrayList<Integer>>> makeLongestPieceListMap(){
        HashMap<Integer,HashMap<String, ArrayList<Integer>>> toReturn = new HashMap<>();

        for (int player = 1; player <= Options.getNumberOfPlayers(); player++) {
            HashMap<String, ArrayList<Integer>> toReturn1 = new HashMap<>();
            ArrayList<Integer> hardList = new ArrayList<>(Arrays.asList(18,10,12,5,9,4));
            ArrayList<Integer> mediumList = new ArrayList<>(Arrays.asList(0,8,16,13,11,1,2,3,6,7));
            ArrayList<Integer> easyList = new ArrayList<>(Arrays.asList(19,20,17,15,14));
            int finalPlayer = player;
            hardList.removeIf(piece -> {
                return !Player.getPlayer(finalPlayer).getAvailablePieces().contains(piece);
            });
            mediumList.removeIf(piece -> {
                return !Player.getPlayer(finalPlayer).getAvailablePieces().contains(piece);
            });
            easyList.removeIf(piece -> {
                return !Player.getPlayer(finalPlayer).getAvailablePieces().contains(piece);
            });
            toReturn1.put("Hard", hardList);
            toReturn1.put("Medium", mediumList);
            toReturn1.put("Easy", easyList);
            toReturn.put(player, toReturn1);
        }
        return toReturn;
    }

    //TODO: Trying to pick a better piece
    public static void setLongestPieceListMap(HashMap<Integer,HashMap<String, ArrayList<Integer>>> map){
        longestPieceListMap = map;
    }

    private static ArrayList<Integer> arrayListOfPiecesToSelect(String difficulty){
        if(!longestPieceListMap.get(currentTurn).get(difficulty).isEmpty()){
            return longestPieceListMap.get(currentTurn).get(difficulty);
        }
        System.out.println("All the pieces have been used up, arrayListOfPiecesToSelect");
        return null;
    }

    private static void splitMoveAndRotateAndFlipAndPlace(String[] move, int piece_index){
        removeFromLongestPieceListMap(piece_index);
        String[] selectedPoint = move[0].split(",");
        int r = Integer.parseInt(selectedPoint[0]);
        int c = Integer.parseInt(selectedPoint[1]);
        int rotation = Integer.parseInt(move[1]);
        int flipRight = Integer.parseInt(move[2]);
        int flipUp = Integer.parseInt(move[3]);

        GameEngine.setSelectedPiece(piece_index);
        Piece.resetActionList();
        for (int i = 1; i <= rotation; i++) {
            //System.out.println("rotation");
            Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(piece_index)));
        }
        for (int i = 1; i <= flipRight; i++) {
            //System.out.println("flipright");
            Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(piece_index)));
        }
        for (int i = 1; i <= flipUp; i++) {
            //System.out.println("flipup");
            Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(piece_index)));
        }
        MainGrid.getMainGridPanel().updateUI();
        MainGrid.getMainGridButtons()[r][c].doClick();
        MainGrid.getMainGridPanel().updateUI();
    }

    //TODO: Trying to pick a better piece
    private static void removeFromLongestPieceListMap(int piece_index){
        String difficulty = "";
        currentTurn = GameEngine.getCurrentTurn();

        if(longestPieceListMap.containsKey(currentTurn)){
            if(!longestPieceListMap.get(currentTurn).get("Hard").isEmpty()){
                difficulty = "Hard";
            }
            else if(!longestPieceListMap.get(currentTurn).get("Medium").isEmpty()){
                difficulty = "Medium";
            }
            else if(!longestPieceListMap.get(currentTurn).get("Easy").isEmpty()){
                difficulty = "Easy";
            }
            if(longestPieceListMap.get(currentTurn).containsKey(difficulty)){
                longestPieceListMap.get(currentTurn).get(difficulty).removeIf(piece -> piece == piece_index);
                System.out.println("Player: " + currentTurn + " Size: " + longestPieceListMap.get(currentTurn).get(difficulty).size() + " " + difficulty);
            }
        }
    }

    private static void easyMove(){
        Random rand = new Random();
        ArrayList<String[]> possibleMoves;
        int random_piece = GameEngine.getEasyPlayableMap().get(currentTurn);
        possibleMoves = GameEngine.getPossibleAIMoves(random_piece);
        int index = rand.nextInt(possibleMoves.size());
        String[] move = possibleMoves.get(index);
        splitMoveAndRotateAndFlipAndPlace(move, random_piece);
    }

    private static void mediumMove(){
        HashMap<Integer,ArrayList<String[]>> possibleMoves = new HashMap<>();

        Player.getPlayer(currentTurn).getAvailablePieces().forEach(piece-> {
            GameEngine.setSelectedPiece(piece);
            possibleMoves.put(piece,GameEngine.getPossibleAIMoves(piece));
        });

        int longestPiece = -1;
        for (int piece : longestPieceList) {
            if (possibleMoves.containsKey(piece)) {
                longestPiece = piece;
                //System.out.println("longestPiece"+longestPiece);
                break;
            }
        }

        //TODO: Trying to pick a better piece
//        String difficulty = "";
//        if(!longestPieceListMap.get(currentTurn).get("Hard").isEmpty()){
//            difficulty = "Hard";
//        }
//        else if(!longestPieceListMap.get(currentTurn).get("Medium").isEmpty()){
//            difficulty = "Medium";
//        }
//        else if(!longestPieceListMap.get(currentTurn).get("Easy").isEmpty()){
//            difficulty = "Easy";
//        }
//        int count = 0;
//        int maxCount = 0;
//        while (!arrayListOfPiecesToSelect(difficulty).isEmpty()){
//
//            if(maxCount == 21){
//                break;
//            }
//
//            count++;
//            maxCount++;
//
//            int randomIndex = new Random().nextInt(arrayListOfPiecesToSelect(difficulty).size());
//            int piece_index = arrayListOfPiecesToSelect(difficulty).get(randomIndex);
//
//            if (possibleMoves.containsKey(piece_index)) {
//                longestPiece = piece_index;
//                //System.out.println("longestPiece"+longestPiece);
//                break;
//            }
//            if(count == arrayListOfPiecesToSelect(difficulty).size()){
//                if(difficulty.equals("Medium")){
//                    difficulty = "Easy";
//                    count = 0;
//                }
//                else {
//                    difficulty = "Medium";
//                    count = 0;
//                }
//            }
//
//        }


        if (longestPiece == -1 || possibleMoves.get(longestPiece).size() == 0){
            GameEngine.updateCurrentTurn();
        }

        else {
            int index = new Random().nextInt(possibleMoves.get(longestPiece).size());
            String[] move = possibleMoves.get(longestPiece).get(index);
            splitMoveAndRotateAndFlipAndPlace(move, longestPiece);
        }
    }

    private static void hardMove() {
        Random rand = new Random();
        HashMap<Integer, ArrayList<String[]>> possibleMoves = new HashMap<>();
        ArrayList<Integer> availablePieces = Player.getPlayer(currentTurn).getAvailablePieces();
        for (int piece : availablePieces) {
            GameEngine.setSelectedPiece(piece);
            possibleMoves.put(piece, GameEngine.getPossibleAIMoves(piece));
        }
        HashMap<Integer, String[]> blockingMove = GameEngine.moveThatBlockOtherPlayerEdges(availablePieces, possibleMoves, currentTurn);
        if (!blockingMove.isEmpty()) {
            AtomicInteger p = new AtomicInteger();
            blockingMove.forEach((k, v) -> {
                p.set(k);
            });
            String[] move = blockingMove.get(p.get());
            splitMoveAndRotateAndFlipAndPlace(move, p.get());
        } else {
            int longestPiece = -1;
            for (int piece : longestPieceList) {
                if (possibleMoves.containsKey(piece)) {
                    longestPiece = piece;
                    //System.out.println("longestPiece"+longestPiece);
                    break;
                }
            }
            if (longestPiece == -1 || possibleMoves.get(longestPiece).size() == 0) {
                GameEngine.updateCurrentTurn();
            } else {
                int index = rand.nextInt(possibleMoves.get(longestPiece).size());
                String[] move = possibleMoves.get(longestPiece).get(index);
                splitMoveAndRotateAndFlipAndPlace(move, longestPiece);
            }
        }
    }
}
