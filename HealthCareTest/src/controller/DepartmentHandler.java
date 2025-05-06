package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import model.Department;
import service.DepartmentService;

public class DepartmentHandler implements HttpHandler {

    private final DepartmentService departmentService = new DepartmentService();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            Map<String, String> formData = parseFormData(exchange);

            Department department = new Department(
                Integer.parseInt(formData.get("depID")),
                formData.get("name")
            );

            departmentService.addDepartment(department);

            exchange.getResponseHeaders().set("Location", "/departments");
            exchange.sendResponseHeaders(303, -1); // Redirect
        } else if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            List<Department> departments = departmentService.getAllDepartments();

            StringBuilder response = new StringBuilder();
            response.append("<html><body style='font-family: Arial;'>");
            response.append("<h2>Registered Departments</h2>");

            if (departments.isEmpty()) {
                response.append("<p>No departments registered yet.</p>");
            } else {
                response.append("<table border='1' cellpadding='8' cellspacing='0'>");
                response.append("<tr><th>Department ID</th><th>Name</th></tr>");
                for (Department d : departments) {
                    response.append("<tr>")
                            .append("<td>").append(d.getDepID()).append("</td>")
                            .append("<td>").append(d.getName()).append("</td>")
                            .append("</tr>");
                }
                response.append("</table>");
            }

            response.append("<br><a href=\"/departmentforms\">Register Another Department</a>");
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
