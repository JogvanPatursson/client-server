import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.text.html.HTMLDocument.Iterator;

public class MyRunnable implements Runnable {

    private Socket socket;
    private BlockingQueue queue;

    MyRunnable(Socket socket, BlockingQueue queue) {
        this.socket = socket;
        this.queue = queue;
    }

    @Override
    public void run() {
        // Check for input from client
        do {
            try {
                // Read from client

                ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
                Object object = input.readObject();

                if (object instanceof Message) {
                    Message message = (Message) object;
                    // Adds message to queue
                    queue.putMessage(message);
                    //

                    System.out.println(message.getMessageText());
                }

                else if (object instanceof User) {
                    User user = (User) object;
                    // Adds user to queue
                    queue.putUser(user);
                }

                Message newMessage = queue.takeMessage();
                // String newMessageString = newMessage.getMessageText() + " from queue";
                // System.out.println(newMessageString);
                ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                output.writeObject(newMessage);
                output.flush();

            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } while (socket.isConnected());
        queue.takeUser();
    }
}
