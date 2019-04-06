package listeners;

import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.Message;

public interface IMessageHandler {
    void onMessageReceived(ActiveMQObjectMessage message, String destination);
}