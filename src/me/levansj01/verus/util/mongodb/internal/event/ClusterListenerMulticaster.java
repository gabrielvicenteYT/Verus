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
import me.levansj01.verus.util.mongodb.event.ClusterClosedEvent;
import me.levansj01.verus.util.mongodb.event.ClusterDescriptionChangedEvent;
import me.levansj01.verus.util.mongodb.event.ClusterListener;
import me.levansj01.verus.util.mongodb.event.ClusterOpeningEvent;

import static java.lang.String.format;
import static me.levansj01.verus.util.mongodb.assertions.Assertions.isTrue;


final class ClusterListenerMulticaster implements ClusterListener {
    private static final Logger LOGGER = Loggers.getLogger("cluster.event");

    private final List<ClusterListener> clusterListeners;

    ClusterListenerMulticaster(final List<ClusterListener> clusterListeners) {
        isTrue("All ClusterListener instances are non-null", !clusterListeners.contains(null));
        this.clusterListeners = new ArrayList<ClusterListener>(clusterListeners);
    }

    @Override
    public void clusterOpening(final ClusterOpeningEvent event) {
        for (final ClusterListener cur : clusterListeners) {
            try {
                cur.clusterOpening(event);
            } catch (Exception e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(format("Exception thrown raising cluster opening event to listener %s", cur), e);
                }
            }
        }
    }

    @Override
    public void clusterClosed(final ClusterClosedEvent event) {
        for (final ClusterListener cur : clusterListeners) {
            try {
                cur.clusterClosed(event);
            } catch (Exception e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(format("Exception thrown raising cluster closed event to listener %s", cur), e);
                }

            }
        }
    }

    @Override
    public void clusterDescriptionChanged(final ClusterDescriptionChangedEvent event) {
        for (final ClusterListener cur : clusterListeners) {
            try {
                cur.clusterDescriptionChanged(event);
            } catch (Exception e) {
                if (LOGGER.isWarnEnabled()) {
                    LOGGER.warn(format("Exception thrown raising cluster description changed event to listener %s", cur), e);
                }
            }
        }
    }
}
