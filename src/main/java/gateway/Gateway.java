package gateway;

import gateway.implementations.CustomMessageReceiver;
import gateway.implementations.CustomMessageSender;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class Gateway {
    private CustomMessageSender sender;
    private CustomMessageReceiver receiver;

    public Gateway(String clientId, String topicName){
        try {
            this.sender = new CustomMessageSender(clientId, topicName);
            this.receiver = new CustomMessageReceiver(clientId, topicName);

            this.sender.send("Client " + clientId + " subscribed to topic " + topicName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public void GatewaySend(String message) {
        try {
            this.sender.send(message);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public TextMessage GatewayReceive()  {
        try {
            return this.receiver.receive();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        return null;
    }

}
