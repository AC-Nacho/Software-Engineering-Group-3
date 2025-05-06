package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class StaffFormHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String html = """
        <html>
            <body>
                <h2>Register New Medical Staff</h2>
                <form method="POST" action="/medicalstaff">
                    Username: <input type="text" name="username"><br>
                    Password: <input type="password" name="password"><br>
                    First Name: <input type="text" name="fname"><br>
                    Last Name: <input type="text" name="lname"><br>
                    Created At (Unix Time): <input type="text" name="createdAt"><br>
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
