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
    private TourDb tourDb = new TourDb();

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
        int location = p.getDestination(); //Validate location
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
