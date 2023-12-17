import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class App {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);

        Message message = new Message("Jogvan", "Hello 2");

        try {
            while (true) {
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                Thread.sleep(1000);
                output.writeObject(message);
                output.flush();

                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Message inputMessage = (Message) input.readObject();
                System.out.println(inputMessage.getSender() + ": " + inputMessage.getMessageText());
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        socket.close();
    }
}
