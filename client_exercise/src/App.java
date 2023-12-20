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
        User user = createUser("Jogvan", "localhost", 8888);
        Message message = createMessage(user.getName(), null);
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
                Thread.sleep(1000);
                // If new message is created
                if (message != null) {
                    output = new ObjectOutputStream(socket.getOutputStream());
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
                    // Cast object to Message, and assign message with value
                    message = (Message) object;
                    // Display message to Java FX
                    System.out.println("User: " + message.getSender() + " Message: " + message.getMessageText());

                }

                else if (isUserList(object)) {
                    // Cast object to ArrayList, and assign userList with value
                    userList = (ArrayList) object;
                    printListOfUsers(userList);
                    
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
        if (object instanceof ArrayList) {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*
     * Print list of users
     */
    public static void printListOfUsers(ArrayList userList) {
        System.out.println("List of users");
        System.out.println("*****");
        for (int i = 0; i < userList.size(); i++) {
            User user = (User)userList.get(i);
            System.out.println(user.getName());
        }
        System.out.println("*****");
    }
}
