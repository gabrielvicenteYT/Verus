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

import me.levansj01.verus.util.bson.BsonReader;
import me.levansj01.verus.util.bson.BsonWriter;
import me.levansj01.verus.util.bson.types.MinKey;

/**
 * Encodes and decodes instances of type {@link me.levansj01.verus.util.bson.types.MinKey}.
 *
 * @since 3.0
 */
public class MinKeyCodec implements Codec<MinKey> {
    @Override
    public void encode(final BsonWriter writer, final MinKey value, final EncoderContext encoderContext) {
        writer.writeMinKey();
    }

    @Override
    public MinKey decode(final BsonReader reader, final DecoderContext decoderContext) {
        reader.readMinKey();
        return new MinKey();
    }

    @Override
    public Class<MinKey> getEncoderClass() {
        return MinKey.class;
    }
}
