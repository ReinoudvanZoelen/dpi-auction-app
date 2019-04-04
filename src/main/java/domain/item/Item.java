package domain.item;

import java.io.Serializable;

public class Item implements Serializable {

    private long id;
    private String name;

    private String price;

    private String seller;

    private String productID;

    private String correlationID;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        if (price != null) {
            return price;
        } else {
            return "Waiting..";
        }
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeller() {
        if (seller != null) {
            return seller;
        } else {
            return "Waiting..";
        }
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public String getProductID() {
        if (productID != null) {
            return productID;
        } else {
            return "Waiting..";
        }
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", seller='" + seller + '\'' +
                ", productID='" + productID + '\'' +
                ", correlationID='" + correlationID + '\'' +
                '}';
    }
}
