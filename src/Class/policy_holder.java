package Class;

import Interface.From_String;
import Interface.Id_generate;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class policy_holder extends customer implements Id_generate, From_String {

    private List<String> dependents;

    // Define policyHolders as a list of policy_holder objects

    public static List<policy_holder> policyHolders = new ArrayList<>();

    // default constructor

    public policy_holder() {
    }

    public policy_holder(String id, String fullName, int insuranceCard, List<String> claims, List<String> dependents) {
        super(id, fullName, insuranceCard, claims);
        this.dependents = dependents;
    }

    //getters and setters

    public List<String> getDependents() {
        return dependents;
    }

    public void setDependents(List<String> dependents) {
        this.dependents = dependents;
    }

    // method section

    // add policy holder

    public static void addPolicyHolder(policy_holder policyHolder) {
        policyHolders.add(policyHolder);
    }

    // get policy holder by id

    public static policy_holder getPolicyHolderById(String id) {
        for (policy_holder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(id)) {
                return policyHolder;
            }
        }
        return null;
    }

    // get all policy holders
    public static List<policy_holder> getPolicyHolders() {
        return policyHolders;
    }

    // This is the method that reads the last assigned id from the file lastAssignedCustomerId.txt. If the file does not exist, it returns 0.

    @Override
    public int readLastAssignedId() {
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

    private int lastAssignedId = readLastAssignedId();

    @Override
    public void writeLastAssignedId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Id_folder/lastAssignedCustomerId.txt"));
            writer.write(String.valueOf(lastAssignedId));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generateId() {
        lastAssignedId++;
        writeLastAssignedId();
        return String.format("C-%07d", lastAssignedId);
    }

    // CRU overriden methods for policyholder

    @Override
    public void create_customer(Scanner scanner) {
        System.out.println("Enter the full name of the policy holder: ");
        String fullName = scanner.nextLine();

        int insuranceCard = 0000000000;

        List<String> claims = new ArrayList<>();
        System.out.println("Enter the claims of the policy holder (separated by comma): ");
        String claimsInput = scanner.nextLine();
        if (!claimsInput.isEmpty()) {
            String[] claimsArray = claimsInput.split(",");
            for (String claim : claimsArray) {
                claims.add(claim.trim());
            }
        }

        List<String> dependents = new ArrayList<>();
        System.out.println("Enter the dependents of the policy holder (separated by comma): ");
        String dependentsInput = scanner.nextLine();
        if (!dependentsInput.isEmpty()) {
            String[] dependentsArray = dependentsInput.split(",");
            for (String dependent : dependentsArray) {
                dependents.add(dependent.trim());
            }
        }

        String id = generateId();
        policy_holder newPolicyHolder = new policy_holder(id, fullName, insuranceCard, claims, dependents);
        policyHolders.add(newPolicyHolder);

        System.out.println("The policy holder has been created successfully with ID: " + id);
    }

    @Override
    public void update_customer(String ids, Scanner scanner) {
        String id = ids;
        policy_holder policyHolderToUpdate = getPolicyHolderById(id);
        if (policyHolderToUpdate != null) {
            System.out.println("Enter the new full name of the policy holder (or press Enter to skip): ");
            String fullName = scanner.nextLine();
            if (!fullName.isEmpty()) {
                policyHolderToUpdate.setFullName(fullName);
            }

            System.out.println("Enter the new insurance card number of the policy holder (or press Enter to skip): ");
            String insuranceCardInput = scanner.nextLine();
            if (!insuranceCardInput.isEmpty()) {
                int insuranceCard = Integer.parseInt(insuranceCardInput);
                policyHolderToUpdate.setInsuranceCard(insuranceCard);
            }

            System.out.println("Enter the new claims of the policy holder (separated by comma, or press Enter to skip): ");
            String claimsInput = scanner.nextLine();
            if (!claimsInput.isEmpty()) {
                List<String> claims = new ArrayList<>();
                String[] claimsArray = claimsInput.split(",");
                for (String claim : claimsArray) {
                    claims.add(claim.trim());
                }
                policyHolderToUpdate.setClaims(claims);
            }

            System.out.println("Enter the new dependents of the policy holder (separated by comma, or press Enter to skip): ");
            String dependentsInput = scanner.nextLine();
            if (!dependentsInput.isEmpty()) {
                List<String> dependents = new ArrayList<>();
                String[] dependentsArray = dependentsInput.split(",");
                for (String dependent : dependentsArray) {
                    dependents.add(dependent.trim());
                }
                policyHolderToUpdate.setDependents(dependents);
            }

            System.out.println("The policy holder has been updated successfully");
        } else {
            System.out.println("The policy holder does not exist");
        }
    }

    @Override
    public void read_customer(String ids) {
        String id = ids;
        policy_holder policyHolderToView = getPolicyHolderById(id);
        if (policyHolderToView != null) {
            System.out.println("Policy Holder ID: " + policyHolderToView.getId());
            System.out.println("Full Name: " + policyHolderToView.getFullName());
            System.out.println("Insurance Card Number: " + policyHolderToView.getInsuranceCard());
            System.out.println("Claims: " + String.join(", ", policyHolderToView.getClaims()));
            System.out.println("Dependents: " + String.join(", ", policyHolderToView.getDependents()));
        } else {
            System.out.println("The policy holder does not exist");
        }
    }

    // fromString method

    @Override
    public void fromString(String line) {
        String[] parts = line.split(",(?![^\\[]*\\])");

        String id = parts[0].split("=")[1].replace("'", "");
        String fullName = parts[1].split("=")[1].replace("'", "");
        int insuranceCard = Integer.parseInt(parts[2].split("=")[1]);

        List<String> claims = new ArrayList<>();
        if (parts.length > 3 && !parts[3].split("=")[1].equals("[]")) {
            String claimsString = parts[3].split("=")[1].replace("[", "").replace("]", "");
            String[] claimsArray = claimsString.split(", ");
            for (String claim : claimsArray) {
                claims.add(claim.trim());
            }
        }

        List<String> dependents = new ArrayList<>();
        if (parts.length > 4 && parts[4].contains("=") && parts[4].split("=").length > 1 && !parts[4].split("=")[1].equals("[]")) {
            String dependentsString = parts[4].split("=")[1].replace("[", "").replace("]", "").trim().replaceAll("\\]$", "").replaceAll("\\}$", "");
            String[] dependentsArray = dependentsString.split(", ");
            for (String dependent : dependentsArray) {
                dependents.add(dependent.trim());
            }
        }

        this.setId(id);
        this.setFullName(fullName);
        this.setInsuranceCard(insuranceCard);
        this.setClaims(claims);
        this.setDependents(dependents);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", insuranceCard=" + getInsuranceCard() +
                ", claim=" + getClaims() +
                ", dependents=" + dependents +
                '}';
    }
}
