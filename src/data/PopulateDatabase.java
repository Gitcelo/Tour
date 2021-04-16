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
 * All the methods are private since no other program should call and use them.
 * Before running this program it is recommended to run MakeDatabase.java.
 */
public class PopulateDatabase {

    private final TourDb tdb;
    private final DateDb ddb;
    private final String url;
    private final String dbName;

    /**
     * The constructor. It initializes the instance variables.
     */
    private PopulateDatabase() {
        tdb = new TourDb();
        ddb = new DateDb();
        String[] strings = getUrlAndDatabase();
        url = strings[0];
        dbName = strings[1];
    }

    /**
     * Checks whether the file given by the path dbName exists.
     * @return True if file exists, false otherwise.
     */
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

    /**
     * Reads .txt file into BufferedReader.
     *
     * @param fileName Name of .txt file in form of String.
     * @return BufferedReader that has read in the .tct file.
     * @throws IOException If file does not exist.
     */
    private BufferedReader readFile(String fileName) throws IOException {
        String srcPath = getSrcPath();
        fileName = srcPath + "fakeData/" + fileName;
        Path path = Paths.get(fileName);
        return Files.newBufferedReader(path);
    }

    /**
     * Clears all tables within tourdb.
     */
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

    /**
     * Populates the Tours table in tour.db with fake data.
     */
    private void makeTours() {
        try {
            BufferedReader br = readFile("tours.txt");
            String line;
            int num=-1;
            tdb.openConnection();
            while ((line = br.readLine()) != null) {
                String[] stringArray = line.split(";");
                String[] lineArray = Arrays.stream(stringArray).map(String::trim).toArray(String[]::new);
                num = tdb.makeTour(
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
            tdb.closeConnection();
            if(num>0) {
                System.out.println("--Tours populated--");
            } else System.out.println("--Failed to populate Tours--");
        } catch (IOException e) {
            System.out.println("--Failed to populate Tours--");
        }
    }

    /**
     * Populates the Dates table in tour.db with fake data.
     */
    private void makeDates() {
        try {
            BufferedReader br = readFile("dates.txt");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
            boolean b = false;
            String line;
            ddb.openConnection();
            while ((line = br.readLine()) != null) {
                String[] stringArray = line.split(";");
                String[] lineArray = Arrays.stream(stringArray).map(String::trim).toArray(String[]::new);
                java.util.Date date = sdf.parse(lineArray[1]);
                Date sqlDate = new Date(date.getTime());
                b = ddb.makeDate(
                        Integer.parseInt(lineArray[0]),
                        sqlDate,
                        Integer.parseInt(lineArray[2]),
                        Integer.parseInt(lineArray[3])
                );
            }
            ddb.closeConnection();
            if(b) {
                System.out.println("--Dates populated--");
            }
            else System.out.println("--Failed to populate Dates--");
        } catch (IOException | ParseException e) {
            System.out.println("--Failed to populate Dates--");
        }
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
