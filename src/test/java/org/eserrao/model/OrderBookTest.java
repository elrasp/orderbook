package org.eserrao.model;

import org.eserrao.model.helpers.OrderBookEntryBuilder;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

class OrderBookTest {
    public static Stream<Arguments> getAskOrderEntriesTestCase() {
        return Stream.of(
                Arguments.of(5, new double[]{5, 7, 1}, new double[]{2, 5, 4}, 3, new double[]{1, 5, 7}),
                Arguments.of(5, new double[]{5, 7, 1, 7}, new double[]{2, 5, 4, 0}, 2, new double[]{1, 5}),
                Arguments.of(5, new double[]{7, 1, 9, 3, 2, 5}, new double[]{0.2, 4.3, 2.2, 0.34, 1.0, 0.00045}, 5, new double[]{1, 2, 3, 5, 7})
        );
    }

    @ParameterizedTest(name = "Max Size: {0}, Prices: {1}, Sizes: {2}, Expected Elements:{4}")
    @MethodSource("getAskOrderEntriesTestCase")
    public void shouldUpdateAsksWhenEntriesAreProvided(int maxSize, double[] prices, double[] sizes, int expectedSize, double[] expectedPrices) {
        String productId = "BTC-ETH";
        SideType ask = SideType.SELL;

        OrderBook orderBook = new OrderBook(maxSize);
        List<OrderBookEntry> entries = new ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            OrderBookEntry entry = new OrderBookEntryBuilder(productId, ask, prices[i])
                    .setSize(sizes[i])
                    .setTimestamp(ZonedDateTime.now())
                    .build();
            entries.add(entry);
        }
        orderBook.handleUpdates(entries);

        Assertions.assertAll(
                () -> MatcherAssert.assertThat(orderBook.getAsks().size(), is(expectedSize)), //
                () -> MatcherAssert.assertThat(orderBook.getAsks().stream().mapToDouble(OrderBookEntry::getPrice).toArray(), is(expectedPrices)), //
                () -> MatcherAssert.assertThat(orderBook.getBids(), is(empty()))
        );
    }

    public static Stream<Arguments> getBidOrderEntriesTestCase() {
        return Stream.of(
                Arguments.of(5, new double[]{5, 7, 1}, new double[]{2, 5, 4}, 3, new double[]{7, 5, 1}),
                Arguments.of(5, new double[]{5, 7, 1, 7}, new double[]{2, 5, 4, 0}, 2, new double[]{5, 1}),
                Arguments.of(5, new double[]{7, 1, 9, 3, 2, 5}, new double[]{0.2, 4.3, 2.2, 0.34, 1.0, 0.00045}, 5, new double[]{9, 7, 5, 3, 2})
        );
    }

    @ParameterizedTest(name = "Max Size: {0}, Prices: {1}, Sizes: {2}, Expected Elements:{4}")
    @MethodSource("getBidOrderEntriesTestCase")
    public void shouldUpdateBidsWhenEntriesAreProvided(int maxSize, double[] prices, double[] sizes, int expectedSize, double[] expectedPrices) {
        String productId = "BTC-ETH";
        SideType ask = SideType.BUY;

        OrderBook orderBook = new OrderBook(maxSize);
        List<OrderBookEntry> entries = new ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            OrderBookEntry entry = new OrderBookEntryBuilder(productId, ask, prices[i])
                    .setSize(sizes[i])
                    .setTimestamp(ZonedDateTime.now())
                    .build();
            entries.add(entry);
        }
        orderBook.handleUpdates(entries);

        Assertions.assertAll(
                () -> MatcherAssert.assertThat(orderBook.getBids().size(), is(expectedSize)), //
                () -> MatcherAssert.assertThat(orderBook.getBids().stream().mapToDouble(OrderBookEntry::getPrice).toArray(), is(expectedPrices)),
                () -> MatcherAssert.assertThat(orderBook.getAsks(), is(empty())) //
        );
    }
}