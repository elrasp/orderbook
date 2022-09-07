package org.eserrao.coinbase.messages;

import org.eserrao.IMessageBus;
import org.eserrao.coinbase.messages.messageHandlers.ICoinbaseMessageHandler;
import org.eserrao.coinbase.messages.messageHandlers.L2UpdateMessageHandler;
import org.eserrao.coinbase.messages.messageHandlers.SnapshotMessageHandler;
import org.eserrao.coinbase.messages.model.CoinbaseMessage;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CoinbaseMessageHandlerFactoryTest {

    IMessageBus messageBus;

    @BeforeEach
    public void beforeEach() {
        this.messageBus = Mockito.mock(IMessageBus.class);
    }

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
        Map<String, ICoinbaseMessageHandler> messageHandlers = Stream.of(new L2UpdateMessageHandler(this.messageBus), new SnapshotMessageHandler(this.messageBus))
                .collect(Collectors.toMap(ICoinbaseMessageHandler::getType, Function.identity()));
        CoinbaseMessageHandlerFactory messageFactory = new CoinbaseMessageHandlerFactory(messageHandlers);
        MatcherAssert.assertThat(messageFactory.getMessageHandler(type), expectedClass == null ? Matchers.nullValue() : Matchers.instanceOf(expectedClass));
    }
}