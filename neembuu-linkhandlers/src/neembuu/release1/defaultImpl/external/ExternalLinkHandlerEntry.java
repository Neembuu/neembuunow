/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.external;

/**
 *
 * @author Shashank Tulsyan
 */
public interface ExternalLinkHandlerEntry {
    String checkingRegex();
    String checkingJavaCode();
    long classHash();
    String[]dependenciesURL();
}
