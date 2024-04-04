/**
 * @author <Nguyen Ha Tuan Nguyen - s3978072>
 */
import Class.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import static Class.Load_Save_Data.loadDataFromFilesPublic;

public class Main {
    public static void main(String[] args) throws IOException {
        loadDataFromFilesPublic();
        System.out.println("Welcome to the Insurance Management System");
        System.out.println("(Note: Please exit the system properly to prevent data compomised.)");
        Scanner scanner = new Scanner(System.in);
        ClaimProcessManagerImplement claimProcessManager = new ClaimProcessManagerImplement();
        String input;
        int choice;
        do {
            System.out.println("What would you like to do?");
            System.out.println("1. Add a new customer");
            System.out.println("2. Update a customer's information");
            System.out.println("3. Get a customer's information");
            System.out.println("4. Get all customers' information");
            System.out.println("5. Add a new claim");
            System.out.println("6. Update a claim's information");
            System.out.println("7. Delete a claim");
            System.out.println("8. Get a claim's information");
            System.out.println("9. Get all claims' information");
            System.out.println("10. Add a new insurance card");
            System.out.println("11. Update an insurance card's information");
            System.out.println("12. Get an insurance card's information");
            System.out.println("13. Get all insurance cards' information");
            System.out.println("14. Check insurance card date");
            System.out.println("15. Exit");
            System.out.print("Enter your choice: ");

            input = scanner.nextLine();
            try {
                choice = Integer.parseInt(input);
                if (choice < 1 || choice > 15) {
                    System.out.println("Invalid choice. Please choose again.");
                    choice = 0; // Reset choice to an invalid value
                } else {
                    switch (choice) {
                        case 1:
                            System.out.println("1. Add a policy holder");
                            System.out.println("2. Add a dependent");
                            System.out.print("Enter your choice: ");
                            int addCustomerChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            switch (addCustomerChoice) {
                                case 1:
                                    policy_holder policyHolderInstance = new policy_holder();
                                    policyHolderInstance.create_customer(scanner);
                                    Load_Save_Data.saveDataToFile();
                                    System.out.println("System saved");
                                    break;
                                case 2:
                                    dependent dependentInstance = new dependent();
                                    dependentInstance.create_customer(scanner);
                                    Load_Save_Data.saveDataToFile();
                                    System.out.println("System saved");
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                                    break;
                            }
                            break;
                        case 2:
                            System.out.println("1. Update a policy holder's information");
                            System.out.println("2. Update a dependent's information");
                            System.out.print("Enter your choice: ");
                            int updateCustomerChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            switch (updateCustomerChoice) {
                                case 1:
                                    System.out.println("Enter the id of the policy holder you want to update");
                                    String policyholderid = scanner.nextLine();
                                    policy_holder policyHolderInstance = policy_holder.getPolicyHolderById(policyholderid);
                                    if (policyHolderInstance != null) {
                                        policyHolderInstance.update_customer(policyholderid, scanner);
                                        Load_Save_Data.saveDataToFile();
                                        System.out.println("System saved");
                                    } else {
                                        System.out.println("The policy holder does not exist");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Enter the id of the dependent you want to update");
                                    String dependentId = scanner.nextLine();
                                    dependent dependentInstance = dependent.getDependentById(dependentId);
                                    if (dependentInstance != null) {
                                        dependentInstance.update_customer(dependentId, scanner);
                                        Load_Save_Data.saveDataToFile();
                                        System.out.println("System saved");
                                    } else {
                                        System.out.println("The dependent does not exist");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                                    break;
                            }
                            break;
                        case 3:
                            System.out.println("1. Get a policy holder's information");
                            System.out.println("2. Get a dependent's information");
                            System.out.print("Enter your choice: ");
                            int readCustomerChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            switch (readCustomerChoice) {
                                case 1:
                                    System.out.println("Enter the id of the policy holder you want to view");
                                    String policyholderid = scanner.nextLine();
                                    policy_holder policyHolderInstance = policy_holder.getPolicyHolderById(policyholderid);
                                    if (policyHolderInstance != null) {
                                        policyHolderInstance.read_customer(policyholderid);
                                    } else {
                                        System.out.println("The policy holder does not exist");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Enter the id of the dependent you want to view");
                                    String dependentId = scanner.nextLine();
                                    dependent dependentInstance = dependent.getDependentById(dependentId);
                                    if (dependentInstance != null) {
                                        dependentInstance.read_customer(dependentId);
                                    } else {
                                        System.out.println("The dependent does not exist");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                                    break;
                            }
                            break;
                        case 4:
                            customer.read_all_customers();
                            break;
                        case 5:
                            claimProcessManager.add(scanner);
                            Load_Save_Data.saveDataToFile();
                            System.out.println("System saved");
                            break;
                        case 6:
                            claimProcessManager.update(scanner);
                            Load_Save_Data.saveDataToFile();
                            System.out.println("System saved");
                            break;
                        case 7:
                            claimProcessManager.delete(scanner);
                            Load_Save_Data.saveDataToFile();
                            System.out.println("System saved");
                            break;
                        case 8:
                            claimProcessManager.getOne(scanner);
                            break;
                        case 9:
                            claimProcessManager.getAll();
                            break;
                        case 10:
                            insurance_card.create_insurance_card(scanner);
                            Load_Save_Data.saveDataToFile();
                            System.out.println("System saved");
                            break;
                        case 11:
                            insurance_card.update_insurance_card(scanner);
                            Load_Save_Data.saveDataToFile();
                            System.out.println("System saved");
                            break;
                        case 12:
                            insurance_card.read_insurance_card(scanner);
                            break;
                        case 13:
                            insurance_card.read_all_insurance_card();
                            break;
                        case 14:
                            System.out.println("1. Read all non-expired insurance cards");
                            System.out.println("2. Read all expired insurance cards");
                            System.out.print("Enter your choice: ");
                            int checkInsuranceCardDateChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume the newline character
                            switch (checkInsuranceCardDateChoice) {
                                case 1:
                                    insurance_card.read_all_nonexpired_insurance_card();
                                    break;
                                case 2:
                                    insurance_card.read_all_expired_insurance_card();
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                                    break;
                            }
                            break;
                        case 15:
                            Load_Save_Data.saveDataToFile();
                            System.out.println("System saved");
                            System.out.println("Exiting the system...");
                            break;
                    }
                }
            } catch (NumberFormatException | ParseException e) {
                System.out.println("Invalid choice. Please enter a number.");
                choice = 0; // Reset choice to an invalid value
            }
        } while (choice != 15);
    }
}