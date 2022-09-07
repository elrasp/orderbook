package org.eserrao.model;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class OrderBook {

    private final NavigableSet<OrderBookEntry> bids;
    private final NavigableSet<OrderBookEntry> asks;

    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = this.lock.readLock();
    private final Lock writeLock = this.lock.writeLock();
    private final int noOfLevels;

    public OrderBook(int noOfLevels) {
        this.noOfLevels = noOfLevels;
        this.bids = new TreeSet<>(OrderBookEntry.comparator().reversed());
        this.asks = new TreeSet<>(OrderBookEntry.comparator());
    }

    private boolean addEntry(OrderBookEntry entry) {
        return switch (entry.getSideType()) {
            case BUY -> this.addBid(entry);
            case SELL -> this.addAsk(entry);
        };
    }

    private boolean addBid(OrderBookEntry entry) {
        return this.bids.add(entry);
    }

    private boolean addAsk(OrderBookEntry entry) {
        return this.asks.add(entry);
    }

    private boolean removeEntry(OrderBookEntry entry) {
        return switch (entry.getSideType()) {
            case BUY -> this.removeBid(entry);
            case SELL -> this.removeAsk(entry);
        };
    }

    private boolean removeBid(OrderBookEntry entry) {
        return this.bids.remove(entry);
    }

    private boolean removeAsk(OrderBookEntry entry) {
        return this.asks.remove(entry);
    }

    public boolean handleUpdates(List<OrderBookEntry> orderBookEntries) {
        boolean isUpdated = false;
        if (this.writeLock.tryLock()) {
            try {
                for (OrderBookEntry orderBookEntry : orderBookEntries) {
                    if (0.0d == orderBookEntry.getSize()) {
                        isUpdated |= this.removeEntry(orderBookEntry);
                    } else {
                        isUpdated |= this.addEntry(orderBookEntry);
                    }
                }
            } finally {
                this.writeLock.unlock();
            }
        }
        return isUpdated;
    }

    public List<OrderBookEntry> getAsks() {
        return this.getOrderBookEntries(this.asks);
    }

    public List<OrderBookEntry> getBids() {
        return this.getOrderBookEntries(this.bids);
    }

    private List<OrderBookEntry> getOrderBookEntries(NavigableSet<OrderBookEntry> treeSet) {
        if (this.readLock.tryLock()) {
            try {
                Iterator<OrderBookEntry> iterator = treeSet.iterator();
                List<OrderBookEntry> entries = new ArrayList<>();
                for (int i = 0; i < this.noOfLevels; i++) {
                    if (iterator.hasNext()) {
                        entries.add(iterator.next());
                    }
                }
                return entries;
            } finally {
                this.readLock.unlock();
            }
        }
        return Collections.emptyList();
    }

    public void printOrderBook() {

    }
}
