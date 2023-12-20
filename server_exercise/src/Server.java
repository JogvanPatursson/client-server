import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    //BlockingQueue queue = new BlockingQueueArray(100, 10);
    static CopyOnWriteArrayList<Socket> socketList = new CopyOnWriteArrayList<Socket>();
    static CopyOnWriteArrayList<User> userList = new CopyOnWriteArrayList<User>();
    CopyOnWriteArrayList<Message> messageList = new CopyOnWriteArrayList<Message>();

    

    public Server() {

    }

    public Server(CopyOnWriteArrayList socketList, CopyOnWriteArrayList userList, CopyOnWriteArrayList messageList) {
        this.socketList = socketList;
        this.userList = userList;
        this.messageList = messageList;
    }

    public void start(int portNumber) {
        /*
         * Creates new ServerSocket
         * Tries to connect to Client in a loop, while ServerSocket is successfully bound to an address.
         * Starts new thread for each connected Client.
         */
        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            
            do {
                Socket socket = serverSocket.accept();
                addSocketToSocketList(socket);
                Runnable runnable = new ClientHandler(socket, userList, messageList);
                Thread thread = new Thread(runnable);
                thread.start();
            } while (serverSocket.isBound());

            // Closes ServerSocket
            serverSocket.close();
        } catch (Exception e) {

        }
    }

    /*
     * Add User to userList
     */
    public static void addUserToUserList(User user) {
        // Check if user is already on user list
        if (!userList.contains(user)) {
            System.out.println("Added user to userList");
            userList.add(user);
        }
    }

    /*
     * Add Socket connection to socketList
     */
    public static void addSocketToSocketList(Socket socket) {
        // Check if socket connection is already on socketList
        if (!socketList.contains(socket)) {
            socketList.add(socket);
        }
    }

    /*
     * Check which Users are online
     */
    public static void checkUserIsOnline(User user) {

    }

    /*
     * Send message to all users
     */
    public static void sendMessage(Message message) {
        // Iterate list of sockets
        // Send message to each socket

        Iterator iterator = socketList.iterator();

        while (iterator.hasNext()) {
            Socket socket = (Socket)iterator.next();
            try {
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(message);
                output.flush();
            } catch (IOException e) {
                
            }
        }
    }

    /*
     * Send list of users to all users
     */
    public static void sendUserList() {
        // Convert userList from CopyOnWriteArrayList to ArrayList
        ArrayList userArrayList = new ArrayList<User>();
        userArrayList.addAll(userList);

        // Iterate list of sockets
        // Send list of users to each socket
        Iterator<Socket> iterator = socketList.iterator();

        while (iterator.hasNext()) {
            Socket socket = (Socket)iterator.next();
            try {
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(userArrayList);
                System.out.println("Sent ArrayList");
            } catch (IOException e) {
                // TODO: handle exception
            }
        }
    }
}
