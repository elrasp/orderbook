package org.eserrao.coinbase.messages.model;

public class ErrorMessage extends CoinbaseMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
