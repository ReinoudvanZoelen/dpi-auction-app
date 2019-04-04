package Controller;


import com.google.gson.Gson;
import gateway.Gateway;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import models.Bid;
import models.Item;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AuctionManagerController implements Initializable {

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
    //endregion

    private List<Item> items = new ArrayList<Item>();
    private List<Bid> bids = new ArrayList<Bid>();
    private Gson gson = new Gson();
    private Item CurrentLot;
    private ArrayList<Bid> Biddings;
    private ArrayList<Item> PendingLots;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void Refresh() {
        // Check ItemSubmitReceiver for any new items submitted for auction
        // true => add to backlog

        // Check Item being sold to verify that it's limit hasn't been reached yet
        // true => clear all pending bids and send a message that a new item is being sold to the LotFetcherSender

        // Check BiddingReceiver for any new biddings on the current item
        // true => check if item is still for sale and then check

        refreshSubmittedItems();
        refreshBids();
        refreshCurrentLot();
    }

    private void refreshSubmittedItems() {
    }

    private void refreshBids() {
    }

    private void refreshCurrentLot() {
    }

}
