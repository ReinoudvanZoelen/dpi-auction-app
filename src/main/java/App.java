import javax.jms.JMSException;

public class App {
    public static void main(String[] args) throws JMSException {

        String clientId = "a";
        String topicName = "b";
        MyMessageReceiver receiver = new MyMessageReceiver(clientId, topicName);
        MyMessageSender sender = new MyMessageSender(clientId, topicName);

        sender.send("hello!");

        System.out.println(receiver.receive());
    }
}