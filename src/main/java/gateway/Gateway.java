package gateway;

import javax.jms.*;
import java.io.Serializable;

public abstract class Gateway extends MQConnection{

    //public MQConnection mqConnection;
    public MessageProducer producer;
    public MessageConsumer consumer;

    public Gateway(String clientId) {
        super(clientId);
    }

    public void sendTextMessage(String text) {
        try {
            TextMessage message = this.getSession().createTextMessage(text);
            this.producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendObjectMessage(Serializable object){
        try {
            ObjectMessage message = this.getSession().createObjectMessage(object);
            this.producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public MessageConsumer getConsumer() {
        return this.consumer;
    }
}
