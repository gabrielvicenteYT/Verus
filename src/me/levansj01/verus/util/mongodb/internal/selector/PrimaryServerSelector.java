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

import static me.levansj01.verus.util.mongodb.internal.connection.ClusterDescriptionHelper.getPrimaries;

import java.util.List;

import me.levansj01.verus.util.mongodb.connection.ClusterDescription;
import me.levansj01.verus.util.mongodb.connection.ServerDescription;
import me.levansj01.verus.util.mongodb.selector.ServerSelector;

/**
 * A server selector that chooses servers that are primaries.
 *
 * @since 3.0
 */
public final class PrimaryServerSelector implements ServerSelector {

    @Override
    public List<ServerDescription> select(final ClusterDescription clusterDescription) {
        return getPrimaries(clusterDescription);
    }

    @Override
    public String toString() {
        return "PrimaryServerSelector";
    }
}
