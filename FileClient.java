import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class FileClient {
    public static void main(String[] args) {
        String serverIp = "127.0.0.1"; // Gebruik "localhost" of het IP van de server
        int port = 5000;
        String savePath = "ontvangen_bestand.txt";

        try (Socket socket = new Socket(serverIp, port);
             InputStream is = socket.getInputStream();
             FileOutputStream fos = new FileOutputStream(savePath)) {

            System.out.println("Verbonden met server. Downloaden gestart...");

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }

            System.out.println("Bestand succesvol ontvangen en opgeslagen als: " + savePath);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
