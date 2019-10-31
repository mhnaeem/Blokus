import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class Player {
    private String playerName;
    private Color playerColor;
    private int playerIndex;
    PlayerGrid grid;

    public Player(int index){
        this.setIndex(index);
        this.setName();
        this.setColor(Options.getColor(getPlayerIndex()));
    }

    private void setName(){
        this.playerName = "Player "+playerIndex;
    }

    private void setIndex(int index){
        this.playerIndex = index;
    }

    private void setColor(Color color){
        this.playerColor = color;
    }


    public JPanel createGrid(){
        grid = new PlayerGrid(getPlayerIndex());
        return grid.getPlayerGridPanel();
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
        return PiecesMonitor.getAvailablePieces(getPlayerIndex());
    }


}
