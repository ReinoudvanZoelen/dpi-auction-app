package service;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.NamingException;

public class MQConnection {

    private javax.jms.Connection connection;
    private Session session;
    private Topic topic;
    private Context jndiContext;

    public MQConnection(String clientId, String topicName) throws NamingException, JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        connectionFactory.setTrustAllPackages(true);
        connection = connectionFactory.createConnection();
        connection.setClientID(clientId);
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        this.topic = session.createTopic(topicName);
    }

    public Session getSession() throws JMSException {
        return this.session;
    }

    public Topic getTopic() {
        return this.topic;
    }

    public Context getJndiContext() {
        return this.jndiContext;
    }

    public void start() throws JMSException {
        this.connection.start();
    }
}
