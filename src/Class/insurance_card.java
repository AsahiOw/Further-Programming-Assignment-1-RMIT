package Class;

import Interface.Id_generate;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class insurance_card implements Id_generate {
    private int id;
    private String customer;
    private String policyOwner;
    private Date expirationDate;
    // Define insuranceCards as a list of policy_holder objects
    public static List<insurance_card> insuranceCards = new ArrayList<>();

    public insurance_card(int id, String customer, String policyOwner, Date expirationDate) {
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
    public int generateIdInt() {
        lastAssignedId++;
        while (String.valueOf(lastAssignedId).length() < 10) {
            lastAssignedId *= 10;
        }
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
    // CRU for insurance card
    public void create_insurance_card(Scanner scanner) throws ParseException {
        System.out.println("Enter the customer of the insurance card: ");
        String customer = scanner.nextLine();

        System.out.println("Enter the policy owner of the insurance card: ");
        String policyOwner = scanner.nextLine();

        System.out.println("Enter the expiration date of the insurance card (in format yyyy-mm-dd): ");
        String expirationDateInput = scanner.nextLine();
        Date expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(expirationDateInput);

        int id = generateIdInt();
        insurance_card newInsuranceCard = new insurance_card(id, customer, policyOwner, expirationDate);
        insuranceCards.add(newInsuranceCard);

        System.out.println("The insurance card has been created successfully with ID: " + id);
    }

    public static void update_insurance_card(Scanner scanner) throws ParseException {
        System.out.println("Enter the ID of the insurance card you want to update: ");
        int id = Integer.parseInt(scanner.nextLine());
        insurance_card insuranceCardToUpdate = getInsuranceCardById(id);
        if (insuranceCardToUpdate != null) {
            System.out.println("Enter the new customer of the insurance card (or press Enter to skip): ");
            String customer = scanner.nextLine();
            if (!customer.isEmpty()) {
                insuranceCardToUpdate.setCustomer(customer);
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
        int id = Integer.parseInt(scanner.nextLine());
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
            System.out.println("Insurance Card ID: " + insuranceCard.getId());
            System.out.println("Customer: " + insuranceCard.getCustomer());
            System.out.println("Policy Owner: " + insuranceCard.getPolicyOwner());
            System.out.println("Expiration Date: " + new SimpleDateFormat("yyyy-MM-dd").format(insuranceCard.getExpirationDate()));
        }
    }

    public static void read_all_nonexpired_insurance_card() {
        for (insurance_card insuranceCard : insuranceCards) {
            if (insuranceCard.getExpirationDate().after(new Date())) {
                System.out.println("Insurance Card ID: " + insuranceCard.getId());
                System.out.println("Customer: " + insuranceCard.getCustomer());
                System.out.println("Policy Owner: " + insuranceCard.getPolicyOwner());
                System.out.println("Expiration Date: " + new SimpleDateFormat("yyyy-MM-dd").format(insuranceCard.getExpirationDate()));
            }
        }
    }

    public static void read_all_expired_insurance_card() {
        for (insurance_card insuranceCard : insuranceCards) {
            if (insuranceCard.getExpirationDate().before(new Date())) {
                System.out.println("Insurance Card ID: " + insuranceCard.getId());
                System.out.println("Customer: " + insuranceCard.getCustomer());
                System.out.println("Policy Owner: " + insuranceCard.getPolicyOwner());
                System.out.println("Expiration Date: " + new SimpleDateFormat("yyyy-MM-dd").format(insuranceCard.getExpirationDate()));
            }
        }
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
