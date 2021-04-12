package data;

import java.sql.SQLException;

public class test {
    public static void main(String[] args) {
        TourDb db = new TourDb();
        try {
            int s = db.makeTour(
                    "Jón",
                    100,
                    "",
                    10,
                    1,
                    0,
                    2,
                    "Siggi"
            );
            System.out.println(s);
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
