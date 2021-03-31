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

package me.levansj01.verus.util.mongodb.management;

import java.util.concurrent.atomic.AtomicInteger;

import me.levansj01.verus.util.mongodb.ServerAddress;
import me.levansj01.verus.util.mongodb.connection.ConnectionPoolSettings;
import me.levansj01.verus.util.mongodb.event.ConnectionCheckedInEvent;
import me.levansj01.verus.util.mongodb.event.ConnectionCheckedOutEvent;
import me.levansj01.verus.util.mongodb.event.ConnectionClosedEvent;
import me.levansj01.verus.util.mongodb.event.ConnectionCreatedEvent;
import me.levansj01.verus.util.mongodb.event.ConnectionPoolCreatedEvent;
import me.levansj01.verus.util.mongodb.event.ConnectionPoolListener;

/**
 * An MBean implementation for connection pool statistics.
 */
final class ConnectionPoolStatistics implements ConnectionPoolListener, ConnectionPoolStatisticsMBean {
    private final ServerAddress serverAddress;
    private final ConnectionPoolSettings settings;
    private final AtomicInteger size = new AtomicInteger();
    private final AtomicInteger checkedOutCount = new AtomicInteger();

    ConnectionPoolStatistics(final ConnectionPoolCreatedEvent event) {
        serverAddress = event.getServerId().getAddress();
        settings = event.getSettings();
    }

    @Override
    public String getHost() {
        return serverAddress.getHost();
    }

    @Override
    public int getPort() {
        return serverAddress.getPort();
    }

    @Override
    public int getMinSize() {
        return settings.getMinSize();
    }

    @Override
    public int getMaxSize() {
        return settings.getMaxSize();
    }

    @Override
    public int getSize() {
        return size.get();
    }

    @Override
    public int getCheckedOutCount() {
        return checkedOutCount.get();
    }

    @Override
    public void connectionCheckedOut(final ConnectionCheckedOutEvent event) {
        checkedOutCount.incrementAndGet();
    }

    @Override
    public void connectionCheckedIn(final ConnectionCheckedInEvent event) {
        checkedOutCount.decrementAndGet();
    }

    @Override
    public void connectionCreated(final ConnectionCreatedEvent event) {
        size.incrementAndGet();
    }

    @Override
    public void connectionClosed(final ConnectionClosedEvent event) {
        size.decrementAndGet();
    }

}
