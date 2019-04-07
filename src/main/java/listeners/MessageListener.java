package listeners;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Message;

public class MessageListener implements javax.jms.MessageListener {

    IMessageHandler messageHandler;

    public MessageListener(IMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void onMessage(Message message) {
        if (message.getClass() == ActiveMQTextMessage.class) {
            ActiveMQTextMessage textMessage = (ActiveMQTextMessage) message;
            this.messageHandler.onTextMessageReceived(textMessage);
        } else if (message.getClass() == ActiveMQObjectMessage.class) {
            ActiveMQObjectMessage objectMessage = (ActiveMQObjectMessage) message;
            this.messageHandler.onObjectMessageReceived(objectMessage);
        } else {
            System.out.println("Neither a text message nor an object message was received");
        }
    }
}


