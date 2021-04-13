package data;

import java.time.LocalDate;
import Model.Tour;
import Model.TourDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

import static application.Utils.getUrlAndDatabase;
import static application.Utils.toLocalDateTime;

public class TourDb {
    Connection conn = null;
    Statement stmt;
    PreparedStatement ps;
    ResultSet rs;
    String url;

    public TourDb() { url=getUrlAndDatabase()[0]; }

    public int makeTour(String tourName, int price, String description, int difficulty, int location, int childFriendly, int season, String providerName, Connection conn) throws SQLException {
        try {
            String url = conn.getMetaData().getURL();
            if(!url.equals(getUrlAndDatabase()[0])) throw new IllegalArgumentException("Incorrect database connection");
        } catch (SQLException e) {
            throw new IllegalArgumentException("Incorrect database connection");
        }
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
            conn.commit();
            return rs.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //sækja tours útfrá þessum parametrum
    public ObservableList<Tour> fetchTours(int difficulty, int[] priceRange, int groupSize, int location, LocalDate[] dateRange) throws SQLException {
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
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);
            Tour currentTour = new Tour();
            ArrayList<TourDate> currentTourDates = new ArrayList<>();
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
                    currentTourDates = new ArrayList<>();
                }
                LocalDateTime ldt = toLocalDateTime(rs.getLong("tourDate"));
                TourDate td = new TourDate(
                        ldt,
                        rs.getInt("availableSeats"),
                        rs.getInt("maxAvailableSeats")
                );
                currentTourDates.add(td);
            }
            currentTour.setDates(currentTourDates);
            t.add(currentTour);
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
        return t;
    }

    //Skilar true ef náðist að eyða, false annars
    public boolean removeTour(/*Einhverjir parametrar*/) {
        return true;
    }
    
}
