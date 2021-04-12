package data;

import Model.Tour;
import Model.TourDate;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

public class PopulateDatabase {

    private TourDb tdb;
    private ReservationDb rdb;
    private Connection conn;
    private Statement stmt;
    private String url;
    private String dbName;

    private PopulateDatabase() {
        tdb = new TourDb();
        rdb = new ReservationDb();
        String[] strings = application.Utils.getUrlAndDatabase();
        url = strings[0];
        dbName = strings[1];
    }

    private BufferedReader readFile(String fileName) throws IOException {
        fileName = "D:/Documents/Tour/src/fakeData/" + fileName;
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);
        return br;
    }

    private void clearTables() throws SQLException {
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.addBatch("DELETE FROM TOURS");
            stmt.addBatch("DELETE FROM Dates");
            stmt.addBatch("DELETE FROM Reservations");
            stmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
    }

    private void makeTours() throws SQLException {
        try {
            BufferedReader br = readFile("tours.txt");
            String line;
            while((line=br.readLine())!=null) {
                String[] lineArray = line.split(",");
                tdb.makeTour(
                        lineArray[0],
                        Integer.parseInt(lineArray[1]),
                        lineArray[2],
                        Integer.parseInt(lineArray[3]),
                        Integer.parseInt(lineArray[4]),
                        Integer.parseInt(lineArray[5]),
                        Integer.parseInt(lineArray[6]),
                        lineArray[7]
                );
            }
            System.out.println("Tours populated");
        } catch (SQLException | IOException e) {
            System.out.println("Failed to populate Tours");
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
    }

    private void makeDates() throws SQLException {
        String query = "INSERT INTO Dates"
                +"(tourId,"
                +"tourDate,"
                +"availableSeats,"
                +"maxAvailableSeats)"
                +"values(?,?,?,?);";
        Connection conn = null;
        Statement stmt;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            ps = conn.prepareStatement(query);
            BufferedReader br = readFile("dates.txt");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
            String line;
            while((line=br.readLine())!=null) {
                String[] stringArray = line.split(",");
                String[] lineArray = Arrays.stream(stringArray).map(String::trim).toArray(String[]::new);
                ps.setInt(1, Integer.parseInt(lineArray[0]));
                java.util.Date date = sdf.parse(lineArray[1]);
                java.sql.Date sqlDate = new Date(date.getTime());
                ps.setDate(2, sqlDate);
                ps.setInt(3, Integer.parseInt(lineArray[2]));
                ps.setInt(4, Integer.parseInt(lineArray[3]));
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            System.out.println("Dates populated");
        } catch (SQLException | IOException | ParseException e) {
            System.out.println("Failed to populate Dates");
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
    }

    private boolean realFile() {
        try {
            File dbFile = new File(dbName);
            if (dbFile.exists()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        PopulateDatabase pd = new PopulateDatabase();
        if(pd.realFile()) {
            try {
                pd.clearTables();
                pd.makeTours();
                pd.makeDates();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
