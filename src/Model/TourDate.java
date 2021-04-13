package Model;

import java.time.LocalDateTime;

public class TourDate {
    private LocalDateTime date;
    private int availableSeats;
    private int maxSeats;

    public TourDate(LocalDateTime date, int availableSeats, int maxSeats) {
        if(maxSeats <= 0 || (availableSeats < 0 || availableSeats > maxSeats)) {
            throw new IllegalArgumentException("maxSeats is negative or available seats is not in [0,maxSeats]");
        }
        if(date == null) {
            throw new IllegalArgumentException("date is not valid");
        }
        this.date = date;
        this.availableSeats = availableSeats;
        this.maxSeats = maxSeats;
    }

    public LocalDateTime getDate() {
        return date;
    }

    /**
     * 
     * @param seatCount positive number if booking is made, negative if booking is cancelled.
     * @return true if updating seat was valid, false otherwise
     */
    public boolean updateSeat(int seatCount) {
        int updatedSeats = availableSeats + seatCount;
        if(updatedSeats >= 0 && updatedSeats <= maxSeats) {
            availableSeats = updatedSeats;
            return true;
        }
        return false;
    }

    //public Consumer getNextCustomerInLine(){} ef við útfærum

    
}
