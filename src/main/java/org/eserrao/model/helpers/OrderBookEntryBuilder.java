package org.eserrao.model.helpers;

import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.SideType;

import java.time.ZonedDateTime;

public class OrderBookEntryBuilder {
    private final String productId;
    private final SideType sideType;
    private final double price;
    private double size;
    private ZonedDateTime timestamp;

    public OrderBookEntryBuilder(String productId, SideType sideType, double price) {
        this.productId = productId;
        this.sideType = sideType;
        this.price = price;
    }

    public OrderBookEntryBuilder setSize(double size) {
        this.size = size;
        return this;
    }

    public OrderBookEntryBuilder setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public OrderBookEntry build() {
        OrderBookEntry entry = new OrderBookEntry(this.productId, this.sideType, this.price);
        entry.setSize(this.size);
        entry.setTimestamp(this.timestamp);
        return entry;
    }

}
