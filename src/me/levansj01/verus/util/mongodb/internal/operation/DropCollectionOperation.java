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

package me.levansj01.verus.util.mongodb.internal.operation;

import static me.levansj01.verus.util.mongodb.assertions.Assertions.notNull;
import static me.levansj01.verus.util.mongodb.internal.async.ErrorHandlingResultCallback.errorHandlingCallback;
import static me.levansj01.verus.util.mongodb.internal.operation.CommandOperationHelper.executeCommand;
import static me.levansj01.verus.util.mongodb.internal.operation.CommandOperationHelper.executeCommandAsync;
import static me.levansj01.verus.util.mongodb.internal.operation.CommandOperationHelper.isNamespaceError;
import static me.levansj01.verus.util.mongodb.internal.operation.CommandOperationHelper.rethrowIfNotNamespaceError;
import static me.levansj01.verus.util.mongodb.internal.operation.CommandOperationHelper.writeConcernErrorTransformer;
import static me.levansj01.verus.util.mongodb.internal.operation.CommandOperationHelper.writeConcernErrorWriteTransformer;
import static me.levansj01.verus.util.mongodb.internal.operation.OperationHelper.LOGGER;
import static me.levansj01.verus.util.mongodb.internal.operation.OperationHelper.releasingCallback;
import static me.levansj01.verus.util.mongodb.internal.operation.OperationHelper.withAsyncConnection;
import static me.levansj01.verus.util.mongodb.internal.operation.OperationHelper.withConnection;
import static me.levansj01.verus.util.mongodb.internal.operation.WriteConcernHelper.appendWriteConcernToCommand;

import org.bson.BsonDocument;
import org.bson.BsonString;

import me.levansj01.verus.util.mongodb.MongoCommandException;
import me.levansj01.verus.util.mongodb.MongoNamespace;
import me.levansj01.verus.util.mongodb.WriteConcern;
import me.levansj01.verus.util.mongodb.connection.ConnectionDescription;
import me.levansj01.verus.util.mongodb.internal.async.SingleResultCallback;
import me.levansj01.verus.util.mongodb.internal.binding.AsyncWriteBinding;
import me.levansj01.verus.util.mongodb.internal.binding.WriteBinding;
import me.levansj01.verus.util.mongodb.internal.connection.AsyncConnection;
import me.levansj01.verus.util.mongodb.internal.connection.Connection;
import me.levansj01.verus.util.mongodb.internal.operation.OperationHelper.AsyncCallableWithConnection;
import me.levansj01.verus.util.mongodb.internal.operation.OperationHelper.CallableWithConnection;

/**
 * Operation to drop a Collection in MongoDB.  The {@code execute} method throws MongoCommandFailureException if something goes wrong, but
 * it will not throw an Exception if the collection does not exist before trying to drop it.
 *
 * @since 3.0
 */
public class DropCollectionOperation implements AsyncWriteOperation<Void>, WriteOperation<Void> {
    private final MongoNamespace namespace;
    private final WriteConcern writeConcern;

    /**
     * Construct a new instance.
     *
     * @param namespace the database and collection namespace for the operation.
     */
    public DropCollectionOperation(final MongoNamespace namespace) {
        this(namespace, null);
    }

    /**
     * Construct a new instance.
     *
     * @param namespace    the database and collection namespace for the operation.
     * @param writeConcern the write concern
     * @since 3.4
     */
    public DropCollectionOperation(final MongoNamespace namespace, final WriteConcern writeConcern) {
        this.namespace = notNull("namespace", namespace);
        this.writeConcern = writeConcern;
    }

    /**
     * Gets the write concern.
     *
     * @return the write concern, which may be null
     * @since 3.4
     */
    public WriteConcern getWriteConcern() {
        return writeConcern;
    }

    @Override
    public Void execute(final WriteBinding binding) {
        return withConnection(binding, new CallableWithConnection<Void>() {
            @Override
            public Void call(final Connection connection) {
                try {
                    executeCommand(binding, namespace.getDatabaseName(), getCommand(connection.getDescription()),
                            connection, writeConcernErrorTransformer());
                } catch (MongoCommandException e) {
                    rethrowIfNotNamespaceError(e);
                }
                return null;
            }
        });
    }

    @Override
    public void executeAsync(final AsyncWriteBinding binding, final SingleResultCallback<Void> callback) {
        withAsyncConnection(binding, new AsyncCallableWithConnection() {
            @Override
            public void call(final AsyncConnection connection, final Throwable t) {
                SingleResultCallback<Void> errHandlingCallback = errorHandlingCallback(callback, LOGGER);
                if (t != null) {
                    errHandlingCallback.onResult(null, t);
                } else {
                    final SingleResultCallback<Void> releasingCallback = releasingCallback(errHandlingCallback, connection);
                    executeCommandAsync(binding, namespace.getDatabaseName(), getCommand(connection.getDescription()),
                            connection, writeConcernErrorWriteTransformer(), new SingleResultCallback<Void>() {
                                @Override
                                public void onResult(final Void result, final Throwable t) {
                                    if (t != null && !isNamespaceError(t)) {
                                        releasingCallback.onResult(null, t);
                                    } else {
                                        releasingCallback.onResult(result, null);
                                    }
                                }
                            });
                }
            }
        });
    }

    private BsonDocument getCommand(final ConnectionDescription description) {
        BsonDocument commandDocument = new BsonDocument("drop", new BsonString(namespace.getCollectionName()));
        appendWriteConcernToCommand(writeConcern, commandDocument, description);
        return commandDocument;
    }

}
