package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.coinbase.messages.model.L2UpdateMessage;
import org.eserrao.model.OrderBookEntry;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class L2UpdateMessageHandlerTest {
    @Test
    public void shouldCreateL2UpdateMessageFromJson() {
        String jsonMessage = "{\"type\": \"l2update\",\"product_id\": \"BTC-USD\",\"changes\": [[\"buy\",\"22356.270000\",\"0.00000000\"],[\"buy\",\"22356.300000\",\"1.00000000\"]],\"time\": \"2022-08-04T15:25:05.010758Z\"}";
        ICoinbaseMessageHandler<L2UpdateMessage> messageHandler = new L2UpdateMessageHandler();
        L2UpdateMessage l2UpdateMessage = messageHandler.handleMessage(jsonMessage);
        Assertions.assertAll(
                () -> MatcherAssert.assertThat(l2UpdateMessage.getType(), Matchers.is("l2update")),
                () -> MatcherAssert.assertThat(l2UpdateMessage.getProductId(), Matchers.is("BTC-USD")),
                () -> MatcherAssert.assertThat(l2UpdateMessage.getChanges().size(), Matchers.is(2))
        );
    }

    @Test
    public void shouldCreateOrderBookEntriesFromJson() {
        String jsonMessage = "{\"type\": \"l2update\",\"product_id\": \"BTC-USD\",\"changes\": [[\"buy\",\"22356.270000\",\"0.00000000\"],[\"buy\",\"22356.300000\",\"1.00000000\"]],\"time\": \"2022-08-04T15:25:05.010758Z\"}";
        ICoinbaseMessageHandler<L2UpdateMessage> messageHandler = new L2UpdateMessageHandler();
        L2UpdateMessage l2UpdateMessage = messageHandler.handleMessage(jsonMessage);
        List<OrderBookEntry> entries = messageHandler.convert(l2UpdateMessage);
        MatcherAssert.assertThat(entries.size(), Matchers.is(2));
    }

    @Test
    public void shouldReturnEmptyCollectionWhenUpdateMesageIsNull() {
        ICoinbaseMessageHandler<L2UpdateMessage> messageHandler = new L2UpdateMessageHandler();
        List<OrderBookEntry> entries = messageHandler.convert(null);
        MatcherAssert.assertThat(entries, Matchers.is(Matchers.empty()));
    }

    @Test
    public void testType() {
        ICoinbaseMessageHandler<L2UpdateMessage> messageHandler = new L2UpdateMessageHandler();
        MatcherAssert.assertThat(messageHandler.getType(), Matchers.is("l2update"));
    }

}