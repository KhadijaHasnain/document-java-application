import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseOperations dbOps = new DatabaseOperations();

        while (true) {
            System.out.println("Menu:");
            System.out.println("1. See information about all found objects");
            System.out.println("2. See information on all found objects older than <year>");
            System.out.println("3. Get information on the number of found objects registered");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    try {
                        List<Finding> findings = dbOps.getAllFindings();
                        findings.forEach(System.out::println);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    System.out.print("Enter year: ");
                    int year = scanner.nextInt();
                    try {
                        List<Finding> findings = dbOps.getFindingsOlderThan(year);
                        findings.forEach(System.out::println);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 3:
                    try {
                        int count = dbOps.getCountOfFindings();
                        System.out.println("Total number of findings: " + count);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                case 4:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
