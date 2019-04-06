package models;

import java.io.Serializable;

public class Item implements Serializable {
    public String name;
    public User seller;
    public Bid winningBid;
    public int autoSellPrice;

    public Item(String name, User seller, int autoSellPrice) {
        this.name = name;
        this.seller = seller;
        this.autoSellPrice = autoSellPrice;
    }

    @Override
    public String toString() {
        return "Item "+ name + ", sold by " + seller.name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Bid getWinningBid() {
        return winningBid;
    }

    public void setWinningBid(Bid winningBid) {
        this.winningBid = winningBid;
    }

    public int getAutoSellPrice() {
        return autoSellPrice;
    }

    public void setAutoSellPrice(int autoSellPrice) {
        this.autoSellPrice = autoSellPrice;
    }
}
