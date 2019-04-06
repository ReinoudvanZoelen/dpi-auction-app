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
        System.out.println(message);
        ActiveMQObjectMessage objectMessage = (ActiveMQObjectMessage) message;
        String physicalName = objectMessage.getDestination().getPhysicalName();
        this.messageHandler.onMessageReceived(objectMessage, physicalName);
    }
}


