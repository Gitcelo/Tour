package Model;

/**
 * @author Team 3D
 *
 * Object that contains a specific tour reservation.
 */
public class Reservation {
    private TourDate dateRef;
    private int reservationId;
    private int noOfSeats;
    private String customerName;
    private String customerEmail;
    private final boolean validReservation;

    /**
     * An empty Reservaton object that is not valid to be inserted into the database.
     */
    public Reservation(){
        validReservation = false;
    }
    
    /**
     * 
     * @param dateRef Reference to the Date object specific to this reservation.
     * @param reservationId The ID given to this reservation.
     * @param noOfSeats The number of seats needed for this reservation.
     * @param customerName Name of the customer making the reservation.
     * @param customerEmail E-mail of the customer that making the reservation.
     */
    public Reservation(TourDate dateRef, int reservationId, int noOfSeats, String customerName, String customerEmail) {
        if(noOfSeats < 0) {
            throw new IllegalArgumentException("Seats cannot be negative");
        }
        this.dateRef = dateRef;
        this.reservationId = reservationId;
        this.noOfSeats = noOfSeats;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        validReservation = true;
    }

    public TourDate getDate(){ return dateRef; }

    public int getNoOfSeats(){ return noOfSeats; }

    public int getReservationId(){ return reservationId; }

    public String getCustomerName() { return customerName; }

    public String getCustomerEmail() { return customerEmail; }

    public boolean getValid() { return validReservation; }
}
