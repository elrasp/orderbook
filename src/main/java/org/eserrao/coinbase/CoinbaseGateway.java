package org.eserrao.coinbase;

import com.google.inject.Singleton;
import org.eserrao.gateway.IGateway;
import org.eserrao.gateway.IGatewayMessageHandler;
import org.eserrao.websocket.WebSocketClientEndpoint;

import javax.inject.Inject;
import java.net.URI;

@Singleton
public class CoinbaseGateway implements IGateway {

    private static final String COINBASE_WSS = "wss://ws-feed.exchange.coinbase.com";
    private static final URI COINBASE_URI = URI.create(COINBASE_WSS);
    private static final String SUBSCRIPTION_MESSAGE = "{\"type\": \"subscribe\",\"product_ids\": [\"%s\"],\"channels\": [\"level2_batch\"]}";
    private final WebSocketClientEndpoint websocketClient;

    @Inject
    public CoinbaseGateway(IGatewayMessageHandler messageHandler) {
        this.websocketClient = new WebSocketClientEndpoint(COINBASE_URI, messageHandler);
    }

    @Override
    public void connect() {
        this.websocketClient.connect();
    }

    @Override
    public void disconnect() {
        this.websocketClient.disconnect();
    }

    @Override
    public void subscribe(String productId) {
        String message  = String.format(SUBSCRIPTION_MESSAGE, productId);
        this.websocketClient.sendMessage(message);
    }

}
