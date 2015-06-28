/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Sequence;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface SequenceManager {

    /**
     * Vytvori novou Sekvenci
     * @param sequence entita, ktera ma byt vytvorena a pridana.
     * @throws PreexistingEntityException pokus o vytvoreni duplicitni entity.
     * @throws Exception - jina JPA vyjimka pri pokusu o pridani sekvence.
     */
    void create(Sequence sequence) throws PreexistingEntityException, Exception;

    /**
     * Zruseni sekvence s danym id.
     * @param id klic sekvence, ktera se ma smazat.
     * @throws NonexistentEntityException mazana sekvence neexistuje.
     */
    void destroy(String id) throws NonexistentEntityException;

    /**
     * Uprava dane sekvence
     * @param sequence - nove udaje pro sekvenci s klicem = sequence.id
     * @throws NonexistentEntityException upravovana sekvence neexistuje.
     * @throws Exception jina JPA vyjimka. 
     */
    void edit(Sequence sequence) throws NonexistentEntityException, Exception;

    /**
     * Vyhledani sekvence s danym id.
     * @param id klic hledane sekvence
     * @return nalezenou sekvenci.
     */
    Sequence findSequence(String id);

    /**
     * Vrati seznam vsech sekvenci.
     * @return vsechny sekvence ulozene v DB.
     */
    List<Sequence> findSequenceEntities();

    /**
     * Vraci sekvence s danym rozsahem klicu.
     * @param maxResults maximalni vraceny pocet sekvenci.
     * @param firstResult klic 1. vracene sekvence.
     * @return rozsah sekvenci s danymi parametry.
     */
    List<Sequence> findSequenceEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet sekvenci v db.
     * @return pocet sekvenci v db.
     */
    int getSequenceCount();
    
}
