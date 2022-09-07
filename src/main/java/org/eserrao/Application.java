package org.eserrao;

import com.google.common.eventbus.Subscribe;
import com.google.inject.Singleton;
import org.eserrao.gateway.IGateway;
import org.eserrao.model.OrderBook;
import org.eserrao.model.events.OrderBookUpdateEvent;

import javax.inject.Inject;

@Singleton
public class Application {
    private final MessageBus bus;
    private final IGateway gateway;
    private final OrderBook orderBook = new OrderBook(10);

    @Inject
    public Application(MessageBus bus, IGateway gateway) {
        this.bus = bus;
        this.gateway = gateway;
    }

    public void start() {
        this.bus.register(this);
        this.gateway.connect();
    }

    public void subscribe(String productId) {
        this.gateway.subscribe(productId);
    }

    public void stop() {
        this.bus.unregister(this);
        this.bus.stop();
        this.gateway.disconnect();
        System.exit(0);
    }

    @Subscribe
    public void onOrderBookUpdateEvent(OrderBookUpdateEvent event) {
        boolean isOrderBookUpdated = this.orderBook.handleUpdates(event.getEntries());
        if (isOrderBookUpdated) {
            this.orderBook.printOrderBook();
        }
    }
}
