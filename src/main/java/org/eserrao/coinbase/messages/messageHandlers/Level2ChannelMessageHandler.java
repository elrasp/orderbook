package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.events.OrderBookUpdateEvent;

import java.util.List;

public abstract class Level2ChannelMessageHandler<T extends CoinbaseMessage> extends CoinbaseMessageHandler<T> {

    public Level2ChannelMessageHandler(IMessageBus bus) {
        super(bus);
    }

    @Override
    public void handleMessage(String message) {
        List<OrderBookEntry> entries = this.generateOrderBookEntries(message);
        this.bus.post(new OrderBookUpdateEvent(entries));
    }

    public abstract List<OrderBookEntry> generateOrderBookEntries(String message);

    public abstract List<OrderBookEntry> convert(T message);
}
