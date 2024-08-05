import java.io.*;
import java.net.*;

public class HTTPServer {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Web server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");

                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())) {

            // Read the HTTP request
            String line;
            StringBuilder request = new StringBuilder();
            while (!(line = in.readLine()).isEmpty()) {
                request.append(line).append("\n");
            }

            // Generate a simple HTML response
            String response = generateHttpResponse("Welcome to my simple web server!");

            // Send the HTTP response
            out.print(response);
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String generateHttpResponse(String content) {
        String httpResponse = "HTTP/1.1 200 OK\r\n" +
                              "Content-Type: text/html\r\n" +
                              "Content-Length: " + content.length() + "\r\n" +
                              "\r\n" +
                              content;
        return httpResponse;
    }
}
