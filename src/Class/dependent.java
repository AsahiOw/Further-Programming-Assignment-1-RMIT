package Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class dependent extends customer{
    private String policyHolder;
    private static List<dependent> Dependents = new ArrayList<>();

    public dependent(String id, String fullName, int insuranceCard, List<String> claims, String policyHolder) {
        super(id, fullName, insuranceCard, claims);
        this.policyHolder = policyHolder;
        Dependents.add(this);
    }

    //getters and setters
    public String getPolicyHolder() {
        return policyHolder;
    }
    public void setPolicyHolder(String policyHolder) {
        this.policyHolder = policyHolder;
    }

    // method section
    //    get dependent by id
    public static dependent getDependentById(String id) {
        for (dependent dependent : Dependents) {
            if (dependent.getId().equals(id)) {
                return dependent;
            }
        }
        return null;
    }
    //    list of all dependent
    public static List<dependent> getDependents() {
        return Dependents;
    }
    //    RUD overriden methods for dependent
    @Override
    public void delete_customer(Scanner scanner) {
        System.out.println("Enter the id of the dependent you want to delete");
        String id = scanner.nextLine();
        dependent dependent = getDependentById(id);
        if (dependent != null) {
            // Remove the dependent from the policy_holder's dependents list
            policy_holder policyHolder = dependent.getPolicyHolder();
            if (policyHolder != null) {
                policyHolder.getDependents().remove(dependent);
            }
            // Remove the dependent from the Dependents list
            Dependents.remove(dependent);
            System.out.println("The dependent has been deleted successfully");
        } else {
            System.out.println("The dependent does not exist");
        }
    }

    @Override
    public void update_customer(Scanner scanner) {
        System.out.println("Enter the ID of the dependent you want to update: ");
        String id = scanner.nextLine();
        dependent dependent = getDependentById(id);
        if (dependent != null) {
            System.out.println("Enter the new full name of the dependent (or press Enter to skip): ");
            String fullName = scanner.nextLine();
            if (!fullName.isEmpty()) {
                dependent.setFullName(fullName);
            }
            System.out.println("Enter the new insurance card ID of the dependent (or press Enter to skip): ");
            String insuranceCardIDInput = scanner.nextLine();
            if (!insuranceCardIDInput.isEmpty()) {
                int insuranceCardID = Integer.parseInt(insuranceCardIDInput);
                insurance_card insuranceCard = insurance_card.getInsuranceCardById(insuranceCardID);
                if (insuranceCard != null) {
                    dependent.setInsuranceCard(insuranceCard);
                } else {
                    System.out.println("There is no valid insurance card with the provided ID.");
                }
            }
            System.out.println("Enter the new policy holder ID of the dependent (or press Enter to skip): ");
            String policyHolderIDInput = scanner.nextLine();
            if (!policyHolderIDInput.isEmpty()) {
                policy_holder newPolicyHolder = policy_holder.getPolicyHolderById(policyHolderIDInput);
                if (newPolicyHolder != null) {
                    policy_holder oldPolicyHolder = dependent.getPolicyHolder();
                    if (oldPolicyHolder != null) {
                        oldPolicyHolder.getDependents().remove(dependent);
                    }
                    newPolicyHolder.getDependents().add(dependent);
                    dependent.setPolicyHolder(newPolicyHolder);
                } else {
                    System.out.println("There is no valid policy holder with the provided ID.");
                }
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
        dependent dependent = getDependentById(id);
        if (dependent != null) {
            System.out.println("Dependent ID: " + dependent.getId());
            System.out.println("Full Name: " + dependent.getFullName());
            if (dependent.getInsuranceCard() != null) {
                System.out.println("Insurance Card ID: " + dependent.getInsuranceCard().getId());
            }
            System.out.println("Claims: ");
            for (claim claim : dependent.getClaims()) {
                System.out.println("Claim ID: " + claim.getId());
            }
            System.out.println("Policy Holder: ");
            if (dependent.getPolicyHolder() != null) {
                System.out.println("Policy Holder ID: " + dependent.getPolicyHolder().getId());
            }
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
}
