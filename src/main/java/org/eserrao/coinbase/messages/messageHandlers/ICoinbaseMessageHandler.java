package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.coinbase.messages.model.CoinbaseMessage;

public interface ICoinbaseMessageHandler<T extends CoinbaseMessage> {
    String getType();

    void handleMessage(String message);

}
