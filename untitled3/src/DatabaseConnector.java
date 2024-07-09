import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:mysql://localhost:3306/HistoricalDiscoveries";
    private static final String USER = "root";
    private static final String PASSWORD = "musa12";
    private static final String FILE_PATH = "resources/funn.txt";

    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH));
             Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            conn.setAutoCommit(false);

            String personSQL = "INSERT INTO Persons (id, name, phone, email) VALUES (?, ?, ?, ?)";
            String museumSQL = "INSERT INTO Museums (id, name, location) VALUES (?, ?, ?)";
            String findingSQL = "INSERT INTO Findings (id, latitude, longitude, person_id, date_found, year_estimated, museum_id, type, material, weight, diameter, item_type, estimated_value, image_filename) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            String line;
            int section = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                switch (line) {
                    case "Personer:":
                        section = 1;
                        break;
                    case "Museer:":
                        section = 2;
                        break;
                    case "Funn:":
                        section = 3;
                        break;
                    default:
                        switch (section) {
                            case 1:
                                insertPerson(conn, personSQL, line, br);
                                break;
                            case 2:
                                insertMuseum(conn, museumSQL, line, br);
                                break;
                            case 3:
                                insertFinding(conn, findingSQL, line, br);
                                break;
                        }
                        break;
                }
            }
            conn.commit();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertPerson(Connection conn, String sql, String idLine, BufferedReader br) throws IOException, SQLException {
        int id = Integer.parseInt(idLine);
        String name = br.readLine().trim();
        String phone = br.readLine().trim();
        String email = br.readLine().trim();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, phone);
            pstmt.setString(4, email);
            pstmt.executeUpdate();
        }
    }

    private static void insertMuseum(Connection conn, String sql, String idLine, BufferedReader br) throws IOException, SQLException {
        int id = Integer.parseInt(idLine);
        String name = br.readLine().trim();
        String location = br.readLine().trim();

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, location);
            pstmt.executeUpdate();
        }
    }

    private static void insertFinding(Connection conn, String sql, String idLine, BufferedReader br) throws IOException, SQLException {
        int id = Integer.parseInt(idLine);
        String[] coordinates = br.readLine().trim().split(",");
        double latitude = Double.parseDouble(coordinates[0]);
        double longitude = Double.parseDouble(coordinates[1]);
        int personId = Integer.parseInt(br.readLine().trim());
        String dateFound = br.readLine().trim();
        int yearEstimated = Integer.parseInt(br.readLine().trim());
        String museumIdStr = br.readLine().trim();
        Integer museumId = museumIdStr.isEmpty() ? null : Integer.parseInt(museumIdStr);
        String type = br.readLine().trim();
        String material = br.readLine().trim();

        Double weight = null;
        Double diameter = null;
        String itemType = null;
        Double estimatedValue = null;
        String imageFilename = null;

        switch (type) {
            case "Coin":
                diameter = Double.parseDouble(br.readLine().trim());
                material = br.readLine().trim();
                break;
            case "Weapon":
                itemType = br.readLine().trim();
                material = br.readLine().trim();
                weight = Double.parseDouble(br.readLine().trim());
                break;
            case "Jewelry":
                itemType = br.readLine().trim();
                estimatedValue = Double.parseDouble(br.readLine().trim());
                imageFilename = br.readLine().trim();
                break;
        }

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.setDouble(2, latitude);
            pstmt.setDouble(3, longitude);
            pstmt.setInt(4, personId);
            pstmt.setString(5, dateFound);
            pstmt.setInt(6, yearEstimated);
            if (museumId == null) {
                pstmt.setNull(7, java.sql.Types.INTEGER);
            } else {
                pstmt.setInt(7, museumId);
            }
            pstmt.setString(8, type);
            pstmt.setString(9, material);
            if (weight == null) {
                pstmt.setNull(10, java.sql.Types.DOUBLE);
            } else {
                pstmt.setDouble(10, weight);
            }
            if (diameter == null) {
                pstmt.setNull(11, java.sql.Types.DOUBLE);
            } else {
                pstmt.setDouble(11, diameter);
            }
            if (itemType == null) {
                pstmt.setNull(12, java.sql.Types.VARCHAR);
            } else {
                pstmt.setString(12, itemType);
            }
            if (estimatedValue == null) {
                pstmt.setNull(13, java.sql.Types.DOUBLE);
            } else {
                pstmt.setDouble(13, estimatedValue);
            }
            if (imageFilename == null) {
                pstmt.setNull(14, java.sql.Types.VARCHAR);
            } else {
                pstmt.setString(14, imageFilename);
            }
            pstmt.executeUpdate();
        }
    }
}
