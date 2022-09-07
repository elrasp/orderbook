package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.SnapshotMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.SideType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class SnapshotMessageHandlerTest {

    IMessageBus messageBus;

    @BeforeEach
    public void beforeEach() {
        this.messageBus = Mockito.mock(IMessageBus.class);
    }

    @Test
    public void shouldCreateOrderBookEntriesFromJson() {
        String jsonMessage = "{\"type\": \"snapshot\",\"product_id\": \"BTC-USD\",\"bids\": [[\"10101.10\", \"0.45054140\"]],\"asks\": [[\"10102.55\", \"0.57753524\"]]}";

        SnapshotMessageHandler handler = new SnapshotMessageHandler(this.messageBus);
        List<OrderBookEntry> entries = handler.generateOrderBookEntries(jsonMessage);
        Assertions.assertAll(
                () -> MatcherAssert.assertThat(handler.getType(), Matchers.is("snapshot")),
                () -> MatcherAssert.assertThat(entries.size(), Matchers.is(2)),
                () -> MatcherAssert.assertThat(entries.get(0).getProductId(), Matchers.is("BTC-USD")),
                () -> MatcherAssert.assertThat(entries.stream().filter(o -> o.getSideType() == SideType.BUY).count(), Matchers.is(1L)),
                () -> MatcherAssert.assertThat(entries.stream().filter(o -> o.getSideType() == SideType.SELL).count(), Matchers.is(1L))
        );
    }

    @Test
    public void shouldReturnEmptyCollectionWhenSnapshotMessageIsNull() {
        Level2ChannelMessageHandler<SnapshotMessage> messageHandler = new SnapshotMessageHandler(this.messageBus);
        List<OrderBookEntry> entries = messageHandler.convert(null);
        MatcherAssert.assertThat(entries, Matchers.is(Matchers.empty()));
    }

    @Test
    public void testType() {
        ICoinbaseMessageHandler<SnapshotMessage> messageHandler = new SnapshotMessageHandler(this.messageBus);
        MatcherAssert.assertThat(messageHandler.getType(), Matchers.is("snapshot"));
    }
}
