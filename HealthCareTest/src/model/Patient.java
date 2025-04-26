package model;

import java.sql.Date;

public class Patient {
    private int patientID;
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String ssn;
    private Date dob;
    private String phoneNum;
    private String address;

    public Patient(int patientID, String username, String password, String fname, String lname,
                   String ssn, Date dob, String phoneNum, String address) {
        this.patientID = patientID;
        this.username = username;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.ssn = ssn;
        this.dob = dob;
        this.phoneNum = phoneNum;
        this.address = address;
    }

    // Getters
    public int getPatientID() { return patientID; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public String getSsn() { return ssn; }
    public Date getDob() { return dob; }
    public String getPhoneNum() { return phoneNum; }
    public String getAddress() { return address; }

    @Override
    public String toString() {
        return fname + " " + lname + " (" + username + "), Phone: " + phoneNum;
    }
}
