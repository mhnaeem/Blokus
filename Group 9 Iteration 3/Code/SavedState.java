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

public class SavedState {

    public static ArrayList<SavedState> savedstates = new ArrayList<SavedState>( Arrays.asList() );
    private String name;
    // DD-MMY-YYYY
    private String date;
    //hh:mm
    private String time;

    SavedState(String name){
        this.name = name;

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
        this.date = myDateObj.format(myFormatObj);

        this.time = myDateObj.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

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
}
