package data;

import java.sql.*;
import static application.Utils.getUrlAndDatabase;

public class DateDb {
    String url;

    public DateDb() {
        url = getUrlAndDatabase()[0];
    }

    public void makeDate(int tourId, Date tourDate, int maxAvailableSeats, int availableSeats, Connection conn) {
        try {
            String url = conn.getMetaData().getURL();
            if(!url.equals(getUrlAndDatabase()[0])) throw new IllegalArgumentException("Incorrect database connection");
        } catch (SQLException e) {
            throw new IllegalArgumentException("Incorrect database connection");
        }
        String query = "INSERT INTO Dates"
                + "(tourId,"
                + "tourDate,"
                + "availableSeats,"
                + "maxAvailableSeats) "
                + "values(?,?,?,?);";
        PreparedStatement ps;
        try {
            conn.setAutoCommit(false);
            ps = conn.prepareStatement(query);
            ps.setInt(1, tourId);
            ps.setDate(2, tourDate);
            ps.setInt(3, maxAvailableSeats);
            ps.setInt(4, availableSeats);
            ps.executeUpdate();
            ps.close();
            conn.commit();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

}
