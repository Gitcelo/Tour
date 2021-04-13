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
import java.util.Arrays;
import static application.Utils.*;

/**
 * @author Team 3D
 *
 * Program that inserts fake data into the tables Tours and Dates in tour.db.
 * Before running this program it is recommended to run MakeDatabase.java.
 */
public class PopulateDatabase {

    private final TourDb tdb;
    private final DateDb ddb;
    private final String url;
    private final String dbName;

    private PopulateDatabase() {
        tdb = new TourDb();
        ddb = new DateDb();
        String[] strings = getUrlAndDatabase();
        url = strings[0];
        dbName = strings[1];
    }

    /**
     * @param fileName
     * @return
     * @throws IOException
     */
    private BufferedReader readFile(String fileName) throws IOException {
        String srcPath = getSrcPath();
        fileName = srcPath + "fakeData/" + fileName;
        Path path = Paths.get(fileName);
        return Files.newBufferedReader(path);
    }

    private void clearTables() {
        Statement stmt;
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            stmt.addBatch("DELETE FROM TOURS");
            stmt.addBatch("DELETE FROM Dates");
            stmt.addBatch("DELETE FROM Reservations");
            stmt.executeBatch();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void makeTours() {
        try {
            BufferedReader br = readFile("tours.txt");
            String line;
            while ((line = br.readLine()) != null) {
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
        }
    }

    private void makeDates() {
        try (Connection conn = DriverManager.getConnection(url)) {
            conn.setAutoCommit(false);
            BufferedReader br = readFile("dates.txt");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
            String line;
            while ((line = br.readLine()) != null) {
                String[] stringArray = line.split(",");
                String[] lineArray = Arrays.stream(stringArray).map(String::trim).toArray(String[]::new);
                java.util.Date date = sdf.parse(lineArray[1]);
                Date sqlDate = new Date(date.getTime());
                ddb.makeDate(
                        Integer.parseInt(lineArray[0]),
                        sqlDate,
                        Integer.parseInt(lineArray[2]),
                        Integer.parseInt(lineArray[3]),
                        conn
                );
            }
            System.out.println("Dates populated");
        } catch (SQLException | IOException | ParseException e) {
            System.out.println("Failed to populate Dates");
            e.printStackTrace();
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
        if (pd.realFile()) {
            pd.clearTables();
            pd.makeTours();
            pd.makeDates();
        } else System.err.println("Database missing");
    }
}
