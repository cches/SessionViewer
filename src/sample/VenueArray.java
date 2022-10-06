package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class VenueArray {
    ObservableList<String> venueIDs = FXCollections.observableArrayList();
//    File venueFile = new File( "." + File.separator + "venueIDs.txt");
    File venueFile = new File("src"  + File.separator + "sample" + File.separator + "venueIDs.txt");

    public VenueArray() {
        String[] venues = {"4705 - Truist Park","3810 - Coolray Field","2813 - Trustmark Park","2851 - AdventHealth Stadium",
                "5345 - SRP Park","5380 - CoolToday Park"};

//        String[] venues = {"2823 - NBT Bank Stadium","2820 - Mirabito Stadium","2795 - Maimonides Park","2849 - St. Lucie Sports Complex"};


        for(String string : venues){
            venueIDs.add(string);
        }
//        if(venueFile.exists()){
//
//            try {
//                BufferedReader venueBufferedReader = new BufferedReader(new FileReader(venueFile));
//                String line;
//                while((line = venueBufferedReader.readLine()) != null){
//                    venueIDs.add(line);
//                }
//
//            } catch (IOException e){
//                System.out.println(e.getMessage());
//            }
//        }else{
//            System.out.println("Make sure that a venueIDs.txt file exists in the folder");
//        }

    }



    public ObservableList<String> getVenueIDs() {
        return venueIDs;
    }


}
