package Class;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class insurance_card {
    private static int lastAssignedId = readLastAssignedId();
    private int id;
    private customer customer;
    private policy_holder policyOwner;
    private Date expirationDate;
    private static List<insurance_card> insuranceCards;

    public insurance_card(int id, customer customer, policy_holder policyOwner, Date expirationDate) {
        this.id = id;
        this.customer = customer;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }

    public policy_holder getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(policy_holder policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<insurance_card> getInsuranceCards() {
        return insuranceCards;
    }

    public void setInsuranceCards(List<insurance_card> insuranceCards) {
        insurance_card.insuranceCards = insuranceCards;
    }

    // method section
    //    This is the method that reads the last assigned id from the file lastAssignedInsuranceId.txt. If the file does not exist, it returns 0.
    private static int readLastAssignedId() {
        try {
            File file = new File("Id_folder/lastAssignedInsuranceId.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            return line != null ? Integer.parseInt(line.trim()) : 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static void writeLastAssignedId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("Id_folder/lastAssignedInsuranceId.txt"));
            writer.write(String.valueOf(lastAssignedId));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int generateId() {
        lastAssignedId++;
        writeLastAssignedId();
        return lastAssignedId;
    }
    // get insurance card by id
    public static insurance_card getInsuranceCardById(int id){
        for (insurance_card insuranceCard : insuranceCards) {
            if(insuranceCard.getId() == id){
                return insuranceCard;
            }
        }
        return null;
    }
    //    CRUD for insurance card
    public void create_insurance_card(Scanner scanner){
        customer Customer = null;
        while (Customer == null) {
            System.out.print("Enter the customer's ID: ");
            String customerID = scanner.nextLine();
            Customer = customer.getCustomerById(customerID);
            if (Customer == null) {
                System.out.println("There is no valid customer with the provided ID.");
            }
        }

        policy_holder policyHolder = null;
        while (policyHolder == null) {
            System.out.print("Enter the policy holder's ID: ");
            String policyHolderID = scanner.nextLine();
            policyHolder = policy_holder.getPolicyHolderById(policyHolderID);
            if (policyHolder == null) {
                System.out.println("There is no valid policy holder with the provided ID.");
            }
        }

        Date expirationDate = null;
        while (expirationDate == null) {
            System.out.print("Enter the expiration date of the insurance card (yyyy-MM-dd): ");
            String expirationDateInput = scanner.nextLine();
            try {
                expirationDate = new Date(expirationDateInput);
            } catch (Exception e) {
                System.out.println("Invalid date format.");
            }
        }

        int id = generateId();
        insurance_card new_card = new insurance_card(id, Customer, policyHolder, expirationDate);
    }
    public void delete_insurance_card(Scanner scanner){
        System.out.println("Enter the id of the insurance card you want to delete");
        int id = scanner.nextInt();
        insurance_card insuranceCard = getInsuranceCardById(id);
        if (insuranceCard != null) {
            customer Customer = insuranceCard.getCustomer();
            if (Customer != null) {
                Customer.setInsuranceCard(null);
            }
            insuranceCards.remove(insuranceCard);
            System.out.println("The insurance card has been deleted successfully");
        } else {
            System.out.println("The insurance card does not exist");
        }
    }
    public void update_insurance_card(Scanner scanner){
        System.out.println("Enter the ID of the insurance card you want to update: ");
        int id = scanner.nextInt();
        insurance_card insuranceCard = getInsuranceCardById(id);
        if (insuranceCard != null) {
            System.out.println("Enter the new customer ID of the insurance card (or press Enter to skip): ");
            String customerIDInput = scanner.nextLine();
            if (!customerIDInput.isEmpty()) {
                customer Customer = customer.getCustomerById(customerIDInput);
                if (Customer != null) {
                    insuranceCard.setCustomer(Customer);
                } else {
                    System.out.println("There is no valid customer with the provided ID.");
                }
            }
            System.out.println("Enter the new policy holder ID of the insurance card (or press Enter to skip): ");
            String policyHolderIDInput = scanner.nextLine();
            if (!policyHolderIDInput.isEmpty()) {
                policy_holder policyHolder = policy_holder.getPolicyHolderById(policyHolderIDInput);
                if (policyHolder != null) {
                    insuranceCard.setPolicyOwner(policyHolder);
                } else {
                    System.out.println("There is no valid policy holder with the provided ID.");
                }
            }
            System.out.println("Enter the new expiration date of the insurance card (yyyy-MM-dd), (or press Enter to skip): ");
            String expirationDateInput = scanner.nextLine();
            if (!expirationDateInput.isEmpty()) {
                Date expirationDate = null;
                try {
                    expirationDate = new Date(expirationDateInput);
                    insuranceCard.setExpirationDate(expirationDate);
                } catch (Exception e) {
                    System.out.println("Invalid date format.");
                }
            }
            System.out.println("The insurance card has been updated successfully");
        } else {
            System.out.println("The insurance card does not exist");
        }
    }
    public void read_insurance_card(Scanner scanner){
        System.out.println("Enter the ID of the insurance card you want to view: ");
        int id = scanner.nextInt();
        insurance_card insuranceCard = getInsuranceCardById(id);
        if (insuranceCard != null) {
            System.out.println("ID: " + insuranceCard.getId());
            System.out.println("Customer: " + insuranceCard.getCustomer().getFullName());
            System.out.println("Policy Holder: " + insuranceCard.getPolicyOwner().getFullName());
            System.out.println("Expiration Date: " + insuranceCard.getExpirationDate());
        } else {
            System.out.println("The insurance card does not exist");
        }
    }
}
