package Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import static Class.dependent.getDependentById;

public class policy_holder extends customer{
    private List<dependent> dependents;
    private static List<policy_holder> policyHolders = new ArrayList<>();

    public policy_holder(String id, String fullName, insurance_card insuranceCard, List<claim> claims, List<dependent> dependents) {
        super(id, fullName, insuranceCard, claims);
        this.dependents = dependents;
        policyHolders.add(this);
    }

    //getters and setters
    public List<dependent> getDependents() {
        return dependents;
    }
    public void setDependents(List<dependent> dependents) {
        this.dependents = dependents;
    }

    // method section
    public static policy_holder getPolicyHolderById(String id) {
        for (policy_holder policyHolder : policyHolders) {
            if (policyHolder.getId().equals(id)) {
                return policyHolder;
            }
        }
        return null;
    }

    public static List<policy_holder> getPolicyHolders() {
        return policyHolders;
    }

    @Override
    public void delete_customer(Scanner scanner) {
        System.out.println("Enter the id of the dependent you want to delete");
        String id = scanner.nextLine();
        dependent dependent = getDependentById(id);
        if (dependent != null) {
            dependents.remove(dependent);
            System.out.println("The dependent has been deleted successfully");
        } else {
            System.out.println("The dependent does not exist");
        }
    }

    @Override
    public void update_customer(Scanner scanner) {
        System.out.println("Enter the ID of the policy holder you want to update: ");
        String id = scanner.nextLine();
        policy_holder policyHolder = getPolicyHolderById(id);
        if (policyHolder != null) {
            System.out.println("Enter the new full name of the policy holder (or press Enter to skip): ");
            String fullName = scanner.nextLine();
            if (!fullName.isEmpty()) {
                policyHolder.setFullName(fullName);
            }
            System.out.println("Enter the new insurance card ID of the policy holder (or press Enter to skip): ");
            String insuranceCardIDInput = scanner.nextLine();
            if (!insuranceCardIDInput.isEmpty()) {
                int insuranceCardID = Integer.parseInt(insuranceCardIDInput);
                insurance_card insuranceCard = insurance_card.getInsuranceCardById(insuranceCardID);
                if (insuranceCard != null) {
                    policyHolder.setInsuranceCard(insuranceCard);
                } else {
                    System.out.println("There is no valid insurance card with the provided ID.");
                }
            }
            System.out.println("The policy holder has been updated successfully");
        } else {
            System.out.println("The policy holder does not exist");
        }
    }

    @Override
    public void read_customer(Scanner scanner) {
        System.out.println("Enter the ID of the policy holder you want to view: ");
        String id = scanner.nextLine();
        policy_holder policyHolder = getPolicyHolderById(id);
        if (policyHolder != null) {
            System.out.println("Policy Holder ID: " + policyHolder.getId());
            System.out.println("Full Name: " + policyHolder.getFullName());
            if (policyHolder.getInsuranceCard() != null) {
                System.out.println("Insurance Card ID: " + policyHolder.getInsuranceCard().getId());
            }
            System.out.println("Claims: ");
            for (claim claim : policyHolder.getClaims()) {
                System.out.println("Claim ID: " + claim.getId());
            }
            System.out.println("Dependents: ");
            for (dependent dependent : policyHolder.getDependents()) {
                System.out.println("Dependent ID: " + dependent.getId());
            }
        } else {
            System.out.println("The policy holder does not exist");
        }
    }

}
