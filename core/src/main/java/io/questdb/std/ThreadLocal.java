/*******************************************************************************
 *     ___                  _   ____  ____
 *    / _ \ _   _  ___  ___| |_|  _ \| __ )
 *   | | | | | | |/ _ \/ __| __| | | |  _ \
 *   | |_| | |_| |  __/\__ \ |_| |_| | |_) |
 *    \__\_\\__,_|\___||___/\__|____/|____/
 *
 *  Copyright (c) 2014-2019 Appsicle
 *  Copyright (c) 2019-2022 QuestDB
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 ******************************************************************************/

package io.questdb.std;

import java.io.Closeable;
import java.io.IOException;

public class ThreadLocal<T> extends java.lang.ThreadLocal<T> implements Closeable {
    private final ObjectFactory<T> fact;

    public ThreadLocal(ObjectFactory<T> fact) {
        this.fact = fact;
    }

    @Override
    public void close() throws IOException {
        Misc.freeIfCloseable(super.get());
        remove();
    }

    @Override
    public T get() {
        T val = super.get();
        if (val == null) {
            val = fact.newInstance();
            set(val);
        }
        return val;
    }
}
