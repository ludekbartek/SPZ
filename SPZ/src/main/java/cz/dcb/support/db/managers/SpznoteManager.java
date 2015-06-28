/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Spznote;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface SpznoteManager {
    /**
     * Vytvori novou poznamku k SPZ
     * @param spznote nova pozanamka k SPZ
     * @throws PreexistingEntityException poznamka k SPZ v databazi existuje.
     * @throws Exception jina vyjimka pri vytvareni SPZ
     */
    void create(Spznote spznote) throws PreexistingEntityException, Exception;
    
    /**
     * Smaze poznamku k SPZ s danym id.
     * @param id klic odstranovane poznamky
     * @throws IllegalOrphanException odstranovana poznamka k SPZ je odkazovana;
     *         pripadny zdroj nekonzistence dat.
     * @throws NonexistentEntityException poznamka s danym klicem neexistuje.
     */
    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;

    /**
     * Zmena udaju o poznamce k SPZ
     * @param spznote nova hodnota dat k poznamce o SPZ s id=spznote.id.
     * @throws IllegalOrphanException odstranovana poznamka k SPZ je odkazovana;
     *         pripadny zdroj nekonzistence dat.
     * @throws NonexistentEntityException poznamka s danym klicem neexistuj.
     * @throws Exception jina vyjimka pri zmene udaju o poznamce k SPZ.
     */
    void edit(Spznote spznote) throws IllegalOrphanException, NonexistentEntityException, Exception;

    /**
     * Vraci Spznote s danym klicem. 
     * @param id klic hledane poznamky.
     * @return Nalezena poznamka k Spz.
     */
    Spznote findSpznote(Integer id);

    /**
     * Vraci seznam vsech poznamek k Spz.
     * @return seznam vsech poznamek k Spz.
     */
    List<Spznote> findSpznoteEntities();

    /**
     * Vraci seznam poznamek s danymi parametry.
     * @param maxResults maximalni pocet poznamek.
     * @param firstResult klic 1. hledane poznamky.
     * @return 
     */
    List<Spznote> findSpznoteEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet poznamek k Spz.
     * @return pocet poznamek k Spz.
     */
    int getSpznoteCount();
    
}
