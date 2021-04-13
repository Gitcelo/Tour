package Model;

import java.util.ArrayList;

public class Tour {
    private ArrayList<TourDate> dates;
    private int tourId;
    private String tourName;
    private String description;
    private int price;
    private int difficulty;
    private int location;
    private int childFriendly;
    private int season;
    private String providerName;
    private final boolean validTour;

    /**
     * Constructor for empty Tour object that is not valid for insertion into the Tours table.
     */
    public Tour() { validTour = false; }

    /** Constructor for a Tour object
     * @param tourId Id of tour object
     * @param description Description of tour object
     * @param price Price of tour object
     * @param tourName Name of tour object
     * @param difficulty Tour difficulty: 10: disabled, 11-13: easy to hard
     * @param location Location of tour object
     * @param childFriendly An integer of value 0 or 1, 0 representing false and 1 representing true
     * @param season An integer between values 1-4, inclusive.
     * @param providerName The name of the tour provider
     */
    public Tour(int tourId, String tourName, String description, int price, int difficulty, int childFriendly, int season, int location, String providerName) {
        if(tourId <= 0) {
            throw new IllegalArgumentException("tourId is invalid");
        }
        if(price < 0) {
            throw new IllegalArgumentException("price is less than 0 ");
        }
        if(difficulty < 10 || difficulty > 13) {
            throw new IllegalArgumentException("difficulty not valid value");
        }
        if(childFriendly < 0 || childFriendly > 1) {
            throw new IllegalArgumentException("ChildFriendly should be either 0 or 1");
        }
        if(season < 1 || season > 4) {
            throw new IllegalArgumentException("Season should be a value in [0,4]");
        }
        if(tourName == null ) {
            throw new IllegalArgumentException("tourName is not valid");
        }
        if(description == null ) {
            throw new IllegalArgumentException("description is not valid");
        }
        if( providerName == null) {
            throw new IllegalArgumentException("providerName is not valid");
        }

        dates = new ArrayList<TourDate>();
        this.tourId = tourId;
        this.tourName = tourName;
        this.description = description;
        this.price = price;
        this.difficulty = difficulty;
        this.location = location;
        this.childFriendly = childFriendly;
        this.season = season;
        this.providerName = providerName;
        validTour = true;
    }

    public ArrayList<TourDate> getDates(){
        return dates;
    }

    public int getTourId() {return tourId;}

    public String getTourName(){ return tourName; }

    public String getDescription(){ return description; }

    public int getPrice(){ return price; }

    public int getDifficulty(){ return difficulty; }

    public String getProvider(){ return providerName; }
    
    public int getLocation(){ return location; }

    public boolean isChildFriendly(){
        if(childFriendly==1) return true;
        return false;
    }

    public int getSeason(){ return season; }

    public boolean getValidTour() { return validTour; }

    public void setDates(ArrayList<TourDate> dates) {this.dates = dates;}
}
