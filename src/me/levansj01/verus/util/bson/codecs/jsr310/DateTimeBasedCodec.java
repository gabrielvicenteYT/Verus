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

package me.levansj01.verus.util.bson.codecs.jsr310;

import static java.lang.String.format;

import me.levansj01.verus.util.bson.BsonReader;
import me.levansj01.verus.util.bson.BsonType;
import me.levansj01.verus.util.bson.codecs.Codec;
import me.levansj01.verus.util.bson.codecs.configuration.CodecConfigurationException;

abstract class DateTimeBasedCodec<T> implements Codec<T> {

    long validateAndReadDateTime(final BsonReader reader) {
        BsonType currentType = reader.getCurrentBsonType();
        if (!currentType.equals(BsonType.DATE_TIME)) {
            throw new CodecConfigurationException(format("Could not decode into %s, expected '%s' BsonType but got '%s'.",
                    getEncoderClass().getSimpleName(), BsonType.DATE_TIME, currentType));
        }
        return reader.readDateTime();
    }

}
