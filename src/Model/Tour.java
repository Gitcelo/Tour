package Model;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Tour {
    private ArrayList<TourDate> dates;
    int tourId;
    private String tourName;
    private String description;
    private int price;
    private int difficulty;
    private int location;
    private int childFriendly;
    private int season;
    private int availableSeats;
    private String providerName;

    /**
     * Constructor for empty Tour object
     */
    public Tour() {}

    /** Constructor for a Tour object
     * @param tourId id of tour
     * @param description Description of the tour
     * @param price Price of the tour
     * @param tourName Name of tour
     * @param difficulty Tour difficulty: 10: disabled, 11-13: easy to hard
     * @param location Location of the tour
     * @param childFriendly An integer of value 0 or 1, 0 representing false and 1 representing true
     * @param season An integer between values 1-4, inclusive.
     * @param providerName The name of the tour provider
     */
    public Tour(int tourId, String tourName, String description, int price, int difficulty, int childFriendly, int season, int location, String providerName) {
        dates = new ArrayList<TourDate>(); //Vil ekki upphafssetja með tilbúinn lista
        this.tourId = tourId;
        this.tourName = tourName;
        this.description = description;
        this.price = price;
        this.difficulty = difficulty;
        this.location = location;
        this.childFriendly = childFriendly;
        this.season = season;
        this.providerName = providerName;
    }

    public ArrayList<TourDate> getDate(){
        return dates;
    }

    public int getTourId() {return tourId;}

    public String getTourName(){ return tourName; }

    public String getDescription(){
        return description;
    }

    public int getPrice(){
        return price;
    }

    public int getDifficulty(){
        return difficulty;
    }

    public String getProvider(){
        return providerName;
    }
    
    public int getLocation(){
        return location;
    }

    public boolean isChildFriendly(){
        if(childFriendly==1) return true;
        return false;
    }

    public int getSeason(){
        return season;
    }

    public void setDates(ArrayList<TourDate> dates) {this.dates = dates;}
}