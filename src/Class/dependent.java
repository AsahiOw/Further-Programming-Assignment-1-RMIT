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

    public dependent(String id, String fullName, int insuranceCard, List<String> claims, String policyHolder) {
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
    //  CRU overriden methods for dependent
    @Override
    public void create_customer(Scanner scanner) {
        System.out.println("Enter the full name of the dependent: ");
        String fullName = scanner.nextLine();

        System.out.println("Enter the insurance card number of the dependent: ");
        int insuranceCard = Integer.parseInt(scanner.nextLine());

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
    public void update_customer(Scanner scanner) {
        System.out.println("Enter the ID of the dependent you want to update: ");
        String id = scanner.nextLine();
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
                dependentToUpdate.setInsuranceCard(insuranceCard);
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
    public void read_customer(Scanner scanner) {
        System.out.println("Enter the ID of the dependent you want to view: ");
        String id = scanner.nextLine();
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
    // fromString method
    @Override
    public void fromString(String line) {
        String[] parts = line.split(",");
        String id = parts[0];
        String fullName = parts[1];
        int insuranceCard = Integer.parseInt(parts[2]);

        List<String> claims = new ArrayList<>();
        if (!parts[3].equals("null")) {
            String[] claimsArray = parts[3].split(";");
            for (String claim : claimsArray) {
                claims.add(claim);
            }
        }

        String policyHolder = parts[4];

        this.setId(id);
        this.setFullName(fullName);
        this.setInsuranceCard(insuranceCard);
        this.setClaims(claims);
        this.setPolicyHolder(policyHolder);

        Dependents.add(this);
    }
}
