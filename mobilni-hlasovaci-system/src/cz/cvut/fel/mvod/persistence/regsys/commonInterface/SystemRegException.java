/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.mvod.persistence.regsys.commonInterface;

/**
 *
 * @author Lahvi
 */
public class SystemRegException extends java.lang.Exception {

    public SystemRegException(String msg) {
        super(msg);
    }

    public SystemRegException(Throwable cause) {
        super(cause);
    }
}
