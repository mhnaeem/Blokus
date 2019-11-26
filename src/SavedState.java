import javax.swing.JOptionPane;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Saved State class
 *
 *
 * @author (Muhammad Hammad)
 * @version (Version 1.0)
 */

class SavedState {

    public static ArrayList<SavedState> savedstates = new ArrayList<>();
    private String name;
    // DD-MMY-YYYY
    private String date;
    //hh:mm:ss
    private String time;
    private String path;
    private final static ArrayList<Character> FORBIDDEN_LETTERS = new ArrayList<>(Arrays.asList('/', '\\', ':', '*', '?', '"', '<', '>', '|', '.'));

    SavedState(String name){
        boolean continueSaving = true;

        for (int i = 0; i < name.length(); i++){
            char letter = name.charAt(i);
            if (FORBIDDEN_LETTERS.contains(letter)){
                continueSaving = false;
            }
        }

        if(continueSaving) {
            this.name = name;

            LocalDateTime myDateObj = LocalDateTime.now();
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
            this.date = myDateObj.format(myFormatObj);

            this.date = this.date.replace(".","");

            this.time = myDateObj.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

            String newBrokenTime = "";
            for (String s : this.time.split(":")) {
                newBrokenTime += s;
            }

            String fileName = this.date.toString() + newBrokenTime + this.name + ".txt";

            SaveGame.createSaveFile(fileName);

            this.path = "./SavedGames/" + fileName;
            savedstates.add(this);
        }
        else{
            JOptionPane.showMessageDialog(null, "Name of the save file cannot contain the following characters  / . \\ : * ? \" < > |   Try Again!", "Save Name Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    SavedState(String name, String time, String date, String path){
        this.name = name;
        this.date = date;
        this.time = time;
        this.path = path;

        savedstates.add(this);
    }

    String getName(){
        return this.name;
    }

    String getDate(){
        return this.date;
    }

    String getTime(){
        return this.time;
    }

    public String getPath() {
        return this.path;
    }

    public static void updateForLoad(){
        savedstates.clear();

        File[] listOfFiles = new File("./SavedGames").listFiles();
        for (File listOfFile : listOfFiles) {
            String file = listOfFile.toString();
            file = file.substring(13,file.length()).replace(".","");

            String date = file.substring(0,11);
            String time = file.substring(11,17);
            String name = file.substring(17, file.length()-3);

            time = time.substring(0,2) + ":" + time.substring(2,4) + ":" + time.substring(4,6);

            new SavedState(name, time, date, file);
        }
    }

    public static ArrayList<SavedState> getSavedStates() {
        return savedstates;
    }
}
