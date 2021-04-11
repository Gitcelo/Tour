package Model;

public class Reservation {
    private TourDate dateRef;
    private String reservationId;
    private int seats;
    
    /**
     * 
     * @param dateRef reference to the Date object specific to this reservation
     * @param customerRef the consumer that reserved the tour according to Date
     * @param reservationId the ID given to this reservation
     * @param seats
     */

    public Reservation(TourDate dateRef, String reservationId, int seats) {
        this.dateRef = dateRef;
        this.reservationId = reservationId;
        this.seats = seats;
    }

    

    /**
     * 
     * @return the object containing information on this reservation
     */
    public TourDate getDate(){
        return dateRef;
    }
    /**
     * 
     * @return seats needed for this reservation
     */
    public int getSeats(){
        return seats;
    }

    /**
     * 
     * @return id of this reservation
     */
    public String getReservationId(){
        return reservationId;
    }
    
}
