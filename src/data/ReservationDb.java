package data;

import Model.Reservation;
import Model.Tour;
import Model.TourDate;
import java.sql.*;
import static application.Utils.*;

/**
 * @author Team 3D
 *
 * Object that can do queries on the Reservations table.
 */
public class ReservationDb implements MakeConnection {
    private final String url;
    private Connection conn;

    /**
     * Constructor that initializes the instance variable url.
     */
    public ReservationDb(){ url = getUrlAndDatabase()[0]; }

    /**
     * Opens up a connection to the database.
     * Should be called at least once before using the other methods.
     */
    @Override
    public void openConnection() {
        try {
            conn = DriverManager.getConnection(url);
        } catch(SQLException e) {
            throw new IllegalArgumentException("Could not connect to database");
        }
    }

    /**
     * Closes a connection to the database.
     * Should be called when there is no need to query the database any more.
     */
    @Override
    public void closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            throw new IllegalArgumentException("Could not disconnect from database");
        }
    }

    /**
     * Makes a reservation.
     *
     * @param tour The tour to be booked
     * @param date The date for the given tour
     * @param noOfSeats Number of seats to be booked
     * @param customerName Name of the customer making reservation
     * @param customerEmail Email of the customer making reservation
     * @return returns reservationId if successful, 0 otherwise
     */
    public int makeReservation(Tour tour, TourDate date, int noOfSeats,String customerName, String customerEmail) {
        validConnection(conn);

        String queryReservations = "INSERT INTO Reservations ("
                + "reservationId,"
                + "tourId,"
                + "tourDate,"
                + "noOfSeats,"
                + "customerName,"
                + "customerEmail ) VALUES ("
                + "?,?,?,?,?,?)";
        String queryDate = "UPDATE Dates SET availableSeats = ? "
                + "WHERE tourId = ? AND tourDate = ?";
        try {
            conn.setAutoCommit(false);
            //update Dates
            PreparedStatement pstDates = conn.prepareStatement(queryDate);
            pstDates.setInt(1,date.getAvailableSeats() - noOfSeats);
            pstDates.setInt(2,tour.getTourId());
            pstDates.setDate(3,localDateTimeToSQLDate(date.getDate()));
            pstDates.executeUpdate();
            pstDates.close();
            //Insert into reservation
            PreparedStatement pstReservation = conn.prepareStatement(queryReservations);
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) as total FROM Reservations");
            int resId = rs.getInt("total")+1;
            java.sql.Date sqlDate = localDateTimeToSQLDate(date.getDate());
            pstReservation.setInt(1,resId);
            pstReservation.setInt(2, tour.getTourId());
            pstReservation.setDate(3, sqlDate);
            pstReservation.setInt(4, noOfSeats);
            pstReservation.setString(5,customerName);
            pstReservation.setString(6, customerEmail);
            pstReservation.addBatch();
            pstReservation.executeBatch();
            pstReservation.close();
            conn.commit();
            return resId;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Fetches a reservation from the reservations table.
     * @param reservationId Identification number of reservation to be fetched.
     * @return The reservation if it exists, otherwise an empty and invalid Reservation object.
     */
    public Reservation fetchReservationById(int reservationId) {
        validConnection(conn);
        String query = "SELECT * FROM Reservations WHERE "
                + "reservationId="
                + reservationId;
        try {
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Reservation reservation;
            String q = "SELECT * FROM Dates WHERE "
                    + "tourId = " + rs.getInt("tourId")
                    + "AND tourDate = " + rs.getDate("tourDate");
            ResultSet dateResults = stmt.executeQuery(q);
            TourDate td = new TourDate( //date info for this reservation
                    toLocalDateTime(dateResults.getLong("tourDate")),
                    dateResults.getInt("availableSeats"),
                    dateResults.getInt("maxAvailableSeats")
            );
            reservation = new Reservation(
                    td,
                    rs.getInt("reservationId"),
                    rs.getInt("noOfSeats"),
                    rs.getString("customerName"),
                    rs.getString("customerEmail")
            );
            stmt.close();
            rs.close();
            return reservation;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new Reservation();
    }

    /**
     * Cancels a reservation.
     *
     * @param reservationId Identification number of the reservation that is being cancelled.
     * @return true if reservation was cancelled, false otherwise.
     */
    public boolean removeReservation(int reservationId) {
        validConnection(conn);
        String deleteReservationQuery = "DELETE FROM Reservations WHERE reservationId = ?";
        String selectReservationQuery = "SELECT * FROM Reservations WHERE reservationId = ?";
        String updateDatesQuery = "UPDATE Dates SET availableSeats = availableSeats + ? "
                + "WHERE tourId = ? AND tourDate = ?";
        try {
            conn.setAutoCommit(false);
            //Update Dates
            ResultSet rs = conn.createStatement().executeQuery(selectReservationQuery);
            PreparedStatement psDates = conn.prepareStatement(updateDatesQuery);
            psDates.setInt(1,reservationId);
            psDates.setInt(2,rs.getInt("tourId"));
            psDates.setDate(3,rs.getDate("tourDate"));
            psDates.executeUpdate();
            psDates.close();
            //delete reservation
            PreparedStatement psReservation = conn.prepareStatement(deleteReservationQuery);
            psReservation.setInt(1, reservationId);
            int result = psDates.executeUpdate();
            psReservation.close();
            conn.commit();
            return result > 0;
        }catch (SQLException e){
            return false;
        }
    }
}
