/**
 * @author <Nguyen Ha Tuan Nguyen - s3978072>
 */
package Class;

import Interface.From_String;
import Interface.Id_generate;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class insurance_card implements Id_generate, From_String {
    private String id;
    private String customer;
    private String policyOwner;
    private Date expirationDate;

    // Define insuranceCards as a list of insurance_card objects

    public static List<insurance_card> insuranceCards = new ArrayList<>();

    // default constructor

    public insurance_card() {
    }

    public insurance_card(String id, String customer, String policyOwner, Date expirationDate) {
        this.id = id;
        this.customer = customer;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    //getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    // method section

    // add insurance card

    public static void addInsuranceCard(insurance_card insuranceCard) {
        insuranceCards.add(insuranceCard);
    }

    // get all claims

    public static List<insurance_card> getInsuranceCards() {
        return insuranceCards;
    }

    // This is the method that reads the last assigned id from the file lastAssignedInsuranceId.txt. If the file does not exist, it returns 0.

    @Override
    public int readLastAssignedId() {
        try {
            File file = new File("src/Id_folder/lastAssignedInsuranceId.txt");
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

    private int lastAssignedId = readLastAssignedId();

    @Override
    public void writeLastAssignedId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Id_folder/lastAssignedInsuranceId.txt"));
            writer.write(String.valueOf(lastAssignedId));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateId() {
        lastAssignedId++;
        writeLastAssignedId();
        return String.format("%010d", lastAssignedId);
    }

    // get insurance card by id

    public static insurance_card getInsuranceCardById(String id){
        for (insurance_card insuranceCard : insuranceCards) {
            if(insuranceCard.getId() == id){
                return insuranceCard;
            }
        }
        return null;
    }

    // CRU for insurance card

    public static void create_insurance_card(Scanner scanner) throws ParseException {
        System.out.println("Enter the ID of the customer: ");
        String customerId = scanner.nextLine();

        // Check if the customer exists in policy_holder list
        policy_holder policyHolderToCheck = policy_holder.getPolicyHolderById(customerId);
        // Check if the customer exists in dependent list
        dependent dependentToCheck = dependent.getDependentById(customerId);

        if (policyHolderToCheck != null || dependentToCheck != null) {
            String policyOwner;
            while (true) {
                System.out.println("Enter the policy owner of the insurance card: ");
                policyOwner = scanner.nextLine();

                // Check if the policy owner exists
                policy_holder policyOwnerToCheck = policy_holder.getPolicyHolderById(policyOwner);
                if (policyOwnerToCheck != null) {
                    break;
                } else {
                    System.out.println("The policy owner does not exist. Please enter a valid policy owner.");
                }
            }

            System.out.println("Enter the expiration date of the insurance card (in format yyyy-mm-dd): ");
            String expirationDateInput = scanner.nextLine();
            Date expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateInput);

            insurance_card newInsuranceCardInsert = new insurance_card();
            String id = newInsuranceCardInsert.generateId();
            insurance_card newInsuranceCard = new insurance_card(id, customerId, policyOwner, expirationDate);
            insuranceCards.add(newInsuranceCard);

            // Update the insurance card ID for the customer
            if (policyHolderToCheck != null) {
                policyHolderToCheck.setInsuranceCard(id);
            } else {
                dependentToCheck.setInsuranceCard(id);
            }

            System.out.println("The insurance card has been created successfully with ID: " + id);
        } else {
            System.out.println("The customer does not exist");
        }
    }

    public static void update_insurance_card(Scanner scanner) throws ParseException {
        System.out.println("Enter the ID of the insurance card you want to update: ");
        String id = scanner.nextLine();
        insurance_card insuranceCardToUpdate = getInsuranceCardById(id);
        if (insuranceCardToUpdate != null) {
            System.out.println("Enter the new customer of the insurance card (or press Enter to skip): ");
            String newCustomer = scanner.nextLine();
            if (!newCustomer.isEmpty()) {
                // Check if the new customer exists in policy_holder list
                policy_holder newPolicyHolderToCheck = policy_holder.getPolicyHolderById(newCustomer);
                // Check if the new customer exists in dependent list
                dependent newDependentToCheck = dependent.getDependentById(newCustomer);

                if (newPolicyHolderToCheck != null || newDependentToCheck != null) {
                    // Remove the insurance card ID from the old customer
                    String oldCustomer = insuranceCardToUpdate.getCustomer();
                    policy_holder oldPolicyHolderToCheck = policy_holder.getPolicyHolderById(oldCustomer);
                    dependent oldDependentToCheck = dependent.getDependentById(oldCustomer);
                    if (oldPolicyHolderToCheck != null) {
                        oldPolicyHolderToCheck.setInsuranceCard(String.valueOf(0));
                    } else if (oldDependentToCheck != null) {
                        oldDependentToCheck.setInsuranceCard(String.valueOf(0));
                    }

                    // Assign the insurance card ID to the new customer
                    insuranceCardToUpdate.setCustomer(newCustomer);
                    if (newPolicyHolderToCheck != null) {
                        newPolicyHolderToCheck.setInsuranceCard(id);
                    } else {
                        newDependentToCheck.setInsuranceCard(id);
                    }
                } else {
                    System.out.println("The new customer does not exist. Please enter a valid customer.");
                    return;
                }
            }

            System.out.println("Enter the new policy owner of the insurance card (or press Enter to skip): ");
            String policyOwner = scanner.nextLine();
            if (!policyOwner.isEmpty()) {
                insuranceCardToUpdate.setPolicyOwner(policyOwner);
            }

            System.out.println("Enter the new expiration date of the insurance card (in format yyyy-mm-dd, or press Enter to skip): ");
            String expirationDateInput = scanner.nextLine();
            if (!expirationDateInput.isEmpty()) {
                Date expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateInput);
                insuranceCardToUpdate.setExpirationDate(expirationDate);
            }

            System.out.println("The insurance card has been updated successfully");
        } else {
            System.out.println("The insurance card does not exist");
        }
    }

    public static void read_insurance_card(Scanner scanner) {
        System.out.println("Enter the ID of the insurance card you want to view: ");
        String id = scanner.nextLine();
        insurance_card insuranceCardToView = getInsuranceCardById(id);
        if (insuranceCardToView != null) {
            System.out.println("Insurance Card ID: " + insuranceCardToView.getId());
            System.out.println("Customer: " + insuranceCardToView.getCustomer());
            System.out.println("Policy Owner: " + insuranceCardToView.getPolicyOwner());
            System.out.println("Expiration Date: " + new SimpleDateFormat("yyyy-MM-dd").format(insuranceCardToView.getExpirationDate()));
        } else {
            System.out.println("The insurance card does not exist");
        }
    }

    public static void read_all_insurance_card() {
        for (insurance_card insuranceCard : insuranceCards) {
            System.out.println(insuranceCard.toString());
        }
    }

    public static void read_all_nonexpired_insurance_card() {
        for (insurance_card insuranceCard : insuranceCards) {
            if (insuranceCard.getExpirationDate().after(new Date())) {
                System.out.println(insuranceCard);
            }
        }
    }

    public static void read_all_expired_insurance_card() {
        for (insurance_card insuranceCard : insuranceCards) {
            if (insuranceCard.getExpirationDate().before(new Date())) {
                System.out.println(insuranceCard);
            }
        }
    }

    // fromString method

    @Override
    public void fromString(String line) {
        String[] parts = line.split(",(?![^\\[]*\\])");

        String id = parts[0].split("=")[1];
        String customer = parts[1].split("=")[1].replace("'", "");
        String policyOwner = parts[2].split("=")[1].replace("'", "");

        Date expirationDate = null;
        if (!parts[3].split("=")[1].equals("null")) {
            try {
                String expirationDateString = parts[3].split("=")[1].replaceAll("\\}$", "");
                expirationDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(expirationDateString);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        this.setId(id);
        this.setCustomer(customer);
        this.setPolicyOwner(policyOwner);
        this.setExpirationDate(expirationDate);
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", customer='" + customer +
                ", policyOwner=" + policyOwner +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
