package gateway;

import org.apache.activemq.command.ActiveMQTextMessage;
import service.MQConnection;

import javax.jms.*;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Gateway {

    private static ArrayList<ActiveMQTextMessage> receivedMessages;
    private MQConnection mqConnection;
    private MessageProducer producer;
    private MessageConsumer consumer;

    public Gateway(String name) {
        try {
            this.mqConnection = new MQConnection(Long.toString(new Date().getTime()), name);
            this.producer = this.mqConnection.getSession().createProducer(mqConnection.getTopic());
            this.consumer = this.mqConnection.getSession().createConsumer(mqConnection.getTopic());
            this.mqConnection.start();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Serializable object) {
        ObjectMessage message = null;
        try {
            message = this.mqConnection.getSession().createObjectMessage(object);
            producer.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public MessageConsumer getConsumer() {
        return this.consumer;
    }
}
