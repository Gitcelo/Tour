package application;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    /**
     * A function that finds the path of the current directory
     * (which should be the parent directory of the src folder)
     * and appends "/src/" to it.
     *
     * @return A String that represents the path to the src folder.
     *         Note that the path ends in "/".
     */
    public static String getSrcPath() {
        String root = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        String dir = root.replace(separator, "/");
        return dir + "/src/";
    }

    /**
     * @return A string array that contains two Strings. The first String is
     *         the jdbc-sqlite url to the database and the latter String is
     *         the path to the database.
     */
    public static String[] getUrlAndDatabase() {
        String srcPath = getSrcPath();
        String dbName = srcPath + "data/tour.db";
        String url = "jdbc:sqlite:" + dbName;
        return new String[]{url, dbName};
    }

    /**
     * Converts date in milliseconds to a LocalDateTime object.
     *
     * @param date Long that represents a date in milliseconds.
     * @return The LocalDateTimeObject.
     */
    public static LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    /**
     * Converts an object of type LocalDateTime to an object of type java.sql.Date.
     *
     * @param date Object to be converted.
     * @return Converted object.
     */
    public static java.sql.Date localDateTimeToSQLDate(LocalDateTime date) {
        java.sql.Date sqlDate;
        try{
            String formatted = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH"));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH");
            java.util.Date parsedDate = sdf.parse(formatted);
            sqlDate = new java.sql.Date(parsedDate.getTime());
            return sqlDate;
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if a connection to the database is valid.
     *
     * @param conn The connection being tested.
     */
    public static void validConnection(Connection conn) {
        try {
            String url = conn.getMetaData().getURL();
            if(!url.equals(getUrlAndDatabase()[0])) throw new IllegalArgumentException("Invalid database connection");
        } catch (SQLException e) {
            throw new IllegalArgumentException("Invalid database connection");
        }
    }

    public static boolean isValidEmail(String email)
    {
        Pattern emailPattern = Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");
        Matcher m = emailPattern.matcher(email);
        return m.matches();
    }
}
