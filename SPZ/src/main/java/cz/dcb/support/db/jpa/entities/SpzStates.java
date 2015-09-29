/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.entities;

/**
 * States the SPZ goes through
 * @author bar
 */
public enum SpzStates {
    POSTED, NEW, ANALYSIS, 
    REFINE, SPECIFIED, IMPLEMENTATION, DCB_ACCEPTED, 
    RELEASED, IMPLEMENTATION_REFINE, RE_IMPLEMENTATION, RE_ANALYSIS, 
    RECLAIMED, CONFIRMED, CANCELED, INVOICED
}
