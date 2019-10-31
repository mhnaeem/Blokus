import javax.swing.*;
import java.util.*;

import static java.util.Map.entry;

public  class Piece {
    private ArrayList<int[]> actionsList;
    private static HashMap<Integer,ArrayList<int[]>> actionsListMap = new HashMap<>(); //piece number -> actionList[]
    private static HashMap<String,Integer> PIECE_MAP; //button coordinates to piece number
    private static ArrayList<Integer> PIECE_LIST = new ArrayList<>(); //piece number
    private static HashMap<Integer, JButton[][]> playerGridMap = new HashMap<>();
    public Piece(){
        PIECE_MAP = createPieceMap();
        for (int i=0;i<21;i++){
            PIECE_LIST.add(i);
        }
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0},new int[]{1,0},new int[]{2,0}));;
        actionsListMap.put(0,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0},new int[]{1,0},new int[]{2,0},new int[]{2,-1}));;
        actionsListMap.put(1,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0},new int[]{1,0},new int[]{2,0},new int[]{1,-1}));;
        actionsListMap.put(2,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,-1}));;
        actionsListMap.put(3,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{1,0},new int[]{1,1},new int[]{0,-1}));;
        actionsListMap.put(4,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,-1},new int[]{2,-1}));;
        actionsListMap.put(5,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{-1,1},new int[]{0,-1},new int[]{1,-1}));;
        actionsListMap.put(6,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,0},new int[]{-1,0}));
        actionsListMap.put(7,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,0}));
        actionsListMap.put(8,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,1},new int[]{1,-1}));
        actionsListMap.put(9,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{-1,1},new int[]{-1,0}));
        actionsListMap.put(10,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{0,-1},new int[]{1,1},new int[]{-1,1}));
        actionsListMap.put(11,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{-1,1},new int[]{1,0},new int[]{0,-1}));
        actionsListMap.put(12,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{-2,0},new int[]{-1,0}));
        actionsListMap.put(13,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{-1,0}));
        actionsListMap.put(14,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{0,1},new int[]{1,0}));
        actionsListMap.put(15,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,-1},new int[]{0,-1},new int[]{1,0}));
        actionsListMap.put(16,actionsList);
        actionsList = new ArrayList<>();
        actionsListMap.put(17,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,0},new int[]{2,0},new int[]{-1,0},new int[]{-2,0}));
        actionsListMap.put(18,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{1,0},new int[]{-1,0}));
        actionsListMap.put(19,actionsList);
        actionsList = new ArrayList<>(Arrays.asList(new int[]{0,0},new int[]{-1,0}));
        actionsListMap.put(20,actionsList);
    }

    public static HashMap<Integer,ArrayList<int[]>> getActionsListMap(){
        return actionsListMap;
    }


    public static HashMap<String,Integer> getPieceMap(){
        return PIECE_MAP;
    }

    public static ArrayList<Integer> getPieceList(){
        return PIECE_LIST;
    }

    public static HashMap<Integer, JButton[][]> getPlayerGridMap(){
        return playerGridMap;
    }

    private static HashMap<String, Integer> createPieceMap(){
        return new HashMap<>(Map.ofEntries(entry("0,0", 0),entry("0,1", 0),entry("0,2", 0),entry("0,3", 0),entry("0,5", 1),entry("0,6", 1),entry("0,7", 1),entry("0,8", 1),entry("1,8", 1),entry("0,11", 2),entry("0,12", 2),entry("0,13", 2),entry("0,14", 2),entry("1,13", 2),entry("3,0", 3),entry("4,0", 3),entry("5,0", 3),entry("5,1",3),entry("6,1", 3),entry("3,3", 4),entry("3,4", 4),entry("4,3", 4),entry("4,4", 4),entry("5,3", 4),entry("3,6", 5),entry("4,6", 5),entry("5,6", 5),entry("5,7", 5),entry("5,8", 5),entry("3,10", 6),entry("3,11", 6),entry("4,11", 6),entry("5,11", 6),entry("5,12", 6),entry("3,15", 7),entry("4,15", 7),entry("5,15", 7),entry("4,14", 7),entry("4,16", 7),entry("7,0", 8),entry("8,0", 8),entry("9,0", 8),entry("8,1", 8),entry("7,3", 9),entry("7,4", 9),entry("8,3", 9),entry("9,3", 9),entry("9,4", 9),entry("7,6", 10),entry("8,6", 10),entry("8,7", 10),entry("9,7", 10),entry("9,8", 10),entry("7,10", 11),entry("7,11", 11),entry("7,12", 11),entry("8,11", 11),entry("9,11", 11),entry("7,14", 12),entry("7,15", 12),entry("8,15", 12),entry("9,15", 12),entry("8,16", 12),entry("11,0", 13),entry("11,1", 13),entry("11,2", 13),entry("12,2", 13),entry("12,3", 13),entry("11,5", 14),entry("11,6", 14),entry("12,6", 14),entry("12,7", 14),entry("12,9", 15),entry("11,9", 15),entry("12,10", 15),entry("11,12", 16),entry("11,13", 16),entry("12,12", 16),entry("12,13", 16),entry("11,15", 17),entry("14,0", 18),entry("14,1", 18),entry("14,2", 18),entry("14,3", 18),entry("14,4", 18),entry("14,6", 19),entry("14,7", 19),entry("14,8", 19),entry("14,10", 20),entry("14,11",20)
        ));
    }
}


