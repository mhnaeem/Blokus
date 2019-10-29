import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.util.Map.entry;

public class Piece {

    private String name;

    //Actions that the piece needs to perform given a point to display on grid
    private ArrayList<int[]> pieceActions;

    //This is an array of points corresponding to the player area pieces in GameGUI
    private String[] displayCoordinates;

    private static HashMap<String, Integer> PIECE_MAP = createPieceMap();

    /*
    A visual representation of how the pieces ArrayList is stored.

        [ [Piece 0, Piece 1, ...., Piece 20],                <- Pieces for player 1
          [Piece 0, Piece 1, ...., Piece 20],                <- Pieces for player 2
          [Piece 0, Piece 1, ...., Piece 20],                <- Pieces for player 3
          [Piece 0, Piece 1, ...., Piece 20] ]                <- Pieces for player 4
     */
    private static ArrayList<ArrayList<Piece>> pieces = generatePieces();


    public Piece(String piece_name, ArrayList<int[]> piece_actions, String[] coordinates){
        this.name = piece_name;
        this.pieceActions = piece_actions;
        this.displayCoordinates = coordinates;
    }

    public ArrayList<int[]> getPieceActions(){
        return this.pieceActions;
    }

    public String[] getDisplayCoordinates(){
        return this.displayCoordinates;
    }

