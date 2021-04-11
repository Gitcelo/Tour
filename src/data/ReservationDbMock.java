package data;

import vinnsla.Date;

/**
 * A simulation of an object that connects to the reservation database.
 */
public class ReservationDbMock implements ReservationDb {

    /**
     * A function that simulates a function that creates a new entry in the reservation database.
     *
     * @param date An object of type Date, which is in essence a single instance of a tour with information on the date.
     * @param customer An object of type Customer, which contains the information for the particular customer.
     * @param noOfSeats Integer value for how many seats are needed for the reservation
     * @return Boolean value true. We are assuming that makeReservation in the controller already
     *         made sure the inputs do not cause failures.
     */
    @Override
    public boolean reserveTour(Date date, Customer customer, int noOfSeats){
        return true;
    }
}
