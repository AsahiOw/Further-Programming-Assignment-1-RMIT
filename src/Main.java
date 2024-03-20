import Class.*;
import Enum.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;
import static Class.Load_Save_Data.loadDataFromFiles;

public class Main {
    public static void main(String[] args) throws IOException {
        loadDataFromFiles();
        Scanner scanner = new Scanner(System.in);
        ClaimProcessManagerImplement claimProcessManager = new ClaimProcessManagerImplement();
        String input;
        int choice;
        do {
            System.out.println("Welcome to the Insurance Management System");
            System.out.println("(Note: Please make sure to Exit the system properly to save your data.)");
            System.out.println("What would you like to do?");
            System.out.println("1. Add a new customer");
            System.out.println("2. Update a customer's information");
            System.out.println("3. Delete a customer");
            System.out.println("4. Get a customer's information");
            System.out.println("5. Get all customers' information");
            System.out.println("6. Add a new claim");
            System.out.println("7. Update a claim's information");
            System.out.println("8. Delete a claim");
            System.out.println("9. Get a claim's information");
            System.out.println("10. Get all claims' information");
            System.out.println("11. Add a new insurance card");
            System.out.println("12. Update an insurance card's information");
            System.out.println("13. Delete an insurance card");
            System.out.println("14. Get an insurance card's information");
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
                            customer.create_customer(scanner);
                            break;
                        case 2:
                            System.out.println("1. Update a policy holder's information");
                            System.out.println("2. Update a dependent's information");
                            System.out.print("Enter your choice: ");
                            int updateCustomerChoice = scanner.nextInt();
                            switch (updateCustomerChoice) {
                                case 1:
                                    System.out.println("Enter the id of the policy holder you want to update");
                                    String id = scanner.nextLine();
                                    policy_holder policyHolderInstance = policy_holder.getPolicyHolderById(id);
                                    if (policyHolderInstance != null) {
                                        policyHolderInstance.update_customer(scanner);
                                    } else {
                                        System.out.println("The policy holder does not exist");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Enter the id of the dependent you want to update");
                                    String dependentId = scanner.nextLine();
                                    dependent dependentInstance = dependent.getDependentById(dependentId);
                                    if (dependentInstance != null) {
                                        dependentInstance.update_customer(scanner);
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
                            System.out.println("1. Delete a policy holder");
                            System.out.println("2. Delete a dependent");
                            System.out.print("Enter your choice: ");
                            int deleteCustomerChoice = scanner.nextInt();
                            switch (deleteCustomerChoice) {
                                case 1:
                                    System.out.println("Enter the id of the policy holder you want to delete");
                                    String id = scanner.nextLine();
                                    policy_holder policyHolderInstance = policy_holder.getPolicyHolderById(id);
                                    if (policyHolderInstance != null) {
                                        policyHolderInstance.delete_customer(scanner);
                                    } else {
                                        System.out.println("The policy holder does not exist");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Enter the id of the dependent you want to delete");
                                    String dependentId = scanner.nextLine();
                                    dependent dependentInstance = dependent.getDependentById(dependentId);
                                    if (dependentInstance != null) {
                                        dependentInstance.delete_customer(scanner);
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
                            System.out.println("1. Get a policy holder's information");
                            System.out.println("2. Get a dependent's information");
                            System.out.print("Enter your choice: ");
                            int readCustomerChoice = scanner.nextInt();
                            switch (readCustomerChoice) {
                                case 1:
                                    System.out.println("Enter the id of the policy holder you want to view");
                                    String id = scanner.nextLine();
                                    policy_holder policyHolderInstance = policy_holder.getPolicyHolderById(id);
                                    if (policyHolderInstance != null) {
                                        policyHolderInstance.read_customer(scanner);
                                    } else {
                                        System.out.println("The policy holder does not exist");
                                    }
                                    break;
                                case 2:
                                    System.out.println("Enter the id of the dependent you want to view");
                                    String dependentId = scanner.nextLine();
                                    dependent dependentInstance = dependent.getDependentById(dependentId);
                                    if (dependentInstance != null) {
                                        dependentInstance.read_customer(scanner);
                                    } else {
                                        System.out.println("The dependent does not exist");
                                    }
                                    break;
                                default:
                                    System.out.println("Invalid choice.");
                                    break;
                            }
                            break;
                        case 5:
                            customer.read_all_customers();
                            break;
                        case 6:
                            claimProcessManager.add(scanner);
                            break;
                        case 7:
                            claimProcessManager.update(scanner);
                            break;
                        case 8:
                            claimProcessManager.delete(scanner);
                            break;
                        case 9:
                            claimProcessManager.getOne(scanner);
                            break;
                        case 10:
                            claimProcessManager.getAll();
                            break;
                        case 11:
                            insurance_card.create_insurance_card(scanner);
                            break;
                        case 12:
                            insurance_card.update_insurance_card(scanner);
                            break;
                        case 13:
                            insurance_card.delete_insurance_card(scanner);
                            break;
                        case 14:
                            insurance_card.read_insurance_card(scanner);
                            break;
                        case 15:
                            Load_Save_Data.saveDataToFile();
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