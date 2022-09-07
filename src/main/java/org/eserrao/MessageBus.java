package org.eserrao;

import com.google.common.eventbus.AsyncEventBus;
import com.google.inject.Singleton;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Singleton
public class MessageBus {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final AsyncEventBus eventBus = new AsyncEventBus(executor);

    public void register(Object eventListener) {
        this.eventBus.register(eventListener);
    }

    public void unregister(Object eventListener) {
        this.eventBus.unregister(eventListener);
    }

    public void post(Object event) {
        this.eventBus.post(event);
    }

    public void stop() {
        this.executor.shutdown();
        try {
            this.executor.awaitTermination(1L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            this.executor.shutdownNow();
        }
    }
}
