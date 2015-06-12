/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db;

/**
 *
 * @author bar
 */
public class SPZException extends Exception{

    public SPZException(String message) {
        super(message);
    }

    public SPZException(String message, Throwable cause) {
        super(message, cause);
    }

    public SPZException(Throwable cause) {
        super(cause);
    }
    
}
