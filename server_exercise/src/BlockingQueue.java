public interface BlockingQueue {
    public void putMessage(Message message);

    public Message takeMessage();

    public void putUser(User user);

    public User takeUser();
}
