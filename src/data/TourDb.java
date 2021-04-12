package data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import Model.Tour;
import Model.TourDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class TourDb {
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

    //Skilar true ef insert náðist, annars false
    public boolean makeTour(/*Einhverjir parametrar*/) {
        return true;
    }

    //sækja tours útfrá þessum parametrum
    public ObservableList<Tour> fetchTours(int difficulty, int[] priceRange, int groupSize, int location, LocalDate[] dateRange) {
        ObservableList<Tour> t = FXCollections.observableArrayList();
        try {
        String format = "yyyy-MM-dd-HH";
        String formatStart = dateRange[0].format(DateTimeFormatter.ofPattern(format));
        String formatEnd = dateRange[1].format(DateTimeFormatter.ofPattern(format));
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        long startDate = sdf.parse(formatStart).getTime();
        long endDate = sdf.parse(formatEnd).getTime();
        String query = "SELECT Tours.*, Dates.tourDate, Dates.availableSeats, Dates.maxAvailableSeats"
                +"FROM Tours, Dates"
                +"WHERE Tours.tourId=Dates.tourId"
                +"AND difficulty="
                +difficulty
                +"AND price BETWEEN"
                +priceRange[0]
                +"AND"
                +priceRange[1]
                +"AND availableSeats>="
                +groupSize
                +"AND location="
                +location
                +"AND tourDate BETWEEN"
                +startDate
                +"AND"
                +endDate
                +"ORDER BY Tours.tourId;";
            conn = DriverManager.getConnection(getUrl());
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Tour currentTour = new Tour();
            ArrayList<TourDate> currentTourDates = new ArrayList<TourDate>();
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
                    currentTourDates = new ArrayList<TourDate>();
                }
                LocalDateTime ldt = toLocalDateTime(rs.getLong("tourDate"));
                TourDate td = new TourDate(
                        ldt,
                        rs.getInt("availableSeats"),
                        rs.getInt("maxAvailableSeats")
                );
                currentTourDates.add(td);
            }
        } catch(SQLException | ParseException e) {
            e.printStackTrace();
        }
        return t;
    }

    //Skilar true ef náðist að eyða, false annars
    public boolean removeTour(/*Einhverjir parametrar*/) {
        return true;
    }
    
}
