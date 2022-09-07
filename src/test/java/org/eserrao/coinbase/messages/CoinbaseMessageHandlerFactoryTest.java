package org.eserrao.coinbase.messages;

import jdk.jfr.MetadataDefinition;
import org.eserrao.coinbase.messages.messageHandlers.ICoinbaseMessageHandler;
import org.eserrao.coinbase.messages.messageHandlers.L2UpdateMessageHandler;
import org.eserrao.coinbase.messages.messageHandlers.SnapshotMessageHandler;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;
import org.eserrao.coinbase.messages.model.Level2ChannelMessage;
import org.eserrao.coinbase.messages.model.SnapshotMessage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CoinbaseMessageHandlerFactoryTest {

    public static Stream<Arguments> factoryTestCase() {
        return Stream.of(
                Arguments.of("snapshot", SnapshotMessageHandler.class),
                Arguments.of("l2update", L2UpdateMessageHandler.class),
                Arguments.of("error", null)
        );
    }

    @ParameterizedTest(name = "type: {0}")
    @MethodSource("factoryTestCase")
    public void shouldReturnMessageHandlerForCorrespondingType(String type, Class<? extends CoinbaseMessage> expectedClass) {
        Map<String, ICoinbaseMessageHandler> messageHandlers = Stream.of(new L2UpdateMessageHandler(), new SnapshotMessageHandler())
                .collect(Collectors.toMap(ICoinbaseMessageHandler::getType, Function.identity()));
        CoinbaseMessageHandlerFactory messageFactory = new CoinbaseMessageHandlerFactory(messageHandlers);
        MatcherAssert.assertThat(messageFactory.getMessageHandler(type), expectedClass == null ? Matchers.nullValue() : Matchers.instanceOf(expectedClass));
    }
}