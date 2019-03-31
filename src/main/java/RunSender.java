import javax.jms.JMSException;

public class RunSender {
    public static void main(String[] args) throws JMSException, InterruptedException {
        MyMessageSender mProducer = new MyMessageSender("id_sender_2", "cars");

        int counter = 0;

        while (counter < 100) {
            System.out.println();

            mProducer.send("[" + counter + "] Hello!");

            counter++;
        }
    }
}