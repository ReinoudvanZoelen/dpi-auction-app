package listeners;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

public interface IMessageHandler {
    void onTextMessageReceived(ActiveMQTextMessage message);
    void onObjectMessageReceived(ActiveMQObjectMessage message);
}