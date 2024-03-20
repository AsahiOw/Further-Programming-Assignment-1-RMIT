package Class;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public abstract class customer {
    private static int lastAssignedId = readLastAssignedId();
    private String id;
    private String fullName;
    private insurance_card insuranceCard;
    private List<claim> claim;

    public customer(String id,String fullName, insurance_card insuranceCard, List<claim> claim) {
        this.id = id;
        this.fullName = fullName;
        this.insuranceCard = insuranceCard;
        this.claim = claim;
    }

    // getters and setters
    public String getId() {
        return id;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public insurance_card getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(insurance_card insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public List<claim> getClaims() {
        return claim;
    }

    public void setClaims(List<claim> claims) {
        this.claim = claims;
    }

    // method section

    //    This is the method that reads the last assigned id from the file lastAssignedCustomerId.txt. If the file does not exist, it returns 0.
    private static int readLastAssignedId() {
        try {
            File file = new File("src/Id_folder/lastAssignedCustomerId.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            return line != null ? Integer.parseInt(line) : 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    private static void writeLastAssignedId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Id_folder/lastAssignedCustomerId.txt"));
            writer.write(String.valueOf(lastAssignedId));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String generateId() {
        lastAssignedId++;
        writeLastAssignedId();
        return String.format("C-%07d", lastAssignedId);
    }

    // customer CRUD
    public static void create_customer(Scanner scanner){
        String fullName = "";
        insurance_card insuranceCard = null;
        while (true) {
            System.out.print("Enter the customer's full name: ");
            fullName = scanner.nextLine();
            System.out.print("Enter the customer's insurance card ID (or press Enter to skip): ");
            String insuranceCardIDInput = scanner.nextLine();
            if (!insuranceCardIDInput.isEmpty()) {
                int insuranceCardID = Integer.parseInt(insuranceCardIDInput);
                insuranceCard = insurance_card.getInsuranceCardById(insuranceCardID);
                if (insuranceCard == null) {
                    System.out.println("There is no valid insurance card with the provided ID.");
                    continue;
                }
            }
            break;
        }
        while (true) {
            System.out.print("Enter the customer's type (Policy Holder = 1) (Dependent = 2): ");
            String type = scanner.nextLine();
            if (type.equals("1")) {
                String id = generateId();
                List<claim> claims = new ArrayList<>();
                List<dependent> dependents = new ArrayList<>();
                policy_holder new_holder = new policy_holder(id, fullName, insuranceCard, claims, dependents);
                break;
            } else if (type.equals("2")) {
                System.out.print("Enter the policy holder's ID: ");
                String policyHolderId = scanner.nextLine();
                policy_holder policyHolder = policy_holder.getPolicyHolderById(policyHolderId);
                if (policyHolder == null) {
                    System.out.println("There is no valid policy holder with the provided ID.");
                    continue;
                }
                String id = generateId();
                List<claim> claims = new ArrayList<>();
                dependent new_dependent = new dependent(id, fullName, insuranceCard, claims, policyHolder);
                break;
            } else {
                System.out.println("Invalid type.");
            }
        }
    }
    public static void read_all_customers(){
        for (policy_holder policyHolder : policy_holder.getPolicyHolders()) {
            System.out.println("ALL Policy holders");
            System.out.println(policyHolder);
        }
        for (dependent dependent : dependent.getDependents()) {
            System.out.println("ALL Dependents");
            System.out.println(dependent);
        }
    };

    // abstract methods
    public abstract void delete_customer(Scanner scanner);
    public abstract void update_customer(Scanner scanner);
    public abstract void read_customer(Scanner scanner);

}
