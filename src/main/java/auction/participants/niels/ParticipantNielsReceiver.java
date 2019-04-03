package auction.participants.niels;

import gateway.base.BaseReceiver;

import javax.jms.JMSException;

public class ParticipantNielsReceiver extends BaseReceiver {

    public static void main(String[] args) {
        ParticipantNielsReceiver receiver = new ParticipantNielsReceiver();

        int counter = 0;

        while(true){
            try {
                receiver.receiveMessage(counter++);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private static String CLIENT_ID = "niels";
    private static String TOPIC_NAME = "auction";

    public ParticipantNielsReceiver() {
        super(CLIENT_ID, TOPIC_NAME);
    }


    private void receiveMessage(int counter) throws JMSException {
        String message = this.receiver.receive();

        System.out.println("[#" + counter + "] " + message);

        counter++;

    }
}