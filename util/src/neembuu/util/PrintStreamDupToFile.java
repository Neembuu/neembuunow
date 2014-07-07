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

package neembuu.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Locale;

/**
 *
 * @author Shashank Tulsyan
 */
public final class PrintStreamDupToFile extends PrintStream{
    private final PrintStream original;
    public PrintStreamDupToFile(File file,PrintStream original) throws FileNotFoundException {
        super(new FileOutputStream(file),true);
        this.original = original;
    }

    @Override
    public void flush() {
        original.flush();
        super.flush();
    }

    @Override
    public void close() {
        original.close();
        super.close();
    }

    @Override
    public boolean checkError() {
        return original.checkError();
    }

    @Override
    public void write(int b) {
        original.write(b);
        super.write(b);
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        original.write(buf, off, len);
        super.write(buf, off, len);
    }

    @Override
    public void print(boolean b) {
        original.print(b);
        super.print(b);
    }

    @Override
    public void print(char c) {
        original.print(c);
        super.print(c);
    }

    @Override
    public void print(int i) {
        original.print(i);
        super.print(i);
    }

    @Override
    public void print(long l) {
        original.print(l);
        super.print(l);
    }

    @Override
    public void print(float f) {
        original.print(f);
        super.print(f);
    }

    @Override
    public void print(double d) {
        original.print(d);
        super.print(d);
    }

    @Override
    public void print(char[] s) {
        original.print(s);
        super.print(s);
    }

    @Override
    public void print(String s) {
        original.print(s);
        super.print(s);
    }

    @Override
    public void print(Object obj) {
        original.print(obj);
        super.print(obj);
    }

    @Override
    public void println() {
        original.println();
        super.println();
    }

    @Override
    public void println(boolean x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(char x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(int x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(long x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(float x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(double x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(char[] x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(String x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public void println(Object x) {
        original.println(x);
        super.println(x);
    }

    @Override
    public PrintStream printf(String format, Object... args) {
        original.printf(format, args);
        super.printf(format, args);
        return this;
    }

    @Override
    public PrintStream printf(Locale l, String format, Object... args) {
        original.printf(l, format, args);
        super.printf(l,format, args);
        return this;
    }

    @Override
    public PrintStream format(String format, Object... args) {
        original.format(format, args);
        super.format(format, args);
        return this;
    }

    @Override
    public PrintStream format(Locale l, String format, Object... args) {
        original.format(l, format, args);
        super.format(l,format, args);
        return this;
    }

    @Override
    public PrintStream append(CharSequence csq) {
        original.append(csq);
        super.append(csq);
        return this;
    }

    @Override
    public PrintStream append(CharSequence csq, int start, int end) {
        original.append(csq, start, end);
        super.append(csq,start,end);
        return this;
    }

    @Override
    public PrintStream append(char c) {
        original.append(c);
        super.append(c);
        return this;
    }

    @Override
    public void write(byte[] b) throws IOException {
        original.write(b);
        super.write(b);
    }
    
    
    
}
