package model;

public class Doctor {
    private int doctorID;
    private String username;
    private String fname;
    private String lname;
    private String phoneNum;
    private String specialty;
   

    public Doctor(int doctorID, String username, String fname, String lname,
            String phoneNum, String specialty) {
        this.doctorID = doctorID;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.specialty = specialty;
        this.phoneNum = phoneNum;
      
    }

    // Getters
    public int getDoctorID() { return doctorID; }
    public String getUsername() { return username; }
    public String getFname() { return fname; }
    public String getLname() { return lname; }
    public String getSpecialty() {return specialty;}
    public String getPhoneNum() { return phoneNum; }
   

    @Override
    public String toString() {
        return fname + " " + lname + " (" + specialty + "), Phone: " + phoneNum;
    }
}
