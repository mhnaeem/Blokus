import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * GameEngine class:
 *
 * created the class and implemented the isLegal, isWithinGrid and isOccupied functionality
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */
public class GameEngine {

    private static int currentTurn = 1;
    private static int testturn = 1;
    private static boolean ret = false;
    private static Integer selectedPiece = null;

    public static boolean isLegal(String selectedPoint){
        JButton[][] grid = MainGrid.getMainGridButtons();
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);

        for (int[] action : Piece.getActionsList(selectedPiece)) {
            if(!isWithinGrid(selectedPoint, action, grid) || isOccupied(selectedPoint, grid)){
                //JOptionPane.showMessageDialog(null, "Not a legal move", "Illegal move!",JOptionPane.ERROR_MESSAGE);
                //System.out.println("Not a legal move");
                return false;
            }
        }
        /*if (isEdge(selectedPoint) ||testturn<=4){
            return true;
        }
        else {
            return false;
        }*/
        return true;
    }

    private static boolean isWithinGrid(String point, int[] action, JButton[][] grid){
        int maxWidth = grid.length-1;
        int maxHeight = grid[0].length-1;

        String[] strArr = point.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);

        if ( c+action[0] > maxWidth || c+action[0] < 0){
            return false;
        }
        if( r+action[1] > maxHeight || r+action[0] < 0){
            return false;
        }
        return true;
    }

    private static boolean isOccupied(String point, JButton[][] grid){
        String[] strArr = point.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        for (int[] action : Piece.getActionsList(selectedPiece)) {
            int newC = c+action[0];
            int newR = r+action[1];
            if (isWithinGrid(point, action, grid)){
                if (!grid[newR][newC].isEnabled()){
                    return true;
                }
            }
        }
        return false;
    }


    private static boolean isEdge(String selectedPoint) {
        System.out.println("button(0,0) currently on " + selectedPoint);
        ArrayList<String> possibleEdges = calculateEdge(MainGrid.getMainGridButtons());
        ArrayList<String> pieceEdge = calculateSelectedPieceEdges(selectedPoint);
        for (String button : possibleEdges) {
            System.out.println("possible edges" + button);
            if (pieceEdge.contains(button)){
            }
            for (String button2 : pieceEdge){
                System.out.println("piece edges " + button2);
            }
        }
        return true;
    }

    private static ArrayList<String> calculateEdge(JButton[][] grid) {
        ArrayList<String> toReturn = new ArrayList<>();
        Color color = Options.getColor(currentTurn);
        int row = grid[0].length;
        int col = grid[1].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (c-1>=0&&r-1>=0) {
                    if ((grid[r][c-1].isEnabled() && grid[r-1][c].isEnabled() && grid[r-1][c-1].isEnabled()) && (!grid[r][c].isEnabled()&& (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains(new String((r-1)+","+(c-1)))){
                            toReturn.add(new String((r-1)+","+(c-1)));
                        }
                    }
                }
                if (c+1<col&&r-1>=0) {
                    if  ((grid[r][c+1].isEnabled() && grid[r-1][c].isEnabled() && grid[r-1][c+1].isEnabled()) && (!grid[r][c].isEnabled()&& (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r-1)+","+(c+1))))){
                            toReturn.add((new String((r - 1) + "," + (c + 1))));
                        }
                    }
                }
                if (c-1>=0&&r+1<row) {
                    if  ((grid[r][c-1].isEnabled() && grid[r+1][c].isEnabled() && grid[r+1][c-1].isEnabled()) && (!grid[r][c].isEnabled()&& (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r+1)+","+(c-1))))){
                            toReturn.add((new String((r+1)+","+(c-1))));
                        }
                    }
                }
                if (c+1<col&&r+1<row) {
                    if  ((grid[r][c+1].isEnabled() && grid[r+1][c+1].isEnabled() && grid[r+1][c].isEnabled()) && (!grid[r][c].isEnabled()&& (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r+1)+","+(c+1))))){
                            toReturn.add((new String((r+1)+","+(c+1))));
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    private static ArrayList<String> calculateSelectedPieceEdges(String selectedPoint){
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        ArrayList<String> toReturn = new ArrayList<>();
        ArrayList<int[]> actions = new ArrayList<>();
        for (int[] action : Piece.getActionsList(selectedPiece)){
            actions.add(new int[]{r+action[0],c+action[1]});
        }
        actions.forEach(array->{
            int x = array[0];
            int y = array[1];
            if (!actions.contains(new int[]{x,y-1}) && !actions.contains(new int[]{x-1,y})){
                if (!toReturn.contains(new String(x+","+y))){
                    toReturn.add(new String(x+","+y));
                }
            }
            if (!actions.contains(new int[]{x,y+1}) && !actions.contains(new int[]{x,y+1})){
                if (!toReturn.contains(new String(x+","+y))){
                    toReturn.add(new String(x+","+y));
                }
            }
            if (!actions.contains(new int[]{x,y-1}) && !actions.contains(new int[]{x+1,y})){
                if (!toReturn.contains(new String(x+","+y))){
                    toReturn.add(new String(x+","+y));
                }
            }
            if (!actions.contains(new int[]{x,y+1}) && !actions.contains(new int[]{x+1,y})){
                if (!toReturn.contains(new String(x+","+y))){
                    toReturn.add(new String(x+","+y));
                }
            }
        });
        return toReturn;
    }

    private static boolean isSide(String selectedPoint, JButton[][] grid){
        return false;
    }

    public static int getCurrentTurn(){
        return currentTurn;
    }

    //TODO: implement for two players and three players later
    public static void updateCurrentTurn(){
        if (getCurrentTurn() == 4){
            currentTurn = 1;
        }
        else {
            currentTurn += 1;
        }
        testturn++;
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        selectedPiece = null;
    }

    public static Integer getSelectedPiece(){
        if(selectedPiece == null){
            //System.out.println("Error in getSelectedPiece, no piece was selected");
            //JOptionPane.showMessageDialog(null,"Error in getSelectedPiece, no piece was selected");
        }
        return selectedPiece;
    }

    public static void setSelectedPiece(Integer piece_index){
        selectedPiece = piece_index;
    }
}
