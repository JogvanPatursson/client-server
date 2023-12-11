import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MyRunnable implements Runnable {
    
    private Socket socket;
    BlockingQueue queue;
    
    MyRunnable(Socket socket, BlockingQueue queue) {
        this.socket = socket;
        this.queue = queue;
    }

    @Override
    public void run() {
        // Check for input from client
        while (true) {
            
        
                try {
                    // Read from client

                    ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
       
                    Message message = (Message) input.readObject();
                    //Object object = input.readObject();
                    System.out.println("Client: " + message.getMessageText());
                    
                    queue.putMessage(message);
                    // Add to BlockingQueue
                    
                    Message newMessage = queue.takeMessage();
                    String newMessageString = newMessage.getMessageText() + " from queue";
                    System.out.println(newMessageString);
                    //ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                
                    //output.writeObject(newMessage);
                
                } catch (IOException | ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
    }
}
