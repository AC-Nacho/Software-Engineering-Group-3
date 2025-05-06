package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class AppointmentFormsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String html = """
        <html>
            <body>
                <h2>Schedule Appointment</h2>
                <form method="POST" action="/appointments">
                    Doctor ID: <input type="text" name="doctorID"><br>
                    Patient ID: <input type="text" name="patientID"><br>
                    Date (YYYY-MM-DD): <input type="text" name="date"><br>
                    Time (HH:MM): <input type="text" name="time"><br>
                    Location: <input type="text" name="location"><br>
                    <input type="submit" value="Schedule">
                </form>
            </body>
        </html>
        """;

        exchange.sendResponseHeaders(200, html.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(html.getBytes());
        }
    }
}
