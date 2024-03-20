package Method;

import Class.*;
import java.util.List;
public class find_by_id {
    private List<insurance_card> insuranceCards;
    private List<claim> claims;
    private List<customer> customers;
    private List<policy_holder> policyHolders;
    private List<dependent> dependents;

    public insurance_card getInsuranceCardById(int id){
        for (insurance_card insuranceCard : insuranceCards) {
            if(insuranceCard.getId() == id){
                return insuranceCard;
            }
        }
        return null;
    }

    public claim getClaimById(String id){
        for (claim claim : claims) {
            if(claim.getId().equals(id)){
                return claim;
            }
        }
        return null;
    }

    public customer getCustomerById(String id){
        for (customer customer : customers) {
            if(customer.getId().equals(id)){
                return customer;
            }
        }
        return null;
    }

    public policy_holder getPolicyHolderById(String id){
        for (policy_holder policyHolder : policyHolders) {
            if(policyHolder.getId().equals(id)){
                return policyHolder;
            }
        }
        return null;
    }

    public dependent getDependentById(String id){
        for (dependent dependent : dependents) {
            if(dependent.getId().equals(id)){
                return dependent;
            }
        }
        return null;
    }
    
}
