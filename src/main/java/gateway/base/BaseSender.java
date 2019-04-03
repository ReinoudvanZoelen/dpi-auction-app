package gateway.base;

import gateway.implementations.MyMessageReceiver;
import gateway.implementations.MyMessageSender;

import javax.jms.JMSException;

public abstract class BaseSender {

    public MyMessageSender sender;

    public BaseSender(String clientId, String topicName){
        try {
            this.sender = new MyMessageSender(clientId, topicName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
