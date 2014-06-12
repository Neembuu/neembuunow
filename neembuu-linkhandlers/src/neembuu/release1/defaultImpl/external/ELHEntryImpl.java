/*s
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

package neembuu.release1.defaultImpl.external;

/**
 *
 * @author Shashank Tulsyan
 */
public final class ELHEntryImpl implements ELHEntry{

    private String checkingRegex;
    private String className;
    private String[]resourcesHash;
    private String[]dependenciesURL;
    private boolean broken;
    private long lastWorkingOn;
    private long minimumReleaseVerReq;
    private String[]dependenciesLocalPath;

    public ELHEntryImpl() {
    }
    
    public ELHEntryImpl(ELHProvider elhp) {
        checkingRegex = elhp.checkingRegex();
        broken = elhp.isBroken();
        minimumReleaseVerReq = elhp.minimumReleaseVerReq();
        lastWorkingOn = System.currentTimeMillis();
    }
    
    @Override public boolean isBroken() {
        return broken;
    }

    public void setBroken(boolean broken) {
        this.broken = broken;
    }

    @Override public long getLastWorkingOn() {
        return lastWorkingOn;
    }

    public void setLastWorkingOn(long lastWorkingOn) {
        this.lastWorkingOn = lastWorkingOn;
    }

    @Override public String getCheckingRegex() {
        return checkingRegex;
    }

    public void setCheckingRegex(String checkingRegex) {
        this.checkingRegex = checkingRegex;
    }

    @Override public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    @Override public String[] getResourcesHash() {
        return resourcesHash;
    }

    public void setResourcesHash(String[] resourcesHash) {
        this.resourcesHash = resourcesHash;
    }

    @Override public String[] getDependenciesURL() {
        return dependenciesURL;
    }

    public void setDependenciesURL(String[] dependenciesURL) {
        this.dependenciesURL = dependenciesURL;
    }

    public void setMinimumReleaseVerReq(long minimumReleaseVerReq) {
        this.minimumReleaseVerReq = minimumReleaseVerReq;
    }

    @Override public long getMinimumReleaseVerReq() {
        return minimumReleaseVerReq;
    }

    @Override public String[] getDependenciesLocalPath() {
        return dependenciesLocalPath;
    }

    public void setDependenciesLocalPath(String[] dependenciesLocalPath) {
        this.dependenciesLocalPath = dependenciesLocalPath;
    }
    
}
