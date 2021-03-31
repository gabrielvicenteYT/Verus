/*
 * Decompiled with CFR 0.150.
 */
package me.levansj01.verus.util.java;

import java.util.function.Supplier;

public class CachedSupplier<T> implements Supplier<T> {
    private T instance;
    private Supplier<T> supplier;

    public void setInstance(T t) {
        this.instance = t;
    }

    @Override
    public T get() {
        if (this.supplier != null) {
            this.instance = this.supplier.get();
            this.supplier = null;
        }
        return this.instance;
    }

    public void setSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public static <T> CachedSupplier<T> of(Supplier<T> supplier) {
        CachedSupplier cachedSupplier = new CachedSupplier();
        cachedSupplier.setSupplier(supplier);
        return cachedSupplier;
    }

    public T getInstance() {
        return this.instance;
    }

    public Supplier<T> getSupplier() {
        return this.supplier;
    }
}

