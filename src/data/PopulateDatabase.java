package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class PopulateDatabase {
    private static String url;
    private static boolean real;
    private static String filePath;

    private static BufferedReader readFile(String fileName) throws IOException {
        fileName = "D:/Documents/Tour/src/gervigogn/" + fileName;
        Path path = Paths.get(fileName);
        BufferedReader br = Files.newBufferedReader(path);
        return br;
    }

    public static void makeTours() throws SQLException {
        String trunc = "DELETE FROM Tours";
        String query = "INSERT INTO Tours"
        +"(tourName,"
        +"price,"
        +"description,"
        +"difficulty,"
        +"providerName)"
        +"values(?,?,?,?,?);";
        Connection conn = null;
        Statement stmt;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(trunc);
            ps = conn.prepareStatement(query);
            BufferedReader br = readFile("tours.txt");
            String line;
            while((line=br.readLine())!=null) {
                String[] lineArray = line.split(",");
                ps.setString(1, lineArray[0]);
                ps.setInt(2, Integer.parseInt(lineArray[1]));
                ps.setString(3, lineArray[2]);
                ps.setInt(4, Integer.parseInt(lineArray[3]));
                ps.setString(5, lineArray[4]);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            System.out.println("Tours populated");
        } catch (SQLException | IOException e) {
            System.out.println("Failed to populate Tours");
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
    }

    public static void makeDates() throws SQLException {
        String trunc = "DELETE FROM Dates";
        String query = "INSERT INTO Dates"
                +"(tourId,"
                +"tourDate,"
                +"availableSeats)"
                +"values(?,?,?);";
        Connection conn = null;
        Statement stmt;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(trunc);
            ps = conn.prepareStatement(query);
            BufferedReader br = readFile("dates.txt");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            String line;
            while((line=br.readLine())!=null) {
                String[] lineArray = line.split(",");
                ps.setInt(1, Integer.parseInt(lineArray[0]));
                java.util.Date date = sdf.parse(lineArray[1]);
                java.sql.Date sqlDate = new Date(date.getTime());
                ps.setDate(2, sqlDate);
                ps.setString(3, lineArray[2]);
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

    public static void makeCustomers() throws SQLException {
        String trunc = "DELETE FROM Customers";
        String query = "INSERT INTO Customers"
                +"(name,"
                +"email)"
                +"values(?,?);";
        Connection conn = null;
        Statement stmt;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(trunc);
            ps = conn.prepareStatement(query);
            BufferedReader br = readFile("customers.txt");
            String line;
            while((line=br.readLine())!=null) {
                String[] lineArray = line.split(",");
                ps.setString(1, lineArray[0]);
                ps.setString(2, lineArray[1]);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            System.out.println("Customers populated");
        } catch (SQLException | IOException e) {
            System.out.println("Failed to populate Customers");
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
    }

    public static void makeReservations() throws SQLException {
        String trunc = "DELETE FROM Reservations";
        String query = "INSERT INTO Reservations"
                +"(tourId,"
                +"tourDate,"
                +"noOfSeats,"
                +"customerName,"
                +"customerEmail)"
                +"values(?,?,?,?,?);";
        Connection conn = null;
        Statement stmt;
        PreparedStatement ps;
        try {
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.executeUpdate(trunc);
            ps = conn.prepareStatement(query);
            BufferedReader br = readFile("reservations.txt");
            SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
            String line;
            while((line=br.readLine())!=null) {
                String[] lineArray = line.split(",");
                ps.setInt(1, Integer.parseInt(lineArray[0]));
                java.util.Date date = sdf.parse(lineArray[1]);
                java.sql.Date sqlDate = new Date(date.getTime());
                ps.setDate(2, sqlDate);
                ps.setInt(3, Integer.parseInt(lineArray[2]));
                ps.setString(4, lineArray[3]);
                ps.setString(5, lineArray[4]);
                ps.addBatch();
            }
            ps.executeBatch();
            conn.commit();
            System.out.println("Dates populated");
        } catch (SQLException | IOException | ParseException e) {
            System.out.println("Failed to populate Customers");
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
    }

    public static void realFile(String file) {
        try {
            File dbFile = new File(file);
            if (dbFile.exists()) {
                real = true;
                return;
            }
        } catch (Exception e) {
            System.out.println("File " + file + "does not exist.");
        }
    }

    public static void main(String[] args) {
        String root = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        String dir = root.replace(separator, "/");
        String dbName = dir + "/data/tour.db";
        url = "jdbc:sqlite:" + dbName;
        realFile(dbName);
        if(real) {
            try {
                makeTours();
                makeDates();
                makeCustomers();
                makeReservations();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
