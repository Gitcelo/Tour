package gagnagrunnur;

import vinnsla.Customer;
import vinnsla.Date;

public interface ReservationDb {

    public boolean book(Date date, Customer customer, int noOfSeats);

}