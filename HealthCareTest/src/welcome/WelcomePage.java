package welcome;

import com.sun.net.httpserver.HttpServer;
import controller.FormsHandler;
import controller.PatientHandler;  
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import model.Patient;

public class WelcomePage {
    private static final List<Patient> patients = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        server.createContext("/", exchange -> {
            String response = """
                <html><body>
                    <h1>Welcome to the Back End Homepage of the Healthcare Application!</h1>
                    <a href="/forms">Go to Forms</a><br>
                    <a href="/patients">See Patient List</a>
                </body></html>
                """;
            exchange.sendResponseHeaders(200, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        });

        server.createContext("/forms", new FormsHandler());
        server.createContext("/patients", new PatientHandler());


        server.setExecutor(Executors.newFixedThreadPool(10));
        server.start();
        System.out.println("Server is listening on port 8000");
    }
}
