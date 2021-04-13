package Controller;


import Model.*;
import data.*;
import static application.Utils.*;

public class ReservationController {
    private ReservationDb resDb;

    public ReservationController() {
        this.resDb = new ReservationDb();
    }

    public int getInt() {
        return 5;
    }

    public int confirmBooking(Tour tour, TourDate tourDate, int noOfSeats, String customerName, String customerEmail) {
        if(!isValidEmail(customerEmail)) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if(customerName.equals("")) {
            throw new IllegalArgumentException("customerName must not be empty");
        }

        return resDb.makeReservation(tour, tourDate,noOfSeats,customerName,customerEmail);
    }


    


}