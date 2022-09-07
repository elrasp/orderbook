package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.L2UpdateMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.SideType;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class L2UpdateMessageHandlerTest {

    IMessageBus messageBus;

    @BeforeEach
    public void beforeEach() {
        this.messageBus = Mockito.mock(IMessageBus.class);
    }

    @Test
    public void shouldCreateOrderBookEntriesFromJson() {
        String jsonMessage = "{\"type\": \"l2update\",\"product_id\": \"BTC-USD\",\"changes\": [[\"buy\",\"22356.270000\",\"0.00000000\"],[\"buy\",\"22356.300000\",\"1.00000000\"]],\"time\": \"2022-08-04T15:25:05.010758Z\"}";
        L2UpdateMessageHandler handler = new L2UpdateMessageHandler(this.messageBus);
        List<OrderBookEntry> entries = handler.generateOrderBookEntries(jsonMessage);
        Assertions.assertAll(
                () -> MatcherAssert.assertThat(handler.getType(), Matchers.is("l2update")),
                () -> MatcherAssert.assertThat(entries.size(), Matchers.is(2)),
                () -> MatcherAssert.assertThat(entries.get(0).getProductId(), Matchers.is("BTC-USD")),
                () -> MatcherAssert.assertThat(entries.stream().filter(o -> o.getSideType() == SideType.BUY).count(), Matchers.is(2L)),
                () -> MatcherAssert.assertThat(entries.stream().filter(o -> o.getSideType() == SideType.SELL).count(), Matchers.is(0L))
        );
    }

    @Test
    public void shouldReturnEmptyCollectionWhenUpdateMesageIsNull() {
        Level2ChannelMessageHandler<L2UpdateMessage> messageHandler = new L2UpdateMessageHandler(this.messageBus);
        List<OrderBookEntry> entries = messageHandler.convert(null);
        MatcherAssert.assertThat(entries, Matchers.is(Matchers.empty()));
    }

    @Test
    public void testType() {
        CoinbaseWebsocketMessageHandler<L2UpdateMessage> messageHandler = new L2UpdateMessageHandler(this.messageBus);
        MatcherAssert.assertThat(messageHandler.getType(), Matchers.is("l2update"));
    }

}