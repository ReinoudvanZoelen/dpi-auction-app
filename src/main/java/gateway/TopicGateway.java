package gateway;

import javax.jms.Topic;
import javax.jms.JMSException;

public class TopicGateway extends Gateway {

    public Topic topic;

    public TopicGateway(String clientId, String topicName) {
        super(clientId);

        try {
            this.topic = this.getSession().createTopic(topicName);
            this.producer = this.getSession().createProducer(this.topic);
            this.consumer = this.getSession().createConsumer(this.topic);
            this.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
