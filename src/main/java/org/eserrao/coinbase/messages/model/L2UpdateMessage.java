package org.eserrao.coinbase.messages.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.eserrao.coinbase.messages.model.deserializers.L2UpdateDeserializer;

import java.time.ZonedDateTime;
import java.util.List;

public class L2UpdateMessage extends Level2ChannelMessage {
    private ZonedDateTime time;
    @JsonDeserialize(using = L2UpdateDeserializer.class)
    private List<L2Update> changes;

    public ZonedDateTime getTime() {
        return time;
    }

    public void setTime(ZonedDateTime time) {
        this.time = time;
    }

    public List<L2Update> getChanges() {
        return changes;
    }

    public void setChanges(List<L2Update> changes) {
        this.changes = changes;
    }
}
