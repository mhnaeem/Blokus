import javax.swing.*;

/**
 * GameEngine class:
 *
 * created the class and implemented the isLegal, isWithinGrid and isOccupied functionality
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */
public class GameEngine {

    private static int currentTurn = 1;
    private static int selectedPiece;

    public GameEngine(){

    }

    public static boolean isLegal(String selectedPoint){
        JButton[][] grid = MainGrid.getMainGridButtons();
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);


        for (int[] action : Piece.getActionsList(selectedPiece)) {
            if(!isWithinGrid(selectedPoint, action, grid) || isOccupied(selectedPoint, grid)){
                JOptionPane.showMessageDialog(null, "Not a legal move", "Illegal move!",JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
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
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        selectedPiece = -1;
    }

    public static Integer getSelectedPiece(){
        if(selectedPiece == -1){
            JOptionPane.showMessageDialog(null,"Error in getSelectedPiece, no piece was selected");
            return -1;
        }
        return selectedPiece;
    }

    public static void setSelectedPiece(int piece_index){
        selectedPiece = piece_index;
    }
}
