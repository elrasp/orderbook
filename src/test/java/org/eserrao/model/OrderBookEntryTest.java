package org.eserrao.model;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class OrderBookEntryTest {
    @Test
    public void shouldBeEqualEntriesWhenKeyIsIdentical() {
        OrderBookEntry entry1 = new OrderBookEntry("BTC-ETH", SideType.BUY, 2.0);
        OrderBookEntry entry2 = new OrderBookEntry("BTC-ETH", SideType.BUY, 2.0);
        assertEquals(entry1, entry2);
    }

    public static Stream<Arguments> equalityTestCase() {
        return Stream.of(
                Arguments.of("Product Id is different", "BTC-ADA", SideType.BUY, 2.0), //
                Arguments.of("Side is different", "BTC-ETH", SideType.SELL, 2.0), //
                Arguments.of("Price is different", "BTC-ETH", SideType.BUY, 1.0)
        );
    }

    @ParameterizedTest(name = "Scenario: {0}")
    @MethodSource("equalityTestCase")
    public void shouldNotBeEqualEntriesWhenKeyIsDifferent(String scenario, String productId, SideType sideType, double price) {
        OrderBookEntry entry1 = new OrderBookEntry("BTC-ETH", SideType.BUY, 2.0);
        OrderBookEntry entry2 = new OrderBookEntry(productId, sideType, price);
        assertNotEquals(entry1, entry2);
    }

    public static Stream<Arguments> comparatorTestCase() {
        return Stream.of(
                Arguments.of("Prices are equal", 2.0, 2.0, 0), //
                Arguments.of("Price 1 < Price 2", 1.0, 2.0, -1), //
                Arguments.of("Price 1 > Price 2", 2.0, 1.0, 1)
        );
    }

    @ParameterizedTest(name = "Scenario: {0}, Price1: {1}, Price2: {2}")
    @MethodSource("comparatorTestCase")
    public void testComparator(String scenario, double price1, double price2, int expectedResult){
        OrderBookEntry entry1 = new OrderBookEntry("BTC-ETH", SideType.BUY, price1);
        OrderBookEntry entry2 = new OrderBookEntry("BTC-ETH", SideType.BUY, price2);
        MatcherAssert.assertThat(entry1.compareTo(entry2), Matchers.is(expectedResult));
    }

}