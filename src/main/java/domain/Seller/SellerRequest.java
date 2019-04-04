package domain.Seller;

import java.io.Serializable;

public class SellerRequest implements Serializable {
    private String correlationID;
    private String productID;

    public SellerRequest(String correlationID, String productID) {
        this.correlationID = correlationID;
        this.productID = productID;
    }

    public String getCorrelationID() {
        return correlationID;
    }

    public void setCorrelationID(String correlationID) {
        this.correlationID = correlationID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }
}
