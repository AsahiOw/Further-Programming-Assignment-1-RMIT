package Class;

import java.util.List;

public abstract class customer {
//    private static int lastAssignedId = readLastAssignedId();
    private String id;
    private String fullName;
    private insurance_card insuranceCard;
    private List<claim> claims;

    public customer(String id,String fullName, insurance_card insuranceCard, List<claim> claims) {
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

    public insurance_card getInsuranceCard() {
        return insuranceCard;
    }

    public void setInsuranceCard(insurance_card insuranceCard) {
        this.insuranceCard = insuranceCard;
    }

    public List<claim> getClaims() {
        return claims;
    }

    public void setClaims(List<claim> claims) {
        this.claims = claims;
    }

    //    This is the method that reads the last assigned id from the file lastAssignedId.txt. If the file does not exist, it returns 0.
//    private static int readLastAssignedId() {
//        try {
//            File file = new File("Id_folder/lastAssignedId.txt");
//            if (!file.exists()) {
//                file.createNewFile();
//            }
//            BufferedReader reader = new BufferedReader(new FileReader(file));
//            String line = reader.readLine();
//            reader.close();
//            return line != null ? Integer.parseInt(line) : 0;
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private static void writeLastAssignedId() {
//        try {
//            BufferedWriter writer = new BufferedWriter(new FileWriter("Id_folder/lastAssignedId.txt"));
//            writer.write(String.valueOf(lastAssignedId));
//            writer.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//    private String generateId() {
//        lastAssignedId++;
//        writeLastAssignedId();
//        return String.format("C-%07d", lastAssignedId);
//    }
}