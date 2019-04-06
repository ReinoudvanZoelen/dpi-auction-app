package Controller;

import gateway.Gateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import listeners.IMessageHandler;
import listeners.MessageListener;
import models.Bid;
import models.Item;
import models.User;
import org.apache.activemq.command.ActiveMQObjectMessage;

import javax.jms.JMSException;
import java.net.URL;
import java.util.ResourceBundle;

public class BidderController implements Initializable, IMessageHandler {

    @FXML
    private Label labelCurrentLotName;
    @FXML
    private Button buttonCreateOffer;
    @FXML
    private Button buttonRefreshOffers;
    @FXML
    private Button buttonCreateLot;
    @FXML
    private TextField textfieldOfferInput;
    @FXML
    private TextField textfieldLotnameInput;
    @FXML
    private ListView<Bid> listviewOfferOverview;

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

    private ObservableList<Bid> offers = FXCollections.observableArrayList();
    private Item currentItem;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.listviewOfferOverview.setItems(offers);

        try {
            this.biddingGateway.getConsumer().setMessageListener(this.listener);
            this.lotPublisherGateway.getConsumer().setMessageListener(this.listener);
            this.lotSubmitterGateway.getConsumer().setMessageListener(this.listener);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    //region Button handlers
    public void onCreateOfferClicked() {
        User user = new User("Reinoud");
        Bid bid = new Bid(user, 100);
        this.biddingGateway.sendMessage(bid);
    }

    public void onCreateLotClicked() {
        String description = this.textfieldLotnameInput.getText();
        if (description.length() > 0) {
            User user = new User("Reinoud");
            Item item = new Item(description, user, 100);
            this.lotSubmitterGateway.sendMessage(item);
        }
    }
    //endregion

    //region Message handling
    @Override
    public void onMessageReceived(ActiveMQObjectMessage message, String destination) {

        try {
            switch (destination) {
                case "Bidding":
                    Bid bid = (Bid) message.getObject();
                    this.onBidMade(bid);
                    break;
                case "LotPublisher":
                    Item item = (Item) message.getObject();
                    this.onLotPublished(item);
                    break;
                //case "LotSubmitter":
                //    // No actions are needed from the Bidder when a new lot is submitted
                //    break;
                default:
                    System.out.println("no match");
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    private void onBidMade(Bid bid) {
        System.out.println(bid.toString());
        this.offers.add(bid);
    }

    private void onLotPublished(Item item) {
        System.out.println("RECEIVED: " + item.toString());
        this.currentItem = item;
        this.labelCurrentLotName.setText(item.name);
    }
    //endregion
}