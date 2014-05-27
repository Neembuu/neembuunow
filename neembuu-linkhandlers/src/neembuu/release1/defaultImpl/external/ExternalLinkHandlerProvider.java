/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.defaultImpl.external;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Shashank Tulsyan
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ExternalLinkHandlerProvider {
    /**
     * @return If url.matches(checkingRegex())==true implies that
     * this link handler can handle the given url.
     */
    String checkingRegex();
    /**
     * @return if more than simple regex checking is required
     * to know if the link can be handled or not.
     */
    String checkingJavaCode() default "";
    /**
     * @return List of required, class files and jar files
     */
    String[]dependenciesURL();
    /**
     * @return The minimum version of NeembuuNow required for this plugin
     * to function properly. Refer {@link neembuu.release1.app.Application#releaseTime(long time) } 
     */
    long minimumReleaseVerReq() default 1398604095683L;
}
