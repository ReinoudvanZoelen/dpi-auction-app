package gateway;

import org.apache.activemq.command.ActiveMQTextMessage;
import service.MQConnection;

import javax.jms.*;
import javax.naming.NamingException;
import java.io.Serializable;
import java.util.ArrayList;

public class Gateway {

    private MQConnection mqConnection;
    private MessageProducer producer;
    private MessageConsumer consumer;
    private static ArrayList<ActiveMQTextMessage> receivedMessages;

    public Gateway(String name) {
        try {
            this.mqConnection = new MQConnection(name);
            this.producer = this.mqConnection.getSession().createProducer((Destination) mqConnection.getJndiContext().lookup(name));
            this.consumer = this.mqConnection.getSession().createConsumer((Destination) mqConnection.getJndiContext().lookup(name));
            this.mqConnection.start();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(Serializable object) {
        ObjectMessage msg = null;
        try {
            msg = this.mqConnection.getSession().createObjectMessage(object);
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public MessageConsumer getConsumer() {
        return this.consumer;
    }

    public ActiveMQTextMessage receiveMessage(long timeout) {
        try {
            ActiveMQTextMessage message = (ActiveMQTextMessage) this.consumer.receive(timeout);
            this.receivedMessages.add(message);
            return message;
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
}
