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

    /**
     *
     * @param tour The selected tour for the booking
     * @param tourDate the date for the tour
     * @param noOfSeats number of seats booked
     * @param customerName username of the booker
     * @param customerEmail email of the booker
     * @return booking ID if confirmation successful, otherwise 0
     */
    public int confirmBooking(Tour tour, TourDate tourDate, int noOfSeats, String customerName, String customerEmail) {
        if(!isValidEmail(customerEmail)) {
            throw new IllegalArgumentException("Email is not valid");
        }
        if(customerName.equals("")) {
            throw new IllegalArgumentException("customerName must not be empty");
        }
        if(noOfSeats <= 0 || noOfSeats > tourDate.getAvailableSeats()) {
            throw new IllegalArgumentException("noOfSeats is not valid");
        }

        return resDb.makeReservation(tour, tourDate,noOfSeats,customerName,customerEmail);
    }


    


}