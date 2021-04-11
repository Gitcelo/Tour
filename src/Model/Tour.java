package Model;

import java.util.ArrayList;


public class Tour {
    private ArrayList<TourDate> dates;
    private String description;
    private int price;
    private String tourName;
    private int difficulty;
    private String providerName;
    private int location;
    private boolean childFriendly;
    private int season;


    /** Constructor for a Tour object
     * 
     * @param dates Date object containing information on this tour
     * @param description decription of the tour
     * @param price price of the tour
     * @param tourName name of tour
     * @param difficulty tour difficulty: 10: disabled, 11-13: easy to hard
     * @param providerName the name of the tour provider
     */
    public Tour(ArrayList<TourDate> dates, String description, int price, String tourName, int difficulty, String providerName, int location, boolean childFriendly, int season) {
        this.dates = dates;
        this.description = description;
        this.price = price;
        this.tourName = tourName;
        this.difficulty = difficulty;
        this.providerName = providerName;
        this.location = location;
        this.childFriendly = childFriendly;
        this.season = season;
    }

    /**
     * 
     * @return a list of multiple Date objects for different trips based on this same tour
     */
    public ArrayList<TourDate> getDate(){
        return dates;
    }

    /**
     * 
     * @return description of this tour
     */
    public String getDescription(){
        return description;
    }

    /**
     * 
     * @return price of this tour
     */
    public int getPrice(){
        return price;
    }
    
    /**
     * 
     * @return name of tour
     */
    public String tourName(){
        return tourName;
    }
    /**
     * 
     * @return int value on difficulty: 10 -> disabled 11-13 -> easy-hard
     */
    public int getDifficulty(){
        return difficulty;
    }
    /**
     * 
     * @return name of provider
     */
    public String getProvider(){
        return providerName;
    }
    
    public int getLocation(){
        return location;
    }

    public boolean isChildFriendly(){
        return childFriendly;
    }

    /**
     * 
     * @return number between 1-4 specifying the season in which the tour takes place
     */
    public int getSeason(){
        return season;
    }
}
