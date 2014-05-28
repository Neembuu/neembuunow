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

package neembuu.release1.defaultImpl.external;

/**
 *
 * @author Shashank Tulsyan
 */
public final class ExternalLinkHandlerEntryImpl implements ExternalLinkHandlerEntry{

    String checkingRegex;
    String checkingJavaCode;
    String className;
    String[]resourcesHash;
    String[]dependenciesURL;

    public String getCheckingRegex() {
        return checkingRegex;
    }

    public void setCheckingRegex(String checkingRegex) {
        this.checkingRegex = checkingRegex;
    }

    public String getCheckingJavaCode() {
        return checkingJavaCode;
    }

    public void setCheckingJavaCode(String checkingJavaCode) {
        this.checkingJavaCode = checkingJavaCode;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String[] getResourcesHash() {
        return resourcesHash;
    }

    public void setResourcesHash(String[] resourcesHash) {
        this.resourcesHash = resourcesHash;
    }

    public String[] getDependenciesURL() {
        return dependenciesURL;
    }

    public void setDependenciesURL(String[] dependenciesURL) {
        this.dependenciesURL = dependenciesURL;
    }
    
    
}
