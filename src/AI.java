import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

//TODO restructure and move code to appropriate class
abstract class AI {
    private static int currentTurn;
    private static ArrayList<Integer> longestPieceList = new ArrayList<>(Arrays.asList(18,10,4,12,5,7,9,6,1,2,11,13,0,3,8,16,14,15,19,20,17));//in order of longest piece

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

    private static void splitMoveAndRotateAndFlipAndPlace(String[] move, int piece_index){
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
        Random rand = new Random();
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
        if (longestPiece==-1 ||possibleMoves.get(longestPiece).size()==0){
            GameEngine.updateCurrentTurn();
        }
        else {
        int index = rand.nextInt(possibleMoves.get(longestPiece).size());
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
