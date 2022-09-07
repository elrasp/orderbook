package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.coinbase.messages.model.L2UpdateMessage;
import org.eserrao.coinbase.messages.model.SnapshotMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.SideType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class SnapshotMessageHandlerTest {
    @Test
    public void shouldCreateSnapshotMessageFromJson() {
        String jsonMessage = "{\"type\": \"snapshot\",\"product_id\": \"BTC-USD\",\"bids\": [[\"10101.10\", \"0.45054140\"]],\"asks\": [[\"10102.55\", \"0.57753524\"]]}";
        ICoinbaseMessageHandler<SnapshotMessage> handler = new SnapshotMessageHandler();
        SnapshotMessage snapshot = handler.handleMessage(jsonMessage);
        Assertions.assertAll(
                () -> MatcherAssert.assertThat(snapshot.getType(), Matchers.is("snapshot")),
                () -> MatcherAssert.assertThat(snapshot.getProductId(), Matchers.is("BTC-USD")),
                () -> MatcherAssert.assertThat(snapshot.getAsks().size(), Matchers.is(1)),
                () -> MatcherAssert.assertThat(snapshot.getBids().size(), Matchers.is(1))
        );
    }

    @Test
    public void shouldCreateOrderBookEntriesFromJson() {
        String jsonMessage = "{\"type\": \"snapshot\",\"product_id\": \"BTC-USD\",\"bids\": [[\"10101.10\", \"0.45054140\"]],\"asks\": [[\"10102.55\", \"0.57753524\"]]}";
        ICoinbaseMessageHandler<SnapshotMessage> handler = new SnapshotMessageHandler();
        SnapshotMessage snapshot = handler.handleMessage(jsonMessage);
        List<OrderBookEntry> entries = handler.convert(snapshot);
        Assertions.assertAll(
                () -> MatcherAssert.assertThat(entries.size(), Matchers.is(2)),
                () -> MatcherAssert.assertThat(entries.stream().filter(o -> o.getSideType() == SideType.BUY).count(), Matchers.is(1L)),
                () -> MatcherAssert.assertThat(entries.stream().filter(o -> o.getSideType() == SideType.SELL).count(), Matchers.is(1L))
        );
    }

    @Test
    public void shouldReturnEmptyCollectionWhenSnapshotMessageIsNull() {
        ICoinbaseMessageHandler<SnapshotMessage> messageHandler = new SnapshotMessageHandler();
        List<OrderBookEntry> entries = messageHandler.convert(null);
        MatcherAssert.assertThat(entries, Matchers.is(Matchers.empty()));
    }

    @Test
    public void testType() {
        ICoinbaseMessageHandler<SnapshotMessage> messageHandler = new SnapshotMessageHandler();
        MatcherAssert.assertThat(messageHandler.getType(), Matchers.is("snapshot"));
    }
}
