package listeners;

import javax.jms.Message;

public interface IMessageHandler {
    void onMessageReceived(Message message);
}