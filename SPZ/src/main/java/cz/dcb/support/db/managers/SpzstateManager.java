/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Spzstate;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface SpzstateManager {

    /**
     * Vytvori novy stav SPZ
     * @param spzstate  novy stav spz.
     * @throws PreexistingEntityException Stav spz s danym klicem jiz existuje.
     * @throws Exception Jina vyjimka JPA
     */
    void create(Spzstate spzstate) throws PreexistingEntityException, Exception;

    /**
     * Smaze stav SPZ
     * @param id klic mazaneho stavu SPZ
     * @throws IllegalOrphanException Mazany stav SPZ je vyuzivan; naruseni 
     *         konzistence dat.
     * @throws NonexistentEntityException mazany stav neexistuje. 
     */
    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    /**
     * Upravi stav SPZ
     * @param spzstate novy stav spz, upravuje se stav 
     *        spz s klicem = spzstate.id
     * @throws IllegalOrphanException Meneny stav SPZ je vyuzivan; mozne naruseni
     *          konzistence dat.
     * @throws NonexistentEntityException - Meneny stav SPZ neexistuje.
     * @throws Exception Jina vyjimka pri pokusu o zmenu stavu SPZ.
     */
    void edit(Spzstate spzstate) throws IllegalOrphanException, NonexistentEntityException, Exception;

    /**
     * Vraci stav SPZ.
     * @param id klic hledaneho stavu SPZ.
     * @return odpovidaji stav SPZ.
     */
    Spzstate findSpzstate(Integer id);

    /**
     * Vraci seznam vsech stavu SPZ 
     * @return seznam vsech stavu SPZ
     */
    List<Spzstate> findSpzstateEntities();

    /**
     * Vraci interval stavu SPZ.
     * @param maxResults maximalni pocet stavu SPZ.
     * @param firstResult klic prvniho stavu SPZ.
     * @return Seznam stavu SPZ.
     */
    List<Spzstate> findSpzstateEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet stavu spz.
     * @return Pocet stavu SPZ. 
     */
    int getSpzstateCount();
    
}
