/*
 * Copyright 2008-present MongoDB, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.levansj01.verus.util.mongodb.connection.netty;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;
import me.levansj01.verus.util.mongodb.connection.BufferProvider;

import org.bson.ByteBuf;

final class NettyBufferProvider implements BufferProvider {

    private final ByteBufAllocator allocator;

    NettyBufferProvider() {
        allocator = PooledByteBufAllocator.DEFAULT;
    }

    NettyBufferProvider(final ByteBufAllocator allocator) {
        this.allocator = allocator;
    }

    @Override
    public ByteBuf getBuffer(final int size) {
        return new NettyByteBuf(allocator.directBuffer(size, size));
    }
}
