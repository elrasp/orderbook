package org.eserrao.coinbase.messages.messageHandlers;

import org.eserrao.coinbase.messages.model.CoinbaseMessage;
import org.eserrao.model.OrderBookEntry;

import java.util.List;

public interface ICoinbaseMessageHandler<T extends CoinbaseMessage> {
    String getType();

    T handleMessage(String message);

    List<OrderBookEntry> convert(T message);
}
