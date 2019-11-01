import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Player {
    private String playerName;
    private Color playerColor;
    private int playerIndex;
    private PlayerGrid playerGrid;
    private ArrayList<Integer> availablePiecesIndexes;
    private static Player[] players = new Player[5];

    public Player(int index){
        this.setIndex(index);
        this.setName();
        this.setColor(Options.getColor(getPlayerIndex()));
        this.availablePiecesIndexes = Piece.getPieceList();
        players[index] = this;
    }

    private void setName(){
        this.playerName = "Player " + playerIndex;
    }

    private void setIndex(int index){
        this.playerIndex = index;
    }

    private void setColor(Color color){
        this.playerColor = color;
    }


    public JPanel createGrid(){
        playerGrid = new PlayerGrid(this.playerIndex);
        return PlayerGrid.getPlayerGridPanel(this.playerIndex);
    }

    public int getPlayerIndex(){
        return  this.playerIndex;
    }

    public String getPlayerName(){
        return this.playerName;
    }

    public Color getColor(){
        return this.playerColor;
    }

    public ArrayList<Integer> getAvailablePieces(){
        return this.availablePiecesIndexes;
    }

    public void pieceUsed(int piece_index){
        this.availablePiecesIndexes.removeIf(value -> value == piece_index);
    }

    public static Player getPlayer(int player_index){
        return players[player_index];
    }
}
