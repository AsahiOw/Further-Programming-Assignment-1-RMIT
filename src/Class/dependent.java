package Class;

import java.util.List;

public class dependent extends customer{
    private policy_holder policyHolder;

    public dependent(String id, String fullName, insurance_card insuranceCard, List<claim> claims, policy_holder policyHolder) {
        super(id, fullName, insuranceCard, claims);
        this.policyHolder = policyHolder;
    }
}
