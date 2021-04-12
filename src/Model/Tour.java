package Model;

import java.util.ArrayList;

public class Tour {
    private ArrayList<TourDate> dates;
    int tourId;
    private String tourName;
    private String description;
    private int price;
    private int difficulty;
    private int location;
    private boolean childFriendly;
    private int season;
    private String providerName;


    /** Constructor for a Tour object
     * 
     * @param dates Date object containing information on this tour
     * @param description Description of the tour
     * @param price Price of the tour
     * @param tourName Name of tour
     * @param difficulty Tour difficulty: 10: disabled, 11-13: easy to hard
     * @param location Location of the tour
     * @param childFriendly Boolean that tells whether tour is child friendly or not
     * @param season An integer value representing the season the tour is active in
     * @param providerName The name of the tour provider
     */
    public Tour(ArrayList<TourDate> dates, int tourId, String tourName, String description, int price, int difficulty, int location, boolean childFriendly, int season, String providerName) {
        this.dates = dates;
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
        return childFriendly;
    }

    public int getSeason(){
        return season;
    }
}
