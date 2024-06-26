/**
 * @author <Nguyen Ha Tuan Nguyen - s3978072>
 */
package Class;

import Interface.From_String;
import Interface.Id_generate;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import Enum.*;
public class claim implements Id_generate, From_String {
    private String id;
    private Date ClaimDate;
    private String insuredPerson;
    private String insuranceCard;
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

    public claim(String id, Date claimDate, String insuredPerson, String insuranceCard, Date examDate, List<String> documents, double claimAmount, allStatus status, String bankingInfo) {
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

    public void setClaimDate(Date claimDate) {
        ClaimDate = claimDate;
    }

    public void setInsuredPerson(String insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public void setInsuranceCard(String insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public void setExamDate(Date examDate) {
        this.examDate = examDate;
    }

    public void setDocuments(List<String> documents) {
        this.documents = documents;
    }

    public void setClaimAmount(double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public void setStatus(allStatus status) {
        this.status = status;
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
        return String.format("f-%010d", lastAssignedId);
    }

    // fromString method

    @Override
    public void fromString(String line) {
        String[] parts = line.split(",(?![^\\[]*\\])");

        String id = parts[0].split("=")[1].replace("'", "");

        Date claimDate = null;
        try {
            String claimDateString = parts[1].split("=")[1].replaceAll("\\}$", "");
            claimDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(claimDateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        String insuredPerson = parts[2].split("=")[1].replace("'", "");
        String insuranceCard = parts[3].split("=")[1];

        Date examDate = null;
        try {
            String examDateString = parts[4].split("=")[1].replaceAll("\\}$", "");
            examDate = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(examDateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        List<String> documents = new ArrayList<>();
        if (!parts[5].split("=")[1].equals("[]")) {
            String documentsString = parts[5].split("=")[1].replace("[", "").replace("]", "").trim().replaceAll("\\]$", "").replaceAll("\\}$", "");
            String[] documentsArray = documentsString.split(", ");
            for (String document : documentsArray) {
                documents.add(document.trim());
            }
        }

        double claimAmount = Double.parseDouble(parts[6].split("=")[1]);
        allStatus status = allStatus.valueOf(parts[7].split("=")[1].replace("'", ""));
        String bankingInfo = parts[8].split("=")[1].replace("'", "");

        this.setId(id);
        this.setClaimDate(claimDate);
        this.setInsuredPerson(insuredPerson);
        this.setInsuranceCard(insuranceCard);
        this.setExamDate(examDate);
        this.setDocuments(documents);
        this.setClaimAmount(claimAmount);
        this.setStatus(status);
        this.setBankingInfo(bankingInfo);
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
}
