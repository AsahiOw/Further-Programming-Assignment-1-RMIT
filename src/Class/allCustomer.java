package Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class allCustomer extends customer{
    private policy_holder policyHolder;
    private static dependent dependent;
    private static List<allCustomer> Customers;

    public allCustomer(String id, String fullName, insurance_card insuranceCard, List<claim> claims, policy_holder policyHolder, dependent dependent) {
        super(id, fullName, insuranceCard, claims);
        this.policyHolder = policyHolder;
        this.dependent = dependent;
        if (Customers == null) {
            Customers = new ArrayList<>();
        }
        Customers.add(this);
    }

    // getters and setters for policyHolder and dependent
    public policy_holder getPolicyHolder() {
        return policyHolder;
    }

    public void setPolicyHolder(policy_holder policyHolder) {
        this.policyHolder = policyHolder;
    }

    public dependent getDependent() {
        return dependent;
    }

    public void setDependent(dependent dependent) {
        this.dependent = dependent;
    }

    public static List<allCustomer> getCustomers() {
        if (Customers == null) {
            Customers = new ArrayList<>();
        }
        return Customers;
    }

    // method section
    //    get customer by id
    public static allCustomer getCustomerById(String id) {
        for (allCustomer customer : Customers) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }
    @Override
    public void delete_customer(Scanner scanner) {

    }

    @Override
    public void update_customer(Scanner scanner) {

    }

    @Override
    public void read_customer(Scanner scanner) {

    }

}

