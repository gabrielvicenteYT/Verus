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

 import java.util.UUID;

import me.levansj01.verus.util.bson.UuidRepresentation;
import me.levansj01.verus.util.bson.codecs.configuration.CodecProvider;
import me.levansj01.verus.util.bson.codecs.configuration.CodecRegistry;

 /**
  * A {@code CodecProvider} for UUID Codecs with custom UUID representations
  *
  * @since 3.0
  */
 public class UuidCodecProvider implements CodecProvider {

     private UuidRepresentation uuidRepresentation;

     /**
      * Set the UUIDRepresentation to be used in the codec
      * default is JAVA_LEGACY to be compatible with existing documents
      *
      * @param uuidRepresentation the representation of UUID
      *
      * @since 3.0
      * @see me.levansj01.verus.util.bson.UuidRepresentation
      */
     public UuidCodecProvider(final UuidRepresentation uuidRepresentation) {
         this.uuidRepresentation = uuidRepresentation;
     }

     @Override
     @SuppressWarnings("unchecked")
     public <T> Codec<T> get(final Class<T> clazz, final CodecRegistry registry) {
         if (clazz == UUID.class) {
             return (Codec<T>) (new UuidCodec(uuidRepresentation));
         }
         return null;
     }
 }
