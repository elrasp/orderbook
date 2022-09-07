package org.eserrao.coinbase.messages;

import com.google.inject.Singleton;
import org.eserrao.coinbase.messages.messageHandlers.ICoinbaseMessageHandler;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;

import javax.inject.Inject;
import java.util.Map;

@SuppressWarnings("rawtypes")
@Singleton
public class CoinbaseMessageHandlerFactory {

    private final Map<String, ICoinbaseMessageHandler> messageHandlers;

    @Inject
    public CoinbaseMessageHandlerFactory(Map<String, ICoinbaseMessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers;
    }

    public ICoinbaseMessageHandler getMessageHandler(String type) {
        return this.messageHandlers.get(type);
    }
}
