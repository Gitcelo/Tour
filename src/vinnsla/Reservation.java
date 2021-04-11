package vinnsla;

public class Reservation {
    private Date dateRef;
    private Customer customerRef;
    private String reservationId;
    private int seats;
    
    /**
     * 
     * @param dateRef reference to the Date object specific to this reservation
     * @param customerRef the consumer that reserved the tour according to Date
     * @param reservationId the ID given to this reservation
     * @param seats
     */

    public Reservation(Date dateRef, Customer customerRef, String reservationId, int seats) {
        this.dateRef = dateRef;
        this.customerRef = customerRef;
        this.reservationId = reservationId;
        this.seats = seats;
    }

    public Customer getCustomer(){
        return customerRef;
    }

    public Date getDate(){
        return dateRef;
    }

    public int getSeats(){
        return seats;
    }
    public String getReservationId(){
        return reservationId;
    }
    
}
