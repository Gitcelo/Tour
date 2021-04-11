package vinnsla;

import java.time.LocalDate;

import gagnagrunnur.TourDb; //eigum eftir að stofna pakka

public class TourController {
    private TourDb tourDb;


    //Parameter is a T team component 
    public Tour[] searchTour(Parameter p) {
        int difficulty = p.getDifficulty();
        int[] price = p.getPrice();
        int groupSize = p.getGroupSize();
        int destination = p.getDestination(); //skila tour sem er nálægt þessu svæði?
        LocalDate[] dateRange = {p.getCheckIn(),p.getCheckOut()};
        return tourDb.fetchTours(difficulty, price, groupSize, destination, dateRange); 
    }
}
