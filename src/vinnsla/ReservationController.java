package vinnsla;

import gagnagrunnur.ReservationDb;

import java.util.Comparator;

public class ReservationController {
    private int s = 5;
    private ReservationDb resDb;

    public ReservationController(ReservationDb resDb) {
        this.resDb = resDb;
    }

    public int getInt() {
        return 5;
    }

    public boolean makeReservation(Date date, Customer customer, int noOfSeats) {

        Comparator<ReservationController> comparator = Comparator.comparingInt(ReservationController::getInt);
        return true;
    }
}