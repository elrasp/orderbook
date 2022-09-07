package org.eserrao.coinbase.messages.messageHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.events.OrderBookUpdateEvent;

import java.util.List;

public abstract class Level2ChannelMessageHandler<T extends CoinbaseMessage> implements ICoinbaseMessageHandler<T> {
    protected final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    protected final IMessageBus bus;

    public Level2ChannelMessageHandler(IMessageBus bus) {
        this.bus = bus;
    }

    @Override
    public void handleMessage(String message) {
        List<OrderBookEntry> entries = this.generateOrderBookEntries(message);
        this.bus.post(new OrderBookUpdateEvent(entries));
    }

    abstract List<OrderBookEntry> generateOrderBookEntries(String message);

    abstract List<OrderBookEntry> convert(T message);
}
