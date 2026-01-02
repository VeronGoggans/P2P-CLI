import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    private final int port = 5000;
    private final int threadCount = 10;
    private ExecutorService threadPool;
    private boolean running;

    public void run() {
        threadPool = Executors.newFixedThreadPool(threadCount);
        running = true;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started, listening on port " + port + "...");
            while (running) {
                Connection connection = new Connection(serverSocket);
                System.out.println("New connection established: " + connection);
                threadPool.execute(() -> handleClient(connection));
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleClient(Connection connection) {
        Socket socket = connection.connectionSocket;
        String filePath = "test.txt";
        try (socket;
             FileInputStream fis = new FileInputStream(filePath);
             OutputStream os = socket.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            System.out.println("File successfully sent: " + filePath);
            System.out.println("File successfully sent to client: " + connection.inetAddress);
        } catch (IOException e) {
            System.err.println("Error during handling of the client: " + e.getMessage());
        }
    }
}