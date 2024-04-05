/**
 * @author <Nguyen Ha Tuan Nguyen - s3978072>
 */
package Class;

import Interface.From_String;
import Interface.Id_generate;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class dependent extends customer implements Id_generate, From_String {
    private String policyHolder;

    // Define Dependents as a list of dependent objects

    public static List<dependent> Dependents = new ArrayList<>();

    // default constructor

    public dependent() {
    }

    public dependent(String id, String fullName, String insuranceCard, List<String> claims, String policyHolder) {
        super(id, fullName, insuranceCard, claims);
        this.policyHolder = policyHolder;
    }

    //getters and setters

    public String getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    // method section

    // add dependent

    public static void addDependent(dependent dependent) {
        Dependents.add(dependent);
    }

    // get dependent by id

    public static dependent getDependentById(String id) {
        for (dependent dependent : Dependents) {
            if (dependent.getId().equals(id)) {
                return dependent;
            }
        }
        return null;
    }

    // get all dependents

    public static List<dependent> getDependents() {
        return Dependents;
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

    //  CRU override methods for dependent

    @Override
    public void create_customer(Scanner scanner) {
        System.out.println("Enter the full name of the dependent: ");
        String fullName = scanner.nextLine();

        String insuranceCard = String.valueOf(0);

        List<String> claims = new ArrayList<>();
        System.out.println("Enter the claims of the dependent (separated by comma): ");
        String claimsInput = scanner.nextLine();
        if (!claimsInput.isEmpty()) {
            String[] claimsArray = claimsInput.split(",");
            for (String claim : claimsArray) {
                claims.add(claim.trim());
            }
        }

        System.out.println("Enter the policy holder of the dependent: ");
        String policyHolder = scanner.nextLine();

        String id = generateId();
        dependent newDependent = new dependent(id, fullName, insuranceCard, claims, policyHolder);
        Dependents.add(newDependent);

        System.out.println("The dependent has been created successfully with ID: " + id);
    }

    @Override
    public void update_customer(String ids, Scanner scanner) {
        String id = ids;
        dependent dependentToUpdate = getDependentById(id);
        if (dependentToUpdate != null) {
            System.out.println("Enter the new full name of the dependent (or press Enter to skip): ");
            String fullName = scanner.nextLine();
            if (!fullName.isEmpty()) {
                dependentToUpdate.setFullName(fullName);
            }

            System.out.println("Enter the new insurance card number of the dependent (or press Enter to skip): ");
            String insuranceCardInput = scanner.nextLine();
            if (!insuranceCardInput.isEmpty()) {
                int insuranceCard = Integer.parseInt(insuranceCardInput);
                dependentToUpdate.setInsuranceCard(String.valueOf(insuranceCard));
            }

            System.out.println("Enter the new claims of the dependent (separated by comma, or press Enter to skip): ");
            String claimsInput = scanner.nextLine();
            if (!claimsInput.isEmpty()) {
                List<String> claims = new ArrayList<>();
                String[] claimsArray = claimsInput.split(",");
                for (String claim : claimsArray) {
                    claims.add(claim.trim());
                }
                dependentToUpdate.setClaims(claims);
            }

            System.out.println("Enter the new policy holder of the dependent (or press Enter to skip): ");
            String policyHolder = scanner.nextLine();
            if (!policyHolder.isEmpty()) {
                dependentToUpdate.setPolicyHolder(policyHolder);
            }

            System.out.println("The dependent has been updated successfully");
        } else {
            System.out.println("The dependent does not exist");
        }
    }

    @Override
    public void read_customer(String ids) {
        String id = ids;
        dependent dependentToView = getDependentById(id);
        if (dependentToView != null) {
            System.out.println("Dependent ID: " + dependentToView.getId());
            System.out.println("Full Name: " + dependentToView.getFullName());
            System.out.println("Insurance Card Number: " + dependentToView.getInsuranceCard());
            System.out.println("Claims: " + String.join(", ", dependentToView.getClaims()));
            System.out.println("Policy Holder: " + dependentToView.getPolicyHolder());
        } else {
            System.out.println("The dependent does not exist");
        }
    }

    // fromString method
    @Override
    public void fromString(String line) {
        String[] parts = line.split(",(?![^\\[]*\\])");

        String id = parts[0].split("=")[1].replace("'", "");
        String fullName = parts[1].split("=")[1].replace("'", "");
        String insuranceCard = parts[2].split("=")[1];

        List<String> claims = new ArrayList<>();
        if (parts.length > 3 && !parts[3].split("=")[1].equals("[]")) {
            String claimsString = parts[3].split("=")[1].replace("[", "").replace("]", "");
            String[] claimsArray = claimsString.split(", ");
            for (String claim : claimsArray) {
                claims.add(claim.trim());
            }
        }

        String policyHolder = "";
        if (parts.length > 4 && parts[4].contains("=") && parts[4].split("=").length > 1 && !parts[4].split("=")[1].equals("[]")) {
            policyHolder = parts[4].split("=")[1].replace("[", "").replace("]", "").trim().replaceAll("\\]$", "").replaceAll("\\}$", "");
        }

        this.setId(id);
        this.setFullName(fullName);
        this.setInsuranceCard(insuranceCard);
        this.setClaims(claims);
        this.setPolicyHolder(policyHolder);
    }

    @Override
    public String toString() {
        return "{" +
                "id='" + getId() + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", insuranceCard=" + getInsuranceCard() +
                ", claim=" + getClaims() +
                ", policyHolder=" + policyHolder +
                '}';
    }
}
