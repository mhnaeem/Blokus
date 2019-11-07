import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * GameEngine class:
 *
 * created the class and implemented the isLegal, isWithinGrid and isOccupied functionality
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */
public class GameEngine {

    private static Integer selectedPiece = null;
    private static HashMap<Integer, int[]> firstTurnMap = new HashMap<>();
    private static int[] turnOrder;
    private static int currentTurn;
    private static int turn_index = 0;
    private static int alternateTurn = 1;


    public GameEngine() {
        turn_index = 0;
        currentTurn = Options.getTurnOrder(turn_index);
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        alternateTurn = 1;
    }

    public static boolean isLegal(String selectedPoint) {
        JButton[][] grid = MainGrid.getMainGridButtons();
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);


        for (int[] action : Piece.getActionsList(selectedPiece)) {
            if (!isWithinGrid(selectedPoint, action, grid) || isOccupied(selectedPoint, grid)) {
                //JOptionPane.showMessageDialog(null, "Not a legal move", "Illegal move!",JOptionPane.ERROR_MESSAGE);
                System.out.println("Not a legal move");
                return false;
            }


        }
        if (Options.getFirstTurnMap().containsKey(currentTurn)) {
            return isOnStartingPoint(Options.getFirstTurnMap().get(currentTurn), selectedPoint);
        }
        if ((isEdge(selectedPoint) && !isSide(selectedPoint)) || ((isEdge(selectedPoint) && !isSide(selectedPoint)) & (isLegalSide(selectedPoint)))) {
            return true;
        }
        else {
            return false;
        }
    }

    private static boolean isWithinGrid(String point, int[] action, JButton[][] grid) {
        int maxWidth = grid[1].length;
        int maxHeight = grid[0].length;

        String[] strArr = point.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);

        if (c + action[0] >= maxWidth || c + action[0] < 0) {
            return false;
        }
        if (r + action[1] >= maxHeight || r + action[1] < 0) {
            return false;
        }
        return true;
    }

    private static boolean isOccupied(String point, JButton[][] grid) {
        String[] strArr = point.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        for (int[] action : Piece.getActionsList(selectedPiece)) {
            int newC = c + action[0];
            int newR = r + action[1];
            if (isWithinGrid(point, action, grid)) {
                if (!grid[newR][newC].isEnabled()) {
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
        possibleEdges.forEach(button -> System.out.println("possible edge" + button));
        pieceEdge.forEach(button -> System.out.println("piece edge" + button));
        for (String button : pieceEdge) {
            if (possibleEdges.contains(button)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isSide(String selectedPoint) {
        System.out.println("button(0,0) currently on " + selectedPoint);
        ArrayList<String> possibleSides = calculateSide(MainGrid.getMainGridButtons());
        ArrayList<String> pieceSide = calculateSelectedPieceSide(selectedPoint);
        possibleSides.forEach(button -> System.out.println("possible side" + button));
        pieceSide.forEach(button -> System.out.println("piece side" + button));
        for (String button : pieceSide) {
            if (possibleSides.contains(button)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isLegalSide(String selectedPoint) {
        System.out.println("button(0,0) currently on " + selectedPoint);
        ArrayList<String> IllegalSides = calculateSide(MainGrid.getMainGridButtons());
        ArrayList<String> possibleSides = calculateLegalSide(MainGrid.getMainGridButtons());
        ArrayList<String> pieceSide = calculateSelectedPieceSide(selectedPoint);
        possibleSides.forEach(button -> System.out.println("possible side" + button));
        pieceSide.forEach(button -> System.out.println("piece side" + button));
        for (String button : pieceSide) {
            if (IllegalSides.contains(button)) {
                return false;
            }
        }
        for (String button : pieceSide) {
            if (possibleSides.contains(button)) {
                return true;
            }
        }
        return false;
    }

    private static ArrayList<String> calculateEdge(JButton[][] grid) {
        ArrayList<String> toReturn = new ArrayList<>();
        Color color = Options.getColor(currentTurn);
        int row = grid[0].length;
        int col = grid[1].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (c - 1 >= 0 && r - 1 >= 0) {
                    if ((grid[r][c - 1].isEnabled() && grid[r - 1][c].isEnabled() && grid[r - 1][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains(new String((r - 1) + "," + (c - 1)))) {
                            toReturn.add(new String((r - 1) + "," + (c - 1)));
                        }
                    }
                }
                if (c + 1 < col && r - 1 >= 0) {
                    if ((grid[r][c + 1].isEnabled() && grid[r - 1][c].isEnabled() && grid[r - 1][c + 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r - 1) + "," + (c + 1))))) {
                            toReturn.add((new String((r - 1) + "," + (c + 1))));
                        }
                    }
                }
                if (c - 1 >= 0 && r + 1 < row) {
                    if ((grid[r][c - 1].isEnabled() && grid[r + 1][c].isEnabled() && grid[r + 1][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r + 1) + "," + (c - 1))))) {
                            toReturn.add((new String((r + 1) + "," + (c - 1))));
                        }
                    }
                }
                if (c + 1 < col && r + 1 < row) {
                    if ((grid[r][c + 1].isEnabled() && grid[r + 1][c + 1].isEnabled() && grid[r + 1][c].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r + 1) + "," + (c + 1))))) {
                            toReturn.add((new String((r + 1) + "," + (c + 1))));
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    private static ArrayList<String> calculateSelectedPieceEdges(String selectedPoint) {
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        ArrayList<String> toReturn = new ArrayList<>();
        ArrayList<int[]> actions = new ArrayList<>();
        for (int[] action : Piece.getActionsList(selectedPiece)) {
            actions.add(new int[]{r + action[1], c + action[0]});
        }
        actions.forEach(array -> {
            int x = array[0];
            int y = array[1];
            if (!actions.contains(new int[]{x, y - 1}) && !actions.contains(new int[]{x - 1, y})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
            if (!actions.contains(new int[]{x - 1, y}) && !actions.contains(new int[]{x, y + 1})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
            if (!actions.contains(new int[]{x, y - 1}) && !actions.contains(new int[]{x + 1, y})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
            if (!actions.contains(new int[]{x, y + 1}) && !actions.contains(new int[]{x + 1, y})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
        });
        return toReturn;
    }

    private static ArrayList<String> calculateSide(JButton[][] grid) {
        ArrayList<String> toReturn = new ArrayList<>();
        Color color = Options.getColor(currentTurn);
        int row = grid[0].length;
        int col = grid[1].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (r - 1 >= 0) {
                    if ((grid[r - 1][c].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains(new String((r - 1) + "," + (c)))) {
                            toReturn.add(new String((r - 1) + "," + (c)));
                        }
                    }
                }
                if (c - 1 >= 0) {
                    if ((grid[r][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r) + "," + (c - 1))))) {
                            toReturn.add((new String((r) + "," + (c - 1))));
                        }
                    }
                }
                if (c + 1 < col) {
                    if ((grid[r][c + 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r) + "," + (c + 1))))) {
                            toReturn.add((new String((r) + "," + (c + 1))));
                        }
                    }
                }
                if (r + 1 < row) {
                    if ((grid[r + 1][c].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r + 1) + "," + (c))))) {
                            toReturn.add((new String((r + 1) + "," + (c))));
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    private static ArrayList<String> calculateLegalSide(JButton[][] grid) {
        ArrayList<String> toReturn = new ArrayList<>();
        Color color = Options.getColor(currentTurn);
        int row = grid[0].length;
        int col = grid[1].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (r - 1 >= 0) {
                    if ((grid[r - 1][c].isEnabled()) && (!grid[r][c].isEnabled() && (!grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains(new String((r - 1) + "," + (c)))) {
                            toReturn.add(new String((r - 1) + "," + (c)));
                        }
                    }
                }
                if (c - 1 >= 0) {
                    if ((grid[r][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (!grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r) + "," + (c - 1))))) {
                            toReturn.add((new String((r) + "," + (c - 1))));
                        }
                    }
                }
                if (c + 1 < col) {
                    if ((grid[r][c + 1].isEnabled()) && (!grid[r][c].isEnabled() && (!grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r) + "," + (c + 1))))) {
                            toReturn.add((new String((r) + "," + (c + 1))));
                        }
                    }
                }
                if (r + 1 < row) {
                    if ((grid[r + 1][c].isEnabled()) && (!grid[r][c].isEnabled() && (!grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r + 1) + "," + (c))))) {
                            toReturn.add((new String((r + 1) + "," + (c))));
                        }
                    }
                }
            }
        }
        return toReturn;
    }

    private static ArrayList<String> calculateSelectedPieceSide(String selectedPoint) {
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        ArrayList<String> toReturn = new ArrayList<>();
        ArrayList<int[]> actions = new ArrayList<>();
        for (int[] action : Piece.getActionsList(selectedPiece)) {
            actions.add(new int[]{r + action[1], c + action[0]});
        }
        actions.forEach(array -> {
            int x = array[0];
            int y = array[1];
            if (!actions.contains(new int[]{x - 1, y})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
            if (!actions.contains(new int[]{x, y - 1})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
            if (!actions.contains(new int[]{x, y + 1})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
            if (!actions.contains(new int[]{x + 1, y})) {
                if (!toReturn.contains(new String(x + "," + y))) {
                    toReturn.add(new String(x + "," + y));
                }
            }
        });
        return toReturn;
    }


    public static int getCurrentTurn() {
        return currentTurn;
    }

    //TODO: implement AI move
    public static void updateCurrentTurn() {

        //if alternate player update ALTERNATE PLAYER LABEL
        if (Options.hasAlternatePlayer()) {
            Player.getPlayer(4).setName("Alternate");
            GameGUI.updateLabels();
        }

        turn_index++;
        if (turn_index >= 4) {
            turn_index = 0;
        }

        currentTurn = Options.getTurnOrder(turn_index);
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        selectedPiece = null;

        //IF AI TURN
        if (Options.getAI_indexList().contains(currentTurn)) {
            //AI turn here
            //TODO: AI should make move using current turn
            //DISABLE ALL GRIDS WHILE AI IS PLAYING
            //AI TURN IS OVER WHEN updateCurrentTurn() is called again
        }

        //if game has alternate player & alternate player turn
        if (Options.hasAlternatePlayer() && currentTurn == 4) {

            //update alternate label to the player who should make the alternate move
            Player.getPlayer(4).setName(Player.getPlayer(alternateTurn).getPlayerName());
            GameGUI.updateLabels();

            //if AI turn to play alternate player
            if (Options.getAI_indexList().contains(alternateTurn)) {
                //TODO: here AI function to play alternate turn here
                //AI should play using the current turn
                //DISABLE ALL GRIDS WHILE AI IS PLAYING
                //turn over when updateCurrentTurn() is called again
            }
            else {
                //alternate turn is played by a human
                //turn over when updateCurrentTurn() is called again
            }

            alternateTurn++;
            //alternate turn is played by player index 1,2,3. It cannot be played by index 4 which is itself
            if (alternateTurn >= 4) {
                alternateTurn = 1;
            }
        }
    }


    private static boolean isOnStartingPoint(int[] array, String selectedPoint) {
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        for (int[] action : Piece.getActionsList(selectedPiece)) {
            if (((r + action[1]) == array[0]) && ((c + action[0]) == array[1])) {
                return true;
            }
        }
        return false;
    }

    public static Integer getSelectedPiece() {
        if (selectedPiece == null) {
            System.out.println("Error in getSelectedPiece, no piece was selected");
            //JOptionPane.showMessageDialog(null,"Error in getSelectedPiece, no piece was selected");
        }
        return selectedPiece;
    }


    public static void setSelectedPiece(Integer piece_index) {
        selectedPiece = piece_index;
    }


    public static HashMap<Integer, Integer> playerScoring() {
        int numPlayers = Options.getNumberOfPlayers();

        HashMap<Integer, Integer> playerScoreList = new HashMap<>();
        Map.Entry<Integer, Integer> min = null;
        Map.Entry<Integer, Integer> max = null;

        for (int h = 1; h <= numPlayers; h++) {
            JButton[][] playerPieces = PlayerGrid.getPlayerGridButtons(h);
            int playerScore = 0;

            for (int i = 0; i < playerPieces.length; i++) {
                for (int j = 0; j < playerPieces[i].length; j++) {
                    JButton playerGridCell = playerPieces[i][j];
                    if (playerGridCell.isEnabled()) {
                        if (Options.getScoringType().equals("Basic")) {
                            playerScore += 1;
                        } else if (Options.getScoringType().equals("Advanced")) {
                            playerScore -= 1;
                        }
                    }
                }
            }

            /*
             * this is the map of player int and their score
             * can edit this function's return value to this if playerScoreList needed instead of the winner
             * done by changing return type to HashMap<Integer, Integer>
             * and calling to return playerScoreList;
             */
            playerScoreList.put(h, playerScore);
        }
        /*
         * for basic scoring
         * finds the minimum score in playerScoreList. the player with the minimum score is the winner
         */
        if (Options.getScoringType().equals("Basic")) {
            for (Map.Entry<Integer, Integer> entry : playerScoreList.entrySet()) {
                if (min == null || min.getValue() > entry.getValue()) {
                    min = entry;
                }
            }
        }

        /*
         * for advanced scoring
         * finds the maximum score in playerScoreList. the player with the maximum score is the winner
         */
        else if (Options.getScoringType().equals("Advanced")) {
            for (Map.Entry<Integer, Integer> entry : playerScoreList.entrySet()) {
                if (max == null || max.getValue() > entry.getValue()) {
                    max = entry;
                }
            }
        }

        return playerScoreList;

        /*
         * returns basic scoring winner
         */
//            if (Options.getScoringType().equals("Basic")) {
//                return (min.getKey());
//        }

        /*
         * returns advanced scoring winner
         */
//            else if (Options.getScoringType().equals("Advanced")) {
//                return (max.getKey());
//        }



    }
    public static void gameEnd(){
        new GameOver(playerScoring());
    }
}