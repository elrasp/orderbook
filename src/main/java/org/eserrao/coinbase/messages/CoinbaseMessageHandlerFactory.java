package org.eserrao.coinbase.messages;

import com.google.inject.Singleton;
import org.eserrao.coinbase.messages.messageHandlers.CoinbaseMessageHandler;

import javax.inject.Inject;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Singleton
public class CoinbaseMessageHandlerFactory {

    private final Map<String, CoinbaseMessageHandler> messageHandlers;

    @Inject
    public CoinbaseMessageHandlerFactory(Map<String, CoinbaseMessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public CoinbaseMessageHandler getMessageHandler(String type) {
        return this.messageHandlers.get(type);
    }
}
