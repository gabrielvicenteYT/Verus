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

package me.levansj01.verus.util.bson.codecs;

import me.levansj01.verus.util.bson.BsonDecimal128;
import me.levansj01.verus.util.bson.BsonReader;
import me.levansj01.verus.util.bson.BsonWriter;

/**
 * A Codec for BsonDecimal128 instances.
 *
 * @since 3.4
 */
public class BsonDecimal128Codec implements Codec<BsonDecimal128> {
    @Override
    public BsonDecimal128 decode(final BsonReader reader, final DecoderContext decoderContext) {
        return new BsonDecimal128(reader.readDecimal128());
    }

    @Override
    public void encode(final BsonWriter writer, final BsonDecimal128 value, final EncoderContext encoderContext) {
        writer.writeDecimal128(value.getValue());
    }

    @Override
    public Class<BsonDecimal128> getEncoderClass() {
        return BsonDecimal128.class;
    }
}
