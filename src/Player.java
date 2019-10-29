import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {

    private String playerName;
    private Color playerColour;
    private ArrayList<Piece> availablePieces;
    private static ArrayList<Player> players = new ArrayList<>(Arrays.asList());

    public Player(String player_name, Color player_color, ArrayList<Piece> player_pieces){
        this.playerName = player_name;
        this.playerColour = player_color;
        this.availablePieces = player_pieces;

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

    public static ArrayList<Player> getPlayers(){
        return players;
    }
}
