package Model;

import java.time.LocalDateTime;

/**
 * @author Team 3D
 *
 * An object that represents an instance of a tour for a specific date.
 */
public class TourDate {
    private LocalDateTime date;
    private int availableSeats;
    private final int maxSeats;

    /**
     * Constructor for the TourDate object.
     *
     * @param date Date of this instance of a tour.
     * @param availableSeats Number of available seats left on this instance of a tour.
     * @param maxSeats Number of maximum availavle seats for this instance of a tour.
     */
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

    public LocalDateTime getDate() { return date; }

    /**
     * Updates the amount of available seats on this instance of a tour.
     *
     * @param seatCount Negative number if booking is made, positive if booking is cancelled.
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

    public int getAvailableSeats(){ return availableSeats; }

    public int getMaxSeats() { return maxSeats; }
}
