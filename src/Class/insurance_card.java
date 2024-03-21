package Class;

import Interface.Id_generate;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class insurance_card implements Id_generate {
    private int id;
    private String customer;
    private String policyOwner;
    private Date expirationDate;
    // Define insuranceCards as a list of policy_holder objects
    public static List<insurance_card> insuranceCards = new ArrayList<>();

    public insurance_card(int id, String customer, String policyOwner, Date expirationDate) {
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

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getPolicyOwner() {
        return policyOwner;
    }

    public void setPolicyOwner(String policyOwner) {
        this.policyOwner = policyOwner;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    // method section
    // This is the method that reads the last assigned id from the file lastAssignedInsuranceId.txt. If the file does not exist, it returns 0.
    @Override
    public int readLastAssignedId() {
        try {
            File file = new File("src/Id_folder/lastAssignedInsuranceId.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            reader.close();
            return line != null ? Integer.parseInt(line.trim()) : 0;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private int lastAssignedId = readLastAssignedId();
    @Override
    public void writeLastAssignedId() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/Id_folder/lastAssignedInsuranceId.txt"));
            writer.write(String.valueOf(lastAssignedId));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public int generateIdInt() {
        lastAssignedId++;
        while (String.valueOf(lastAssignedId).length() < 10) {
            lastAssignedId *= 10;
        }
        writeLastAssignedId();
        return lastAssignedId;
    }
    // get insurance card by id
    public static insurance_card getInsuranceCardById(int id){
        for (insurance_card insuranceCard : insuranceCards) {
            if(insuranceCard.getId() == id){
                return insuranceCard;
            }
        }
        return null;
    }
    // CRU for insurance card
    public static void create_insurance_card(Scanner scanner){

    }
    public static void update_insurance_card(Scanner scanner){

    }
    public static void read_insurance_card(Scanner scanner){

    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", customer='" + customer +
                ", policyOwner=" + policyOwner +
                ", expirationDate=" + expirationDate +
                '}';
    }
}
