/*
 *  Copyright (C) 2014 Shashank Tulsyan
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package neembuu.release1.externalImpl.linkhandler;

import neembuu.release1.defaultImpl.external.ExternalLinkHandlerEntry;
import neembuu.release1.defaultImpl.external.ExternalLinkHandlerProviderAnnotation;

/**
 *
 * @author Shashank Tulsyan
 */
public class ExternalLinkHandlerEntryImpl implements ExternalLinkHandlerEntry {

    private final ExternalLinkHandlerProviderAnnotation annotation;
    private String className;
    private String[] resourcesHash;
    private String[] dependenciesURL;

    public ExternalLinkHandlerEntryImpl(ExternalLinkHandlerProviderAnnotation annotation) {
        this.annotation = annotation;
    }

    public ExternalLinkHandlerEntryImpl() {
        this.annotation = null;
    }

    void setClassName(String className) {
        this.className = className;
    }

    void setResourcesHash(String[] resourcesHash) {
        this.resourcesHash = resourcesHash;
    }

    void setDependenciesURL(String[] dependenciesURL) {
        this.dependenciesURL = dependenciesURL;
    }

    @Override
    public String getCheckingRegex() {
        return annotation.checkingRegex();
    }

    @Override
    public String getCheckingJavaCode() {
        return annotation.checkingJavaCode();
    }

    @Override
    public String[] getResourcesHash() {
        return resourcesHash;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String[] getDependenciesURL() {
        return dependenciesURL;
    }
}
