package retailStore;

import java.sql.*;
import java.util.Scanner;

public class CheckoutSystem {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {

        Scanner scanner = new Scanner(System.in);

        // 1. Load Driver
        Class.forName(OracleInfo.DRIVER_CLASS_ORACLE);
        System.out.println("Driver is loaded");

        // 2. Connect to database
        Connection connection = DriverManager.getConnection(OracleInfo.URL, OracleInfo.U, OracleInfo.P);
        System.out.println("Database is connected!\n");

        // 3. Create Statement
        Statement statement = connection.createStatement();

        // Loop for multiple purchases
        String choice = "yes";

        while (choice.equalsIgnoreCase("yes")) {

            // Ask user for product ID
            System.out.print("Enter Product ID: ");

            // Validate product ID is a number
            if (!scanner.hasNextInt()) {
                System.out.println("Error: Product ID must be a number.");
                scanner.next();
                continue;
            }

            int productId = scanner.nextInt();

            // Validate product ID is positive
            if (productId <= 0) {
                System.out.println("Error: Product ID must be greater than 0.");
                continue;
            }

            // 4. Retrieve product by ID using column index
            ResultSet rs = statement.executeQuery("SELECT * FROM Products WHERE id = " + productId);

            // Check if product exists
            if (!rs.next()) {
                System.out.println("Error: No product found with ID " + productId);
                rs.close();
                continue;
            }

            // 5. Display product details using column index
            int id = rs.getInt(1);
            String name = rs.getString(2);
            double price = rs.getDouble(3);
            int stock = rs.getInt(4);

            System.out.println("\n------ PRODUCT DETAILS ------");
            System.out.println("ID    : " + id);
            System.out.println("Name  : " + name);
            System.out.println("Price : $" + price);
            System.out.println("Stock : " + stock);

            rs.close();

            // Ask for quantity
            System.out.print("Enter Quantity to purchase: ");

            // Validate quantity is a number
            if (!scanner.hasNextInt()) {
                System.out.println("Error: Quantity must be a number.");
                scanner.next();
                continue;
            }

            int quantity = scanner.nextInt();

            // Validate quantity is positive
            if (quantity <= 0) {
                System.out.println("Error: Quantity must be greater than 0.");
                continue;
            }

            // 6. Check if enough stock exists
            if (quantity > stock) {
                System.out.println("Error: Not enough stock. Available stock: " + stock);
                continue;
            }

            // 7. Update stock after purchase
            int newStock = stock - quantity;
            statement.executeUpdate("UPDATE Products SET stock = " + newStock + " WHERE id = " + productId);

            System.out.println("\n------ PURCHASE SUCCESSFUL ------");
            System.out.println("You bought   : " + quantity + " x " + name);
            System.out.println("Total cost   : $" + (price * quantity));
            System.out.println("Updated Stock: " + newStock);

            // Show updated product from database
            ResultSet updated = statement.executeQuery("SELECT * FROM Products WHERE id = " + productId);
            if (updated.next()) {
                System.out.println("\n------ UPDATED PRODUCT ------");
                System.out.println(updated.getInt(1) + " | " +
                                   updated.getString(2) + " | $" +
                                   updated.getDouble(3) + " | Stock: " +
                                   updated.getInt(4));
            }
            updated.close();

            // Ask if user wants to continue
            System.out.print("\nDo you want to make another purchase? (yes/no): ");
            choice = scanner.next();
        }

        System.out.println("\nThank you for shopping! Goodbye.");

        // 8. Close everything
        statement.close();
        connection.close();
        scanner.close();

        System.out.println("Connection closed.");
    }
}