package data;

import java.sql.*;
import static application.Utils.*;

/**
 * @author Team 3D
 *
 * Object that can do queries on the Dates table.
 */
public class DateDb implements MakeConnection {
    private final String url;
    private Connection conn;

    /**
     * Constructor that initializes the instance variable url.
     */
    public DateDb() {
        url = getUrlAndDatabase()[0];
    }

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
     * Inserts into Dates.
     *
     * @param tourId Identification number of tour the date refers to.
     * @param tourDate Date of tour, java.sql.Date object in the form of yyyy-MM-dd-HH.
     * @param maxAvailableSeats Maximum number of available seats for this date.
     * @param availableSeats Current number of available seats for this date.
     */
    public boolean makeDate(int tourId, Date tourDate, int maxAvailableSeats, int availableSeats) {
        validConnection(conn);
        String query = "INSERT INTO Dates"
                + "(tourId,"
                + "tourDate,"
                + "maxAvailableSeats,"
                + "availableSeats) "
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
            return true;
        } catch(SQLException e) {
            return false;
        }
    }

}
