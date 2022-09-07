package org.eserrao.gateway;

public interface IGateway {
    void connect();
    void disconnect();
    void subscribe(String productId);
}
