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

import static me.levansj01.verus.util.mongodb.internal.async.ErrorHandlingResultCallback.errorHandlingCallback;
import static me.levansj01.verus.util.mongodb.internal.authentication.NativeAuthenticationHelper.getAuthCommand;
import static me.levansj01.verus.util.mongodb.internal.authentication.NativeAuthenticationHelper.getNonceCommand;
import static me.levansj01.verus.util.mongodb.internal.connection.CommandHelper.executeCommand;
import static me.levansj01.verus.util.mongodb.internal.connection.CommandHelper.executeCommandAsync;

import org.bson.BsonDocument;
import org.bson.BsonString;

import me.levansj01.verus.util.mongodb.MongoCommandException;
import me.levansj01.verus.util.mongodb.MongoSecurityException;
import me.levansj01.verus.util.mongodb.ServerApi;
import me.levansj01.verus.util.mongodb.connection.ConnectionDescription;
import me.levansj01.verus.util.mongodb.diagnostics.logging.Logger;
import me.levansj01.verus.util.mongodb.diagnostics.logging.Loggers;
import me.levansj01.verus.util.mongodb.internal.async.SingleResultCallback;
import me.levansj01.verus.util.mongodb.lang.Nullable;

class NativeAuthenticator extends Authenticator {
    public static final Logger LOGGER = Loggers.getLogger("authenticator");

    NativeAuthenticator(final MongoCredentialWithCache credential, final @Nullable ServerApi serverApi) {
        super(credential, serverApi);
    }

    @Override
    public void authenticate(final InternalConnection connection, final ConnectionDescription connectionDescription) {
        try {
            BsonDocument nonceResponse = executeCommand(getMongoCredential().getSource(),
                                                         getNonceCommand(), getServerApi(),
                                                         connection);

            BsonDocument authCommand = getAuthCommand(getUserNameNonNull(),
                                                      getPasswordNonNull(),
                                                      ((BsonString) nonceResponse.get("nonce")).getValue());
            executeCommand(getMongoCredential().getSource(), authCommand, getServerApi(), connection);
        } catch (MongoCommandException e) {
            throw new MongoSecurityException(getMongoCredential(), "Exception authenticating", e);
        }
    }

    @Override
    void authenticateAsync(final InternalConnection connection, final ConnectionDescription connectionDescription,
                           final SingleResultCallback<Void> callback) {
        SingleResultCallback<Void> errHandlingCallback = errorHandlingCallback(callback, LOGGER);
        executeCommandAsync(getMongoCredential().getSource(), getNonceCommand(), getServerApi(), connection,
                            new SingleResultCallback<BsonDocument>() {
                                @Override
                                public void onResult(final BsonDocument nonceResult, final Throwable t) {
                                    if (t != null) {
                                        errHandlingCallback.onResult(null, translateThrowable(t));
                                    } else {
                                        executeCommandAsync(getMongoCredential().getSource(),
                                                            getAuthCommand(getUserNameNonNull(), getPasswordNonNull(),
                                                                           ((BsonString) nonceResult.get("nonce")).getValue()),
                                                            getServerApi(), connection,
                                                            new SingleResultCallback<BsonDocument>() {
                                                                @Override
                                                                public void onResult(final BsonDocument result, final Throwable t) {
                                                                    if (t != null) {
                                                                        errHandlingCallback.onResult(null, translateThrowable(t));
                                                                    } else {
                                                                        errHandlingCallback.onResult(null, null);
                                                                    }
                                                                }
                                                            });
                                    }
                                }
                            });
    }

    private Throwable translateThrowable(final Throwable t) {
        if (t instanceof MongoCommandException) {
            return new MongoSecurityException(getMongoCredential(), "Exception authenticating", t);
        } else {
            return t;
        }
    }
}
