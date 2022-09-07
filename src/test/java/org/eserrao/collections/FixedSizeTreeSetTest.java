package org.eserrao.collections;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Comparator;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class FixedSizeTreeSetTest {
    @Test
    public void shouldReturnTheMaxSizeWhenInitialized() {
        int maxSize = 5;
        FixedSizeTreeSet<Double> treeSet = new FixedSizeTreeSet<>(Comparator.naturalOrder(), maxSize);
        assertThat(treeSet.getMaxSize(), is(maxSize));
    }

    public static Stream<Arguments> getAddElementsTestParameters() {
        return Stream.of(
                Arguments.of(5, Comparator.naturalOrder(), new int[]{5, 7, 1}, 3, new int[]{1, 5, 7}),
                Arguments.of(5, Comparator.naturalOrder().reversed(), new int[]{5, 7, 1}, 3, new int[]{7, 5, 1}),
                Arguments.of(5, Comparator.naturalOrder(), new int[]{7, 1, 9, 3, 2, 5}, 5, new int[]{1, 2, 3, 5, 7}),
                Arguments.of(5, Comparator.naturalOrder().reversed(), new int[]{7, 1, 9, 3, 2, 5}, 5, new int[]{9, 7, 5, 3, 2})
        );
    }

    @ParameterizedTest(name = "Max Size: {0}, Elements Added: {2}, Expected Size: {3}, Expected Elements:{4}")
    @MethodSource("getAddElementsTestParameters")
    public void shouldUpdateTreeSetWhenNewElementsAreAdded(int maxSize, Comparator<Integer> c, int[] elementsToAdd, int expectedSize, int[] expectedElements) {
        FixedSizeTreeSet<Integer> treeSet = new FixedSizeTreeSet<>(c, maxSize);
        for (int element : elementsToAdd) {
            treeSet.add(element);
        }
        Assertions.assertAll(
                () -> assertThat(treeSet.size(), is(expectedSize)), //
                () -> assertThat(treeSet.toArray(new Integer[0]), is(expectedElements))
        );
    }
}