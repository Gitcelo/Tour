package Controller;

import java.time.LocalDate;
import Model.Tour;
import utils.Parameters;

import data.TourDb;

public class TourController {
    private TourDb tourDb;


    //Kommentaði þetta út svo ég fái ekki endalausa villumeldingu meðan ég vinn í TourDb. Það má alveg afkommenta

    //Parameter is a T team component - spurning hvort þetta sé besta leiðin til að vinna með Parameter
   /* public Tour[] searchTour(Parameters p) {
        int difficulty = p.getdifficulty();
        int[] price = p.getPrice();
        int groupSize = p.getGroupSize();
        int location = p.getLocation();
        LocalDate[] dateRange = {p.getCheckIn(),p.getCheckOut()};
        return tourDb.fetchTours(difficulty, price, groupSize, destination, dateRange); 
    }*/
}
