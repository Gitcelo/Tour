package Controller;

import Model.*;
import data.*;
import static application.Utils.*;

/**
 * @author Team 3D
 *
 * Object that sanitizes parameters before sending them on to be used in reservation queries.
 */
public class ReservationController {
    private final ReservationDb resDb;

    public ReservationController() {
        resDb = new ReservationDb();
    }


    /**
     * Makes a booking.
     *
     * @param tour The selected tour for the booking
     * @param tourDate the date for the tour
     * @param noOfSeats number of seats booked
     * @param customerName username of the booker
     * @param customerEmail email of the booker
     * @return booking ID if confirmation successful, otherwise 0.
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
        resDb.openConnection();
        int resId = resDb.makeReservation(tour, tourDate,noOfSeats,customerName,customerEmail);
        resDb.closeConnection();
        return resId;
    }

    /**
     * Searches for a booking.
     *
     * @param reservationId Identification number of booking to be found.
     * @return Valid Reservation object if the booking exists, otherwise an invalid and empty Reservation object.
     */
    public Reservation searchBooking(int reservationId) {
        if(reservationId<=0) {
            throw new IllegalArgumentException("The reservation id must be greater than or equal to zero.");
        }
        resDb.openConnection();
        Reservation res = resDb.fetchReservationById(reservationId);
        resDb.closeConnection();
        return res;
    }

    /**
     * Cancels a booking.
     *
     * @param reservationId Identification number for the booking
     * @return true if cancelling reservation succeeded, false otherwise
     */
    public boolean cancelBooking(int reservationId) {
        if(reservationId<=0) {
            throw new IllegalArgumentException("The reservation id must be greater than or equal to zero.");
        }
        resDb.openConnection();
        boolean cancelled = resDb.removeReservation(reservationId);
        resDb.closeConnection();
        return cancelled;
    }
}