package gateway;

import gateway.implementations.MyMessageReceiver;
import gateway.implementations.MyMessageSender;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public abstract class Gateway {
    private MyMessageSender sender;
    private MyMessageReceiver receiver;

    public Gateway(String clientId, String topicName){
        try {
            this.sender = new MyMessageSender(clientId, topicName);
            this.receiver = new MyMessageReceiver(clientId, topicName);

            this.sender.send("Client " + clientId + " subscribed to topic " + topicName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void GatewaySend(String message) throws JMSException {
        this.sender.send(message);
    }

    public TextMessage GatewayReceive() throws JMSException {
        return this.receiver.receive();
    }

}
