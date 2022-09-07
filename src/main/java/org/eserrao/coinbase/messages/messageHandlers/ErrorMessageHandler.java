package org.eserrao.coinbase.messages.messageHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.model.ErrorMessage;
import org.eserrao.model.events.ErrorEvent;

@Singleton
public class ErrorMessageHandler extends CoinbaseWebsocketMessageHandler<ErrorMessage> {
    public static final String TYPE = "error";

    @Inject
    public ErrorMessageHandler(IMessageBus bus) {
        super(bus);
        this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void handleMessage(String message) {
        try {
            ErrorMessage error = mapper.readValue(message, ErrorMessage.class);
            this.bus.post(new ErrorEvent(error.getMessage()));
        } catch (JsonProcessingException e) {
            System.out.println("Error Message received: " + message);
        }
    }
}
