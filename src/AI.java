import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

//TODO restructure and move code to appropriate class
abstract class AI {
    private static int currentTurn;
    private static ArrayList<Integer> longestPieceList = new ArrayList<>(Arrays.asList(0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20));//in order of longest piece

    public static void makeMove(){
        PlayerGrid.disableAllPlayerGrids();
        currentTurn = GameEngine.getCurrentTurn();
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
      ArrayList<String[]> possibleMoves = new ArrayList<>();
      ArrayList<Integer> availablePieces = Player.getPlayer(currentTurn).getAvailablePieces();
      int random_piece = availablePieces.get((int)Math.random()*availablePieces.size());
      possibleMoves = GameEngine.getPossibleAIMoves(random_piece);
      if (possibleMoves.size()==0){
          GameEngine.hasGameEndedEvent();
          GameEngine.updateCurrentTurn();
      }
      else {
          int index = (int) (Math.random() * possibleMoves.size());
          String[] move = possibleMoves.get(index);
          String[] selectedPoint = move[0].split(",");
          int r = Integer.parseInt(selectedPoint[0]);
          int c = Integer.parseInt(selectedPoint[1]);
          int rotation = Integer.parseInt(move[1]);
          int flipRight = Integer.parseInt(move[2]);
          int flipUp = Integer.parseInt(move[3]);
          GameEngine.setSelectedPiece(random_piece);
          Piece.resetActionList();
          for (int i = 1; i <= rotation; i++) {
              //System.out.println("rotation");
              Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(random_piece)));
          }
          for (int i = 1; i <= flipRight; i++) {
              //System.out.println("flipright");
              Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(random_piece)));
          }
          for (int i = 1; i <= flipUp; i++) {
              //System.out.println("flipup");
              Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(random_piece)));
          }
          MainGrid.getMainGridPanel().updateUI();
          MainGrid.getMainGridButtons()[r][c].doClick();
          MainGrid.getMainGridPanel().updateUI();
      }
    }

    private static void mediumMove(){
        HashMap<Integer,ArrayList<String[]>> possibleMoves = new HashMap<>();
        Player.getPlayer(currentTurn).getAvailablePieces().forEach(piece-> {
            GameEngine.setSelectedPiece(piece);
            possibleMoves.put(piece,GameEngine.getPossibleAIMoves(piece));
        });
        int longestPiece = 0;
        for (int piece:longestPieceList){
            if (possibleMoves.containsKey(piece)){
                longestPiece = piece;
                //System.out.println("longestPiece"+longestPiece);
                break;
            }
        }
        int index = (int) (Math.random()*possibleMoves.get(longestPiece).size());
        String [] move = possibleMoves.get(longestPiece).get(index);
        String[] selectedPoint = move[0].split(",");
        int r = Integer.parseInt(selectedPoint[0]);
        int c = Integer.parseInt(selectedPoint[1]);
        int rotation = Integer.parseInt(move[1]);
        int flipRight = Integer.parseInt(move[2]);
        int flipUp = Integer.parseInt(move[3]);
        GameEngine.setSelectedPiece(longestPiece);
        Piece.resetActionList();
        for (int i=1;i<=rotation;i++){
            //System.out.println("rotation");
            Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(longestPiece)));
        }
        for (int i=1;i<=flipRight;i++){
            //System.out.println("flipright");
            Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(longestPiece)));
        }
        for (int i=1;i<=flipUp;i++){
            //System.out.println("flipup");
            Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(longestPiece)));
        }
        MainGrid.getMainGridPanel().updateUI();
        MainGrid.getMainGridButtons()[r][c].doClick();
        MainGrid.getMainGridPanel().updateUI();
    }
    private static void hardMove(){ }
}
