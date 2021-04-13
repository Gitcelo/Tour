package data;

import java.io.*;
import java.sql.*;
import static application.Utils.getUrlAndDatabase;
import static application.Utils.getSrcPath;

public class MakeDatabase {
    private static void createDatabase() throws SQLException {
        Connection conn = null;
        Statement stmt;
        String[] strings = getUrlAndDatabase();
        String url = strings[0];
        String dbName = strings[1];
        String schema = getSrcPath() + "data/schema.sql";
        StringBuffer command=null;
        try {
            File dbFile = new File(dbName);
            if (dbFile.exists()) {
                dbFile.delete();
            }
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            Reader reader = new FileReader(schema);
            LineNumberReader lineReader = new LineNumberReader(reader);
            String line;
            stmt = conn.createStatement();
            while((line=lineReader.readLine())!=null) {
                if(command==null) command = new StringBuffer();
                String trimmed = line.trim();
                if(trimmed.startsWith("--")) {
                    System.out.println(trimmed);
                    continue;
                }
                else if(trimmed.length()<1||trimmed.startsWith("//")) continue;
                command.append(line);
                if(trimmed.endsWith(";")) {
                    stmt.execute(command.toString());
                    command = null;
                }
                conn.commit();
            }

        } catch(SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            if(conn!=null) conn.close();
        }
    }

    public static void main(String[] args) {
        try {
            createDatabase();
        } catch (SQLException e) {
            System.err.println("Could not create database");
            e.printStackTrace();
        }
    }
}
