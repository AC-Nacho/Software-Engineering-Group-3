package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class DFormsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String html = """
        <html>
            <body>
                <h2>Register New Doctor</h2>
                <form method="POST" action="/doctors">
                    Doctor ID:<input type="text" name="doctorID"><br>
                    Username: <input type="text" name="username"><br>
                    First Name: <input type="text" name="fname"><br>
                    Last Name: <input type="text" name="lname"><br>
                    Specialty: <input type="text" name="specialty"><br>
                    Phone Number: <input type="text" name="phoneNum"><br>
                    <input type="submit" value="Register">
                </doctorform>
            </body>
        </html>
        """;

        exchange.sendResponseHeaders(200, html.getBytes().length);
        try (OutputStream os = exchange.getResponseBody()) {
            os.write(html.getBytes());
        }
    }
}
