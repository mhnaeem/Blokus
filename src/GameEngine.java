import javax.swing.*;

/**
 * GameEngine class:
 *
 * created the class and implemented the isLegal, isWithinGrid and isOccupied functionality
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */
public class GameEngine {

    public GameEngine(){

    }

    public static boolean isLegal(JButton[][] grid, String selectedPoint){
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);


        for (int[] action : Piece.getActionsListMap().get(PiecesMonitor.getSelectedPiece(1))) {
            if(!isWithinGrid(selectedPoint, action, grid) || isOccupied(selectedPoint, grid)){
                return false;
            }
        }
        return true;
    }

    public static boolean isWithinGrid(String point, int[] action, JButton[][] grid){
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

    public static boolean isOccupied(String point, JButton[][] grid){
        String[] strArr = point.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);

        for (int[] action : Piece.getActionsListMap().get(PiecesMonitor.getSelectedPiece(1))) {
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
}
