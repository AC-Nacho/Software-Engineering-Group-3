package model;

public class Appointment {
    private int appointmentID;
    private int doctorID;
    private int patientID;
    private String date;
    private String time;
    private String location;

    public Appointment(int appointmentID, int doctorID, int patientID, String date, String time, String location) {
        this.appointmentID = appointmentID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
        this.time = time;
        this.location = location;
    }

    public int getAppointmentID() { return appointmentID; }
    public int getDoctorID() { return doctorID; }
    public int getPatientID() { return patientID; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getLocation() { return location; }
}
