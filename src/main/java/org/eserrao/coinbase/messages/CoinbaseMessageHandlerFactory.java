package org.eserrao.coinbase.messages;

import com.google.inject.Singleton;
import org.eserrao.coinbase.messages.messageHandlers.CoinbaseWebsocketMessageHandler;

import javax.inject.Inject;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Singleton
public class CoinbaseMessageHandlerFactory {

    private final Map<String, CoinbaseWebsocketMessageHandler> messageHandlers;

    @Inject
    public CoinbaseMessageHandlerFactory(Map<String, CoinbaseWebsocketMessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public CoinbaseWebsocketMessageHandler getMessageHandler(String type) {
        return this.messageHandlers.get(type);
    }
}
