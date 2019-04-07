package gateway;

import javax.jms.Destination;
import javax.jms.JMSException;

public class QueueGateway extends Gateway {

    public Destination destination;

    public QueueGateway(String clientId, String queueName) {
        super(clientId);

        try {
            this.destination = session.createQueue(queueName);
            this.producer = this.getSession().createProducer(this.destination);
            this.consumer = this.getSession().createConsumer(this.destination);
            this.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
