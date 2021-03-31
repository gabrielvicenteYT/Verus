
package me.levansj01.verus.compat;

import java.util.function.Consumer;

public abstract class VPacket<P> implements Consumer<Object> {
    public P create() {
        throw new UnsupportedOperationException();
    }

    public void done() {
    }
}
