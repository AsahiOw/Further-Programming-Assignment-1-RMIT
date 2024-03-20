package Class;
import Interface.ClaimProcessManager;
import Enum.allStatus;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Date;

public class ClaimProcessManagerImplement implements ClaimProcessManager{
    //    CRUD for claim
    @Override
    public void add(Scanner scanner) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        Date claimDate = null;
        while (claimDate == null) {
            System.out.println("Enter the claim date (in format yyyy-MM-dd): ");
            String claimDateStr = scanner.nextLine();
            try {
                claimDate = dateFormat.parse(claimDateStr);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        allCustomer insuredPerson = null;
        while (insuredPerson == null) {
            System.out.println("Enter the ID of the insured person: ");
            String insuredPersonId = scanner.nextLine();
            insuredPerson = allCustomer.getCustomerById(insuredPersonId);
            if (insuredPerson == null) {
                System.out.println("No customer found with the provided ID. Please try again.");
            }
        }

        insurance_card insuranceCard = null;
        while (insuranceCard == null) {
            System.out.println("Enter the insurance card ID: ");
            int insuranceCardId = scanner.nextInt();
            insuranceCard = insurance_card.getInsuranceCardById(insuranceCardId);
            if (insuranceCard == null) {
                System.out.println("No insurance card found with the provided ID. Please try again.");
            }
        }
        System.out.println("Enter the exam date (in format yyyy-MM-dd): ");
        String examDateStr = scanner.nextLine();
        Date examDate = new SimpleDateFormat("yyyy-MM-dd").parse(examDateStr);
        System.out.println("Enter the documents (separated by comma): ");
        String documentsStr = scanner.nextLine();
        List<String> documents = Arrays.asList(documentsStr.split(","));
        System.out.println("Enter the claim amount: ");
        double claimAmount = scanner.nextDouble();
        allStatus status = null;
        while (status == null) {
            System.out.println("Enter the status (1 for New, 2 for Processing, 3 for Done): ");
            int statusInt = scanner.nextInt();
            switch (statusInt) {
                case 1:
                    status = allStatus.New;
                    break;
                case 2:
                    status = allStatus.Processing;
                    break;
                case 3:
                    status = allStatus.Done;
                    break;
                default:
                    System.out.println("Invalid status. Please enter 1 for APPROVED, 2 for REJECTED, or 3 for PENDING.");
                    break;
            }
        }
        System.out.println("Enter the banking info: ");
        String bankingInfo = scanner.nextLine();
        String id = claim.generateId();
        claim newClaim = new claim(id, claimDate, insuredPerson, insuranceCard, examDate, documents, claimAmount, status, bankingInfo);
        claim.getClaims().add(newClaim);
        System.out.println("The claim has been added successfully");
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

        System.out.println("Enter the new claim date (in format yyyy-MM-dd), (or press Enter to skip): ");
        String claimDateStr = scanner.nextLine();
        if (!claimDateStr.isEmpty()) {
            Date claimDate = null;
            try {
                claimDate = new SimpleDateFormat("yyyy-MM-dd").parse(claimDateStr);
                existingClaim.setClaimDate(claimDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        System.out.println("Enter the new ID of the insured person, (or press Enter to skip): ");
        String insuredPersonId = scanner.nextLine();
        if (!insuredPersonId.isEmpty()) {
            allCustomer insuredPerson = allCustomer.getCustomerById(insuredPersonId);
            if (insuredPerson != null) {
                existingClaim.setInsuredPerson(insuredPerson);
            } else {
                System.out.println("No customer found with the provided ID. Please try again.");
            }
        }

        System.out.println("Enter the new insurance card ID, (or press Enter to skip): ");
        String insuranceCardIdStr = scanner.nextLine();
        if (!insuranceCardIdStr.isEmpty()) {
            int insuranceCardId = Integer.parseInt(insuranceCardIdStr);
            insurance_card insuranceCard = insurance_card.getInsuranceCardById(insuranceCardId);
            if (insuranceCard != null) {
                existingClaim.setInsuranceCard(insuranceCard);
            } else {
                System.out.println("No insurance card found with the provided ID. Please try again.");
            }
        }

        System.out.println("Enter the new exam date (in format yyyy-MM-dd), (or press Enter to skip): ");
        String examDateStr = scanner.nextLine();
        if (!examDateStr.isEmpty()) {
            Date examDate = null;
            try {
                examDate = new SimpleDateFormat("yyyy-MM-dd").parse(examDateStr);
                existingClaim.setExamDate(examDate);
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
        }

        System.out.println("Enter the new documents (separated by comma), (or press Enter to skip): ");
        String documentsStr = scanner.nextLine();
        if (!documentsStr.isEmpty()) {
            List<String> documents = Arrays.asList(documentsStr.split(","));
            existingClaim.setDocuments(documents);
        }

        System.out.println("Enter the new claim amount, (or press Enter to skip): ");
        String claimAmountStr = scanner.nextLine();
        if (!claimAmountStr.isEmpty()) {
            double claimAmount = Double.parseDouble(claimAmountStr);
            existingClaim.setClaimAmount(claimAmount);
        }

        System.out.println("Enter the new status (1 for New, 2 for Processing, 3 for Done), (or press Enter to skip): ");
        String statusStr = scanner.nextLine();
        if (!statusStr.isEmpty()) {
            int statusInt = Integer.parseInt(statusStr);
            allStatus status = null;
            switch (statusInt) {
                case 1:
                    status = allStatus.New;
                    break;
                case 2:
                    status = allStatus.Processing;
                    break;
                case 3:
                    status = allStatus.Done;
                    break;
                default:
                    System.out.println("Invalid status. Please enter 1 for APPROVED, 2 for REJECTED, or 3 for PENDING.");
                    return;
            }
            existingClaim.setStatus(status);
        }

        System.out.println("Enter the new banking info, (or press Enter to skip): ");
        String bankingInfo = scanner.nextLine();
        if (!bankingInfo.isEmpty()) {
            existingClaim.setBankingInfo(bankingInfo);
        }

        System.out.println("The claim has been updated successfully");
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
        // Remove the claim from the customer's list of claims
        customer insuredPerson = existingClaim.getInsuredPerson();
        insuredPerson.getClaims().remove(existingClaim);
        // Remove the claim from the list of all claims
        claim.getClaims().remove(existingClaim);
        System.out.println("The claim has been deleted successfully");
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
        return claim.getClaims();
    }
}
