package Application;

import gateway.Gateway;
import listeners.IMessageHandler;
import listeners.MessageListener;
import models.User;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import java.io.Serializable;

public class TestMain implements IMessageHandler {


    private Gateway testGateway = new Gateway("test");

    private MessageListener listener = new MessageListener(this);

    public static void main(String[] args) {
        new TestMain();
    }

    public TestMain() {
        try {
            this.testGateway.getConsumer().setMessageListener(this.listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        this.sendTextMessage("hi this is a text message");
        User user = new User("Gavin Belson");
        this.sendObjectMessage(user);
    }

    private void sendTextMessage(String message) {
        System.out.println("Sending a text message...");
        this.testGateway.sendObjectMessage(message);
    }

    private void sendObjectMessage(Serializable object){
        System.out.println("Sending an object...");
        this.testGateway.sendObjectMessage(object);
    }

    @Override
    public void onTextMessageReceived(ActiveMQTextMessage message) {
        System.out.println("Received a message");
        System.out.println(message);
    }

    @Override
    public void onObjectMessageReceived(ActiveMQObjectMessage message) {
        System.out.println("Received an object");
        System.out.println(message);
    }
}
