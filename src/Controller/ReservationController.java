package Controller;

import java.util.Comparator;
import Model.*;

public class ReservationController {
   // private ReservationDb resDb;

   /* public ReservationController(ReservationDb resDb) {
        this.resDb = resDb;
    }*/

    public int getInt() {
        return 5;
    }

    public boolean makeReservation(TourDate date, int noOfSeats) {

        Comparator<ReservationController> comparator = Comparator.comparingInt(ReservationController::getInt);
        return true;
    }
    


}