package Class;

import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import Enum.allStatus;
import java.util.stream.Collectors;

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

    // load data from file using two-pass loading process
    // first pass: load all basic data to the system
    // second pass: load all dependent data to the system
    private static List<insurance_card> loadBasicInsuranceCard(String fileName) {
        List<insurance_card> insuranceCards = new ArrayList<>();
        List<allCustomer> customers = new ArrayList<>();
        List<policy_holder> policyHolders = new ArrayList<>();

        // First pass: create instances with basic information
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Create instances with basic information
                int id = Integer.parseInt(parts[0]);
                allCustomer customer = new allCustomer(parts[1], null, null, null, null, null);
                policy_holder policyOwner = new policy_holder(parts[2], null, null, null, null);
                Date expirationDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[3]);
                insurance_card insuranceCard = new insurance_card(id, customer, policyOwner, expirationDate);

                // Add instances to lists
                customers.add(customer);
                policyHolders.add(policyOwner);
                insuranceCards.add(insuranceCard);
            }
        } catch (FileNotFoundException | ParseException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return insuranceCards;
    }

    private static void loadAllInsuranceCard(List<insurance_card> insuranceCards) {
        // Second pass: fill in the rest of the information
        for (insurance_card insuranceCard : insuranceCards) {
            allCustomer cust = allCustomer.getCustomerById(insuranceCard.getCustomer().getId());
            policy_holder policyHolder = policy_holder.getPolicyHolderById(insuranceCard.getPolicyOwner().getId());

            // Check if the customer and policy_holder fields of the insurance_card are null before setting them
            if (insuranceCard.getCustomer() == null) {
                insuranceCard.setCustomer(cust);
            }
            if (insuranceCard.getPolicyOwner() == null) {
                insuranceCard.setPolicyOwner(policyHolder);
            }

            // Check if the insurance_card field of the customer and policy_holder are null before setting them
            if (cust.getInsuranceCard() == null) {
                cust.setInsuranceCard(insuranceCard);
            }
            if (policyHolder.getInsuranceCard() == null) {
                policyHolder.setInsuranceCard(insuranceCard);
            }
        }
    }
    private static List<claim> loadBasicClaim(String fileName) {
        List<claim> claims = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Create a new claim instance using the parts
                String id = parts[0];
                Date claimDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[1]);
                allCustomer insuredPerson = new allCustomer(parts[2], null, null, null, null, null);
                insurance_card insuranceCard = new insurance_card(Integer.parseInt(parts[3]), insuredPerson, null, null);
                Date examDate = new SimpleDateFormat("yyyy-MM-dd").parse(parts[4]);
                List<String> documents = Arrays.asList(parts[5].split(";"));
                double claimAmount = Double.parseDouble(parts[6]);
                allStatus status = allStatus.valueOf(parts[7]);
                String bankingInfo = parts[8];
                claim claim = new claim(id, claimDate, insuredPerson, insuranceCard, examDate, documents, claimAmount, status, bankingInfo);

                // Add the claim instance to the list
                claims.add(claim);
            }
        } catch (FileNotFoundException | ParseException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return claims;
    }

    private static void loadAllClaim(List<claim> claims) {
        // Second pass: fill in the rest of the information
        for (claim claim : claims) {
            allCustomer insuredPerson = allCustomer.getCustomerById(claim.getInsuredPerson().getId());
            insurance_card insuranceCard = insurance_card.getInsuranceCardById(claim.getInsuranceCard().getId());

            // Check if the insuredPerson and insuranceCard fields of the claim are null before setting them
            if (claim.getInsuredPerson() == null) {
                claim.setInsuredPerson(insuredPerson);
            }
            if (claim.getInsuranceCard() == null) {
                claim.setInsuranceCard(insuranceCard);
            }

            // Create a List<claim> and add the claim to it
            List<claim> claimList = new ArrayList<>();
            claimList.add(claim);

            // Check if the claim field of the insuredPerson and insuranceCard are null before setting them
            if (insuredPerson.getClaims() == null) {
                insuredPerson.setClaims(claimList);
            }
            if (insuranceCard.getClaim() == null) {
                insuranceCard.setClaim(claim);
            }
        }
    }

    private static List<customer> loadBasicCustomer(String fileName) {
        List<customer> customers = new ArrayList<>();

        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Create a new customer instance based on the type
                if (parts[0].equals("p")) {
                    // Create a new policy_holder instance
                    String id = parts[1];
                    String fullName = parts[2];
                    policy_holder policyHolder = new policy_holder(id, fullName, null, new ArrayList<>(), new ArrayList<>());
                    customers.add(policyHolder);
                } else if (parts[0].equals("d")) {
                    // Create a new dependent instance
                    String id = parts[1];
                    String fullName = parts[2];
                    dependent dependent = new dependent(id, fullName, null, new ArrayList<>(), null);
                    customers.add(dependent);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        return customers;
    }
    private static void loadAllCustomer(List<customer> customers, String fileName) {
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");

                // Find the customer in the customers list that matches the ID
                for (customer cust : customers) {
                    if (cust.getId().equals(parts[0])) {
                        // Fill in the rest of the information based on the type
                        if (cust instanceof policy_holder) {
                            // Fill in the List<dependent> field
                            List<dependent> dependents = new ArrayList<>();
                            for (int i = 1; i < parts.length; i++) {
                                dependent dep = dependent.getDependentById(parts[i]);
                                if (dep != null) {
                                    dependents.add(dep);
                                }
                            }
                            if (((policy_holder) cust).getDependents() == null) {
                                ((policy_holder) cust).setDependents(dependents);
                            }
                        } else if (cust instanceof dependent) {
                            // Fill in the policy_holder field
                            policy_holder policyHolder = policy_holder.getPolicyHolderById(parts[1]);
                            if (policyHolder != null && ((dependent) cust).getPolicyHolder() == null) {
                                ((dependent) cust).setPolicyHolder(policyHolder);
                            }
                        }
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    // load data from file
    public static void loadDataFromFiles() throws IOException {

        // check if the file exists
        createFileIfNotExists(insuranceCardFileName);
        createFileIfNotExists(claimFileName);
        createFileIfNotExists(customerFileName);

        // Load the basic data
        List<insurance_card> insuranceCards = loadBasicInsuranceCard(insuranceCardFileName);
        List<claim> claims = loadBasicClaim(claimFileName);
        List<customer> customers = loadBasicCustomer(customerFileName);

        // Load the rest of the data
        loadAllInsuranceCard(insuranceCards);
        loadAllClaim(claims);
        loadAllCustomer(customers, customerFileName);
    }

    public static void saveDataToFile() {
        List<insurance_card> insuranceCards = insurance_card.getInsuranceCards();
        List<claim> claims = claim.getClaims();
        List<allCustomer> customers = allCustomer.getCustomers();
        // Save insurance cards
        try (PrintWriter insuranceCardWriter = new PrintWriter(new File(insuranceCardFileName))) {
            for (insurance_card insuranceCard : insuranceCards) {
                if (insuranceCard != null) {
                    insuranceCardWriter.println(insuranceCard.getId() + "," + (insuranceCard.getCustomer() != null ? insuranceCard.getCustomer().getId() : "") + "," + (insuranceCard.getPolicyOwner() != null ? insuranceCard.getPolicyOwner().getId() : "") + "," + new SimpleDateFormat("yyyy-MM-dd").format(insuranceCard.getExpirationDate()));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to save insurance cards.");
            e.printStackTrace();
        }

        // Save claims
        try (PrintWriter claimWriter = new PrintWriter(new File(claimFileName))) {
            for (claim claim : claims) {
                if (claim != null) {
                    claimWriter.println(claim.getId() + "," + new SimpleDateFormat("yyyy-MM-dd").format(claim.getClaimDate()) + "," + (claim.getInsuredPerson() != null ? claim.getInsuredPerson().getId() : "") + "," + (claim.getInsuranceCard() != null ? claim.getInsuranceCard().getId() : "") + "," + new SimpleDateFormat("yyyy-MM-dd").format(claim.getExamDate()) + "," + String.join(";", claim.getDocuments()) + "," + claim.getClaimAmount() + "," + claim.getStatus().toString() + "," + claim.getBankingInfo());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to save claims.");
            e.printStackTrace();
        }

        // Save customers
        try (PrintWriter customerWriter = new PrintWriter(new File(customerFileName))) {
            for (customer cust : customers) {
                if (cust != null) {
                    if (cust instanceof policy_holder) {
                        policy_holder policyHolder = (policy_holder) cust;
                        String dependents = policyHolder.getDependents().stream().filter(Objects::nonNull).map(dependent::getId).collect(Collectors.joining(";"));
                        customerWriter.println("p," + policyHolder.getId() + "," + policyHolder.getFullName() + "," + dependents);
                    } else if (cust instanceof dependent) {
                        dependent dependent = (dependent) cust;
                        customerWriter.println("d," + dependent.getId() + "," + dependent.getFullName() + "," + (dependent.getPolicyHolder() != null ? dependent.getPolicyHolder().getId() : ""));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred while trying to save customers.");
            e.printStackTrace();
        }
    }
}