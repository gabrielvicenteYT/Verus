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
import me.levansj01.verus.util.bson.types.Symbol;

/**
 * A codec for BSON symbol type.
 *
 * @since 3.0
 */
public class SymbolCodec implements Codec<Symbol> {
    @Override
    public Symbol decode(final BsonReader reader, final DecoderContext decoderContext) {
        return new Symbol(reader.readSymbol());
    }

    @Override
    public void encode(final BsonWriter writer, final Symbol value, final EncoderContext encoderContext) {
        writer.writeSymbol(value.getSymbol());
    }

    @Override
    public Class<Symbol> getEncoderClass() {
        return Symbol.class;
    }
}
