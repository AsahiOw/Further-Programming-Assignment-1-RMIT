/**
 * @author <Nguyen Ha Tuan Nguyen - s3978072>
 */
package Class;

import java.io.*;
import java.util.Scanner;


public class Load_Save_Data {
    // Specify the file names
    static String insuranceCardFileName = "src/Data_folder/insurance_card_data.txt";
    static String claimFileName = "src/Data_folder/claim_data.txt";
    static String dependentFileName = "src/Data_folder/dependent_data.txt";
    static String policyHolderFileName = "src/Data_folder/policyHolder_data.txt";

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
                insurance_card insuranceCard = new insurance_card();
                insuranceCard.fromString(line);
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
                claim Claim = new claim();
                Claim.fromString(line);
                if (Claim != null) {
                    claim.addClaim(Claim);
                }
            }
            claimScanner.close();

            // Load policy holder data
            File policyHolderFile = new File(policyHolderFileName);
            Scanner policyHolderScanner = new Scanner(policyHolderFile);
            while (policyHolderScanner.hasNextLine()) {
                String line = policyHolderScanner.nextLine();
                policy_holder policyHolder = new policy_holder();
                policyHolder.fromString(line);
                if (policyHolder != null) {
                    policy_holder.addPolicyHolder(policyHolder);
                }
            }
            policyHolderScanner.close();

            // Load dependent data
            File dependentFile = new File(dependentFileName);
            Scanner dependentScanner = new Scanner(dependentFile);
            while (dependentScanner.hasNextLine()) {
                String line = dependentScanner.nextLine();
                dependent Dependent = new dependent();
                Dependent.fromString(line);
                if (Dependent != null) {
                    dependent.addDependent(Dependent);
                }
            }
            dependentScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error loading data from file: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // load data from file

    public static void loadDataFromFilesPublic() throws IOException {

        // check if the file exists
        createFileIfNotExists(insuranceCardFileName);
        createFileIfNotExists(claimFileName);
        createFileIfNotExists(dependentFileName);
        createFileIfNotExists(policyHolderFileName);
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

            // Save policy holder data
            PrintWriter policyHolderWriter = new PrintWriter(policyHolderFileName);
            for (policy_holder policyHolder : policy_holder.getPolicyHolders()) {
                policyHolderWriter.println(policyHolder.toString());
            }
            policyHolderWriter.close();

            // Save dependent data
            PrintWriter dependentWriter = new PrintWriter(dependentFileName);
            for (dependent dependent : dependent.getDependents()) {
                dependentWriter.println(dependent.toString());
            }
            dependentWriter.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error saving data to file: " + e.getMessage());
        }
    }
}