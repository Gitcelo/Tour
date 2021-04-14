package Controller;

import java.time.LocalDate;
import Model.Tour;
import javafx.collections.ObservableList;
import application.Parameters;
import data.TourDb;

/**
 * @author Team 3D
 *
 * Object that sanitizes parameters before sending them on to be used in tour queries.
 */
public class TourController {
    private final TourDb tourDb = new TourDb();

    /**
     * Empty constructor.
     */
    public TourController() {}

    /**
     * Creates a new tour and inserts it into the Tours table.
     *
     * @param tourName Name of the tour.
     * @param price Price of the tour.
     * @param description Description of the tour.
     * @param difficulty Difficulty of the tour.
     * @param location Location of the tour.
     * @param childFriendly Tells whether the tour is child friendly or not.
     * @param season The season the tour is set in.
     * @param providerName Name of the provider of the tour.
     * @return A Tour object created from the parameters and the tourId if the insertion worked, otherwise
     *        an invalid and empty Tour object.
     */
    public Tour createTour(String tourName, int price, String description, int difficulty, int location, boolean childFriendly, int season, String providerName) {
        if(price<=0) {
            throw new IllegalArgumentException("Price must be greater than zero");
        }
        if(10<difficulty&&difficulty<13) {
            throw new IllegalArgumentException("Difficulty must be between 10 and 13 inclusive");
        }
        int cf;
        if(childFriendly) cf = 1;
        else cf = 0;
        if(season<1||season>4) {
            throw new IllegalArgumentException("Season must be between 1 and 4 inclusive");
        }
        tourDb.openConnection();
        int tourId = tourDb.makeTour(tourName, price, description, difficulty, location, cf, season, providerName);
        tourDb.closeConnection();
        return new Tour(
                tourId,
                tourName,
                description,
                price,
                difficulty,
                cf,
                season,
                location,
                providerName
        );
    }

    /**
     * Searches for tours.
     *
     * @param p Object that contains parameters for the search function.
     * @return  An ObservableList that contains tours if the query worked and returned
     *          data, otherwise the list is empty.
     */
    public ObservableList<Tour> searchTour(Parameters p) {
        int difficulty = p.getdifficulty();
        if(10<difficulty&&difficulty<13) {
            throw new IllegalArgumentException("Difficulty must be between 10 and 13 inclusive");
        }
        int[] price = {p.getLowerPrice(), p.getMaxPrice()};
        if(price[0]<=0) {
            throw new IllegalArgumentException("Minimum price must be greater than zero");
        }
        if(price[0]>=price[1]) {
            throw new IllegalArgumentException("Minimum price must be less than maximum price");
        }
        int groupSize = p.getgroupSize();
        if(groupSize<=0) {
            throw new IllegalArgumentException("Size of group must be greater than zero");
        }
        int location = p.getDestination();
        //We don't know how many locations there will be
        if(location<=0) {
            throw new IllegalArgumentException("Location must be greater than zero");
        }
        LocalDate[] dateRange = {p.getcheckIn(), p.getcheckOut()};
        if(dateRange[0].isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Check in date can't come before the current date");
        }
        if(dateRange[0].isAfter(dateRange[1])) {
            throw new IllegalArgumentException("Check in date can't come before check out date");
        }
        tourDb.openConnection();
        ObservableList<Tour> t = tourDb.fetchTours(difficulty, price, groupSize, location, dateRange);
        tourDb.closeConnection();
        return t;
    }
}
