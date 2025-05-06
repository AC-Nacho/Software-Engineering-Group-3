package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import model.Appointment;
import service.AppointmentService;

public class AppointmentHandler implements HttpHandler {

    private final AppointmentService service = new AppointmentService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            Map<String, String> form = parseFormData(exchange);

            Appointment a = new Appointment(
                0,
                Integer.parseInt(form.get("doctorID")),
                Integer.parseInt(form.get("patientID")),
                form.get("date"),
                form.get("time"),
                form.get("location")
            );

            service.addAppointment(a);
            exchange.getResponseHeaders().set("Location", "/appointments");
            exchange.sendResponseHeaders(303, -1);
        } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            List<Appointment> all = service.getAllAppointments();
            StringBuilder res = new StringBuilder();
            res.append("<html><body><h2>Appointments</h2>");

            if (all.isEmpty()) {
                res.append("<p>No appointments found.</p>");
            } else {
                res.append("<table border='1'><tr><th>ID</th><th>DoctorID</th><th>PatientID</th><th>Date</th><th>Time</th><th>Location</th></tr>");
                for (Appointment a : all) {
                    res.append("<tr>")
                       .append("<td>").append(a.getAppointmentID()).append("</td>")
                       .append("<td>").append(a.getDoctorID()).append("</td>")
                       .append("<td>").append(a.getPatientID()).append("</td>")
                       .append("<td>").append(a.getDate()).append("</td>")
                       .append("<td>").append(a.getTime()).append("</td>")
                       .append("<td>").append(a.getLocation()).append("</td>")
                       .append("</tr>");
                }
                res.append("</table>");
            }

            res.append("<br><a href=\"/appointmentforms\">Add Another Appointment</a>");
            res.append("</body></html>");

            byte[] bytes = res.toString().getBytes();
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    private Map<String, String> parseFormData(HttpExchange exchange) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        Map<String, String> data = new HashMap<>();
        for (String pair : sb.toString().split("&")) {
            String[] parts = pair.split("=");
            String key = URLDecoder.decode(parts[0], StandardCharsets.UTF_8);
            String value = parts.length > 1 ? URLDecoder.decode(parts[1], StandardCharsets.UTF_8) : "";
            data.put(key, value);
        }

        return data;
    }
}
