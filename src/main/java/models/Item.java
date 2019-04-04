package models;

import java.io.Serializable;

public class Item implements Serializable {
    public String name;
    public User seller;
    public Bid winningBid;
    public int autoSellPrice;

    public boolean isSold(){
        return true;
    }
}
