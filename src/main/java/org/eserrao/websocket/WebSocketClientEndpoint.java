package org.eserrao.websocket;


import jakarta.websocket.*;
import org.eserrao.gateway.IGatewayMessageHandler;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public class WebSocketClientEndpoint {

    private final URI endpointURI;
    private final IGatewayMessageHandler messageHandler;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private Session userSession;

    public WebSocketClientEndpoint(URI endpointURI, IGatewayMessageHandler messageHandler) {
        this.endpointURI = endpointURI;
        this.messageHandler = messageHandler;
    }

    public void connect() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.userSession = container.connectToServer(this, endpointURI);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        if (this.userSession != null) {
            this.executor.shutdown();
            try {
                this.executor.awaitTermination(1L, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                this.executor.shutdownNow();
            }

            try {
                System.out.println("Closing WebSocket session");
                this.userSession.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnOpen
    public void onOpen(Session userSession) {
        // Callback to be called only once upon a new connection
        System.out.println("opening WebSocket");
        this.userSession = userSession;
    }

    @OnClose
    public void onClose(Session userSession, CloseReason reason) {
        System.out.println("onClose: " + reason.toString());
        this.userSession = null;
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        this.executor.execute(() -> this.messageHandler.handleMessage(message));
    }

    @OnError
    public void onError(Session session, Throwable ex) {
        System.err.printf("WebSocket error => '%s' => '%s'", session, ex.getMessage());
    }

    public void sendMessage(String message) {
        this.userSession.getAsyncRemote().sendText(message);
    }
}
