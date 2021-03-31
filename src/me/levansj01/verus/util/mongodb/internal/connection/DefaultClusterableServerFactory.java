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

package me.levansj01.verus.util.mongodb.internal.connection;

import java.util.Collections;
import java.util.List;

import me.levansj01.verus.util.mongodb.MongoCompressor;
import me.levansj01.verus.util.mongodb.MongoCredential;
import me.levansj01.verus.util.mongodb.MongoDriverInformation;
import me.levansj01.verus.util.mongodb.ServerAddress;
import me.levansj01.verus.util.mongodb.ServerApi;
import me.levansj01.verus.util.mongodb.connection.ClusterId;
import me.levansj01.verus.util.mongodb.connection.ClusterSettings;
import me.levansj01.verus.util.mongodb.connection.ConnectionPoolSettings;
import me.levansj01.verus.util.mongodb.connection.ServerId;
import me.levansj01.verus.util.mongodb.connection.ServerSettings;
import me.levansj01.verus.util.mongodb.connection.StreamFactory;
import me.levansj01.verus.util.mongodb.event.CommandListener;
import me.levansj01.verus.util.mongodb.event.ServerListener;
import me.levansj01.verus.util.mongodb.lang.Nullable;

public class DefaultClusterableServerFactory implements ClusterableServerFactory {
    private final ClusterId clusterId;
    private final ClusterSettings clusterSettings;
    private final ServerSettings serverSettings;
    private final ConnectionPoolSettings connectionPoolSettings;
    private final StreamFactory streamFactory;
    private final MongoCredentialWithCache credential;
    private final StreamFactory heartbeatStreamFactory;
    private final CommandListener commandListener;
    private final String applicationName;
    private final MongoDriverInformation mongoDriverInformation;
    private final List<MongoCompressor> compressorList;
    @Nullable
    private final ServerApi serverApi;

    public DefaultClusterableServerFactory(final ClusterId clusterId, final ClusterSettings clusterSettings,
                                           final ServerSettings serverSettings, final ConnectionPoolSettings connectionPoolSettings,
                                           final StreamFactory streamFactory, final StreamFactory heartbeatStreamFactory,
                                           final MongoCredential credential, final CommandListener commandListener,
                                           final String applicationName, final MongoDriverInformation mongoDriverInformation,
                                           final List<MongoCompressor> compressorList, final @Nullable ServerApi serverApi) {
        this.clusterId = clusterId;
        this.clusterSettings = clusterSettings;
        this.serverSettings = serverSettings;
        this.connectionPoolSettings = connectionPoolSettings;
        this.streamFactory = streamFactory;
        this.credential = credential == null ? null : new MongoCredentialWithCache(credential);
        this.heartbeatStreamFactory = heartbeatStreamFactory;
        this.commandListener = commandListener;
        this.applicationName = applicationName;
        this.mongoDriverInformation = mongoDriverInformation;
        this.compressorList = compressorList;
        this.serverApi = serverApi;
    }

    @Override
    public ClusterableServer create(final ServerAddress serverAddress,
                                    final ServerDescriptionChangedListener serverDescriptionChangedListener,
                                    final ServerListener serverListener,
                                    final ClusterClock clusterClock) {
        ConnectionPool connectionPool = new DefaultConnectionPool(new ServerId(clusterId, serverAddress),
                new InternalStreamConnectionFactory(streamFactory, credential, applicationName,
                        mongoDriverInformation, compressorList, commandListener, serverApi), connectionPoolSettings);

        connectionPool.start();

        // no credentials, compressor list, or command listener for the server monitor factory
        ServerMonitorFactory serverMonitorFactory =
            new DefaultServerMonitorFactory(new ServerId(clusterId, serverAddress), serverSettings, clusterClock,
                    new InternalStreamConnectionFactory(heartbeatStreamFactory, null,
                            applicationName, mongoDriverInformation, Collections.<MongoCompressor>emptyList(), null, serverApi),
                    connectionPool, serverApi);

        return new DefaultServer(new ServerId(clusterId, serverAddress), clusterSettings.getMode(), connectionPool,
                new DefaultConnectionFactory(), serverMonitorFactory, serverDescriptionChangedListener, serverListener, commandListener,
                clusterClock);
    }

    @Override
    public ServerSettings getSettings() {
        return serverSettings;
    }
}
