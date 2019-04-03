package auction.participants;

import gateway.Gateway;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.Scanner;

public class ParticipantNiels extends Gateway {

    private static String CLIENT_ID = "niels";
    private static String TOPIC_NAME = "auction";

    public ParticipantNiels() {
        super(CLIENT_ID, TOPIC_NAME);
    }

    public static void main(String[] args) {
        ParticipantNiels participantNiels = new ParticipantNiels();

        int counter = 0;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                participantNiels.receiveMessage(counter++);
                participantNiels.sendMessageIfEntered(scanner);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessageIfEntered(Scanner scanner) throws JMSException {
//        log("Starting nextline");
//        String message = scanner.nextLine();
//        log("Done nextline");

        String message = "";
        if (new Date().getSeconds() % 10 == 0) {
            message = "Ten seconds have passed";
        }

        if (message.length() > 0) {
            log("Sending message: " + message);
            this.GatewaySend(message);
            log("Done sending message");
        }
    }

    public void receiveMessage(int counter) throws JMSException {
        log("Starting receive message");
        TextMessage message = this.GatewayReceive();
        log("Done getting message");

        if (message != null) {
            System.out.println("[#" + counter + "] " + message.getText());
        }
    }

    private void log(String message) {
        Date date = new Date();
        //System.out.println(date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ", " + message);
    }
}