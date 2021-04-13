package Model;

public class Reservation {
    private TourDate dateRef;
    private int reservationId;
    private int seats;
    private boolean validReservation;
    private String customerName;
    private String customerEmail;
    public Reservation(){
        validReservation = false;
    }
    
    /**
     * 
     * @param dateRef reference to the Date object specific to this reservation
     * @param reservationId the ID given to this reservation
     * @param seats
     * @param customerName
     * @param customerEmail
     */

    public Reservation(TourDate dateRef, int reservationId, int seats, String customerName, String customerEmail) {
        if(seats < 0) {
            throw new IllegalArgumentException("Seats cannot be negative");
        }
        this.dateRef = dateRef;
        this.reservationId = reservationId;
        this.seats = seats;
        this.customerEmail = customerEmail;
        this.customerName = customerName;
        this.validReservation = true;
    }

    public TourDate getDate(){
        return dateRef;
    }

    public int getSeats(){
        return seats;
    }

    public int getReservationId(){
        return reservationId;
    }

    public String getCustomerName() { return customerName; }

    public String getCustomerEmail() { return customerEmail; }

    
}
