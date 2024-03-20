package Class;

import java.util.List;

public class policy_holder extends customer{
    private List<dependent> dependents;

    public policy_holder(String id, String fullName, insurance_card insuranceCard, List<claim> claims, List<dependent> dependents) {
        super(id, fullName, insuranceCard, claims);
        this.dependents = dependents;
    }

    //getters and setters
    public List<dependent> getDependents() {
        return dependents;
    }
    public void setDependents(List<dependent> dependents) {
        this.dependents = dependents;
    }
}
