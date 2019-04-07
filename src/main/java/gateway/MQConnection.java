package gateway;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.Context;

public class MQConnection {

    public javax.jms.Connection connection;
    public Context jndiContext;
    public Session session;

    public MQConnection(String clientId) {
        try {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            connectionFactory.setTrustAllPackages(true);

            connection = connectionFactory.createConnection();
            connection.setClientID(clientId);

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public Session getSession() {
        return this.session;
    }

    public Context getJndiContext() {
        return this.jndiContext;
    }

    public void start() throws JMSException {
        this.connection.start();
    }
}
