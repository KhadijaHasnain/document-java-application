import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseOperations {

    private static final String URL = "jdbc:mysql://localhost:3306/HistoricalDiscoveries";
    private static final String USER = "root";
    private static final String PASSWORD = "musa12";

    public static List<Finding> getAllFindings() throws SQLException {
        List<Finding> findings = new ArrayList<>();

        String sql = "SELECT * FROM Findings";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Finding finding = createFinding(rs);
                findings.add(finding);
            }
        }
        return findings;
    }

    public static List<Finding> getFindingsOlderThan(int year) throws SQLException {
        List<Finding> findings = new ArrayList<>();

        String sql = "SELECT * FROM Findings WHERE year_estimated < ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Finding finding = createFinding(rs);
                    findings.add(finding);
                }
            }
        }
        return findings;
    }

    public static int getCountOfFindings() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Findings";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    private static Finding createFinding(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double latitude = rs.getDouble("latitude");
        double longitude = rs.getDouble("longitude");
        int personId = rs.getInt("person_id");
        String dateFound = rs.getString("date_found");
        int yearEstimated = rs.getInt("year_estimated");
        Integer museumId = (Integer) rs.getObject("museum_id");
        String type = rs.getString("type");
        String material = rs.getString("material");

        Finding finding = null;
        switch (type) {
            case "Coin":
                double diameter = rs.getDouble("diameter");
                finding = new Coin(id, latitude, longitude, personId, dateFound, yearEstimated, museumId, material, diameter);
                break;
            case "Weapon":
                String itemType = rs.getString("item_type");
                double weight = rs.getDouble("weight");
                finding = new Weapon(id, latitude, longitude, personId, dateFound, yearEstimated, museumId, material, itemType, weight);
                break;
            case "Jewelry":
                itemType = rs.getString("item_type");
                double estimatedValue = rs.getDouble("estimated_value");
                String imageFilename = rs.getString("image_filename");
                finding = new Jewelry(id, latitude, longitude, personId, dateFound, yearEstimated, museumId, material, itemType, estimatedValue, imageFilename);
                break;
        }
        return finding;
    }
}
