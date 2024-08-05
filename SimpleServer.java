import java.io.*;
import java.net.*;

public class SimpleServer {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

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

//class ClientHandler extends Thread {
//    private Socket socket;
//
//    public ClientHandler(Socket socket) {
//        this.socket = socket;
//    }
//
//    @Override
//    public void run() {
//        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
//
//            String message;
//            // Read messages from the client and respond
//            while ((message = in.readLine()) != null) {
//                System.out.println("Received: " + message);
//                out.println("Echo: " + message);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                socket.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}
