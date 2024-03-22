package Class;

import Interface.From_String;
import Interface.Id_generate;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Enum.*;
public class claim implements Id_generate, From_String {
    private String id;
    private Date ClaimDate;
    private String insuredPerson;
    private int insuranceCard;
    private Date examDate;
    private List<String> documents;
    private double claimAmount;
    private allStatus status;
    private String BankingInfo;
    // Define Dependents as a list of dependent objects
    public static List<claim> claims = new ArrayList<>();

    // default constructor
    public claim() {
    }

    public claim(String id, Date claimDate, String insuredPerson, int insuranceCard, Date examDate, List<String> documents, double claimAmount, allStatus status, String bankingInfo) {
        this.id = id;
        ClaimDate = claimDate;
        this.insuredPerson = insuredPerson;
        this.insuranceCard = insuranceCard;
        this.examDate = examDate;
        this.documents = documents;
        this.claimAmount = claimAmount;
        this.status = status;
        BankingInfo = bankingInfo;
    }

    //getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getClaimDate() {
        return ClaimDate;
    }

    public void setClaimDate(Date claimDate) {
        ClaimDate = claimDate;
    }

    public String getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public int getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(int insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public Date getExamDate() {
        return examDate;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public List<String> getDocuments() {
        return documents;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public allStatus getStatus() {
        return status;
    }

    public void setStatus(allStatus status) {
        this.status = status;
    }

    public String getBankingInfo() {
        return BankingInfo;
    }

    public void setBankingInfo(String bankingInfo) {
        BankingInfo = bankingInfo;
    }

    // method section
    // add claim
    public static void addClaim(claim claim) {
        claims.add(claim);
    }
    // get Claim by id
    public static claim getClaimById(String id) {
        for (claim claim : claims) {
            if (claim.getId().equals(id)) {
                return claim;
            }
        }
        return null;
    }
    // get all claims
    public static List<claim> getClaims() {
        return claims;
    }
    //    This is the method that reads the last assigned id from the file lastAssignedClaimId.txt. If the file does not exist, it returns 0.
    @Override
    public int readLastAssignedId() {
        try {
            File file = new File("src/Id_folder/lastAssignedClaimId.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            return line != null ? Integer.parseInt(line) : 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private int lastAssignedId = readLastAssignedId();
    @Override
    public void writeLastAssignedId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Id_folder/lastAssignedClaimId.txt"));
            writer.write(String.valueOf(lastAssignedId));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public String generateId() {
        lastAssignedId++;
        writeLastAssignedId();
        return String.format("f-%10d", lastAssignedId);
    }

    @Override
    public String toString() {
        return  "{" +
                "id='" + id + '\'' +
                ", ClaimDate=" + ClaimDate +
                ", insuredPerson=" + insuredPerson +
                ", insuranceCard=" + insuranceCard +
                ", examDate=" + examDate +
                ", documents=" + documents +
                ", claimAmount=" + claimAmount +
                ", status=" + status +
                ", BankingInfo='" + BankingInfo + '\'' +
                '}';
    }
    // fromString method
    @Override
    public void fromString(String line) {
        String[] fields = line.split(",");
        String id = fields[0];
        Date ClaimDate = new Date(fields[1]);
        String insuredPerson = fields[2];
        int insuranceCard = Integer.parseInt(fields[3]);
        Date examDate = new Date(fields[4]);
        List<String> documents = new ArrayList<>();
        for (String document : fields[5].split(";")) {
            documents.add(document);
        }
        double claimAmount = Double.parseDouble(fields[6]);
        allStatus status = allStatus.valueOf(fields[7]);
        String BankingInfo = fields[8];

        this.setId(id);
        this.setClaimDate(ClaimDate);
        this.setInsuredPerson(insuredPerson);
        this.setInsuranceCard(insuranceCard);
        this.setExamDate(examDate);
        this.setDocuments(documents);
        this.setClaimAmount(claimAmount);
        this.setStatus(status);
        this.setBankingInfo(BankingInfo);

        claims.add(this);
    }
}
