import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Connection {
    public Socket connectionSocket;
    public String inetAddress;

    public Connection(ServerSocket serverSocket) {
        try {
            connectionSocket = serverSocket.accept();
            inetAddress = connectionSocket.getInetAddress().toString();
        } catch (IOException e) {
            System.out.println("Error accepting client connection: " + e.getMessage());
        }
    }
}
