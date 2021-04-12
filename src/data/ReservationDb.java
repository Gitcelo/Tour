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
import java.util.Date;

import static application.Utils.getUrl;

public class ReservationDb {
    Connection conn = null;


    private LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /** Booking function.
     *
     * @param tour the tour to be booked
     * @param date the date for the given tour
     * @param noOfSeats number of seats to be booked
     * @param customerName name of the customer making reservation
     * @param customerEmail email of the customer making reservation
     * @return true if booking successful, false otherwise
     */
    public boolean makeReservation(Tour tour, TourDate date, int noOfSeats,String customerName, String customerEmail) {
        String query = "INSERT INTO Reservations ("
                + "reservationId,"
                + "tourId,"
                + "tourDate,"
                + "noOfSeats,"
                + "customerName,"
                + "customerEmail ) VALUES ("
                + "?,?,?,?,?,?)";
        try{
            conn = DriverManager.getConnection(getUrl());
            conn.setAutoCommit(false);
            PreparedStatement st = conn.prepareStatement(query);

            ResultSet rs = conn.createStatement().executeQuery("SELECT COUNT(*) as total FROM Reservations");
            int resId = 0;
            resId = rs.getInt("total");

            //set variables
            st.setString(1,Integer.toString(resId));
            st.setString(2, Integer.toString(tour.getTourId()));
            st.setString(3, date.getDate().toString());
            st.setString(4,Integer.toString(noOfSeats));
            st.setString(5,customerName);
            st.setString(6, customerEmail);

            boolean result = (st.executeUpdate() > 0);
            st.close();
            return result;
        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ReservationDb(){}


}
