package controller;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;

public class DptFormsHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String html = """
        <html>
            <body>
                <h2>Register New Department</h2>
                <form method="POST" action="/departments">
                    Department ID: <input type="text" name="depID"><br>
                    Department Name: <input type="text" name="name"><br>
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
