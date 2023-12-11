public interface BlockingQueue {
    public void putMessage(Message message);
    public Message takeMessage();
}
