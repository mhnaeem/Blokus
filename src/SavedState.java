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

    public static ArrayList<SavedState> savedstates = new ArrayList<>(Arrays.asList());
    private String name;
    // DD-MMY-YYYY
    private String date;
    //hh:mm:ss
    private String time;
    private String path;

    SavedState(String name){
        this.name = name;

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        this.date = myDateObj.format(myFormatObj);

        this.time = myDateObj.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        String newBrokenTime = "";
        for (String s : this.time.split(":")) {
            newBrokenTime += s;
        }

        String fileName = this.date.toString() + newBrokenTime + this.name +  ".txt";

        SaveGame.createSaveFile(fileName);

        this.path = "./SavedGames/" + fileName;
        savedstates.add(this);
    }

    SavedState(String name, String time, String date, String path){
        savedstates.clear();

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
            String date = file.substring(13,22);
            String time = file.substring(24,30);
            String name = file.substring(30, file.length()-4);

            time = time.substring(0,2) + ":" + time.substring(2,4) + ":" + time.substring(4,6);

            new SavedState(name, time, date, file);
        }
    }
}
