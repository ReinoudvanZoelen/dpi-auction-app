package gateway.base;

import gateway.implementations.MyMessageReceiver;

import javax.jms.JMSException;

public abstract class BaseReceiver {

    public MyMessageReceiver receiver;

    public BaseReceiver(String clientId, String topicName){
        try {
            this.receiver = new MyMessageReceiver(clientId, topicName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}