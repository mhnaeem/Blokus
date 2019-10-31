import java.util.ArrayList;
import java.util.HashMap;

public class PiecesMonitor {
    // Player Number and Piece numbers available
    private static HashMap<Integer,ArrayList<Integer>> availablePiecesMap = new HashMap<>();
    //Player index and selected piece index
    private static HashMap<Integer,Integer> selectedPiecesMap = new HashMap<>();
    public PiecesMonitor(){
        for(int i=1;i<=4;i++){
            availablePiecesMap.put(i,Piece.getPieceList());
            selectedPiecesMap.put(i,null);
        }
    }

    //Player number and piece number
    public static void removePiece(int index,int pieceNumber){
        availablePiecesMap.get(index).remove(pieceNumber);
        selectedPiecesMap.put(index,null);
    }

    public static ArrayList<Integer> getAvailablePieces(int index){
        return availablePiecesMap.get(index);
    }

    public static void setSelectedPiece(int index,int pieceNumber) {
        if (pieceNumber == -1) {
            selectedPiecesMap.put(index, null);
        } else {
            selectedPiecesMap.put(index, pieceNumber);
        }
    }

    public static int getSelectedPiece(int index){
        if (selectedPiecesMap.get(index)==null){
            return -1;
        }
        else{
            return selectedPiecesMap.get(index);
        }
    }


    //for testing returns first available piece for testing
    public static int getSelectedPiece(){
        if (selectedPiecesMap.get(1)==null){
            if (selectedPiecesMap.get(2)==null){
                if (selectedPiecesMap.get(3)==null){
                    if (selectedPiecesMap.get(4)==null){
                        return -1;//no available pieces
                    }
                    return selectedPiecesMap.get(4);
                }
                return selectedPiecesMap.get(3);
            }
            return selectedPiecesMap.get(2);
        }
        return selectedPiecesMap.get(1);
    }
}

