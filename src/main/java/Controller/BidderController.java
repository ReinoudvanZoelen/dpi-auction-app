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

public class BidderController implements Initializable, IMessageHandler {

    public String username;

    @FXML
    private TextField textfieldCurrentLot;
    @FXML
    private TextField textfieldOfferInput;
    @FXML
    private TextField textfieldLotnameInput;
    @FXML
    private TextField textfieldLotAutosellInput;
    @FXML
    private ListView<Bid> listviewOfferOverview;
    @FXML
    private ListView<String> listviewHistory;

    //region Channels
    // Bidding: For placing a bid on the current lot
    private TopicGateway biddingGateway = new TopicGateway(username, "Bidding");

    // LotSeller: For selling lots
    private TopicGateway lotSellerGateway = new TopicGateway(username, "LotSeller");

    // LotSubmitter: For submitting a lot for the AuctionManager to sell
    private TopicGateway lotSubmitterGateway = new TopicGateway(username, "LotSubmitter");

    // Winner: For posting lot winners
    private TopicGateway winnerGateway = new TopicGateway(username, "Winner");

    // Listener
    private MessageListener listener = new MessageListener(this);
    //endregion

    private Item currentItem;
    private ObservableList<Bid> offers = FXCollections.observableArrayList();
    private ObservableList<String> history = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listviewOfferOverview.setItems(offers);
        this.listviewHistory.setItems(history);

        try {
            this.biddingGateway.getConsumer().setMessageListener(this.listener);
            this.lotSellerGateway.getConsumer().setMessageListener(this.listener);
            // Bidder doesn't need a listener for LotSubmitter
            // this.lotSubmitterGateway.getConsumer().setMessageListener(this.listener);
            this.winnerGateway.getConsumer().setMessageListener(this.listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //region Button handlers
    public void onCreateOfferClicked() {
        String textBidValue = this.textfieldOfferInput.getText();
        int bidValue = Integer.parseInt(textBidValue);
        if (bidValue > 0) {
            this.textfieldOfferInput.setText("");
            User user = new User(this.username);
            Bid bid = new Bid(user, bidValue);
            this.biddingGateway.sendObjectMessage(bid);
        }

    }

    public void onCreateLotClicked() {
        String description = this.textfieldLotnameInput.getText();
        String autoSellValueString = this.textfieldLotAutosellInput.getText();

        int autoSellValue = Integer.parseInt(autoSellValueString);

        if (description.length() > 0 && autoSellValue > 0) {
            this.textfieldLotnameInput.setText("");
            this.textfieldLotAutosellInput.setText("");

            User user = new User(this.username);
            Item item = new Item(description, user, autoSellValue);
            this.lotSubmitterGateway.sendObjectMessage(item);
        }
    }
    //endregion

    //region Message handling
    @Override
    public void onTextMessageReceived(ActiveMQTextMessage message) {

    }

    @Override
    public void onObjectMessageReceived(ActiveMQObjectMessage message) {
        try {
            String destination = message.getDestination().getPhysicalName();

            switch (destination) {
                case "Bidding":
                    Bid bid = (Bid) message.getObject();
                    this.onBidMade(bid);
                    break;
                case "LotSeller":
                    Item item = (Item) message.getObject();
                    this.onLotBeingSold(item);
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

    private void onWinnerAnnounced(Item item) {
        System.out.println("Called method onWinnerAnnounced");
        System.out.println("Equals? " + item.winningBid.buyer.getName().equals(this.username));

        System.out.println("Called method onWinnerAnnounced");
        if (item.winningBid.buyer.getName().equals(this.username)) {
            this.history.add("You won the auction! You now own " + item.getName() + " and € " + item.winningBid.buyingPrice + " will be deducted from your account.");
        } else {
            this.history.add("Someone else won this auction. A new auction will start soon.");
        }

        // Reset
        this.currentItem = null;
        this.textfieldCurrentLot.setText("");
        this.offers.clear();

    }

    private void onBidMade(Bid bid) {
        System.out.println("A new bid has been made: " + bid);
        System.out.println(bid.toString());
        this.offers.add(bid);
    }

    private void onLotBeingSold(Item item) {
        System.out.println("A new item has been put of for offer: " + item.getName());
        this.currentItem = item;
        this.textfieldCurrentLot.setText(item.toString());
    }
    //endregion
}