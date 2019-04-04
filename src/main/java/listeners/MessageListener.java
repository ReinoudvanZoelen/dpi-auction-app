package listeners;

import javax.jms.Message;

public class MessageListener implements javax.jms.MessageListener {

    IMessageHandler messageHandler;

    public MessageListener(IMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void onMessage(Message message) {
        this.messageHandler.onMessageReceived(message);
    }
}


