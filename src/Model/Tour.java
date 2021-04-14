package Model;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * @author Team 3D
 *
 * Object that represents a day tour.
 */
public class Tour {
    private ObservableList<TourDate> dates;
    private SimpleIntegerProperty tourId;
    private SimpleStringProperty tourName;
    private SimpleStringProperty description;
    private SimpleIntegerProperty price;
    private SimpleIntegerProperty difficulty;
    private SimpleIntegerProperty location;
    private SimpleIntegerProperty childFriendly;
    private SimpleIntegerProperty season;
    private SimpleStringProperty providerName;
    private final boolean validTour; //true if tour can be inserted into database

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

        dates = FXCollections.observableArrayList();
        this.tourId = new SimpleIntegerProperty(tourId);
        this.tourName = new SimpleStringProperty(tourName);
        this.description = new SimpleStringProperty(description);
        this.price = new SimpleIntegerProperty(price);
        this.difficulty = new SimpleIntegerProperty(difficulty);
        this.location = new SimpleIntegerProperty(location);
        this.childFriendly = new SimpleIntegerProperty(childFriendly);
        this.season = new SimpleIntegerProperty(season);
        this.providerName = new SimpleStringProperty(providerName);
        validTour = true;
    }

    public ObservableList<TourDate> getDates(){ return dates; }

    public int getTourId() {return tourId.get();}

    public String getTourName(){ return tourName.get(); }

    public String getDescription(){ return description.get(); }

    public int getPrice(){ return price.get(); }

    public int getDifficulty(){ return difficulty.get(); }

    public String getProvider(){ return providerName.get(); }
    
    public int getLocation(){ return location.get(); }

    public boolean isChildFriendly(){
        return childFriendly.get()==1;
    }

    public int getSeason(){ return season.get(); }

    public boolean getValidTour() { return validTour; }

    public void setDates(ObservableList<TourDate> dates) {this.dates = dates;}
}
