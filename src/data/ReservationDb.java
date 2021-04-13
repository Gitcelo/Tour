package data;

import Model.Reservation;
import Model.Tour;
import Model.TourDate;
import javafx.collections.ObservableList;

import javax.swing.plaf.nimbus.State;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static application.Utils.*;

public class ReservationDb {
    private Connection conn = null;

    public ReservationDb(){}

    /** Booking function.
     *
     * @param tour the tour to be booked
     * @param date the date for the given tour
     * @param noOfSeats number of seats to be booked
     * @param customerName name of the customer making reservation
     * @param customerEmail email of the customer making reservation
     * @return true if booking successful, false otherwise
     */
    public int makeReservation(Tour tour, TourDate date, int noOfSeats,String customerName, String customerEmail) throws SQLException {
        String query = "INSERT INTO Reservations ("
                + "reservationId,"
                + "tourId,"
                + "tourDate,"
                + "noOfSeats,"
                + "customerName,"
                + "customerEmail ) VALUES ("
                + "?,?,?,?,?,?)";
        try(Connection conn = DriverManager.getConnection(getUrlAndDatabase()[0])){
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement(query);

            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) as total FROM Reservations");
            int resId = rs.getInt("total")+1;

            java.sql.Date sqlDate = localDateTimeToSQLDate(date.getDate());

            //set variables
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

    public Reservation fetchReservationById(int reservationId) {
        String query = "SELECT * FROM Reservations WHERE "
                + "reservationId="
                + reservationId;
        try(Connection conn = DriverManager.getConnection(getUrlAndDatabase()[0])){
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Reservation reservation = new Reservation();
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
                    rs.getInt("reservstionId"),
                    rs.getInt("noOfSeats"),
                    rs.getString("customerName"),
                    rs.getString("customerEmail")
            );
            return reservation;
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean removeReservation(int reservationId) {
        String query = "DELETE FROM Reservations WHERE reservationId = ?";
        try(Connection conn = DriverManager.getConnection(getUrlAndDatabase()[0])){
            conn.setAutoCommit(false);
            PreparedStatement pt = conn.prepareStatement(query);
            pt.setInt(1,reservationId);
            int result = pt.executeUpdate();
            conn.commit();
            return result > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
