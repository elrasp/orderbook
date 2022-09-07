package org.eserrao.coinbase.messages.messageHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.L2UpdateMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.events.OrderBookUpdateEvent;
import org.eserrao.model.helpers.OrderBookEntryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class L2UpdateMessageHandler extends Level2ChannelMessageHandler<L2UpdateMessage> {

    public static final String TYPE = "l2update";

    @Inject
    public L2UpdateMessageHandler(IMessageBus bus) {
        super(bus);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public List<OrderBookEntry> generateOrderBookEntries(String message) {
        try {
            L2UpdateMessage updateMessage = mapper.readValue(message, L2UpdateMessage.class);
            return this.convert(updateMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<OrderBookEntry> convert(L2UpdateMessage message) {
        if (message == null) {
            return Collections.emptyList();
        }
        return message.getChanges()
                .stream()
                .map(change -> new OrderBookEntryBuilder(message.getProductId(), change.sideType(), change.price())
                        .setSize(change.size())
                        .setTimestamp(message.getTime()).build())
                .collect(Collectors.toList());
    }
}
