/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package neembuu.release1.api;

import neembuu.vfs.connection.NewConnectionProvider;

/**
 *
 * @author Shashank Tulsyan
 */
public interface File {
    NewConnectionProvider getConnectionProvider();
    String fileName();
    long fileSize();
}
