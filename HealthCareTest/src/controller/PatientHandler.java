package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Patient;
import service.PatientService;

public class PatientHandler implements HttpHandler {

    private final PatientService patientService = new PatientService();

    @Override
public void handle(HttpExchange exchange) throws IOException {
    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
        Map<String, String> formData = parseFormData(exchange);

        Patient patient = new Patient(
            0, // ID can be auto-incremented in MySQL
            formData.get("username"),
            formData.get("password"),
            formData.get("fname"),
            formData.get("lname"),
            formData.get("ssn"),
            Date.valueOf(formData.get("dob")),
            formData.get("phoneNum"),
            formData.get("address")
        );

        patientService.addPatient(patient);

        // Redirect after successful POST to show the updated list
    exchange.getResponseHeaders().set("Location", "/patients");
    exchange.sendResponseHeaders(303, -1); // 303 = See Other

    } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
    List<Patient> patients = patientService.getAllPatients();

    StringBuilder response = new StringBuilder();
    response.append("<html><body style='font-family: Arial;'>");
    response.append("<h2>Registered Patients</h2>");

    if (patients.isEmpty()) {
        response.append("<p>No patients registered yet.</p>");
    } else {
        response.append("<table border='1' cellpadding='8' cellspacing='0'>");
        response.append("<tr><th>ID</th><th>Name</th><th>Username</th><th>DOB</th><th>Phone</th><th>Address</th></tr>");

        for (Patient p : patients) {
            response.append("<tr>")
                    .append("<td>").append(p.getPatientID()).append("</td>")
                    .append("<td>").append(p.getFname()).append(" ").append(p.getLname()).append("</td>")
                    .append("<td>").append(p.getUsername()).append("</td>")
                    .append("<td>").append(p.getDob()).append("</td>")
                    .append("<td>").append(p.getPhoneNum()).append("</td>")
                    .append("<td>").append(p.getAddress()).append("</td>")
                    .append("</tr>");
        }

        response.append("</table>");
    }

    response.append("<br><a href=\"/forms\">Register Another Patient</a>");
    response.append("</body></html>");

    byte[] bytes = response.toString().getBytes();
    exchange.sendResponseHeaders(200, bytes.length);
    try (OutputStream os = exchange.getResponseBody()) {
        os.write(bytes);
    }
}

}


    private Map<String, String> parseFormData(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);
        StringBuilder buf = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            buf.append(line);
        }

        String[] pairs = buf.toString().split("&");
        Map<String, String> data = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";
            data.put(key, value);
        }

        return data;
    }
}
