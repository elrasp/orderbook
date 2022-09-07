package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.ErrorMessage;
import org.eserrao.coinbase.messages.model.SnapshotMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.SideType;
import org.eserrao.model.events.ErrorEvent;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.List;

class ErrorMessageHandlerTest {

    IMessageBus messageBus;

    @BeforeEach
    public void beforeEach() {
        this.messageBus = Mockito.mock(IMessageBus.class);
    }

    @Test
    public void shouldSendErrorEventWhenErrorMessageIsReceived() {
        String jsonMessage = "{\"type\": \"error\",\"message\": \"Failed to Subscribe\"}";

        ErrorMessageHandler handler = new ErrorMessageHandler(this.messageBus);
        handler.handleMessage(jsonMessage);
        ArgumentCaptor<ErrorEvent> argumentCaptor = ArgumentCaptor.forClass(ErrorEvent.class);
        Mockito.verify(this.messageBus).post(argumentCaptor.capture());
        MatcherAssert.assertThat(argumentCaptor.getValue(), Matchers.notNullValue());
    }

    @Test
    public void testType() {
        CoinbaseMessageHandler<ErrorMessage> messageHandler = new ErrorMessageHandler(this.messageBus);
        MatcherAssert.assertThat(messageHandler.getType(), Matchers.is("error"));
    }
}
