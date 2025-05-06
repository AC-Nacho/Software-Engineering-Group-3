package model;

public class Facility {
    private int facilityID;
    private String name;
    private String address;
    private String phoneNum;

    public Facility(int facilityID, String name, String address, String phoneNum) {
        this.facilityID = facilityID;
        this.name = name;
        this.address = address;
        this.phoneNum = phoneNum;
    }

    public int getFacilityID() { return facilityID; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhoneNum() { return phoneNum; }
}
