package neembuu.vfs.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author Shashank Tulsyan
 */
@Documented
@Retention(value=RetentionPolicy.SOURCE)
@Target(ElementType.METHOD)
public @interface ReadHandlerThread {

}
