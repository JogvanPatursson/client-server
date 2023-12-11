import java.io.Serializable;

public class Message implements Serializable{
    String sender;
    String messageText;

    Message(String sender, String messageText) {
        this.sender = sender;
        this.messageText = messageText;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getSender() {
        return sender;
    }

    public String getMessageText() {
        return messageText;
    }
}
