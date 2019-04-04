package models;

import java.io.Serializable;

public class Bid implements Serializable {
    public User buyer;
    public int buyingPrice;

    public Bid(User buyer, int buyingPrice) {
        this.buyer = buyer;
        this.buyingPrice = buyingPrice;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public int getBuyingPrice() {
        return buyingPrice;
    }

    public void setBuyingPrice(int buyingPrice) {
        this.buyingPrice = buyingPrice;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "buyer=" + buyer +
                ", buyingPrice=" + buyingPrice +
                '}';
    }
}
