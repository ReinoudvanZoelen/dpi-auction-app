package listeners;

import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.Message;

public interface IMessageHandler {
    void onMessageReceived(ActiveMQTextMessage message);
}