package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import model.MedicalStaff;
import service.MedicalStaffService;

public class MedicalStaffHandler implements HttpHandler {

    private final MedicalStaffService staffService = new MedicalStaffService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            Map<String, String> formData = parseFormData(exchange);

            MedicalStaff staff = new MedicalStaff(
                0,
                formData.get("username"),
                formData.get("password"),
                formData.get("fname"),
                formData.get("lname"),
                Integer.parseInt(formData.get("createdAt"))
            );

            staffService.addMedicalStaff(staff);
            exchange.getResponseHeaders().set("Location", "/medicalstaff");
            exchange.sendResponseHeaders(303, -1);
        } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            List<MedicalStaff> staffList = staffService.getAllMedicalStaff();

            StringBuilder response = new StringBuilder();
            response.append("<html><body style='font-family: Arial;'>");
            response.append("<h2>Registered Medical Staff</h2>");

            if (staffList.isEmpty()) {
                response.append("<p>No medical staff registered.</p>");
            } else {
                response.append("<table border='1' cellpadding='8'>");
                response.append("<tr><th>ID</th><th>Username</th><th>Name</th><th>Created At</th></tr>");
                for (MedicalStaff s : staffList) {
                    response.append("<tr>")
                        .append("<td>").append(s.getStaffID()).append("</td>")
                        .append("<td>").append(s.getUsername()).append("</td>")
                        .append("<td>").append(s.getFname()).append(" ").append(s.getLname()).append("</td>")
                        .append("<td>").append(s.getCreatedAt()).append("</td>")
                        .append("</tr>");
                }
                response.append("</table>");
            }

            response.append("<br><a href=\"/staffform\">Register Another Staff</a>");
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
