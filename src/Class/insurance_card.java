package Class;

import java.util.Date;
import java.util.List;

public class insurance_card {
    private int id;
    private customer customer;
    private policy_holder policyOwner;
    private Date expirationDate;
    private static List<insurance_card> insuranceCards;

    public insurance_card(int id, customer customer, policy_holder policyOwner, Date expirationDate) {
        this.id = id;
        this.customer = customer;
        this.policyOwner = policyOwner;
        this.expirationDate = expirationDate;
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public customer getCustomer() {
        return customer;
    }

    public void setCustomer(customer customer) {
        this.customer = customer;
    }

    public policy_holder getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(policy_holder policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public List<insurance_card> getInsuranceCards() {
        return insuranceCards;
    }

    public void setInsuranceCards(List<insurance_card> insuranceCards) {
        this.insuranceCards = insuranceCards;
    }

    // method section
    // get insurance card by id
    public static insurance_card getInsuranceCardById(int id){
        for (insurance_card insuranceCard : insuranceCards) {
            if(insuranceCard.getId() == id){
                return insuranceCard;
            }
        }
        return null;
    }
}
