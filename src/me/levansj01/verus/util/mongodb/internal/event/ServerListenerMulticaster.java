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

package me.levansj01.verus.util.mongodb.internal.event;

import java.util.ArrayList;
import java.util.List;

import me.levansj01.verus.util.mongodb.diagnostics.logging.Logger;
import me.levansj01.verus.util.mongodb.diagnostics.logging.Loggers;
import me.levansj01.verus.util.mongodb.event.ServerClosedEvent;
import me.levansj01.verus.util.mongodb.event.ServerDescriptionChangedEvent;
import me.levansj01.verus.util.mongodb.event.ServerListener;
import me.levansj01.verus.util.mongodb.event.ServerOpeningEvent;

import static java.lang.String.format;
import static me.levansj01.verus.util.mongodb.assertions.Assertions.isTrue;


final class ServerListenerMulticaster implements ServerListener {

    private static final Logger LOGGER = Loggers.getLogger("cluster.event");

    private final List<ServerListener> serverListeners;

    ServerListenerMulticaster(final List<ServerListener> serverListeners) {
        isTrue("All ServerListener instances are non-null", !serverListeners.contains(null));
        this.serverListeners = new ArrayList<ServerListener>(serverListeners);
    }

    @Override
    public void serverOpening(final ServerOpeningEvent event) {
        for (ServerListener cur : serverListeners) {
            try {
                cur.serverOpening(event);
            } catch (Exception e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(format("Exception thrown raising server opening event to listener %s", cur), e);
                }
            }
        }
    }

    @Override
    public void serverClosed(final ServerClosedEvent event) {
        for (ServerListener cur : serverListeners) {
            try {
                cur.serverClosed(event);
            } catch (Exception e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(format("Exception thrown raising server opening event to listener %s", cur), e);
                }
            }
        }
    }

    @Override
    public void serverDescriptionChanged(final ServerDescriptionChangedEvent event) {
        for (ServerListener cur : serverListeners) {
            try {
                cur.serverDescriptionChanged(event);
            } catch (Exception e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(format("Exception thrown raising server description changed event to listener %s", cur), e);
                }
            }
        }
    }
}
