package vinnsla;

public class Tour {
    private Date[] dates;
    private String description;
    private int price;
    private String tourName;
    private int difficulty;
    private String providerName;

    public Tour(Date[] dates, String description, int price, String tourName, int difficulty, String providerName) {
        this.dates = dates;
        this.description = description;
        this.price = price;
        this.tourName = tourName;
        this.difficulty = difficulty;
        this.providerName = providerName;
    }

    public Date[] getDate(){
        return dates;
    }

    public String getDescription(){
        return description;
    }
    public int getPrice(){
        return price;
    }
    
    public String tourName(){
        return tourName;
    }
    public int getDifficulty(){
        return difficulty;
    }
    public String getProvider(){
        return providerName;
    }
}
