package Class;

import java.util.Date;
import java.util.List;
import Enum.*;
public class claim {
    private String id;
    private Date ClaimDate;
    private customer insuredPerson;
    private insurance_card insuranceCard;
    private Date examDate;
    private List<String> documents;
    private double claimAmount;
    private allStatus status;
    private String BankingInfo;

    public claim(String id, Date claimDate, customer insuredPerson, insurance_card insuranceCard, Date examDate, List<String> documents, double claimAmount, allStatus status, String bankingInfo) {
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

    public customer getInsuredPerson() {
        return insuredPerson;
    }

    public void setInsuredPerson(customer insuredPerson) {
        this.insuredPerson = insuredPerson;
    }

    public insurance_card getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(insurance_card insuranceCard) {
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
}
