/*
 * Copyright 2009-2010 Shashank Tulsyan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * File : RangeArrayListener.java
 * Author : Shashank Tulsyan
 */


package neembuu.rangearray;

import java.util.EventListener;

/**
 * The implementation must be non-blocking as events
 * are dispatched from the same thread where modification occurred.
 * Practically the calling thread is mostly a virtual file system 
 * thread, and hence should not be blocked.
 * @author Shashank Tulsyan
 */
public interface RangeArrayListener extends EventListener{
    /**
     * NonBlocking
     * @param elementOperated
     */
    public void rangeArrayModified(
        long modificationResultStart,
        long modificationResultEnd,
        Range elementOperated, 
        ModificationType modificationType,
        boolean removed,
        long modCount);
}
