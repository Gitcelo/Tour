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
     * Bookins a reservation.
     *
     * @param tour The tour to be booked
     * @param date The date for the given tour
     * @param noOfSeats Number of seats to be booked
     * @param customerName Name of the customer making reservation
     * @param customerEmail Email of the customer making reservation
     * @return True if booking successful, false otherwise
     */
    public int makeReservation(Tour tour, TourDate date, int noOfSeats,String customerName, String customerEmail) {
        validConnection(conn);
        String query = "INSERT INTO Reservations ("
                + "reservationId,"
                + "tourId,"
                + "tourDate,"
                + "noOfSeats,"
                + "customerName,"
                + "customerEmail ) VALUES ("
                + "?,?,?,?,?,?)";
        try {
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement(query);
            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) as total FROM Reservations");
            int resId = rs.getInt("total")+1;
            java.sql.Date sqlDate = localDateTimeToSQLDate(date.getDate());
            st.setInt(1,resId);
            st.setInt(2, tour.getTourId());
            st.setDate(3, sqlDate);
            st.setInt(4, noOfSeats);
            st.setString(5,customerName);
            st.setString(6, customerEmail);
            st.executeUpdate();
            st.close();
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
        String query = "DELETE FROM Reservations WHERE reservationId = ?";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, reservationId);
            int result = ps.executeUpdate();
            ps.close();
            conn.commit();
            return result > 0;
        }catch (SQLException e){
            return false;
        }
    }
}
