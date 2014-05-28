/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
