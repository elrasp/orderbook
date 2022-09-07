package org.eserrao.coinbase.messages.messageHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.SnapshotMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.SideType;
import org.eserrao.model.events.OrderBookUpdateEvent;
import org.eserrao.model.helpers.OrderBookEntryBuilder;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Singleton
public class SnapshotMessageHandler extends Level2ChannelMessageHandler<SnapshotMessage> {

    public static final String TYPE = "snapshot";

    @Inject
    public SnapshotMessageHandler(IMessageBus bus) {
        super(bus);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public List<OrderBookEntry> generateOrderBookEntries(String message) {
        try {
            SnapshotMessage snapshotMessage = mapper.readValue(message, SnapshotMessage.class);
            return this.convert(snapshotMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<OrderBookEntry> convert(SnapshotMessage message) {
        if (message == null) {
            return Collections.emptyList();
        }
        final ZonedDateTime snapshotTimestamp = ZonedDateTime.now();
        Stream<OrderBookEntry> asks = message.getAsks()
                .stream()
                .map(ask -> new OrderBookEntryBuilder(message.getProductId(), SideType.SELL, ask.price())
                        .setSize(ask.size())
                        .setTimestamp(snapshotTimestamp)
                        .build());

        Stream<OrderBookEntry> bids = message.getBids()
                .stream()
                .map(bid -> new OrderBookEntryBuilder(message.getProductId(), SideType.BUY, bid.price())
                        .setSize(bid.size())
                        .setTimestamp(snapshotTimestamp)
                        .build());

        return Stream.concat(asks, bids).collect(Collectors.toList());
    }
}
