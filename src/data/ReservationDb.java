package data;

import vinnsla.Date;

public interface ReservationDb {

    public boolean reserveTour(Date date, Customer customer, int noOfSeats);

}
