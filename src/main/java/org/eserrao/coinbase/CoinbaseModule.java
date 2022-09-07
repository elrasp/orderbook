package org.eserrao.coinbase;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.MapBinder;
import org.eserrao.coinbase.messages.CoinbaseMessageHandlerFactory;
import org.eserrao.coinbase.messages.messageHandlers.CoinbaseMessageHandler;
import org.eserrao.coinbase.messages.messageHandlers.ErrorMessageHandler;
import org.eserrao.coinbase.messages.messageHandlers.L2UpdateMessageHandler;
import org.eserrao.coinbase.messages.messageHandlers.SnapshotMessageHandler;

public class CoinbaseModule extends AbstractModule {
    @SuppressWarnings("rawtypes")
    @Override
    protected void configure() {
        MapBinder<String, CoinbaseMessageHandler> messageHandlerBinder = MapBinder.newMapBinder(this.binder(), String.class, CoinbaseMessageHandler.class);
        messageHandlerBinder.addBinding(SnapshotMessageHandler.TYPE).to(SnapshotMessageHandler.class);
        messageHandlerBinder.addBinding(L2UpdateMessageHandler.TYPE).to(L2UpdateMessageHandler.class);
        messageHandlerBinder.addBinding(ErrorMessageHandler.TYPE).to(ErrorMessageHandler.class);

        this.bind(CoinbaseMessageHandlerFactory.class);
    }
}