    private static ArrayList<ArrayList<Piece>> generatePieces(){
        pieces = new ArrayList<>();
        for(int j = 1; j <= 4; j++){
            ArrayList<Piece> playerPieces = new ArrayList<>();
            for (int i = 0; i < 21; i++) {
                ArrayList<int[]> actionsList = new ArrayList<>();
                String[] pointsOnMainScreen = new String[5];

                actionsList.add(new int[]{0,0});
                if(i == 0){
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                    pointsOnMainScreen[0] = "0,0";
                    pointsOnMainScreen[1] = "0,1";
                    pointsOnMainScreen[2] = "0,2";
                    pointsOnMainScreen[3] = "0,3";
                }
                else if(i == 1){
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                    actionsList.add(new int[]{2,-1});
                    pointsOnMainScreen[0] = "0,5";
                    pointsOnMainScreen[1] = "0,6";
                    pointsOnMainScreen[2] = "0,7";
                    pointsOnMainScreen[3] = "0,8";
                    pointsOnMainScreen[4] = "1,8";
                }
                else if(i == 2){
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                    actionsList.add(new int[]{1,-1});
                    pointsOnMainScreen[0] = "0,11";
                    pointsOnMainScreen[1] = "0,12";
                    pointsOnMainScreen[2] = "0,13";
                    pointsOnMainScreen[3] = "0,14";
                    pointsOnMainScreen[4] = "1,13";
                }
                else if(i == 3){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,-1});
                    pointsOnMainScreen[0] = "3,0";
                    pointsOnMainScreen[1] = "4,0";
                    pointsOnMainScreen[2] = "5,0";
                    pointsOnMainScreen[3] = "5,1";
                }
                if(i==4){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{1,1});
                    actionsList.add(new int[]{0,-1});
                    pointsOnMainScreen[0] = "3,3";
                    pointsOnMainScreen[1] = "3,4";
                    pointsOnMainScreen[2] = "4,3";
                    pointsOnMainScreen[3] = "4,4";
                    pointsOnMainScreen[4] = "5,3";
                }
                else if(i == 5){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{2,-1});
                    pointsOnMainScreen[0] = "3,6";
                    pointsOnMainScreen[1] = "4,6";
                    pointsOnMainScreen[2] = "5,6";
                    pointsOnMainScreen[3] = "5,7";
                    pointsOnMainScreen[4] = "5,8";
                }
                else if(i == 6){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{-1,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,-1});
                    pointsOnMainScreen[0] = "3,10";
                    pointsOnMainScreen[1] = "3,11";
                    pointsOnMainScreen[2] = "4,11";
                    pointsOnMainScreen[3] = "5,11";
                    pointsOnMainScreen[4] = "5,12";
                }
                else if(i == 7){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{-1,0});
                    pointsOnMainScreen[0] = "3,15";
                    pointsOnMainScreen[1] = "4,15";
                    pointsOnMainScreen[2] = "5,15";
                    pointsOnMainScreen[3] = "4,14";
                    pointsOnMainScreen[4] = "4,16";
                }
                else if(i == 8){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,0});
                    pointsOnMainScreen[0] = "7,0";
                    pointsOnMainScreen[1] = "8,0";
                    pointsOnMainScreen[2] = "9,0";
                    pointsOnMainScreen[3] = "8,1";
                }
                else if(i == 9){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,1});
                    actionsList.add(new int[]{-1,1});
                    pointsOnMainScreen[0] = "7,3";
                    pointsOnMainScreen[1] = "7,4";
                    pointsOnMainScreen[2] = "8,3";
                    pointsOnMainScreen[3] = "9,3";
                    pointsOnMainScreen[4] = "9,4";
                }
                else if(i == 10){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{-1,1});
                    actionsList.add(new int[]{-1,0});
                    pointsOnMainScreen[0] = "7,6";
                    pointsOnMainScreen[1] = "8,6";
                    pointsOnMainScreen[2] = "8,7";
                    pointsOnMainScreen[3] = "9,7";
                    pointsOnMainScreen[4] = "9,8";
                }
                else if(i == 11){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,1});
                    actionsList.add(new int[]{-1,1});
                    pointsOnMainScreen[0] = "7,10";
                    pointsOnMainScreen[1] = "7,11";
                    pointsOnMainScreen[2] = "7,12";
                    pointsOnMainScreen[3] = "8,11";
                    pointsOnMainScreen[4] = "9,11";
                }
                else if(i == 12){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{-1,1});
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{0,-1});
                    pointsOnMainScreen[0] = "7,14";
                    pointsOnMainScreen[1] = "7,15";
                    pointsOnMainScreen[2] = "8,15";
                    pointsOnMainScreen[3] = "9,15";
                    pointsOnMainScreen[4] = "8,16";
                }
                else if(i == 13){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{-2,0});
                    actionsList.add(new int[]{-1,0});
                    pointsOnMainScreen[0] = "11,0";
                    pointsOnMainScreen[1] = "11,1";
                    pointsOnMainScreen[2] = "11,2";
                    pointsOnMainScreen[3] = "12,2";
                    pointsOnMainScreen[4] = "12,3";
                }
                else if(i == 14){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{-1,0});
                    pointsOnMainScreen[0] = "11,5";
                    pointsOnMainScreen[1] = "11,6";
                    pointsOnMainScreen[2] = "12,6";
                    pointsOnMainScreen[3] = "12,7";
                }
                else if(i == 15){
                    actionsList.add(new int[]{0,1});
                    actionsList.add(new int[]{1,0});
                    pointsOnMainScreen[0] = "12,9";
                    pointsOnMainScreen[1] = "11,9";
                    pointsOnMainScreen[2] = "12,10";
                }
                else if(i == 16){
                    actionsList.add(new int[]{1,-1});
                    actionsList.add(new int[]{0,-1});
                    actionsList.add(new int[]{1,0});
                    pointsOnMainScreen[0] = "11,12";
                    pointsOnMainScreen[1] = "11,13";
                    pointsOnMainScreen[2] = "12,12";
                    pointsOnMainScreen[3] = "12,13";
                }
                else if (i == 17){
                    pointsOnMainScreen[0] = "11,15";
                }
                else if(i == 18){
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{2,0});
                    actionsList.add(new int[]{-1,0});
                    actionsList.add(new int[]{-2,0});
                    pointsOnMainScreen[0] = "14,0";
                    pointsOnMainScreen[1] = "14,1";
                    pointsOnMainScreen[2] = "14,2";
                    pointsOnMainScreen[3] = "14,3";
                    pointsOnMainScreen[4] = "14,4";
                }
                else if(i == 19){
                    actionsList.add(new int[]{1,0});
                    actionsList.add(new int[]{-1,0});
                    pointsOnMainScreen[0] = "14,6";
                    pointsOnMainScreen[1] = "14,7";
                    pointsOnMainScreen[2] = "14,8";
                }
                else if(i == 20){
                    actionsList.add(new int[]{-1,0});
                    pointsOnMainScreen[0] = "14,10";
                    pointsOnMainScreen[1] = "14,11";
                }
                Piece newPiece = new Piece("Piece "+i,actionsList, pointsOnMainScreen);
                playerPieces.add(newPiece);
            }
            pieces.add(playerPieces);
        }
        return pieces;
    }

    public static ArrayList<ArrayList<Piece>> getPieces(){
        return pieces;
    }

    private static HashMap<String, Integer> createPieceMap(){
        return new HashMap<>(Map.ofEntries(entry("0,0", 0),entry("0,1", 0),entry("0,2", 0),entry("0,3", 0),entry("0,5", 1),entry("0,6", 1),entry("0,7", 1),entry("0,8", 1),entry("1,8", 1),entry("0,11", 2),entry("0,12", 2),entry("0,13", 2),entry("0,14", 2),entry("1,13", 2),entry("3,0", 3),entry("4,0", 3),entry("5,0", 3),entry("5,1",3),entry("6,1", 3),entry("3,3", 4),entry("3,4", 4),entry("4,3", 4),entry("4,4", 4),entry("5,3", 4),entry("3,6", 5),entry("4,6", 5),entry("5,6", 5),entry("5,7", 5),entry("5,8", 5),entry("3,10", 6),entry("3,11", 6),entry("4,11", 6),entry("5,11", 6),entry("5,12", 6),entry("3,15", 7),entry("4,15", 7),entry("5,15", 7),entry("4,14", 7),entry("4,16", 7),entry("7,0", 8),entry("8,0", 8),entry("9,0", 8),entry("8,1", 8),entry("7,3", 9),entry("7,4", 9),entry("8,3", 9),entry("9,3", 9),entry("9,4", 9),entry("7,6", 10),entry("8,6", 10),entry("8,7", 10),entry("9,7", 10),entry("9,8", 10),entry("7,10", 11),entry("7,11", 11),entry("7,12", 11),entry("8,11", 11),entry("9,11", 11),entry("7,14", 12),entry("7,15", 12),entry("8,15", 12),entry("9,15", 12),entry("8,16", 12),entry("11,0", 13),entry("11,1", 13),entry("11,2", 13),entry("12,2", 13),entry("12,3", 13),entry("11,5", 14),entry("11,6", 14),entry("12,6", 14),entry("12,7", 14),entry("12,9", 15),entry("11,9", 15),entry("12,10", 15),entry("11,12", 16),entry("11,13", 16),entry("12,12", 16),entry("12,13", 16),entry("11,15", 17),entry("14,0", 18),entry("14,1", 18),entry("14,2", 18),entry("14,3", 18),entry("14,4", 18),entry("14,6", 19),entry("14,7", 19),entry("14,8", 19),entry("14,10", 20),entry("14,11",20)
        ));
    }

    public static Piece getPiece(String name, String playerName){
        int temp = PIECE_MAP.get(name);
        if(playerName.equals("Player 1")){
            return pieces.get(0).get(temp);
        }
        else if(playerName.equals("Player 2")){
            return pieces.get(1).get(temp);
        }
        else if(playerName.equals("Player 3")){
            return pieces.get(2).get(temp);
        }
        else if(playerName.equals("Player 4")) {
            return pieces.get(3).get(temp);
        }
        else return null;
        //TODO: throw exception later for piece not found.
    }

}
