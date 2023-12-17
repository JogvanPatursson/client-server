import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueArray implements BlockingQueue {

    ArrayBlockingQueue<Message> messageQueue;
    ArrayBlockingQueue<User> userQueue;

    public BlockingQueueArray(int messageCapacity, int userCapacity) {
        this.messageQueue = new ArrayBlockingQueue<>(messageCapacity);
        this.userQueue = new ArrayBlockingQueue<>(userCapacity);
    }

    @Override
    public void putMessage(Message message) {
        try {
            messageQueue.put(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Message takeMessage() {
        try {
            return messageQueue.take();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void putUser(User user) {
        try {
            // Check for duplicate
            if (!userQueue.contains(user)) {
                userQueue.put(user);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public User takeUser() {
        try {
            return userQueue.take();
        } catch (Exception e) {
            return null;
        }
    }
}
