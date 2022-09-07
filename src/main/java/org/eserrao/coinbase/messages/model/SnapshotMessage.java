package org.eserrao.coinbase.messages.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.eserrao.coinbase.messages.model.deserializers.SnapshotDeserializer;

import java.util.List;

public class SnapshotMessage extends Level2ChannelMessage {
    @JsonDeserialize(using = SnapshotDeserializer.class)
    private List<Snapshot> bids;
    @JsonDeserialize(using = SnapshotDeserializer.class)
    private List<Snapshot> asks;

    public List<Snapshot> getBids() {
        return bids;
    }

    public void setBids(List<Snapshot> bids) {
        this.bids = bids;
    }

    public List<Snapshot> getAsks() {
        return asks;
    }

    public void setAsks(List<Snapshot> asks) {
        this.asks = asks;
    }
}
