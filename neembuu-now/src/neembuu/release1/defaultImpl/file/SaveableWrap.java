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
package neembuu.release1.defaultImpl.file;

import java.io.File;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;
import neembuu.release1.api.file.Saveable;
import neembuu.vfs.file.MinimumFileInfo;
import neembuu.vfs.file.SeekableConnectionFile;

/**
 *
 * @author Shashank Tulsyan
 */
public final class SaveableWrap implements Saveable {

    private final SeekableConnectionFile scf;

    public SaveableWrap(SeekableConnectionFile scf) { this.scf = scf;  }

    @Override
    public MinimumFileInfo getMinimumFileInfo() {
        return scf;
    }

    @Override
    public boolean mayBeSaved() {
        return scf.getDownloadConstrainHandler().isComplete();
    }

    @Override
    public void saveACopy(File outputFilePath) throws Exception {
        FileChannel fc = FileChannel.open(outputFilePath.toPath(),
                StandardOpenOption.WRITE, StandardOpenOption.CREATE
        );

        scf.getFileStorageManager().copyIfCompleteTo(fc, scf.getFileSize());
        fc.force(true);
        fc.close();
    }

}
