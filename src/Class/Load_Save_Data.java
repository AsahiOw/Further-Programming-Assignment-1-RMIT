package Class;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Load_Save_Data {
    // Specify the file names
    static String insuranceCardFileName = "src/Data_folder/insurance_card_data.txt";
    static String claimFileName = "src/Data_folder/claim_data.txt";
    static String customerFileName = "src/Data_folder/customer_data.txt";
    // create file if not exists
    private static void createFileIfNotExists(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
    }

    // method for load data from file
    private static void loadDataFromFiles() {
        try {
            // Load insurance card data
            File insuranceCardFile = new File(insuranceCardFileName);
            Scanner insuranceCardScanner = new Scanner(insuranceCardFile);
            while (insuranceCardScanner.hasNextLine()) {
                String line = insuranceCardScanner.nextLine();
                insurance_card insuranceCard = insurance_card.fromString(line);
                if (insuranceCard != null) {
                    insurance_card.addInsuranceCard(insuranceCard);
                }
            }
            insuranceCardScanner.close();

            // Load claim data
            File claimFile = new File(claimFileName);
            Scanner claimScanner = new Scanner(claimFile);
            while (claimScanner.hasNextLine()) {
                String line = claimScanner.nextLine();
                claim Claim = claim.fromString(line);
                if (Claim != null) {
                    claim.addClaim(Claim);
                }
            }
            claimScanner.close();

            // Load customer data
            File customerFile = new File(customerFileName);
            Scanner customerScanner = new Scanner(customerFile);
            while (customerScanner.hasNextLine()) {
                String line = customerScanner.nextLine();
                policy_holder policyHolder = policy_holder.fromString(line);
                if (policyHolder != null) {
                    policy_holder.addPolicyHolder(policyHolder);
                }
                dependent Dependent = dependent.fromString(line);
                if (Dependent != null) {
                    dependent.addDependent(Dependent);
                }
            }
            customerScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        }
    }

    // load data from file
    public static void loadDataFromFilesPublic() throws IOException {

        // check if the file exists
        createFileIfNotExists(insuranceCardFileName);
        createFileIfNotExists(claimFileName);
        createFileIfNotExists(customerFileName);
        loadDataFromFiles();
    }

    public static void saveDataToFile() {
        try {
            // Save insurance card data
            PrintWriter insuranceCardWriter = new PrintWriter(insuranceCardFileName);
            for (insurance_card insuranceCard : insurance_card.getInsuranceCards()) {
                insuranceCardWriter.println(insuranceCard.toString());
            }
            insuranceCardWriter.close();

            // Save claim data
            PrintWriter claimWriter = new PrintWriter(claimFileName);
            for (claim claim : claim.getClaims()) {
                claimWriter.println(claim.toString());
            }
            claimWriter.close();

            // Save customer data
            PrintWriter customerWriter = new PrintWriter(customerFileName);
            for (policy_holder policyHolder : policy_holder.getPolicyHolders()) {
                customerWriter.println(policyHolder.toString());
            }
            for (dependent dependent : dependent.getDependents()) {
                customerWriter.println(dependent.toString());
            }
            customerWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
}