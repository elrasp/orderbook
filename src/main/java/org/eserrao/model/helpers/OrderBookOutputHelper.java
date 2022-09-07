package org.eserrao.model.helpers;

import org.eserrao.model.OrderBook;
import org.eserrao.model.OrderBookEntry;

import java.util.List;

public class OrderBookOutputHelper {
    private OrderBookOutputHelper() {
        //do not init
    }

    public static void print(OrderBook orderBook) {
        List<OrderBookEntry> asks = orderBook.getAsks();
        List<OrderBookEntry> bids = orderBook.getBids();

        System.out.format("%20s %20s %20s %20s", "Ask Size", "Ask Price", "Bid Price", "Bid Size").println();

        for (int i = 0; i < Math.max(asks.size(), bids.size()); i++) {
            OrderBookEntry ask = i < asks.size() ? asks.get(i) : null;
            OrderBookEntry bid = i < bids.size() ? bids.get(i) : null;
            System.out.format("%20f %20f %20f %20f",
                            ask == null ? Double.NaN : ask.getSize(),
                            ask == null ? Double.NaN : ask.getPrice(),
                            bid == null ? Double.NaN : bid.getPrice(),
                            bid == null ? Double.NaN : bid.getSize())
                    .println();
        }
        System.out.println("---------------------------------------------------------------------------------------------------");
    }
}
