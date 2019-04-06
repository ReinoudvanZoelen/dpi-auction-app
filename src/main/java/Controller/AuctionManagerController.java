package Controller;


import com.google.gson.Gson;
import gateway.Gateway;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import listeners.IMessageHandler;
import listeners.MessageListener;
import models.Bid;
import models.Item;
import models.User;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AuctionManagerController implements Initializable, IMessageHandler {

    @FXML
    public TextArea textareaCurrentLot;
    @FXML
    public ListView listviewBiddings;
    @FXML
    public ListView listviewPendingLots;
    private Gson gson = new Gson();
    //region Channels
    // Bidding: For placing a bid on the current lot
    private Gateway biddingGateway = new Gateway("Bidding");

    // LotPublisher: For publishing new lots
    private Gateway lotPublisherGateway = new Gateway("LotPublisher");

    // LotSubmitter: For submitting a lot for the AuctionManager to consume
    private Gateway lotSubmitterGateway = new Gateway("LotSubmitter");

    // Listener
    private MessageListener listener = new MessageListener(this);

    //endregion

    private Item currentItem;
    private List<Item> items = new ArrayList<Item>();
    private List<Bid> bids = new ArrayList<Bid>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.biddingGateway.getConsumer().setMessageListener(this.listener);
            this.lotPublisherGateway.getConsumer().setMessageListener(this.listener);
            this.lotSubmitterGateway.getConsumer().setMessageListener(this.listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //region Message handling
    @Override
    public void onMessageReceived(ActiveMQTextMessage message) {
        try {
            String json = message.getText();
            System.out.println(json);

            Bid bid = gson.fromJson(json, Bid.class);
            Item item = gson.fromJson(json, Item.class);
            User user = gson.fromJson(json, User.class);

            System.out.println(bid);
            System.out.println(item);
            System.out.println(user);

            if (bid != null) {
                onBidReceived(bid);
            } if (item != null) {
                onItemReceived(item);
            } if (user != null) {
                onUserReceived(user);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void onBidReceived(Bid bid) {
        System.out.println("RECEIVED: " + bid);
    }

    private void onItemReceived(Item item) {
        System.out.println("RECEIVED: " + item);
        this.currentItem = item;
        this.currentItem.name = this.currentItem.name + " being sold!";
        this.lotPublisherGateway.sendMessage(this.currentItem);
    }

    private void onUserReceived(User user) {
        System.out.println("RECEIVED: " + user);
    }
    //endregion
}
