/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package me.levansj01.verus.util.java;

import java.util.Arrays;
import java.util.Queue;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class JavaV {
    public static <T> T firstNonNull(@Nullable T t, @Nullable T t2) {
        return t != null ? t : t2;
    }

    public static <T> Queue<T> trim(Queue<T> queue, int n) {
        for (int i = queue.size(); i > n; --i) {
            queue.poll();
        }
        return queue;
    }

    public static <T> Stream<T> stream(T ... arrT) {
        return Arrays.stream(arrT);
    }
}

