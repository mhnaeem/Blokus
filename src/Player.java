import java.awt.*;
import java.util.ArrayList;

public class Player {

    private String playerName;
    private Color playerColour;
    private ArrayList<Piece> availablePieces;

    public Player(String player_name, Color player_color, ArrayList<Piece> player_pieces){
        this.playerName = player_name;
        this.playerColour = player_color;
        this.availablePieces = player_pieces;
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
}
