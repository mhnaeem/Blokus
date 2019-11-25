import javax.swing.JButton;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
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
    private static int currentTurn=0;
    private static int turn_index = 0;
    private static int alternateTurn = 1;
    public static int savedTurn= -1;
    private static ArrayList<String> possibleEdges = new ArrayList<>();
    private static ArrayList<String> possibleSides = new ArrayList<>();
    private static ArrayList<String> enabledButtonCoordinates = new ArrayList<>();
    private static HashMap<Integer,Boolean> doesPlayerHasMove = new HashMap<>();
    private static Boolean gameEnded = false;
    //Player Index, Piece Index. Gives the first possible piece that is placeable
    private static HashMap<Integer,Integer> easyPlayableMap = new HashMap<>();

    //Player Number, {Piece Number, [skipRotate, skipFlipRight, skipFlipUp]}
    private static HashMap<Integer, HashMap<Integer, Boolean[]>> directionForEachPlayer = setDirectionsForEachPlayer();

    public GameEngine() {
        gameEnded =false;
        selectedPiece = null;
        turn_index = 0;
        currentTurn = Options.getTurnOrderAccordingToColors(turn_index);
        savedTurn = currentTurn;
        PlayerGrid.disableOtherPlayerGrids(currentTurn);
        alternateTurn = 1;
        hasGameEnded();
        if (isAITurn(currentTurn)){
            PlayerGrid.disableAllPlayerGrids();
            AI.makeMove();
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

    public static boolean isWithinGrid(String point, int[] action, JButton[][] grid) {
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

    public static boolean isOccupied(String point, JButton[][] grid) {
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
        int row = grid[0].length;
        int col = grid[1].length;
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                if (c - 1 >= 0 && r - 1 >= 0) {
                    if ((!grid[r][c - 1].getBackground().equals(color) && !grid[r - 1][c].getBackground().equals(color) && grid[r - 1][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains(new String((r - 1) + "," + (c - 1)))) {
                            toReturn.add(new String((r - 1) + "," + (c - 1)));
                        }
                    }
                }
                if (c + 1 < col && r - 1 >= 0) {
                    if ((!grid[r][c + 1].getBackground().equals(color) && !grid[r - 1][c].getBackground().equals(color) && grid[r - 1][c + 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r - 1) + "," + (c + 1))))) {
                            toReturn.add((new String((r - 1) + "," + (c + 1))));
                        }
                    }
                }
                if (c - 1 >= 0 && r + 1 < row) {
                    if ((!grid[r][c - 1].getBackground().equals(color) && !grid[r + 1][c].getBackground().equals(color) && grid[r + 1][c - 1].isEnabled()) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
                        if (!toReturn.contains((new String((r + 1) + "," + (c - 1))))) {
                            toReturn.add((new String((r + 1) + "," + (c - 1))));
                        }
                    }
                }
                if (c + 1 < col && r + 1 < row) {
                    if ((!grid[r][c + 1].getBackground().equals(color) && grid[r + 1][c + 1].isEnabled() && !grid[r + 1][c].getBackground().equals(color)) && (!grid[r][c].isEnabled() && (grid[r][c].getBackground().equals(color)))) {
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
    }

    public static void setAlternateTurn(int alternate_turn){
        alternateTurn = alternate_turn;
    }

    public static void updateCurrentTurn() {

        if (gameEnded){
            return;
        }

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
        savedTurn = currentTurn;
        PlayerGrid.disableOtherPlayerGrids(currentTurn);

        hasGameEnded();
        if (gameEnded){
            return;
        }
        Piece.resetActionList();
        // current turn to use it in GameGUI class
        GameGUI.setPlayerTurnTopPanel(currentTurn);

        if(Player.getPlayer(currentTurn).getAvailablePieces().isEmpty() || !Player.getPlayer(currentTurn).isOutOfGame()) {

            if(Player.getPlayer(currentTurn).getAvailablePieces().isEmpty()){
                SelectedPiece.pass.doClick();
            }

            if (isAITurn(currentTurn)) {
                PlayerGrid.disableAllPlayerGrids();
                AI.makeMove();
                return;
            }

            //if game has alternate player & alternate player turn
            else if (Options.hasAlternatePlayer() && currentTurn == 4) {

                //update alternate label to the player who should make the alternate move
                Player.getPlayer(4).setName(Player.getPlayer(alternateTurn).getPlayerName());
                GameGUI.updateLabels();

                //if AI turn to play alternate player
                if (isAITurn(alternateTurn)) {
                    //TODO: here AI function to play alternate turn here
                    alternateTurn++;
                    if (alternateTurn >= 4) {
                        alternateTurn = 1;
                    }
                    PlayerGrid.disableAllPlayerGrids();
                    AI.makeMove();
                    return;
                } else {
                    //AI turn played by human
                    alternateTurn++;
                    if (alternateTurn >= 4) {
                        alternateTurn = 1;
                    }
                }
            }
        }
        else{
            if(!isAITurn(currentTurn)){
                new SelectedPiece(currentTurn, "0,0",PlayerGrid.getPlayerGridPanel(currentTurn));
            }
            else {
                SelectedPiece.pass.doClick();
            }
        }
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
        int numPlayers = Options.getRealNumberOfPlayers();

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
                    toReturn.add(row + "," + col);
                }
            }
        }
        return toReturn;
    }

    private static void hasGameEnded(){
        enabledButtonCoordinates = calculatedEnabledButtonCoordinates();

        for (int player = 1; player <= 4; player++) {
            doesPlayerHasMove.put(player,false);
            easyPlayableMap.put(player,-1);
        }

        for (int player_index = 1; player_index <= 4; player_index++){

            if(Player.getPlayer(player_index).isOutOfGame()){
                continue;
            }

            calculatePossibleSidesAndEdges(player_index);
            ArrayList<Integer> availablePieces = Player.getPlayer(player_index).getAvailablePieces();

            outerloop:
            for (int piece_index : availablePieces) {
                if (canAPieceBePlaced(piece_index, player_index)) {
                    doesPlayerHasMove.put(player_index, true);
                    easyPlayableMap.put(player_index,piece_index);
                    break outerloop;
                }
            }

            //TODO: Testing
            if(!doesPlayerHasMove.get(player_index)){
                Player.getPlayer(player_index).setOutOfGame(true);
            }

        }

        if ( !doesPlayerHasMove.get(1) && !doesPlayerHasMove.get(2) && !doesPlayerHasMove.get(3) && !doesPlayerHasMove.get(4)){
            setGameEnded(true);
        }
    }

    private static boolean canAPieceBePlaced(Integer piece_index, Integer player_index){

        Integer originalPieceIndex = GameEngine.getSelectedPiece();
        GameEngine.setSelectedPiece(piece_index);
        savedTurn = currentTurn;
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
                    String selectedPoint = calculatedSelectedPoint();

                    if (selectedPoint == null){
                        toReturn = false;
                        //System.out.println("There is no point available for Piece " + piece_index + " Player: " + player_index);
                    }
                    else {
                        toReturn = true;
                        System.out.println("Player " + player_index + " Piece " + piece_index + " SelectedPoint " + selectedPoint + " Rotate " + rotate + " flipRight " + flipRight + " flipUp " + flipUp);
                        break outerloop;
                    }
                }
            }
        }

        Piece.resetActionList();
        GameEngine.setSelectedPiece(originalPieceIndex);
        currentTurn = savedTurn;
        return toReturn;

    }

    private static String calculatedSelectedPoint(){
        for(String selectedPoint : enabledButtonCoordinates){
            if(isLegal(selectedPoint)){
                return selectedPoint;
            }
        }
        return null;
    }

   public static HashMap<Integer, String[]> moveThatBlockOtherPlayerEdges(ArrayList<Integer> available_pieces , HashMap<Integer,ArrayList<String[]>> map, int turn){
       HashMap<Integer,String[]> toReturn = new HashMap<>();
       ArrayList<Integer> turn_list = new ArrayList<>(Arrays.asList(1,2,3,4));
       Integer remove = turn_list.remove(turn-1);
       ArrayList<String> OtherPlayerEdges = new ArrayList<>();
       turn_list.forEach(index -> OtherPlayerEdges.addAll(calculateBoardEdge(MainGrid.getMainGridButtons(),Options.getColor(index))));
       for (int piece : available_pieces){
               map.get(piece).forEach(strings -> {
                   Piece.resetActionList();
                   String selectedPoint = strings[0];
                   int rotate = Integer.parseInt(strings[1]);
                   int flipRight = Integer.parseInt(strings[2]);
                   int flipUp = Integer.parseInt(strings[3]);

                   for (int rot = 1; rot <= rotate; rot++){
                       Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(piece)));
                   }
                   for (int fr = 1; fr <= flipRight; fr++){
                       Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(piece)));
                   }
                   for (int fu = 1; fu <= flipUp; fu++){
                       Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(piece)));
                   }
                   String[] button = selectedPoint.split(",");
                   int r = Integer.parseInt(button[0]);
                   int c = Integer.parseInt(button[1]);
                   for (int[] action : Piece.getActionsList(piece)){
                       String point = (r + action[1]) + "," + (c + action[0]);
                       if(OtherPlayerEdges.contains(point)){
                           String[] tReturn = new String[] {selectedPoint,String.valueOf(rotate),String.valueOf(flipRight),String.valueOf(flipUp)};
                           toReturn.put(piece,tReturn);
                           break;
                       }
                   }
               });
           }
       Piece.resetActionList();
       return toReturn;
   }

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

    public static ArrayList<String[]> getPossibleAIMoves(int piece_index){
        ArrayList<String[]> toReturn = new ArrayList<>();
        Integer originalPieceIndex = GameEngine.getSelectedPiece();
        GameEngine.setSelectedPiece(piece_index);
        Piece.resetActionList();

        boolean broke = false;
        boolean skipRotate = directionForEachPlayer.get(currentTurn).get(piece_index)[0];
        boolean skipFlipRight = directionForEachPlayer.get(currentTurn).get(piece_index)[1];
        boolean skipFlipUp = directionForEachPlayer.get(currentTurn).get(piece_index)[2];

        if(!skipRotate && !skipFlipRight && !skipFlipUp) {
            for (String selectedPoint : enabledButtonCoordinates) {
                if (isLegal(selectedPoint)) {
                    toReturn.add(new String[]{selectedPoint, "0", "0", "0"});
                    broke = true;
                    break;
                }
            }
        }
        if(!broke) {
            broke = false;
            for (int rotate = 1; rotate <= 4; rotate++) {
                Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(piece_index)));
                if(!skipRotate) {
                    for (String selectedPoint : enabledButtonCoordinates) {
                        if (isLegal(selectedPoint)) {
                            toReturn.add(new String[]{selectedPoint, String.valueOf(rotate), "0", "0"});
                            broke = true;
                            break;
                        }
                    }
                }
                if(!broke){
                    skipRotate = directionForEachPlayer.get(currentTurn).get(piece_index)[0] = true;
                }

                if (skipRotate) {
                    broke = false;
                    for (int flipRight = 1; flipRight <= 2; flipRight++) {
                        Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(piece_index)));
                        if(!skipFlipRight) {
                            for (String selectedPoint : enabledButtonCoordinates) {
                                if (isLegal(selectedPoint)) {
                                    toReturn.add(new String[]{selectedPoint, String.valueOf(rotate), String.valueOf(flipRight), "0"});
                                    broke = true;
                                    break;
                                }
                            }
                        }
                        if(!broke){
                            skipFlipRight = directionForEachPlayer.get(currentTurn).get(piece_index)[1] = true;
                        }
                        if(skipFlipRight) {
                            for (int flipUp = 1; flipUp <= 2; flipUp++) {
                                Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(piece_index)));
                                for (String selectedPoint : enabledButtonCoordinates) {
                                    if (isLegal(selectedPoint)) {
                                        toReturn.add(new String[]{selectedPoint, String.valueOf(rotate), String.valueOf(flipRight), String.valueOf(flipUp)});
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        Piece.resetActionList();
        GameEngine.setSelectedPiece(originalPieceIndex);
        return toReturn;
    }

    public static HashMap<Integer,Integer> getEasyPlayableMap(){
        return easyPlayableMap;
    }

    private static HashMap<Integer, HashMap<Integer, Boolean[]>> setDirectionsForEachPlayer(){
        HashMap<Integer, HashMap<Integer, Boolean[]>> toReturn = new HashMap<>();
        for (int player = 1; player <= 4; player++) {
            HashMap<Integer, Boolean[]> mapToAdd = new HashMap<>();
            for (int piece = 0; piece < 21; piece++) {
                Boolean[] arrayToAdd = new Boolean[]{false, false, false};
                mapToAdd.put(piece, arrayToAdd);
            }
            toReturn.put(player, mapToAdd);
        }

        return toReturn;
    }
}