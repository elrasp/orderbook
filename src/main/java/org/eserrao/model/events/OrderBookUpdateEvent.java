package org.eserrao.model.events;

import org.eserrao.model.OrderBookEntry;

import java.util.List;

public class OrderBookUpdateEvent {
    private final List<OrderBookEntry> entries;

    public OrderBookUpdateEvent(List<OrderBookEntry> entries) {
        this.entries = entries;
    }

    public List<OrderBookEntry> getEntries() {
        return entries;
    }
}
