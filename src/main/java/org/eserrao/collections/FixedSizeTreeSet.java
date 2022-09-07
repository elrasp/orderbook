package org.eserrao.collections;

import java.util.Comparator;
import java.util.TreeSet;

public class FixedSizeTreeSet<T> extends TreeSet<T> {
    final private int maxSize;

    public FixedSizeTreeSet(Comparator<? super T> comparator, int maxSize) {
        super(comparator);
        this.maxSize = maxSize;
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    @Override
    public boolean add(T t) {
        if (this.size() == this.maxSize) {
            T last = this.last();
            if (this.comparator() != null && this.comparator().compare(t, last) < 0) {
                this.pollLast();
                return super.add(t);
            }
            return false;
        }
        return super.add(t);
    }
}
