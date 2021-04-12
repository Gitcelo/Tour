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

public class ReservationDb {
    Connection conn = null;

    private String getUrl() {
        String root = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        String dir = root.replace(separator, "/");
        String dbName = dir + "/data/tour.db";
        return "jdbc:sqlite:" + dbName;
    }

    private LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     *
     * @param tour
     * @param date
     * @param noOfSeats
     * @param customerName
     * @param customerEmail
     * @return
     */
    public boolean makeReservation(Tour tour, TourDate date, int noOfSeats,String customerName, String customerEmail) {
        String query = "INSERT INTO Reservations ("
                + "reservationId,"
                + "tourId,"
                + "tourDate,"
                + "noOfSeats,"
                + "customerName,"
                + "customerEmail ) VALUES ("
                + "SELECT MAX(reservationId) + 1 FROM Reservations,?,?,?,?,?)";
        try{
            String format = "yyyy-MM-dd-HH";
            String formattedDate = date.getDate().format(DateTimeFormatter.ofPattern(format));
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            long tourDate = sdf.parse(formattedDate).getTime();
            PreparedStatement st = conn.prepareStatement(query);
            st.setString(1, Integer.toString(tour.getTourId()));
            st.setString(2, date.getDate().toString());
            st.setString(3,Integer.toString(noOfSeats));
            st.setString(4,customerName);
            st.setString(5, customerEmail);

            boolean result = (st.executeUpdate() > 0);
            st.close();
            return result;
        }catch (SQLException | ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ReservationDb(){}
}
