import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        
        // Check for input from client
        do {
            try {
                /*
                // Write to client
                if (!messageList.isEmpty()) {
                    // Check size of messageCopyOnArrayList
                    Iterator iterator = messageList.iterator();
                    messageList.clear();
                    while (iterator.hasNext()) {
                        message = (Message) iterator.next();
                        System.out.println(message.getMessageText());
                        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                        output.writeObject(message);
                        output.flush();
                    }
                }
                */

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
                }

            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        } while (socket.isConnected());
        
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

