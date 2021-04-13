package application;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

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
        String[] s = {url, dbName};
        return s;
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
}
