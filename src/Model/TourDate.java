package Model;

import java.time.LocalDateTime;

public class TourDate {
    private LocalDateTime date; //Date frá java.utils
    private int availableSeats;
    private int maxSeats;

    public TourDate(LocalDateTime date, int availableSeats, int maxSeats) {
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
