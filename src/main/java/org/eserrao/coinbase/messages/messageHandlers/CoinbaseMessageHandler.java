package org.eserrao.coinbase.messages.messageHandlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;

public abstract class CoinbaseMessageHandler<T extends CoinbaseMessage> {
    protected final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();
    protected final IMessageBus bus;

    public CoinbaseMessageHandler(IMessageBus bus) {
        this.bus = bus;
    }

    public abstract String getType();

    public abstract void handleMessage(String message);

}
