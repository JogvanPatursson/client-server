import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Iterator;

public class ClientHandler implements Runnable {
    
    private Socket socket;
    private CopyOnWriteArrayList userList;
    private CopyOnWriteArrayList messageList;
    

    ClientHandler(Socket socket, CopyOnWriteArrayList userList, CopyOnWriteArrayList messageList) {
        this.socket = socket;
        this.userList = userList;
        this.messageList = messageList;
    }

    @Override
    public void run() {
        
        Message message = new Message(null, null);
        User user = new User(null, null, 0);
        
        try {
            socket.setSoTimeout(5000);
        } catch (SocketException e) {
            // Remove User from userList
            Server.removeUserFromUserList(user);
        }
        // Check for input from client
        do {
            try {
                // Read from client
                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Object object = input.readObject();

                if (isMessage(object)) {
                    message = (Message) object;
                    System.out.println("Message: " + message.messageText);
                    // Adds message to queued
                    Server.sendMessage(message);
                    //messageList.add(message);
                }

                else if (isUser(object)) {
                    user = (User) object;
                    // Adds user to queue
                    Server.addUserToUserList(user);
                    // Write to client
                    // Send userList to client
                    Server.sendUserList();
                }

            } catch (IOException | ClassNotFoundException e) {
   
            }
            
        } while (socket.isBound());
        System.out.println("Disconnected");
        // When socket is not bound, close socket
        try {
            socket.close();
        } catch (IOException e) {
            
        }

    }

    /*
    * Check if object is of class Message
    */
    public boolean isMessage(Object object) {
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
    public boolean isUser(Object object) {
        if (object instanceof User) {
            return true;
        }
        else
        {
            return false;
        }
    }
}

