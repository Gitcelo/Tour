package data;

import java.sql.*;
import static application.Utils.*;

/**
 * Object that can do queries on the Dates table.
 */
public class DateDb {
    String url;

    /**
     * Constructor that initializes the instance variable url.
     */
    public DateDb() {
        url = getUrlAndDatabase()[0];
    }

    /**
     * Inserts into Dates.
     *
     * @param tourId Identification number of tour the date refers to.
     * @param tourDate Date of tour, java.sql.Date object in the form of yyyy-MM-dd-HH.
     * @param maxAvailableSeats Maximum number of available seats for this date.
     * @param availableSeats Current number of available seats for this date.
     * @param conn The connection to the database.
     */
    public void makeDate(int tourId, Date tourDate, int maxAvailableSeats, int availableSeats, Connection conn) {
        if(!validConnection(conn)) {
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
