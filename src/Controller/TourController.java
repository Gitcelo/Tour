package Controller;

import java.time.LocalDate;
import Model.Tour;
import javafx.collections.ObservableList;
import application.Parameters;

import data.TourDb;

public class TourController {
    private TourDb tourDb = new TourDb();

    public ObservableList<Tour> searchTour(Parameters p) {
        int difficulty = p.getdifficulty();
        int[] price = {p.getLowerPrice(),p.getMaxPrice()};
        int groupSize = p.getgroupSize();
        int location = p.getDestination();
        LocalDate[] dateRange = {p.getcheckIn(),p.getcheckOut()};
        return tourDb.fetchTours(difficulty, price, groupSize, location, dateRange);
    }
}
