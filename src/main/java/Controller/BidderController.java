package Controller;

import com.google.gson.Gson;
import gateway.Gateway;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import listeners.IMessageHandler;
import listeners.MessageListener;
import models.Bid;
import models.Item;
import models.User;
import org.apache.activemq.command.ActiveMQTextMessage;

import javax.jms.JMSException;
import javax.jms.Message;
import java.net.URL;
import java.util.ResourceBundle;

public class BidderController implements Initializable, IMessageHandler {

    private Gson gson = new Gson();

    @FXML
    private Button buttonCreateOffer;
    @FXML
    private Button buttonRefreshOffers;
    @FXML
    private Button buttonCreateLot;
    @FXML
    private TextField textfieldOfferInput;
    @FXML
    private TextField textfieldNameInput;
    @FXML
    private ListView<Bid> listviewOfferOverview;

    //region Channels
    // Bidding: For placing a bid on the current lot

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

    public void onCreateOfferClicked() {
        // TODO !!!
        User u = new User("Reinoud");
        Bid bid = new Bid(u, 100);
        this.biddingGateway.sendMessage(gson.toJson(bid));
        System.out.println("Created offer " + bid);
    }

    public void onRefreshOffersClick() {
        System.out.println("Refreshing offers");
        Bid bid = new Bid(new User("Reinoud"), 100);
        this.biddingGateway.sendMessage(bid);

        System.out.println(bid);
        ActiveMQTextMessage message = this.biddingGateway.receiveMessage(5);
        System.out.println(message);

        if (message != null) {
            Bid biddie = null;
            try {
                biddie = gson.fromJson(message.getText(), Bid.class);
            } catch (JMSException e) {
                e.printStackTrace();
            }

            System.out.println(bid);
            System.out.println(biddie);

            this.offers.add(bid);
            this.offers.add(biddie);
        }
    }

    public void onMessageReceived(Message message) {
        System.out.println(message);
    }
}