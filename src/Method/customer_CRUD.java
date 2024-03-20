package Method;

import java.util.Scanner;

public class customer_CRUD {
    public static void createCustomer(Scanner scanner) {
        System.out.println("Enter the customer's ID: ");
        String id = scanner.nextLine();
        System.out.println("Enter the customer's full name: ");
        String fullName = scanner.nextLine();
        System.out.println("Enter the customer's insurance card ID: ");
        int insuranceCardID = scanner.nextInt();
        scanner.nextLine(); //consume newline left-over
    }
}
