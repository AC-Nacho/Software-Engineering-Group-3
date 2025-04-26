package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class FormsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String html = """
        <html>
            <body>
                <h2>Register New Patient</h2>
                <form method="POST" action="/patients">
                    Username: <input type="text" name="username"><br>
                    Password: <input type="password" name="password"><br>
                    First Name: <input type="text" name="fname"><br>
                    Last Name: <input type="text" name="lname"><br>
                    SSN: <input type="text" name="ssn"><br>
                    DOB (YYYY-MM-DD): <input type="text" name="dob"><br>
                    Phone Number: <input type="text" name="phoneNum"><br>
                    Address: <input type="text" name="address"><br>
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
