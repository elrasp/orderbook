package org.eserrao.coinbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eserrao.MessageBus;
import org.eserrao.coinbase.messages.CoinbaseMessageHandlerFactory;
import org.eserrao.coinbase.messages.messageHandlers.ICoinbaseMessageHandler;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;
import org.eserrao.gateway.IGatewayMessageHandler;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.events.OrderBookUpdateEvent;

import java.util.List;

@Singleton
public class CoinbaseMessageHandler implements IGatewayMessageHandler {

    private final MessageBus bus;
    private final CoinbaseMessageHandlerFactory messageHandlerFactory;

    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Inject
    public CoinbaseMessageHandler(MessageBus bus, CoinbaseMessageHandlerFactory messageHandlerFactory) {
        this.bus = bus;
        this.messageHandlerFactory = messageHandlerFactory;
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public void handleMessage(String message) {
        CoinbaseMessage coinbaseMessage = getCoinbaseMessage(message);
        if (coinbaseMessage == null) {
            return;
        }
        ICoinbaseMessageHandler messageHandler = this.messageHandlerFactory.getMessageHandler(coinbaseMessage.getType());
        if (messageHandler == null) {
            return;
        }
        CoinbaseMessage fullMessage = messageHandler.handleMessage(message);
        List<OrderBookEntry> entries = messageHandler.convert(fullMessage);
        this.bus.post(new OrderBookUpdateEvent(entries));
    }

    private CoinbaseMessage getCoinbaseMessage(String message) {
        try {
            return this.mapper.readValue(message, CoinbaseMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
