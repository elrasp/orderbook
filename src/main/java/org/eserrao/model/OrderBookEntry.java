package org.eserrao.model;

import org.checkerframework.checker.nullness.qual.NonNull;

import javax.annotation.Nonnull;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Objects;

public class OrderBookEntry implements Comparable<OrderBookEntry> {
    private static final Comparator<OrderBookEntry> ORDERBOOK_ENTRY_COMPARATOR = Comparator.comparing(OrderBookEntry::getPrice);
    private final String productId;
    private final SideType sideType;
    private final double price;
    private double size;
    private ZonedDateTime timestamp;

    public OrderBookEntry(String productId, SideType sideType, double price) {
        this.productId = productId;
        this.sideType = sideType;
        this.price = price;
    }

    public OrderBookEntry(OrderBookEntry entry) {
        this(entry.getProductId(), entry.getSideType(), entry.getPrice());
        this.size = entry.getSize();
        this.timestamp = entry.getTimestamp();
    }

    public String getProductId() {
        return productId;
    }

    public SideType getSideType() {
        return sideType;
    }

    public double getPrice() {
        return price;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(ZonedDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderBookEntry that = (OrderBookEntry) o;
        return Double.compare(that.price, price) == 0 && Objects.equals(productId, that.productId) && sideType == that.sideType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, sideType, price);
    }

    @Override
    public String toString() {
        return "OrderbookEntry{" +
                "productId='" + productId + '\'' +
                ", sideType=" + sideType +
                ", price=" + price +
                ", size=" + size +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public int compareTo(@Nonnull OrderBookEntry o) {
        return ORDERBOOK_ENTRY_COMPARATOR.compare(this, o);
    }

    public static Comparator<OrderBookEntry> comparator() {
        return ORDERBOOK_ENTRY_COMPARATOR;
    }
}
