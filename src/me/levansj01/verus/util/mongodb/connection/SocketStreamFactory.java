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

package me.levansj01.verus.util.mongodb.connection;

import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;

import me.levansj01.verus.util.mongodb.MongoClientException;
import me.levansj01.verus.util.mongodb.ServerAddress;
import me.levansj01.verus.util.mongodb.UnixServerAddress;
import me.levansj01.verus.util.mongodb.internal.connection.PowerOfTwoBufferPool;
import me.levansj01.verus.util.mongodb.internal.connection.SocketStream;
import me.levansj01.verus.util.mongodb.internal.connection.UnixSocketChannelStream;

import static me.levansj01.verus.util.mongodb.assertions.Assertions.notNull;

import java.security.NoSuchAlgorithmException;

/**
 * Factory for creating instances of {@code SocketStream}.
 *
 * @since 3.0
 */
public class SocketStreamFactory implements StreamFactory {
    private final SocketSettings settings;
    private final SslSettings sslSettings;
    private final SocketFactory socketFactory;
    private final BufferProvider bufferProvider = new PowerOfTwoBufferPool();

    /**
     * Creates a new factory with the given settings for connecting to servers and the given SSL settings
     *
     * @param settings    the SocketSettings for connecting to a MongoDB server
     * @param sslSettings whether SSL is enabled.
     */
    public SocketStreamFactory(final SocketSettings settings, final SslSettings sslSettings) {
        this(settings, sslSettings, null);
    }

    /**
     * Creates a new factory with the given settings for connecting to servers and a factory for creating connections.
     *
     * @param settings      the SocketSettings for connecting to a MongoDB server
     * @param sslSettings   the SSL for connecting to a MongoDB server
     * @param socketFactory a SocketFactory for creating connections to servers.
     */
    public SocketStreamFactory(final SocketSettings settings, final SslSettings sslSettings, final SocketFactory socketFactory) {
        this.settings = notNull("settings", settings);
        this.sslSettings = notNull("sslSettings", sslSettings);
        this.socketFactory = socketFactory;
    }

    @Override
    public Stream create(final ServerAddress serverAddress) {
        Stream stream;
        if (serverAddress instanceof UnixServerAddress) {
            if (sslSettings.isEnabled()) {
                throw new MongoClientException("Socket based connections do not support ssl");
            }
            stream = new UnixSocketChannelStream((UnixServerAddress) serverAddress, settings, sslSettings, bufferProvider);
        } else {
            if (socketFactory != null) {
                stream = new SocketStream(serverAddress, settings, sslSettings, socketFactory, bufferProvider);
            } else if (sslSettings.isEnabled()) {
                stream = new SocketStream(serverAddress, settings, sslSettings, getSslContext().getSocketFactory(), bufferProvider);
            } else {
                stream = new SocketStream(serverAddress, settings, sslSettings, SocketFactory.getDefault(), bufferProvider);
            }
        }
        return stream;
    }

    private SSLContext getSslContext() {
        try {
            return (sslSettings.getContext() == null) ? SSLContext.getDefault() : sslSettings.getContext();
        } catch (NoSuchAlgorithmException e) {
            throw new MongoClientException("Unable to create default SSLContext", e);
        }
    }
}
