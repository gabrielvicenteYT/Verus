package me.levansj01.verus.util.java;

import java.util.Iterator;
import java.util.function.Function;

public class WrappingIterator implements Iterator {
    private final Function converter;
    private final Iterator iterator;

    public WrappingIterator(Iterator iterator, Function function) {
        this.iterator = iterator;
        this.converter = function;
    }

    @Override
    public boolean hasNext() {
        return this.iterator.hasNext();
    }

    public Object next() {
        Object e = this.iterator.next();
        return e == null ? null : this.converter.apply(e);
    }
}

