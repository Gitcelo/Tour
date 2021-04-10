package gagnagrunnur;

import java.io.*;
import java.sql.*;
import java.nio.file.Files;

public class MakeDatabase {
    /*private static void createDatabase() {
        Connection conn = null;
        Statement stmt = null;
        String separator = System.getProperty("file.separator");
        String dir = System.getProperty("user.dir");
        String new_dir = dir.replace(separator, "/");
        String data = new_dir + "/gagnagrunnur/tour.db";
        String url = "jdbc:sqlite:" + data;
        String schema = new_dir + "/gagnagrunnur/schema.sql";
        StringBuffer command=null;
        try {
            File dbFile = new File("data");
            if (dbFile.exists()) {
                System.out.println("File exists");
                Files.deleteIfExists(dbFile.toPath());
                //    return;
            } else {
                System.out.println("not exists");
            }
            //Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(url);
            conn.setAutoCommit(false);
            Reader reader = new FileReader(schema);
            LineNumberReader lineReader = new LineNumberReader(reader);
            String line;
            stmt = conn.createStatement();
            while((line=lineReader.readLine())!=null) {
                if(command==null) command = new StringBuffer();
                String trimmed = line.trim();
                if(trimmed.startsWith("--")) System.out.println(trimmed);
                else if(trimmed.length()<1||trimmed.startsWith("//")) continue;
                command.append(line);
                if(trimmed.endsWith(";")) {
                    System.out.println(command.toString());
                    stmt.execute(command.toString());
                    command = null;
                    }
                conn.commit();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    private static void createDatabase() {
        InputStream scriptStream = null;
        Connection conn = null;
        Statement stmt = null;
        String root = System.getProperty("user.dir");
        String separator = System.getProperty("file.separator");
        String dir = root.replace(separator, "/");
        String dbName = dir + "/gagnagrunnur/tour.db";
        String url = "jdbc:sqlite:" + dbName;
        String schema = dir + "/gagnagrunnur/schema.sql";
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

        } catch(SQLException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        createDatabase();
    }
}
