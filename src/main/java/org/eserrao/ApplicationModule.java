package org.eserrao;

import com.google.inject.AbstractModule;
import org.eserrao.coinbase.CoinbaseGateway;
import org.eserrao.coinbase.CoinbaseMessageHandler;
import org.eserrao.gateway.IGateway;
import org.eserrao.gateway.IGatewayMessageHandler;

public class ApplicationModule extends AbstractModule {
    @Override
    protected void configure() {
        this.bind(MessageBus.class);
        this.bind(Application.class);
        this.bind(IGateway.class).to(CoinbaseGateway.class);
        this.bind(IGatewayMessageHandler.class).to(CoinbaseMessageHandler.class);
    }
}
