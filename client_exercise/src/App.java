import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888);
        User user = createUser("Helgi", "localhost", 8888);
        Message message = createMessage(null, null);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());

        System.out.println("Send message: ");
        Scanner scanner = new Scanner(System.in);
        String asd = scanner.nextLine();
        message.setMessageText(asd);
        scanner.close();

        try {
            // Sending User object to server
            output.writeObject(user);
            output.flush();

            while (true) {
                
                // If new message is created
                System.out.println("check if message != null");
                if (message != null) {
                    output = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("message != null");
                    output.writeObject(message);
                    output.flush();
                    message = null;
                }
                //Thread.sleep(10);
                //output.writeObject(message);
                //output.flush();
                
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Message inputMessage = (Message) input.readObject();
                System.out.println("Reading inputSocket.....");
                System.out.println("From server: " + inputMessage.getSender() + ": " + inputMessage.getMessageText());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        socket.close();
    }

    /*
     * Create User object
     */
    public static User createUser(String name, String adress, int portNumber) {
        User user = new User(name, adress, portNumber);
        return user;
    }
    
    /*
     * Create Message object
     */
    public static Message createMessage(String sender, String messageText) {
        Message message = new Message(sender, messageText);
        return message;
    }
}
