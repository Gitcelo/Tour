package data;

import java.time.LocalDate;
import Model.Tour;
import Model.TourDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import static application.Utils.*;

/**
 * @author Team 3D
 *
 * Object that can do queries on the Tours table.
 */
public class TourDb implements MakeConnection {
    Connection conn;
    Statement stmt;
    PreparedStatement ps;
    ResultSet rs;
    String url;

    /**
     * Constructor that initializes the instance variable url.
     */
    public TourDb() { url=getUrlAndDatabase()[0]; }

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
     * Inserts tour into the Tours table.
     *
     * @param tourName Name of the tour.
     * @param price Price of the tour.
     * @param description Description of the tour.
     * @param difficulty Number that represents the difficulty of the tour (between 10 and 13 inclusive).
     * @param location Location of the tour.
     * @param childFriendly True if the tour is child friendly, false otherwise.
     * @param season Number that represents the season the tour takes place in (between 1 and 4 inclusive).
     * @param providerName Name of the provider of the tour.
     * @return Identification number of the new tour if the function managed to insert it, 0 otherwise.
     */
    public int makeTour(String tourName, int price, String description, int difficulty, int location, int childFriendly, int season, String providerName) {
        validConnection(conn);
        String query = "INSERT INTO Tours"
                +"(tourName,"
                +"price,"
                +"description,"
                +"difficulty,"
                +"location,"
                +"childFriendly,"
                +"season,"
                +"providerName) "
                +"values(?,?,?,?,?,?,?,?);";
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            ps = conn.prepareStatement(query);
            ps.setString(1, tourName);
            ps.setInt(2, price);
            ps.setString(3, description);
            ps.setInt(4, difficulty);
            ps.setInt(5, location);
            ps.setInt(6, childFriendly);
            ps.setInt(7, season);
            ps.setString(8, providerName);
            ps.executeUpdate();
            ps.close();
            conn.commit();
            rs = stmt.executeQuery("SELECT last_insert_rowid();");
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        }
    }

    /**
     * Fetches from tours and dates based on the parameters.
     * Ideally there would be parameters such as season and childFriendly but that is inconvenient for the cluster.
     *
     * @param difficulty Number representing the difficulty of the tour (between 10 and 13 inclusive).
     * @param priceRange Array of size two. The first number is the minimum price needed and the second one is the maximum number needed.
     * @param groupSize Number that represents the minimum amount of available seats needed on the tour.
     * @param location Number that represents the location of the tour.
     * @param dateRange Array of size two that contains two dates the tour needs to take place between.
     * @return An ObservableList of Tour objects if query worked, otherwise the ObservableList is empty.
     */
    public ObservableList<Tour> fetchTours(int difficulty, int[] priceRange, int groupSize, int location, LocalDate[] dateRange) {
        validConnection(conn);
        ObservableList<Tour> t = FXCollections.observableArrayList();
        LocalDateTime startDate = dateRange[0].atStartOfDay();
        LocalDateTime endDate = dateRange[1].atStartOfDay();
        long startDateMilli = startDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long endDateMilli = endDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        String query = "SELECT Tours.*, Dates.tourDate, Dates.availableSeats, Dates.maxAvailableSeats "
                +"FROM Tours, Dates "
                +"WHERE Tours.tourId=Dates.tourId "
                +"AND difficulty="
                +difficulty
                +" AND price BETWEEN "
                +priceRange[0]
                +" AND "
                +priceRange[1]
                +" AND availableSeats>="
                +groupSize
                +" AND location="
                +location
                +" AND tourDate BETWEEN "
                +startDateMilli
                +" AND "
                +endDateMilli
                +" ORDER BY Tours.tourId;";
        try {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            Tour currentTour = new Tour();
            ObservableList<TourDate> currentTourDates = FXCollections.observableArrayList();
            int currentId = 0;
            while(rs.next()) {
                int id = rs.getInt("tourId");
                if(id!=currentId) {
                    if (currentId != 0) {
                        currentTour.setDates(currentTourDates);
                        t.add(currentTour);
                    }
                    currentId = id;
                    currentTour = new Tour(
                            currentId,
                            rs.getString("tourName"),
                            rs.getString("description"),
                            rs.getInt("price"),
                            rs.getInt("difficulty"),
                            rs.getInt("childFriendly"),
                            rs.getInt("season"),
                            rs.getInt("location"),
                            rs.getString("providerName")
                    );
                    currentTourDates = FXCollections.observableArrayList();
                }
                LocalDateTime ldt = toLocalDateTime(rs.getLong("tourDate"));
                TourDate td = new TourDate(
                        ldt,
                        rs.getInt("availableSeats"),
                        rs.getInt("maxAvailableSeats")
                );
                currentTourDates.add(td);
            }
            stmt.close();
            rs.close();
            currentTour.setDates(currentTourDates);
            t.add(currentTour);
            return t;
        } catch(SQLException e) {
            e.printStackTrace();
            return t;
        }
    }

    /**
     * Removes a tour from the Tours table.
     *
     * @param tourId Identification number of the tour that is to be removed.
     * @return true if tour was removed, false otherwise.
     */
    public boolean removeTour(int tourId) {
        validConnection(conn);
        String query = "DELETE FROM Reservations WHERE tourId = ? CASCADE";
        try {
            conn.setAutoCommit(false);
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, tourId);
            int result = ps.executeUpdate();
            ps.close();
            conn.commit();
            return result > 0;
        }catch (SQLException e){
            return false;
        }
    }
    
}
