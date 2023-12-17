import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueueArray implements BlockingQueue{

    ArrayBlockingQueue<Message> messageQueue;
    ArrayBlockingQueue<Users> userQueue;

    public BlockingQueueArray(int capacity) {
        this.messageQueue = new ArrayBlockingQueue<>(capacity);
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
}
