package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class FacilityFormsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String html = """
        <html>
            <body>
                <h2>Register New Facility</h2>
                <form method="POST" action="/facilities">
                    Facility ID: <input type="text" name="facilityID"><br>
                    Name: <input type="text" name="name"><br>
                    Address: <input type="text" name="address"><br>
                    Phone Number: <input type="text" name="phoneNum"><br>
                    <input type="submit" value="Register">
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
