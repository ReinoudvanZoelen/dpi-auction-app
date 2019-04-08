package Controller;

import gateway.TopicGateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import listeners.IMessageHandler;
import listeners.MessageListener;
import models.Bid;
import models.Item;
import models.User;
import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionManagerController implements Initializable, IMessageHandler {

    public String username;

    @FXML
    private TextField textfieldCurrentLot;
    @FXML
    private ListView listviewBiddings;
    @FXML
    private ListView listviewPendingLots;
    @FXML
    private ListView<String> listviewHistory;

    //region Channels
    // Bidding: For placing a bid on the current lot
    private TopicGateway biddingGateway = new TopicGateway(username, "Bidding");

    // LotSeller: For publishing new lots
    private TopicGateway lotSellerGateway = new TopicGateway(username, "LotSeller");

    // LotSubmitter: For submitting a lot for the AuctionManager to consume
    private TopicGateway lotSubmitterGateway = new TopicGateway(username, "LotSubmitter");

    // Winner: For posting lot winners (Item)
    private TopicGateway winnerGateway = new TopicGateway(username, "Winner");

    // Listener
    private MessageListener listener = new MessageListener(this);
    //endregion

    private Item currentItem;
    private ObservableList<Item> pendingLots = FXCollections.observableArrayList();
    private ObservableList<Bid> bids = FXCollections.observableArrayList();
    private ObservableList<String> history = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listviewBiddings.setItems(bids);
        this.listviewPendingLots.setItems(pendingLots);
        this.listviewHistory.setItems(history);

        try {
            this.biddingGateway.getConsumer().setMessageListener(this.listener);
            //this.lotSellerGateway.getConsumer().setMessageListener(this.listener);
            this.lotSubmitterGateway.getConsumer().setMessageListener(this.listener);
            this.winnerGateway.getConsumer().setMessageListener(this.listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //region Message hangling
    @Override
    public void onTextMessageReceived(ActiveMQTextMessage message) {
        System.out.println("Called method onTextMessageReceived");

        try {
            System.out.println("Received text message: " + message.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onObjectMessageReceived(ActiveMQObjectMessage message) {
        System.out.println("Called method onObjectMessageReceived");

        try {
            String destination = message.getDestination().getPhysicalName();
            System.out.println("Received object message: " + message + ", from channel " + destination);

            switch (destination) {
                case "Bidding":
                    Bid bid = (Bid) message.getObject();
                    this.onBidMade(bid);
                    break;
//                case "LotSeller":
//                    Item lotBeingSold = (Item) message.getObject();
//                    this.onLotBeingSold(lotBeingSold);
//                    break;
                case "LotSubmitter":
                    Item lotSubmitted = (Item) message.getObject();
                    this.onLotSubmitted(lotSubmitted);
                    break;
                case "Winner":
                    Item wonItem = (Item) message.getObject();
                    this.onWinnerAnnounced(wonItem);
                    break;
                default:
                    System.out.println("no match on destionation: " + destination);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void onBidMade(Bid bid) {
        System.out.println("Called method onBidMade");

        System.out.println("A new bid has been made: " + bid);

        if (this.currentItem.winningBid == null ||
                bid.buyingPrice > this.currentItem.winningBid.buyingPrice) {

            this.bids.add(bid);

            this.currentItem.winningBid = bid;

            if (this.currentItem.autoSellPrice <= bid.buyingPrice) {
                this.winnerGateway.sendObjectMessage(this.currentItem);
            }
        } else {
            // Bid placed lower than the highest bid
        }
    }

    private void onWinnerAnnounced(Item item) {
        System.out.println("Called method onWinnerAnnounced");

        history.add("The winner of " + item.getName() + " is " + item.winningBid.buyer.getName() + " with a price of € " + item.winningBid.buyingPrice);

        this.reset();
        this.publishNextLot();
    }

    private void reset() {
        System.out.println("Called method reset");

        this.currentItem = null;
        this.textfieldCurrentLot.setText("");
        this.bids.clear();
    }

    private void publishNextLot() {
        System.out.println("Called method publishNextLot");

        if (this.currentItem == null && this.pendingLots.size() > 0) {
            Item nextLot = this.pendingLots.get(0);
            this.pendingLots.remove(0);
            this.currentItem = nextLot;
            this.textfieldCurrentLot.setText(this.currentItem.toString());
            this.lotSellerGateway.sendObjectMessage(this.currentItem);
        }
    }


//    private void onLotBeingSold(Item item) {
//        System.out.println("Called method onLotBeingSold");
//
//        this.pendingLots.add(item);
//        this.publishNextLot();
//    }


    private void onLotSubmitted(Item item) {
        System.out.println("Called method onLotSubmitted");
        System.out.println("A new item has been submitted: " + item.getName());

        this.pendingLots.add(item);
        this.publishNextLot();
    }
    //endregion
}
