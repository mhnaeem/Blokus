import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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
    private static int currentTurn;
    private static int turn_index = 0;
    private static int alternateTurn = 1;
    private static ArrayList<String> possibleEdges = new ArrayList<>();
    private static ArrayList<String> possibleSides = new ArrayList<>();
    //private static ArrayList<String> possibleTopLeftEdges = new ArrayList<>();
    //private static ArrayList<String> possibleTopRightEdges = new ArrayList<>();
    //private static ArrayList<String> possibleBottomLeftEdges = new ArrayList<>();
    //private static ArrayList<String> possibleBottomRightEdges = new ArrayList<>();
    private static ArrayList<String> enabledButtonCoordinates = new ArrayList<>();
    private static HashMap<Integer,Boolean> doesPlayerHasMove = new HashMap<>();
    private static Boolean gameEnded = false;


    public GameEngine() {
        turn_index = 0;

        currentTurn = Options.getTurnOrderAccordingToColors(turn_index);
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        alternateTurn = 1;
        if (isAITurn(currentTurn)){
            PlayerGrid.disableAllPlayerGrids();
            //AI.makeMove(currentTurn);
        }
    }

    public static boolean isLegal(String selectedPoint) {
        JButton[][] grid = MainGrid.getMainGridButtons();
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        calculatePossibleSidesAndEdges(currentTurn);


        for (int[] action : Piece.getActionsList(selectedPiece)) {
            if (!isWithinGrid(selectedPoint, action, grid) || isOccupied(selectedPoint, grid)) {
                //JOptionPane.showMessageDialog(null, "Not a legal move", "Illegal move!",JOptionPane.ERROR_MESSAGE);
                //System.out.println("Not a legal move");
                return false;
            }
        }

        if (Options.getIsFirstTurnMap().containsKey(currentTurn)) {
            return isOnCorner(Options.getIsFirstTurnMap().get(currentTurn), selectedPoint);
        }

        if ((isSameColorEdge(selectedPoint) && !isSameColorSide(selectedPoint))) {
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

    private static boolean isSameColorEdge(String selectedPoint) {
        //System.out.println("button(0,0) currently on " + selectedPoint);
        ArrayList<String> pieceEdge = calculateSelectedPieceEdges(selectedPoint);
        for (String button : pieceEdge) {
            if (possibleEdges.contains(button)) {
                return true;
            }
        }
        return false;
    }

    private static void calculatePossibleSidesAndEdges(int player_index){
        possibleSides = calculateBoardSide(MainGrid.getMainGridButtons(),Options.getColor(player_index));
        possibleEdges = calculateBoardEdge(MainGrid.getMainGridButtons(),Options.getColor(player_index));
    }

    private static boolean isSameColorSide(String selectedPoint) {

        ArrayList<String> pieceSide = calculateSelectedPieceSide(selectedPoint);
        for (String button : pieceSide) {
            if (possibleSides.contains(button)) {
                return true;
            }
        }
        return false;
    }


    private static ArrayList<String> calculateBoardEdge(JButton[][] grid, Color color) {
        ArrayList<String> toReturn = new ArrayList<>();
       //possibleTopLeftEdges = new ArrayList<>();
       //possibleBottomLeftEdges = new ArrayList<>();
       //possibleTopRightEdges = new ArrayList<>();
       //possibleBottomRightEdges = new ArrayList<>();
        int row = grid[0].length;
        int col = grid[1].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (c - 1 >= 0 && r - 1 >= 0) {
                    if ((!grid[r][c - 1].getBackground().equals(color) && !grid[r - 1][c].getBackground().equals(color) && grid[r - 1][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains(new String((r - 1) + "," + (c - 1)))) {
                            toReturn.add(new String((r - 1) + "," + (c - 1)));
                        }
                        //if (!possibleTopLeftEdges.contains(new String((r - 1) + "," + (c - 1)))) {
                        //    possibleTopLeftEdges.add(new String((r - 1) + "," + (c - 1)));
                        //}
                    }
                }
                if (c + 1 < col && r - 1 >= 0) {
                    if ((!grid[r][c + 1].getBackground().equals(color) && !grid[r - 1][c].getBackground().equals(color) && grid[r - 1][c + 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r - 1) + "," + (c + 1))))) {
                            toReturn.add((new String((r - 1) + "," + (c + 1))));
                        }
                        //if (!possibleBottomLeftEdges.contains((new String((r - 1) + "," + (c + 1))))) {
                        //   possibleBottomLeftEdges.add((new String((r - 1) + "," + (c + 1))));
                        //}
                    }
                }
                if (c - 1 >= 0 && r + 1 < row) {
                    if ((!grid[r][c - 1].getBackground().equals(color) && !grid[r + 1][c].getBackground().equals(color) && grid[r + 1][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r + 1) + "," + (c - 1))))) {
                            toReturn.add((new String((r + 1) + "," + (c - 1))));
                        }
                        //if (!possibleTopRightEdges.contains((new String((r + 1) + "," + (c - 1))))) {
                        //   possibleTopRightEdges.add((new String((r + 1) + "," + (c - 1))));
                        //}
                    }
                }
                if (c + 1 < col && r + 1 < row) {
                    if ((!grid[r][c + 1].getBackground().equals(color) && grid[r + 1][c + 1].isEnabled() && !grid[r + 1][c].getBackground().equals(color)) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r + 1) + "," + (c + 1))))) {
                            toReturn.add((new String((r + 1) + "," + (c + 1))));
                        }
                        //if (!possibleBottomRightEdges.contains((new String((r + 1) + "," + (c + 1))))) {
                         //  possibleBottomRightEdges.add((new String((r + 1) + "," + (c + 1))));
                        //}
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

    private static ArrayList<String> calculateBoardSide(JButton[][] grid, Color color) {
        ArrayList<String> toReturn = new ArrayList<>();
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

    public static void setCurrentTurn(int saved_turn_index){
        turn_index=saved_turn_index;
        currentTurn = Options.getTurnOrderAccordingToColors(turn_index);
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        //TODO WHAT IF GAME WAS SAVED DURING AN AI MOVE
    }

    public static void setAlternateTurn(int alternate_turn){
        alternateTurn = alternate_turn;
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

        currentTurn = Options.getTurnOrderAccordingToColors(turn_index);
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        selectedPiece = null;
        //AI.makeMove(currentTurn);

        //IF AI TURN
        if (isAITurn(currentTurn)) {
            //AI turn here
            //TODO: AI should make move using current turn
           PlayerGrid.disableAllPlayerGrids();
            //AI TURN IS OVER WHEN updateCurrentTurn() is called again
        }

        //if game has alternate player & alternate player turn
        else if (Options.hasAlternatePlayer() && currentTurn == 4) {

            //update alternate label to the player who should make the alternate move
            Player.getPlayer(4).setName(Player.getPlayer(alternateTurn).getPlayerName());
            GameGUI.updateLabels();

            //if AI turn to play alternate player
            if (isAITurn(alternateTurn)) {
                //TODO: here AI function to play alternate turn here
                PlayerGrid.disableAllPlayerGrids();
                //AI should play using the current turn
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
        //TODO: this checks whether the game has ended or not.
        //checkValidForEachPlayer();
        hasGameEnded();
        Piece.resetActionList();
    }


    private static boolean isOnCorner(int[] array, String selectedPoint) {
        String[] strArr = selectedPoint.split(",");
        int r = Integer.parseInt(strArr[0]);
        int c = Integer.parseInt(strArr[1]);
        for (int[] action : Piece.getActionsList(selectedPiece)) {
            if (isWithinGrid(selectedPoint,action,MainGrid.getMainGridButtons())&&!isOccupied(selectedPoint, MainGrid.getMainGridButtons())){
                if (((r + action[1]) == array[0]) && ((c + action[0]) == array[1])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static Integer getSelectedPiece() {
        if (selectedPiece == null) {
            //System.out.println("Error in getSelectedPiece, no piece was selected");
            //JOptionPane.showMessageDialog(null,"Error in getSelectedPiece, no piece was selected");
        }
        return selectedPiece;
    }


    static void setSelectedPiece(Integer piece_index) {
        selectedPiece = piece_index;
    }


    private static HashMap<Integer, Integer> playerScoring() {
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
    }

    public static int getTurnIndex(){
        return turn_index;
    }

    private static void gameEnd(){
        if(GameEngine.getGameEnded()){
            new GameOver(playerScoring());
        }
    }

    public static int getAlternateTurn() {
        return alternateTurn;
    }

    private static ArrayList<String> calculatedEnabledButtonCoordinates(){
        JButton[][] grid = MainGrid.getMainGridButtons();
        ArrayList<String> toReturn = new ArrayList<>();
        for (int row = 0; row < 20; row++) {
            for (int col = 0; col < 20; col++) {
                if (grid[row][col].isEnabled()){
                    toReturn.add(row+","+col);
                }
            }
        }
        return toReturn;
    }
    private static void hasGameEnded(){
        enabledButtonCoordinates = calculatedEnabledButtonCoordinates();
        doesPlayerHasMove.put(1,false);
        doesPlayerHasMove.put(2,false);
        doesPlayerHasMove.put(3,false);
        doesPlayerHasMove.put(4,false);
        JButton[][] grid = MainGrid.getMainGridButtons();
        for (int player_index = 1;player_index<=4;player_index++){
            calculatePossibleSidesAndEdges(player_index);
            ArrayList<Integer> availablePieces = Player.getPlayer(player_index).getAvailablePieces();
            outerloop:
            for (int piece_index:availablePieces) {
                if (canAPieceBePlaced(piece_index, player_index)) {
                    doesPlayerHasMove.put(player_index, true);
                    break outerloop;
                }
            }

        }
        if (!doesPlayerHasMove.get(1)&&!doesPlayerHasMove.get(2)&&!doesPlayerHasMove.get(3)&&!doesPlayerHasMove.get(4)){
            setGameEnded(true);
        }
    }


    private static boolean canAPieceBePlaced(Integer piece_index, Integer player_index){
        Integer originalPieceIndex = GameEngine.getSelectedPiece();
        GameEngine.setSelectedPiece(piece_index);
        int saveturn = currentTurn;
        currentTurn = player_index;

        boolean toReturn = false;

        Piece.resetActionList();
        outerloop:
        for (int rotate = 1; rotate <= 4; rotate++) {
            Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(piece_index)));
            for (int flipRight = 1; flipRight <= 2; flipRight++) {
                Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(piece_index)));
                for (int flipUp = 1; flipUp <= 2; flipUp++) {
                    Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(piece_index)));
                    //Piece.setActionList(Piece.getRotateFlipUpFlipRightActionList(piece_index,rotate,flipRight,flipUp));
                    String selectedPoint = calculatedSelectedPoint(piece_index);
                    if (selectedPoint == null){
                        toReturn = false;
                    }
                    else {
                        toReturn = true;
                        System.out.println("Player " + player_index+" Piece "+piece_index+" SelectedPoint "+selectedPoint+" Rotate "+rotate+" flipRight "+flipRight+" flipUp "+flipUp);
                        break outerloop;
                    }
                }
            }
        }
        Piece.resetActionList();
        GameEngine.setSelectedPiece(originalPieceIndex);
        currentTurn = saveturn;
        return toReturn;

    }

    private static String calculatedSelectedPoint(int piece_index){
        String toReturn = null;
        for(String selectedPoint:enabledButtonCoordinates){
            if(isLegal(selectedPoint)){
                return selectedPoint;
            }
        }
        return toReturn;
    }


    //FOR AI
//    public static HashMap<Integer,ArrayList<String[]>> moveThatBlockOtherPlayerEdges(ArrayList<Integer> available_pieces ,HashMap<Integer,ArrayList<String[]>> map,int turn){
//        HashMap<Integer,ArrayList<String[]>> toReturn= new HashMap<>();
//        ArrayList<Integer> turn_list = new ArrayList<>(Arrays.asList(1,2,3,4));
//        turn_list.remove(turn);
//        ArrayList<String> OtherPlayerEdges = new ArrayList<>();
//        turn_list.forEach(index->OtherPlayerEdges.addAll(calculateBoardEdge(MainGrid.getMainGridButtons(),Options.getColor(index))));
//        available_pieces.forEach(piece->{
//            if (map.containsKey(piece)){
//                map.get(piece).forEach(strings -> {
//                    if(OtherPlayerEdges.contains(strings[0])){
//                        toReturn.put(piece,map.get(piece));
//                    }
//                });
//            }
//        });
//        return toReturn;
//    }

    public static boolean isAITurn(int turnIndex){
        return Options.getAI_player_index_List().contains(turnIndex);
    }

    private static Boolean getGameEnded() {
        return gameEnded;
    }

    public static void setGameEnded(Boolean gameEnded) {
        GameEngine.gameEnded = gameEnded;
        gameEnd();
    }
}