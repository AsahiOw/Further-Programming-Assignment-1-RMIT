/**
 * @author <Nguyen Ha Tuan Nguyen - s3978072>
 */
package Class;

import Interface.Id_generate;
import java.util.List;
import java.util.Scanner;

public abstract class customer implements Id_generate {
    private String id;
    private String fullName;
    private String insuranceCard;
    private List<String> claims;

    // default constructor

    public customer() {
    }

    public customer(String id, String fullName, String insuranceCard, List<String> claims) {
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

    public String getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(String insuranceCard) {
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
        System.out.println("ALL Policy holders");
        for (policy_holder policyHolder : policy_holder.getPolicyHolders()) {
            System.out.println(policyHolder);
        }
        System.out.println("ALL Dependents");
        for (dependent dependent : dependent.getDependents()) {
            System.out.println(dependent);
        }
    }

    // abstract methods
    public abstract void create_customer(Scanner scanner);
    public abstract void update_customer(String ids, Scanner scanner);
    public abstract void read_customer(String ids);

}
