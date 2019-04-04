package gateway;

import models.Bid;
import models.Item;

import java.util.ArrayList;

public class AuctionManagementGateway {
    private Gateway auctionManagerGateway;
    private Gateway auctionGateway;

    private boolean isAuctionCurrentlyActive = false;
    private Item currentItem;
    private ArrayList<Item> auctionItemBacklog = new ArrayList<>();

    public AuctionManagementGateway(String clientId) {
        auctionManagerGateway = new Gateway(clientId, "AuctionManagement");
        auctionGateway = new Gateway(clientId, "Auction");
    }

    public void putUpForAuction(Item item) {
        if (isAuctionCurrentlyActive) {
            this.auctionManagerGateway.GatewaySend("An currentItem is already being auctioned off. The currentItem \"" + item.name + "\" has added to the queue");
            this.auctionItemBacklog.add(item);
        } else {
            isAuctionCurrentlyActive = true;
            this.auctionGateway.GatewaySend("A new currentItem is up for sale. Item name: " + item.name);
        }
    }

    public void submitOffer(Bid bid) {
        if (!(this.currentItem == null)) {
            if (this.currentItem.winningBid.buyingPrice < bid.buyingPrice) {
                this.currentItem.winningBid = bid;
                if (this.currentItem.autoSellPrice <= bid.buyingPrice) {
                    this.notifyCompletedAuction(bid);
                } else {
                    this.auctionGateway.GatewaySend("The current highest bid is " + bid.buyingPrice);
                }
            }
        }
    }

    private void notifyCompletedAuction(Bid bid) {
        this.currentItem.winningBid = bid;
        this.auctionGateway.GatewaySend("Congratulations " + currentItem.seller.name + ", your currentItem \"" + currentItem.name + "\" has been sold to " + currentItem.winningBid.buyer.name + " for " + currentItem.winningBid.buyingPrice);
    }

    public Item getCurrentItem() {
        return this.currentItem;
    }

}
