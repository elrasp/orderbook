package org.eserrao.coinbase.messages.messageHandlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.google.inject.Singleton;
import org.eserrao.coinbase.messages.model.L2UpdateMessage;
import org.eserrao.model.OrderBookEntry;
import org.eserrao.model.helpers.OrderBookEntryBuilder;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
public class L2UpdateMessageHandler implements ICoinbaseMessageHandler<L2UpdateMessage> {

    public static final String TYPE = "l2update";
    private final ObjectMapper mapper = JsonMapper.builder()
            .findAndAddModules()
            .build();

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public L2UpdateMessage handleMessage(String message) {
        try {
            return mapper.readValue(message, L2UpdateMessage.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<OrderBookEntry> convert(L2UpdateMessage message) {
        if (message == null) {
            return Collections.emptyList();
        }
        return message.getChanges()
                .stream()
                .map(change -> new OrderBookEntryBuilder(message.getProductId(), change.sideType(), change.price())
                        .setSize(change.size())
                        .setTimestamp(message.getTime()).build())
                .collect(Collectors.toList());
    }
}
