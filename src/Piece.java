import com.sun.jdi.Value;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

abstract class Piece {

    private static HashMap<String,Integer> PIECE_MAP = createPieceMap(); //button coordinates to piece number
    private static ArrayList<Integer> PIECE_LIST = createPieceList(); //piece number
    private static HashMap<Integer, ArrayList<String>> pieceNumberToStringMap = createPieceNumberToStringMap();
    private static HashMap<Integer, ArrayList<int[]>> actionList = createActionsListMap();
    private static  HashMap<Integer,HashMap<String, ArrayList<int[]>>> rotateFlipUpFlipRight = createRotateFlipUpFlipRightMap();

    private static HashMap<Integer, ArrayList<int[]>> createActionsListMap(){
        HashMap<Integer, ArrayList<int[]>> toReturn = new HashMap<>();
        ArrayList<int[]> actionsList;
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0},new int[]{1,0},new int[]{2,0}));
        toReturn.put(0,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0},new int[]{1,0},new int[]{2,0},new int[]{2,-1}));
        toReturn.put(1,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0},new int[]{1,0},new int[]{2,0},new int[]{1,-1}));
        toReturn.put(2,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,-1}));
        toReturn.put(3,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{1,0},new int[]{1,1},new int[]{0,-1}));
        toReturn.put(4,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,-1},new int[]{2,-1}));
        toReturn.put(5,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{-1,1},new int[]{0,-1},new int[]{1,-1}));
        toReturn.put(6,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,0},new int[]{-1,0}));
        toReturn.put(7,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,0}));
        toReturn.put(8,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,1},new int[]{1,-1}));
        toReturn.put(9,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{-1,1},new int[]{-1,0}));
        toReturn.put(10,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,1},new int[]{-1,1}));
        toReturn.put(11,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{-1,1},new int[]{1,0},new int[]{0,-1}));
        toReturn.put(12,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{-2,0},new int[]{-1,0}));
        toReturn.put(13,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{-1,0}));
        toReturn.put(14,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{1,0}));
        toReturn.put(15,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{1,0}));
        toReturn.put(16,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0}));
        toReturn.put(17,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,0},new int[]{2,0},new int[]{-1,0},new int[]{-2,0}));
        toReturn.put(18,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,0},new int[]{-1,0}));
        toReturn.put(19,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0}));
        toReturn.put(20,actionsList);
        return toReturn;
    }

    private static HashMap<Integer, ArrayList<String>> createPieceNumberToStringMap(){
        HashMap<Integer, ArrayList<String>> toReturn = new HashMap<>();
        for (int i = 0; i < 21; i++) {
            ArrayList<String> temp = new ArrayList<>();
            if (i == 0) {
                temp.add("0,0");
                temp.add("0,1");
                temp.add("0,2");
                temp.add("0,3");
            } else if (i == 1) {
                temp.add("0,5");
                temp.add("0,6");
                temp.add("0,7");
                temp.add("0,8");
                temp.add("1,8");
            } else if (i == 2) {
                temp.add("0,11");
                temp.add("0,12");
                temp.add("0,13");
                temp.add("0,14");
                temp.add("1,13");
            } else if (i == 3) {
                temp.add("3,0");
                temp.add("4,0");
                temp.add("5,0");
                temp.add("5,1");
            }
            if (i == 4) {
                temp.add("3,3");
                temp.add("3,4");
                temp.add("4,3");
                temp.add("4,4");
                temp.add("5,3");
            } else if (i == 5) {
                temp.add("3,6");
                temp.add("4,6");
                temp.add("5,6");
                temp.add("5,7");
                temp.add("5,8");
            } else if (i == 6) {
                temp.add("3,10");
                temp.add("3,11");
                temp.add("4,11");
                temp.add("5,11");
                temp.add("5,12");
            } else if (i == 7) {
                temp.add("3,15");
                temp.add("4,15");
                temp.add("5,15");
                temp.add("4,14");
                temp.add("4,16");
            } else if (i == 8) {
                temp.add("7,0");
                temp.add("8,0");
                temp.add("9,0");
                temp.add("8,1");
            } else if (i == 9) {
                temp.add("7,3");
                temp.add("7,4");
                temp.add("8,3");
                temp.add("9,3");
                temp.add("9,4");
            } else if (i == 10) {
                temp.add("7,6");
                temp.add("8,6");
                temp.add("8,7");
                temp.add("9,7");
                temp.add("9,8");
            } else if (i == 11) {
                temp.add("7,10");
                temp.add("7,11");
                temp.add("7,12");
                temp.add("8,11");
                temp.add("9,11");
            } else if (i == 12) {
                temp.add("7,14");
                temp.add("7,15");
                temp.add("8,15");
                temp.add("9,15");
                temp.add("8,16");
            } else if (i == 13) {
                temp.add("11,0");
                temp.add("11,1");
                temp.add("11,2");
                temp.add("12,2");
                temp.add("12,3");
            } else if (i == 14) {
                temp.add("11,5");
                temp.add("11,6");
                temp.add("12,6");
                temp.add("12,7");
            } else if (i == 15) {
                temp.add("12,9");
                temp.add("11,9");
                temp.add("12,10");
            } else if (i == 16) {
                temp.add("11,12");
                temp.add("11,13");
                temp.add("12,12");
                temp.add("12,13");
            } else if (i == 17) {
                temp.add("11,15");
            } else if (i == 18) {
                temp.add("14,0");
                temp.add("14,1");
                temp.add("14,2");
                temp.add("14,3");
                temp.add("14,4");
            } else if (i == 19) {
                temp.add("14,6");
                temp.add("14,7");
                temp.add("14,8");
            } else if (i == 20) {
                temp.add("14,10");
                temp.add("14,11");
            }
            toReturn.put(i, temp);
        }
        return toReturn;
    }

    public static ArrayList<int[]> getActionsList(int piece_index){
        if(actionList.containsKey(piece_index)){
            return actionList.get(piece_index);
        }
        System.out.println("Piece number not found, getStaticActionList");
        //JOptionPane.showMessageDialog(null, "Error in getActionsList, piece index not found");
        return null;
    }

    public static HashMap<String,Integer> getPieceMap(){
        return PIECE_MAP;
    }

    public static ArrayList<Integer> getPieceList(){
        return PIECE_LIST;
    }

    public static ArrayList<String> getPieceDisplayCoordinates(int piece_index){
        if(pieceNumberToStringMap.containsKey(piece_index)){
            return pieceNumberToStringMap.get(piece_index);
        }
        //JOptionPane.showMessageDialog(null, "Error in getPieceDisplayCoordinates, piece index not found");
        System.out.println("Error in getPieceDisplayCoordinates, piece index not found");
        return null;
    }

    private static HashMap<String, Integer> createPieceMap(){
        HashMap<String, Integer> toReturn = new HashMap<>();
        for (int i = 0; i < 21; i++) {
            if (i == 0) {
                toReturn.put("0,0",i);
                toReturn.put("0,1",i);
                toReturn.put("0,2",i);
                toReturn.put("0,3",i);
            } else if (i == 1) {
                toReturn.put("0,5",i);
                toReturn.put("0,6",i);
                toReturn.put("0,7",i);
                toReturn.put("0,8",i);
                toReturn.put("1,8",i);
            } else if (i == 2) {
                toReturn.put("0,11",i);
                toReturn.put("0,12",i);
                toReturn.put("0,13",i);
                toReturn.put("0,14",i);
                toReturn.put("1,13",i);
            } else if (i == 3) {
                toReturn.put("3,0",i);
                toReturn.put("4,0",i);
                toReturn.put("5,0",i);
                toReturn.put("5,1",i);
            }
            if (i == 4) {
                toReturn.put("3,3",i);
                toReturn.put("3,4",i);
                toReturn.put("4,3",i);
                toReturn.put("4,4",i);
                toReturn.put("5,3",i);
            } else if (i == 5) {
                toReturn.put("3,6",i);
                toReturn.put("4,6",i);
                toReturn.put("5,6",i);
                toReturn.put("5,7",i);
                toReturn.put("5,8",i);
            } else if (i == 6) {
                toReturn.put("3,10",i);
                toReturn.put("3,11",i);
                toReturn.put("4,11",i);
                toReturn.put("5,11",i);
                toReturn.put("5,12",i);
            } else if (i == 7) {
                toReturn.put("3,15",i);
                toReturn.put("4,15",i);
                toReturn.put("5,15",i);
                toReturn.put("4,14",i);
                toReturn.put("4,16",i);
            } else if (i == 8) {
                toReturn.put("7,0",i);
                toReturn.put("8,0",i);
                toReturn.put("9,0",i);
                toReturn.put("8,1",i);
            } else if (i == 9) {
                toReturn.put("7,3",i);
                toReturn.put("7,4",i);
                toReturn.put("8,3",i);
                toReturn.put("9,3",i);
                toReturn.put("9,4",i);
            } else if (i == 10) {
                toReturn.put("7,6",i);
                toReturn.put("8,6",i);
                toReturn.put("8,7",i);
                toReturn.put("9,7",i);
                toReturn.put("9,8",i);
            } else if (i == 11) {
                toReturn.put("7,10",i);
                toReturn.put("7,11",i);
                toReturn.put("7,12",i);
                toReturn.put("8,11",i);
                toReturn.put("9,11",i);
            } else if (i == 12) {
                toReturn.put("7,14",i);
                toReturn.put("7,15",i);
                toReturn.put("8,15",i);
                toReturn.put("9,15",i);
                toReturn.put("8,16",i);
            } else if (i == 13) {
                toReturn.put("11,0",i);
                toReturn.put("11,1",i);
                toReturn.put("11,2",i);
                toReturn.put("12,2",i);
                toReturn.put("12,3",i);
            } else if (i == 14) {
                toReturn.put("11,5",i);
                toReturn.put("11,6",i);
                toReturn.put("12,6",i);
                toReturn.put("12,7",i);
            } else if (i == 15) {
                toReturn.put("12,9",i);
                toReturn.put("11,9",i);
                toReturn.put("12,10",i);
            } else if (i == 16) {
                toReturn.put("11,12",i);
                toReturn.put("11,13",i);
                toReturn.put("12,12",i);
                toReturn.put("12,13",i);
            } else if (i == 17) {
                toReturn.put("11,15",i);
            } else if (i == 18) {
                toReturn.put("14,0",i);
                toReturn.put("14,1",i);
                toReturn.put("14,2",i);
                toReturn.put("14,3",i);
                toReturn.put("14,4",i);
            } else if (i == 19) {
                toReturn.put("14,6",i);
                toReturn.put("14,7",i);
                toReturn.put("14,8",i);
            } else if (i == 20) {
                toReturn.put("14,10",i);
                toReturn.put("14,11",i);
            }
        }
        return toReturn;
    }

    private static ArrayList<Integer> createPieceList(){
        ArrayList<Integer> toReturn = new ArrayList<>();
        for (int i = 0; i < 21; i++) {
            toReturn.add(i);
        }
        return toReturn;
    }

    public static void setActionList(ArrayList<int[]> ar){

        if(actionList.containsKey(GameEngine.getSelectedPiece())){
            actionList.put(GameEngine.getSelectedPiece(), ar);
        }
        //JOptionPane.showMessageDialog(null, "Error in getActionsList, piece index not found");
        //System.out.println("Error in setActionsList, piece index, current player error not found");
    }

    public static void resetActionList(){
        actionList = createActionsListMap();
    }

    public static HashMap<Integer, HashMap<String, ArrayList<int[]>>> createRotateFlipUpFlipRightMap(){
        HashMap<Integer,HashMap<String, ArrayList<int[]>>> toReturnMaster = new HashMap<>();
        for (int piece_index=0;piece_index<21;piece_index++){
            Piece.resetActionList();
            HashMap<String, ArrayList<int[]>> toReturn = new HashMap<>();
            for (int rotate=0;rotate<=3;rotate++){
                for (int flipUp=0;flipUp<=1;flipUp++){
                    for (int flipRight=0;flipRight<=1;flipRight++){
                        toReturn.put((String.valueOf(rotate)+String.valueOf(flipUp)+String.valueOf(flipRight)),Piece.getActionsList(piece_index));
                        Piece.setActionList(SelectedPiece.flipRight(Piece.getActionsList(piece_index)));
                    }
                    Piece.setActionList(SelectedPiece.flipUp(Piece.getActionsList(piece_index)));
                }
                Piece.setActionList(SelectedPiece.rotateCounterClock(Piece.getActionsList(piece_index)));
            }
            toReturnMaster.put(piece_index,toReturn);
        }
        return toReturnMaster;
    }

    public static ArrayList<int[]> getRotateFlipUpFlipRightActionList(int piece_index,int rotate,int flipUp,int flipRight) {
        String key = String.valueOf(rotate)+String.valueOf(flipUp)+String.valueOf(flipRight);
        ArrayList<int[]> toReturn = new ArrayList<>();
        if (rotateFlipUpFlipRight.containsKey(piece_index)){
            if (rotateFlipUpFlipRight.get(piece_index).containsKey(key)){
                toReturn = rotateFlipUpFlipRight.get(piece_index).get(key);
            }
        }
        return toReturn;
    }
}


