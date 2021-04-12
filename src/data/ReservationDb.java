package data;

import Model.Tour;
import Model.TourDate;
import javafx.collections.ObservableList;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import static application.Utils.getUrlAndDatabase;

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
        try{
            conn = DriverManager.getConnection(getUrlAndDatabase()[0]);
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement(query);

            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) as total FROM Reservations");
            int resId = rs.getInt("total")+1;
            String formatted = date.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
            java.util.Date parsedDate = sdf.parse(formatted);
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());

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
        }catch (SQLException | ParseException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
        return 0;
    }
}
