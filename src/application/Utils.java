package application;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static String[] getUrlAndDatabase() {
        String root = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        String dir = root.replace(separator, "/");
        String dbName = dir + "/src/data/tour.db";
        String url = "jdbc:sqlite:" + dbName;
        String[] s = {url, dbName};
        return s;
    }

    public static LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
