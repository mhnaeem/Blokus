import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Player {

    private String playerName;
    private Color playerColour;
    private ArrayList<Piece> availablePieces;
    private static ArrayList<Player> players = new ArrayList<>(Arrays.asList());
    private HashSet<String> displayPiecesCoordinates;

    public Player(String player_name, Color player_color, ArrayList<Piece> player_pieces){
        this.playerName = player_name;
        this.playerColour = player_color;
        this.availablePieces = player_pieces;
        this.displayPiecesCoordinates = new HashSet<>(Arrays.asList("0,0","0,1","0,2","0,3","0,5","0,6","0,7","0,8","0,11","0,12","0,13","0,14","1,8","1,13","3,0","3,3","3,4","3,6","3,10","3,11","3,15","4,0","4,3","4,4","4,6","4,11","4,14","4,15","4,16","5,0","5,1","5,3","5,6","5,7","5,8","5,11","5,12","5,15","7,0","7,3","7,4","7,6","7,10","7,11","7,12","7,14","7,15","8,0","8,1","8,3","8,6","8,7","8,11","8,15","8,16","9,0","9,3","9,4","9,7","9,8","9,11","9,15","11,0","11,1","11,2","11,5","11,6","11,9","11,12","11,13","11,15","12,2","12,3","12,6","12,7","12,9","12,10","12,12","12,13","14,0","14,1","14,2","14,3","14,4","14,6","14,7","14,8","14,10","14,11"));

        players.add(this);
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public Color getColour(){
        return this.playerColour;
    }

    public ArrayList<Piece> getAvailablePieces(){
        return this.availablePieces;
    }

    public void removeDisplayPieceCoordinates(String[] points){
        for (String point : points) {
            this.displayPiecesCoordinates.remove(point);
        }
    }

    public HashSet<String> getDisplayPiecesCoordinates(){
        return this.displayPiecesCoordinates;
    }

    public static Player[] getPlayers(){
        return players.toArray(new Player[0]);
    }
}
