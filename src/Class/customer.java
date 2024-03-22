package Class;

import Interface.Id_generate;
import java.util.List;
import java.util.Scanner;

public abstract class customer implements Id_generate {
    private String id;
    private String fullName;
    private int insuranceCard;
    private List<String> claims;

    // default constructor
    public customer() {
    }

    public customer(String id, String fullName, int insuranceCard, List<String> claims) {
        this.id = id;
        this.fullName = fullName;
        this.insuranceCard = insuranceCard;
        this.claims = claims;
    }

    // getters and setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(int insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public List<String> getClaims() {
        return claims;
    }

    public void setClaims(List<String> claims) {
        this.claims = claims;
    }

    // method section
    // customer CRU
    public static void read_all_customers(){
        for (policy_holder policyHolder : policy_holder.getPolicyHolders()) {
            System.out.println("ALL Policy holders");
            System.out.println(policyHolder);
        }
        for (dependent dependent : dependent.getDependents()) {
            System.out.println("ALL Dependents");
            System.out.println(dependent);
        }
    }

    // abstract methods
    public abstract void create_customer(Scanner scanner);
    public abstract void update_customer(Scanner scanner);
    public abstract void read_customer(Scanner scanner);

}
