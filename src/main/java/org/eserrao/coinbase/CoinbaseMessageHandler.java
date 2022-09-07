package org.eserrao.coinbase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eserrao.coinbase.messages.CoinbaseMessageHandlerFactory;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;
import org.eserrao.gateway.IGatewayMessageHandler;

@Singleton
public class CoinbaseMessageHandler implements IGatewayMessageHandler {

    private final CoinbaseMessageHandlerFactory messageHandlerFactory;

    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Inject
    public CoinbaseMessageHandler(CoinbaseMessageHandlerFactory messageHandlerFactory) {
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
        org.eserrao.coinbase.messages.messageHandlers.CoinbaseMessageHandler messageHandler = this.messageHandlerFactory.getMessageHandler(coinbaseMessage.getType());
        if (messageHandler == null) {
            return;
        }
        messageHandler.handleMessage(message);
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
