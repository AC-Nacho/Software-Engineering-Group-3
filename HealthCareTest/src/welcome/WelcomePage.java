package welcome;

import com.sun.net.httpserver.HttpServer;
import controller.*;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import model.Patient;

public class WelcomePage {
    private static final List<Patient> patients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", exchange -> {
            String response = """
                <html><body>
                    <h1>Welcome to the Back End Homepage of the Healthcare Application!</h1>
                    <a href="/forms">Go to Patient Forms</a><br> 
                    <a href="/doctorforms">Go to Doctor Forms</a><br>
                    <a href="/facilityforms">Go to Facility Forms</a><br>
                    <a href="/staffform">Go to Medical Staff Forms</a><br>
                    <a href="/departmentforms">Go to Department Forms</a><br>
                    <a href="/appointmentforms">Go to Appointment Forms</a><br>
                    <a href=""> </a><br>
                    <a href="/departments">See Departments List</a><br>
                    <a href="/patients">See Patient List</a><br>
                    <a href="/medicalstaff">See Medical Staff List</a><br>
                    <a href="/facilities">See Facility List</a><br>
                    <a href="/appointments">See Appointments List</a><br>
                    <a href="/doctors">See Doctor List</a>
                </body></html>
                """;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.createContext("/forms", new FormsHandler());
        server.createContext("/patients", new PatientHandler());
        
        server.createContext("/staffform", new StaffFormHandler());
        server.createContext("/medicalstaff", new MedicalStaffHandler());



        server.createContext("/doctorforms", new DFormsHandler());
        server.createContext("/doctors", new DoctorHandler());


        server.createContext("/facilities", new FacilityHandler());
        server.createContext("/facilityforms", new FacilityFormsHandler());

        server.createContext("/departments", new DepartmentHandler());
        server.createContext("/departmentforms", new DptFormsHandler());

        server.createContext("/appointments", new AppointmentHandler());
        server.createContext("/appointmentforms", new AppointmentFormsHandler());
     
        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        System.out.println("Server is listening on port 8000");
    }
}
