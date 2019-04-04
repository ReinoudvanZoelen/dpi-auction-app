package gateway;

import service.MQConnection;

import javax.jms.*;
import javax.naming.NamingException;
import java.io.Serializable;

public class MessageSenderGateway {
    private MQConnection mqConnection;
    private MessageProducer producer;

    public MessageSenderGateway(String name) {
        try {
            this.mqConnection = new MQConnection(name);
            this.producer = this.mqConnection.getSession().createProducer((Destination) mqConnection.getJndiContext().lookup(name));
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void send(Serializable object) {
        ObjectMessage msg = null;
        try {
            msg = this.mqConnection.getSession().createObjectMessage(object);
            producer.send(msg);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
