//TODO restructure and move code to appropriate class
abstract class AI {
    private static int currentTurn = -1;
    public static void makeMove(int current_turn){
        currentTurn = current_turn;
        switch (Options.getDifficulty()){
            case "Easy":
                AI.easyMove();
                break;
            case "Medium":
                AI.mediumMove();
            case "Hard":
                AI.hardMove();
        }
    }

    private static void easyMove(){
    }

    private static void mediumMove(){
    }

    private static void hardMove(){
    }

}
