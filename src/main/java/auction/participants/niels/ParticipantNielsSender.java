package auction.participants.niels;

import gateway.base.BaseSender;

import javax.jms.JMSException;

import java.util.Scanner;

public class ParticipantNielsSender extends BaseSender {

    public static void main(String[] args) {
        ParticipantNielsSender sender = new ParticipantNielsSender();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                sender.continuousSendMessages(scanner);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }

    private static String CLIENT_ID = "niels";
    private static String TOPIC_NAME = "auction";

    public ParticipantNielsSender() {
        super(CLIENT_ID, TOPIC_NAME);
    }

    private void continuousSendMessages(Scanner scanner) throws JMSException {
        String message = scanner.nextLine();

        if (message.length() > 0) {
            this.sender.send(message);
            message = "";
        }

    }

}