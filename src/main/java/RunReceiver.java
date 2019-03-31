import javax.jms.JMSException;

public class RunReceiver {

    public static void main(String[] args) throws JMSException, InterruptedException {
        MyMessageReceiver mConsumer = new MyMessageReceiver("id_receiver_2", "cars");

        int counter = 0;

        while (true) {
            String message = mConsumer.receive();

            System.out.println("[" + counter + "] " + message);

            counter++;
        }
    }
}