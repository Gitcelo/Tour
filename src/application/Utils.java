package application;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Utils {
    public static String getUrl() {
        String root = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        String dir = root.replace(separator, "/");
        String dbName = dir + "/src/data/tour.db";
        System.out.println(dbName);
        return "jdbc:sqlite:" + dbName;
    }

    public static LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
