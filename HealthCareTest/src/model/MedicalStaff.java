package model;

public class MedicalStaff {
    private int staffID;
    private String username;
    private String password;
    private String fname;
    private String lname;
    private int createdAt;

    public MedicalStaff(int staffID, String username, String password, String fname, String lname, int createdAt) {
        this.staffID = staffID;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.createdAt = createdAt;
    }

    public int getStaffID() { return staffID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public int getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return fname + " " + lname + " (Username: " + username + ")";
    }
}
