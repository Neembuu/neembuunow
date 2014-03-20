/*
 * Copyright (C) 2014 Shashank Tulsyan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neembuu.release1.defaultImpl.splitImpl;

import java.util.Arrays;

/**
 *
 * @author Shashank Tulsyan
 */
public class AggregateException extends Exception {

    private final Exception[] secondaryExceptions;

    public AggregateException(String message, Exception primary, Exception... others) {
        super(message, primary);
        this.secondaryExceptions = others == null ? new Exception[0] : others;
    }

    public Throwable[] getAllExceptions() {

        int start = 0;
        int size = secondaryExceptions.length;
        final Throwable primary = getCause();
        if (primary != null) {
            start = 1;
            size++;
        }

        Throwable[] all = new Exception[size];

        if (primary != null) {
            all[0] = primary;
        }

        Arrays.fill(all, start, all.length, secondaryExceptions);
        return all;
    }

}
