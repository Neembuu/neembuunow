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
 * File : RangeArrayElementRejectedByFilterException.java
 * Author : Shashank Tulsyan
 */


package neembuu.rangearray;

/**
 *
 * @author Shashank Tulsyan
 */
public class RangeRejectedByFilterException extends RuntimeException{
    public RangeRejectedByFilterException() {
    }
    
    public RangeRejectedByFilterException(String str) {
        super(str);
    }
    
    public static final class GreaterThanFileSize extends RangeRejectedByFilterException {

        public GreaterThanFileSize(long fileSize, long elementEnding) {
            super("Range array element being added ends at="+elementEnding+
                    " and is greater than the set fileSize="+fileSize);
        }

    }
}
