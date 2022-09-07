package org.eserrao.coinbase.messages.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class Level2ChannelMessage extends CoinbaseMessage {
    @JsonProperty("product_id")
    private String productId;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
