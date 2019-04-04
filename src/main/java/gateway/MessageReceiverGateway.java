package gateway;

import org.apache.activemq.command.ActiveMQTextMessage;
import service.MQConnection;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.naming.NamingException;

public class MessageReceiverGateway {
    private MQConnection mqConnection;
    private MessageConsumer consumer;

    public MessageReceiverGateway(String name) {
        try {
            this.mqConnection = new MQConnection(name);
            this.consumer = this.mqConnection.getSession().createConsumer((Destination) mqConnection.getJndiContext().lookup(name));
            this.mqConnection.start();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public MessageConsumer getConsumer() {
        return this.consumer;
    }

    public ActiveMQTextMessage getMessage(long timeout) {
        try {
            return (ActiveMQTextMessage) this.consumer.receive(timeout);
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }
}