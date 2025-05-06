package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import model.Facility;
import service.FacilityService;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class FacilityHandler implements HttpHandler {

    private final FacilityService facilityService = new FacilityService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            Map<String, String> formData = parseFormData(exchange);

            Facility facility = new Facility(
                Integer.parseInt(formData.get("facilityID")),
                formData.get("name"),
                formData.get("address"),
                formData.get("phoneNum")
            );

            facilityService.addFacility(facility);

            exchange.getResponseHeaders().set("Location", "/facilities");
            exchange.sendResponseHeaders(303, -1); // redirect
        } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            List<Facility> facilities = facilityService.getAllFacilities();

            StringBuilder response = new StringBuilder();
            response.append("<html><body style='font-family: Arial;'>");
            response.append("<h2>Registered Facilities</h2>");

            if (facilities.isEmpty()) {
                response.append("<p>No facilities registered yet.</p>");
            } else {
                response.append("<table border='1' cellpadding='8' cellspacing='0'>");
                response.append("<tr><th>ID</th><th>Name</th><th>Address</th><th>Phone Number</th></tr>");
                for (Facility f : facilities) {
                    response.append("<tr>")
                            .append("<td>").append(f.getFacilityID()).append("</td>")
                            .append("<td>").append(f.getName()).append("</td>")
                            .append("<td>").append(f.getAddress()).append("</td>")
                            .append("<td>").append(f.getPhoneNum()).append("</td>")
                            .append("</tr>");
                }
                response.append("</table>");
            }

            response.append("<br><a href=\"/facilityforms\">Register New Facility</a>");
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
