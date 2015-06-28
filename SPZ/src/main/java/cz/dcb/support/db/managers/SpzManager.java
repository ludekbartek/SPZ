/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Spz;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface SpzManager {

    /**
     * Vytvori novou SPZ
     * @param spz pozadavek, ktery se ma vytvorit.
     * @throws PreexistingEntityException pozadavek je jiz v DB
     * @throws Exception jina vyjimka pri manipulaci s DB.
     */
    void create(Spz spz) throws PreexistingEntityException, Exception;
    
    /**
     * Smaze SPZ z databaze.
     * @param id klic spz, ktery se ma odstranit.
     * @throws IllegalOrphanException SPZ odkazovan jinou entitou v DB; 
     *          potencialni zdroj nekonzistence dat.
     * @throws NonexistentEntityException  Mazany SPZ neexistuje.
     */
    void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException;
    
    /**
     * Uprava udaju o SPZ.
     * @param spz nove udaje o SPZ s klicem = spz.id. 
     * @throws IllegalOrphanException SPZ odkazovan jinou entitou v DB;
     *          potencialni zdroj nekonzistence dat.
     * @throws NonexistentEntityException Upravovany SPZ neexistuje.
     * @throws Exception Jina vyjimka pri uprave SPZ.
     */
    void edit(Spz spz) throws IllegalOrphanException, NonexistentEntityException, Exception;

    /**
     * Vyhleda SPZ s danym id.
     * @param id klic hledaneho SPZ
     * @return nalezeny SPZ
     */
    Spz findSpz(Integer id);

    /**
     * Vrati vsechny SPZ v DB.
     * @return seznam vsech SPZ v DB.
     */
    List<Spz> findSpzEntities();

    /**
     * Vrati dany rozsah SPZ
     * @param maxResults maximalni pocet vracenych SPZ.
     * @param firstResult klic prvni SPZ v seznamu.
     * @return seznam SPZ s danymi parametry.
     */
    List<Spz> findSpzEntities(int maxResults, int firstResult);

    /**
     * Pocet SPZ v DB.
     * @return Vraci pocet SPZ v databazi.
     */
    int getSpzCount();
    
}
