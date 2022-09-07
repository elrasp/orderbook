package org.eserrao.model;

import org.eserrao.model.helpers.OrderBookEntryBuilder;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

class OrderBookEntryBuilderTest {

    public static Stream<Arguments> builderTestCase() {
        return Stream.of(
                Arguments.of("BTC-ETH", SideType.BUY, 2.5, 0.4566), //
                Arguments.of("BTC-ETH", SideType.SELL, 2.5, 0.4566), //
                Arguments.of("BTC-ADA", SideType.BUY, 24.3435, 10.3465456), //
                Arguments.of("BTC-USD", SideType.BUY, 19000.2345, 0.0034566)
        );
    }

    @ParameterizedTest(name = "ProductId: {0}, Side: {1}, Price: {2}, Size: {3}")
    @MethodSource("builderTestCase")
    public void shouldBuildOrderbookEntryUsingBuilder(String productId, SideType sideType, double price, double size) {
        OrderBookEntry entry = new OrderBookEntryBuilder(productId, sideType, price) //
                .setSize(size) //
                .setTimestamp(ZonedDateTime.now()) //
                .build();
        Assertions.assertAll(
                () -> MatcherAssert.assertThat(entry.getProductId(), Matchers.is(productId)), //
                () -> MatcherAssert.assertThat(entry.getSideType(), Matchers.is(sideType)), //
                () -> MatcherAssert.assertThat(entry.getPrice(), Matchers.is(price)), //
                () -> MatcherAssert.assertThat(entry.getSize(), Matchers.is(size)) //
        );
    }

}