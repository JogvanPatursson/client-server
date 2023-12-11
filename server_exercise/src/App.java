import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        BlockingQueue queue = new BlockingQueueArray(10);
        
        // Create server socket
        ServerSocket serverSocket = new ServerSocket(8888);

        // Runnable object

        try {
            while (true) {
                
                Socket socket = serverSocket.accept();
                
                // Start new thread
                Runnable runnable = new MyRunnable(socket, queue);
                Thread thread = new Thread(runnable);
                thread.start();
            }
        } catch (Exception e) {
            
        }
        serverSocket.close();
        // close executor/threads
    }
}
