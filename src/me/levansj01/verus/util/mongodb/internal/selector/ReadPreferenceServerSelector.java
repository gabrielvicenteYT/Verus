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

package me.levansj01.verus.util.mongodb.internal.selector;

import static me.levansj01.verus.util.mongodb.assertions.Assertions.notNull;
import static me.levansj01.verus.util.mongodb.internal.connection.ClusterDescriptionHelper.getAny;

import java.util.List;

import me.levansj01.verus.util.mongodb.ReadPreference;
import me.levansj01.verus.util.mongodb.connection.ClusterConnectionMode;
import me.levansj01.verus.util.mongodb.connection.ClusterDescription;
import me.levansj01.verus.util.mongodb.connection.ServerDescription;
import me.levansj01.verus.util.mongodb.selector.ServerSelector;

/**
 * A server selector that chooses based on a read preference.
 *
 * @since 3.0
 */
public class ReadPreferenceServerSelector implements ServerSelector {
    private final ReadPreference readPreference;

    /**
     * Gets the read preference.
     *
     * @param readPreference the read preference
     */
    public ReadPreferenceServerSelector(final ReadPreference readPreference) {
        this.readPreference = notNull("readPreference", readPreference);
    }

    /**
     * Gets the read preference.
     *
     * @return the read preference
     */
    public ReadPreference getReadPreference() {
        return readPreference;
    }

    @Override
    public List<ServerDescription> select(final ClusterDescription clusterDescription) {
        if (clusterDescription.getConnectionMode() == ClusterConnectionMode.SINGLE) {
            return getAny(clusterDescription);
        }
        return readPreference.choose(clusterDescription);
    }

    @Override
    public String toString() {
        return "ReadPreferenceServerSelector{"
               + "readPreference=" + readPreference
               + '}';
    }
}
