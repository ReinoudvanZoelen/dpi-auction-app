package gateway;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public interface IMessageHandler {
    void sendMessage(String message) throws JMSException;
    TextMessage receiveMessasge() throws JMSException;
}
