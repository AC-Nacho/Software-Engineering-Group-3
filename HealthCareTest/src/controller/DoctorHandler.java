package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Doctor;
import service.DoctorService;

public class DoctorHandler implements HttpHandler {

    private final DoctorService doctorService = new DoctorService();

    @Override
public void handle(HttpExchange exchange) throws IOException {
    if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
        Map<String, String> formData = parseFormData(exchange);

        Doctor doctor = new Doctor(
            0,
            formData.get("username"),
            formData.get("fname"),
            formData.get("lname"),
            formData.get("phoneNum"),
            formData.get("specialty")
        );

        doctorService.addDoctor(doctor);

        // Redirect after successful POST to show the updated list
    exchange.getResponseHeaders().set("Location", "/doctors");
    exchange.sendResponseHeaders(303, -1); // 303 = See Other

    } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
    List<Doctor> doctors = doctorService.getAllDoctors();

    StringBuilder response = new StringBuilder();
    response.append("<html><body style='font-family: Arial;'>");
    response.append("<h2>Registered Doctors</h2>");

    if (doctors.isEmpty()) {
        response.append("<p>No doctors registered yet.</p>");
    } else {
        response.append("<table border='1' cellpadding='8' cellspacing='0'>");
        response.append("<tr><th>ID</th><th>DoctorID</th><th>Name</th></th><th>Phone Number</th><th>Specialty</tr>");

        for (Doctor p : doctors) {
            response.append("<tr>")
                    .append("<td>").append(p.getDoctorID()).append("</td>")
                    .append("<td>").append(p.getUsername()).append("</td>")
                    .append("<td>").append(p.getFname()).append(" ").append(p.getLname()).append("</td>")
             
                    .append("<td>").append(p.getPhoneNum()).append("</td>")
                    .append("<td>").append(p.getSpecialty()).append("</td>")
                    .append("</tr>");
        }

        response.append("</table>");
    }

    response.append("<br><a href=\"/doctorforms\">Register Another Doctor</a>");
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
