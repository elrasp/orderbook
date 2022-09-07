package org.eserrao;

public interface IMessageBus {
    void register(Object eventListener);

    void unregister(Object eventListener);

    void post(Object event);

    void stop();
}
