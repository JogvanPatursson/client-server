import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class App {
    public static void main(String[] args) throws IOException {
        // Fields
        Socket socket = new Socket("localhost", 8888);
        User user = createUser("Helgi", "localhost", 8888);
        Message message = createMessage(null, null);
        ArrayList<User> userList = new ArrayList<User>();
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
                if (message != null) {
                    output = new ObjectOutputStream(socket.getOutputStream());
                    System.out.println("message != null");
                    output.writeObject(message);
                    output.flush();
                    message = null;
                }

                
                /*
                 * Listening to input
                 */
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Object object = input.readObject();
                
                if (isMessage(object)) {
                    message = (Message) object;
                    // Display message to Java FX
                }

                else if (isUserList(object)) {
                    user = (User) object;
                    // Add user to list of users
                }
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

       /*
    * Check if object is of class Message
    */
    public static boolean isMessage(Object object) {
        if (object instanceof Message) {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
    * Check if object is of class User
    */
    public static boolean isUserList(Object object) {
        if (object instanceof CopyOnWriteArrayList) {
            return true;
        }
        else
        {
            return false;
        }
    }
}
