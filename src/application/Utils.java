package application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

    public static LocalDateTime toLocalDateTime(long date) {
        return new Date(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
