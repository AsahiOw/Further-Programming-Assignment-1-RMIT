package Class;
import Interface.ClaimProcessManager;
import Enum.allStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static Class.claim.claims;

public class ClaimProcessManagerImplement implements ClaimProcessManager{
    //    CRUD for claim
    @Override
    public void add(Scanner scanner) throws ParseException {
        System.out.println("Enter the insured person's id: ");
        String insuredPerson = scanner.nextLine();

        System.out.println("Enter the insurance card number: ");
        int insuranceCard = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the claim date (in format yyyy-mm-dd): ");
        String claimDateInput = scanner.nextLine();
        Date claimDate = new SimpleDateFormat("yyyy-MM-dd").parse(claimDateInput);

        System.out.println("Enter the exam date (in format yyyy-mm-dd): ");
        String examDateInput = scanner.nextLine();
        Date examDate = new SimpleDateFormat("yyyy-MM-dd").parse(examDateInput);

        System.out.println("Enter the claim amount: ");
        double claimAmount = Double.parseDouble(scanner.nextLine());

        System.out.println("Enter the status of the claim: ");
        allStatus status = allStatus.valueOf(scanner.nextLine().toUpperCase());

        System.out.println("Enter the banking info: ");
        String bankingInfo = scanner.nextLine();

        System.out.println("Enter the documents related to the claim (separated by comma): ");
        String documentsInput = scanner.nextLine();
        List<String> documents = new ArrayList<>();
        if (!documentsInput.isEmpty()) {
            String[] documentsArray = documentsInput.split(",");
            for (String document : documentsArray) {
                documents.add(document.trim());
            }
        }
        claim claimInstance = new claim();
        String id = claimInstance.generateId();
        claim newClaim = new claim(id, claimDate, insuredPerson, insuranceCard, examDate, documents, claimAmount, status, bankingInfo);

        claims.add(newClaim);

        System.out.println("The claim has been created successfully with ID: " + id);
    }

    @Override
    public void update(Scanner scanner) {
        System.out.println("Enter the ID of the claim you want to update: ");
        String claimId = scanner.nextLine();
        claim existingClaim = claim.getClaimById(claimId);
        if (existingClaim == null) {
            System.out.println("No claim found with the provided ID.");
            return;
        }

        System.out.println("Enter the new insured person's name (or press Enter to skip): ");
        String insuredPerson = scanner.nextLine();
        if (!insuredPerson.isEmpty()) {
            existingClaim.setInsuredPerson(insuredPerson);
        }

        System.out.println("Enter the new insurance card number (or press Enter to skip): ");
        String insuranceCardInput = scanner.nextLine();
        if (!insuranceCardInput.isEmpty()) {
            int insuranceCard = Integer.parseInt(insuranceCardInput);
            existingClaim.setInsuranceCard(insuranceCard);
        }

        String claimDateInput;
        Date claimDate = null;
        do {
            System.out.println("Enter the new claim date (in format yyyy-mm-dd) (or press Enter to skip): ");
            claimDateInput = scanner.nextLine();
            if (!claimDateInput.isEmpty()) {
                try {
                    claimDate = new SimpleDateFormat("yyyy-MM-dd").parse(claimDateInput);
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Enter the new claim date (in format yyyy-mm-dd) (or press Enter to skip): ");
                }
            }
        } while (claimDate == null && !claimDateInput.isEmpty());
        if (claimDate != null) {
            existingClaim.setClaimDate(claimDate);
        }

        String examDateInput;
        Date examDate = null;
        do {
            System.out.println("Enter the new exam date (in format yyyy-mm-dd) (or press Enter to skip): ");
            examDateInput = scanner.nextLine();
            if (!examDateInput.isEmpty()) {
                try {
                    examDate = new SimpleDateFormat("yyyy-MM-dd").parse(examDateInput);
                } catch (ParseException e) {
                    System.out.println("Invalid date format. Enter the new exam date (in format yyyy-mm-dd) (or press Enter to skip): ");
                }
            }
        } while (examDate == null && !examDateInput.isEmpty());
        if (examDate != null) {
            existingClaim.setExamDate(examDate);
        }

        System.out.println("Enter the new claim amount (or press Enter to skip): ");
        String claimAmountInput = scanner.nextLine();
        if (!claimAmountInput.isEmpty()) {
            double claimAmount = Double.parseDouble(claimAmountInput);
            existingClaim.setClaimAmount(claimAmount);
        }

        System.out.println("Enter the new status of the claim (or press Enter to skip): ");
        String statusInput = scanner.nextLine();
        if (!statusInput.isEmpty()) {
            allStatus status = allStatus.valueOf(statusInput.toUpperCase());
            existingClaim.setStatus(status);
        }

        System.out.println("Enter the new banking info (or press Enter to skip): ");
        String bankingInfo = scanner.nextLine();
        if (!bankingInfo.isEmpty()) {
            existingClaim.setBankingInfo(bankingInfo);
        }

        System.out.println("Enter the new documents related to the claim (separated by comma) (or press Enter to skip): ");
        String documentsInput = scanner.nextLine();
        if (!documentsInput.isEmpty()) {
            List<String> documents = new ArrayList<>();
            String[] documentsArray = documentsInput.split(",");
            for (String document : documentsArray) {
                documents.add(document.trim());
            }
            existingClaim.setDocuments(documents);
        }

        System.out.println("The claim has been updated successfully.");
    }

    @Override
    public void delete(Scanner scanner) {
        System.out.println("Enter the ID of the claim you want to delete: ");
        String claimId = scanner.nextLine();
        claim existingClaim = claim.getClaimById(claimId);
        if (existingClaim == null) {
            System.out.println("No claim found with the provided ID.");
            return;
        }

        // Remove the claim from all customers' claim lists
        for (policy_holder policyHolder : policy_holder.getPolicyHolders()) {
            policyHolder.getClaims().removeIf(claimId::equals);
        }
        for (dependent dependent : dependent.getDependents()) {
            dependent.getClaims().removeIf(claimId::equals);
        }

        claims.remove(existingClaim);
        System.out.println("The claim has been deleted successfully.");
    }

    @Override
    public claim getOne(Scanner scanner) {
        System.out.println("Enter the ID of the claim you want to view: ");
        String claimId = scanner.nextLine();
        claim existingClaim = claim.getClaimById(claimId);
        if (existingClaim == null) {
            System.out.println("No claim found with the provided ID.");
            return null;
        }
        return existingClaim;
    }

    @Override
    public List<claim> getAll() {
        return claims;
    }
}
