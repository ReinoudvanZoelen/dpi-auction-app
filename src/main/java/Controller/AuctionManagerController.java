package Controller;

import gateway.Gateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import listeners.IMessageHandler;
import listeners.MessageListener;
import models.Bid;
import models.Item;
import models.User;
import org.apache.activemq.command.ActiveMQObjectMessage;

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
    private ObservableList<Item> pendingLots = FXCollections.observableArrayList();
    private ObservableList<Bid> bids = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listviewPendingLots.setItems(pendingLots);
        this.listviewBiddings.setItems(bids);

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
    public void onMessageReceived(ActiveMQObjectMessage message, String destination) {
        try {
            switch (destination) {
                case "Bidding":
                    Bid bid = (Bid) message.getObject();
                    this.onBidMade(bid);
                    break;
                //case "LotPublisher":
                //    // No actions are needed from the Bidder when a new lot is submitted
                //    break;
                case "LotSubmitter":
                    Item lot = (Item) message.getObject();
                    this.onLotSubmitted(lot);
                    break;
                default:
                    System.out.println("no match on destionation: " + destination);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void onBidMade(Bid bid) {
        System.out.println("RECEIVED: " + bid);
    }

    private void onLotSubmitted(Item item) {
        System.out.println("RECEIVED: " + item.getName());
        this.pendingLots.add(item);
        this.lotPublisherGateway.sendMessage(this.currentItem);
    }

    private void onUserReceived(User user) {
        System.out.println("RECEIVED: " + user);
    }
    //endregion
}
