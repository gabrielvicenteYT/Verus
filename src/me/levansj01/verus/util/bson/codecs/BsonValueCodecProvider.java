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

import java.util.HashMap;
import java.util.Map;

import me.levansj01.verus.util.bson.BsonArray;
import me.levansj01.verus.util.bson.BsonBinary;
import me.levansj01.verus.util.bson.BsonBoolean;
import me.levansj01.verus.util.bson.BsonDateTime;
import me.levansj01.verus.util.bson.BsonDbPointer;
import me.levansj01.verus.util.bson.BsonDecimal128;
import me.levansj01.verus.util.bson.BsonDocument;
import me.levansj01.verus.util.bson.BsonDocumentWrapper;
import me.levansj01.verus.util.bson.BsonDouble;
import me.levansj01.verus.util.bson.BsonInt32;
import me.levansj01.verus.util.bson.BsonInt64;
import me.levansj01.verus.util.bson.BsonJavaScript;
import me.levansj01.verus.util.bson.BsonJavaScriptWithScope;
import me.levansj01.verus.util.bson.BsonMaxKey;
import me.levansj01.verus.util.bson.BsonMinKey;
import me.levansj01.verus.util.bson.BsonNull;
import me.levansj01.verus.util.bson.BsonObjectId;
import me.levansj01.verus.util.bson.BsonRegularExpression;
import me.levansj01.verus.util.bson.BsonString;
import me.levansj01.verus.util.bson.BsonSymbol;
import me.levansj01.verus.util.bson.BsonTimestamp;
import me.levansj01.verus.util.bson.BsonType;
import me.levansj01.verus.util.bson.BsonUndefined;
import me.levansj01.verus.util.bson.BsonValue;
import me.levansj01.verus.util.bson.RawBsonDocument;
import me.levansj01.verus.util.bson.codecs.configuration.CodecProvider;
import me.levansj01.verus.util.bson.codecs.configuration.CodecRegistry;

/**
 * A {@code CodecProvider} for all subclass of BsonValue.
 *
 * @since 3.0
 */
public class BsonValueCodecProvider implements CodecProvider {
    private static final BsonTypeClassMap DEFAULT_BSON_TYPE_CLASS_MAP;

    private final Map<Class<?>, Codec<?>> codecs = new HashMap<Class<?>, Codec<?>>();

    /**
     * Construct a new instance with the default codec for each BSON type.
     */
    public BsonValueCodecProvider() {
        addCodecs();
    }

    /**
     * Get the {@code BsonValue} subclass associated with the given {@code BsonType}.
     * @param bsonType the BsonType
     * @return the class associated with the given type
     */
    @SuppressWarnings("unchecked")
    public static Class<? extends BsonValue> getClassForBsonType(final BsonType bsonType) {
        return (Class<? extends BsonValue>) DEFAULT_BSON_TYPE_CLASS_MAP.get(bsonType);
    }

    /**
     * Gets the BsonTypeClassMap used by this provider.
     *
     * @return the non-null BsonTypeClassMap
     * @since 3.3
     */
    public static BsonTypeClassMap getBsonTypeClassMap() {
        return DEFAULT_BSON_TYPE_CLASS_MAP;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry registry) {
        if (codecs.containsKey(clazz)) {
            return (Codec<T>) codecs.get(clazz);
        }

        if (clazz == BsonJavaScriptWithScope.class) {
            return (Codec<T>) new BsonJavaScriptWithScopeCodec(registry.get(BsonDocument.class));
        }

        if (clazz == BsonValue.class) {
            return (Codec<T>) new BsonValueCodec(registry);
        }

        if (clazz == BsonDocumentWrapper.class) {
            return (Codec<T>) new BsonDocumentWrapperCodec(registry.get(BsonDocument.class));
        }

        if (clazz == RawBsonDocument.class) {
            return (Codec<T>) new RawBsonDocumentCodec();
        }

        if (BsonDocument.class.isAssignableFrom(clazz)) {
            return (Codec<T>) new BsonDocumentCodec(registry);
        }

        if (BsonArray.class.isAssignableFrom(clazz)) {
            return (Codec<T>) new BsonArrayCodec(registry);
        }

        return null;
    }

    private void addCodecs() {
        addCodec(new BsonNullCodec());
        addCodec(new BsonBinaryCodec());
        addCodec(new BsonBooleanCodec());
        addCodec(new BsonDateTimeCodec());
        addCodec(new BsonDBPointerCodec());
        addCodec(new BsonDoubleCodec());
        addCodec(new BsonInt32Codec());
        addCodec(new BsonInt64Codec());
        addCodec(new BsonDecimal128Codec());
        addCodec(new BsonMinKeyCodec());
        addCodec(new BsonMaxKeyCodec());
        addCodec(new BsonJavaScriptCodec());
        addCodec(new BsonObjectIdCodec());
        addCodec(new BsonRegularExpressionCodec());
        addCodec(new BsonStringCodec());
        addCodec(new BsonSymbolCodec());
        addCodec(new BsonTimestampCodec());
        addCodec(new BsonUndefinedCodec());
    }

    private <T extends BsonValue> void addCodec(final Codec<T> codec) {
        codecs.put(codec.getEncoderClass(), codec);
    }

    static {
        Map<BsonType, Class<?>> map = new HashMap<BsonType, Class<?>>();

        map.put(BsonType.NULL, BsonNull.class);
        map.put(BsonType.ARRAY, BsonArray.class);
        map.put(BsonType.BINARY, BsonBinary.class);
        map.put(BsonType.BOOLEAN, BsonBoolean.class);
        map.put(BsonType.DATE_TIME, BsonDateTime.class);
        map.put(BsonType.DB_POINTER, BsonDbPointer.class);
        map.put(BsonType.DOCUMENT, BsonDocument.class);
        map.put(BsonType.DOUBLE, BsonDouble.class);
        map.put(BsonType.INT32, BsonInt32.class);
        map.put(BsonType.INT64, BsonInt64.class);
        map.put(BsonType.DECIMAL128, BsonDecimal128.class);
        map.put(BsonType.MAX_KEY, BsonMaxKey.class);
        map.put(BsonType.MIN_KEY, BsonMinKey.class);
        map.put(BsonType.JAVASCRIPT, BsonJavaScript.class);
        map.put(BsonType.JAVASCRIPT_WITH_SCOPE, BsonJavaScriptWithScope.class);
        map.put(BsonType.OBJECT_ID, BsonObjectId.class);
        map.put(BsonType.REGULAR_EXPRESSION, BsonRegularExpression.class);
        map.put(BsonType.STRING, BsonString.class);
        map.put(BsonType.SYMBOL, BsonSymbol.class);
        map.put(BsonType.TIMESTAMP, BsonTimestamp.class);
        map.put(BsonType.UNDEFINED, BsonUndefined.class);

        DEFAULT_BSON_TYPE_CLASS_MAP = new BsonTypeClassMap(map);
    }
}
