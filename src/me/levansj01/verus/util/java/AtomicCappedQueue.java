package me.levansj01.verus.util.java;

import java.util.Iterator;
import java.util.Spliterators;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class AtomicCappedQueue<T> implements Iterable<T> {
    private final AtomicReferenceArray<T> internal;
    private final int length;
    private final AtomicInteger startIndex;
    private final AtomicInteger lastIndex = new AtomicInteger(0);

    public void addFirst(T t) {
        Object t2 = this.internal.getAndSet(AtomicCappedQueue.modulo(this.startIndex.decrementAndGet(), this.length), t);
        if (t2 != null) {
            this.lastIndex.decrementAndGet();
        }
    }

    public AtomicCappedQueue(int n) {
        this.startIndex = new AtomicInteger(0);
        if (n <= 1) {
            throw new IllegalArgumentException("Cannot have length of " + n);
        }
        this.length = n;
        this.internal = new AtomicReferenceArray(n);
    }

    private static int modulo(int n, int n2) {
        int n3 = n % n2;
        if (n3 < 0) {
            n3 += n2;
        }
        return n3;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator(){
            private int current;
            {
                this.current = AtomicCappedQueue.this.startIndex.get();
            }

            @Override
            public boolean hasNext() {
                return this.current < AtomicCappedQueue.this.lastIndex.get();
            }

            public Object next() {
                return AtomicCappedQueue.this.internal.get(AtomicCappedQueue.modulo(this.current++, AtomicCappedQueue.this.length));
            }
        };
    }

    public void clear() {
        for (int i = 0; i < this.length; ++i) {
            this.internal.set(i, null);
        }
        this.lastIndex.set(this.startIndex.get());
    }

    public T pollFirst() {
        return this.internal.getAndSet(AtomicCappedQueue.modulo(this.startIndex.getAndIncrement(), this.length), null);
    }

    public T pollLast() {
        return this.internal.getAndSet(AtomicCappedQueue.modulo(this.lastIndex.decrementAndGet(), this.length), null);
    }

    public int size() {
        return this.lastIndex.get() - this.startIndex.get();
    }

    public T peekFirst() {
        return this.internal.get(AtomicCappedQueue.modulo(this.startIndex.get(), this.length));
    }

    public T peekLast() {
        return this.internal.get(AtomicCappedQueue.modulo(this.lastIndex.get() - 1, this.length));
    }

    public Stream<T> stream() {
        return StreamSupport.stream(Spliterators.spliterator(this.iterator(), this.size(), 16), false);
    }

    public void addLast(T t) {
        Object t2 = this.internal.getAndSet(AtomicCappedQueue.modulo(this.lastIndex.getAndIncrement(), this.length), t);
        if (t2 != null) {
            this.startIndex.incrementAndGet();
        }
    }
}

