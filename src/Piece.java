import java.util.ArrayList;

public class Piece {

    private String name;
    private ArrayList<int[]> pieceActions;

    /*
    A visual representation of how the pieces ArrayList is stored.

        [ [Piece 0, Piece 1, ...., Piece 20],                <- Pieces for player 1
          [Piece 0, Piece 1, ...., Piece 20],                <- Pieces for player 2
          [Piece 0, Piece 1, ...., Piece 20],                <- Pieces for player 3
          [Piece 0, Piece 1, ...., Piece 20] ]                <- Pieces for player 4
     */
    private static ArrayList<ArrayList<Piece>> pieces;

    public Piece(String piece_name, ArrayList<int[]> piece_actions){
        this.name = piece_name;
        this.pieceActions = piece_actions;
    }

    public String getName(){
        return this.name;
    }

    public ArrayList<int[]> getPieceActions(){
        return this.pieceActions;
    }

    public static ArrayList<ArrayList<Piece>> getPieces(){
        return pieces;
    }

    public static void generatePieces(){
        pieces = new ArrayList<>();
        for(int j = 1; j <= 4; j++){
            ArrayList<Piece> playerPieces = new ArrayList<>();
            for (int i = 0; i < 21; i++) {
                ArrayList<int[]> actionsList = new ArrayList<>();
                actionsList.add(new int[]{0,0});
                if(i == 0){
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                }
                else if(i == 1){
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                    actionsList.add(new int[]{2,-1});
                }
                else if(i == 2){
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                    actionsList.add(new int[]{1,-1});
                }
                else if(i == 3){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,-1});
                }
                if(i==4){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{1,1});
                    actionsList.add(new int[]{0,-1});
                }
                else if(i == 5){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{2,-1});
                }
                else if(i == 6){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{-1,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,-1});
                }
                else if(i == 7){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{-1,0});
                }
                else if(i == 8){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,0});
                }
                else if(i == 9){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,1});
                    actionsList.add(new int[]{-1,1});
                }
                else if(i == 10){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{-1,1});
                    actionsList.add(new int[]{-1,0});
                }
                else if(i == 11){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,1});
                    actionsList.add(new int[]{-1,1});
                }
                else if(i == 12){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{-1,1});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{0,-1});
                }
                else if(i == 13){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{-2,0});
                    actionsList.add(new int[]{-1,0});
                }
                else if(i == 14){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{-1,0});
                }
                else if(i == 15){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{1,0});
                }
                else if(i == 16){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,0});
                }
                else if(i == 18){
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{-2,0});
                }
                else if(i == 19){
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{-1,0});
                }
                else if(i == 20){
                    actionsList.add(new int[]{-1,0});
                }
                Piece newPiece = new Piece("Piece "+Integer.toString(i),actionsList);
                playerPieces.add(newPiece);
            }
            pieces.add(playerPieces);
        }
    }


}
