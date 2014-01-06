/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neembuu.util.logging;

import java.util.logging.LogRecord;

/**
 *
 * @author Shashank Tulsyan
 */
final class LogRecordContainingException extends RuntimeException{
    final LogRecord lr;

    LogRecordContainingException(LogRecord lr) {
        this.lr = lr;
    }
    
}
