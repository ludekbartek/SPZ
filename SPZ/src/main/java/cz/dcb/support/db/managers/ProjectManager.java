/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Project;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 * Rozhrani definujici chovani spravce projektu.
 * @author bar
 */
public interface ProjectManager {

    /**
     * Vytvori a prida do DB novy projekt.
     * @param project projekt, ktery se ma vytvorit a pridat. 
     * @throws PreexistingEntityException - projekt s hodnotou klice,
     *                             ktera odpovida hodnote klice parametru
     *                             projekt v DB existuje.
     * @throws Exception - propoustena vyjimka z JPA.
     */
    void create(Project project) throws PreexistingEntityException, Exception;

    /**
     * Smaze projekt z DB.
     * @param id hodnota klice projektu, ktery se ma smazat.
     * @throws IllegalOrphanException - projekt je odkazovan jinou entitou 
     *                      v DB.
     * @throws NonexistentEntityException - projekt s danym klicem v db 
     *                      neexistuje.
     */
    void destroy(String id) throws IllegalOrphanException, NonexistentEntityException;

    /**
     * Modifikuje projekt v DB.
     * @param project - nova hodnota projektu s klicem shodnym s klicem
     *                  parametru.
     * @throws IllegalOrphanException - projekt je odkazovan jinou entitou,
     *                  moznost poruseni konzistence dat.
     * @throws NonexistentEntityException - projekt v DB neexistuje.
     * @throws Exception - jina propoustena vyjimka z JPA.
     */
    void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, Exception;

    /**
     * Vyhleda v DB projekt s danym id.
     * @param id - klic hledaneho projektu.
     * @return Hledany projekt.
     */
    Project findProject(String id);

    /**
     * Vrati vsechny projekty v DB.
     * @return - seznam vsech projektu, ktere jsou v DB
     */
    List<Project> findProjectEntities();
    
    /**
     * Vraci dany interval projektu.
     * @param maxResults - maximalni pocet vracenych projektu.
     * @param firstResult  - klic prvniho vraceneho projektu.
     * @return seznam projektu v danem intervalu.
     */
    List<Project> findProjectEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet projektu v DB.
     * @return pocet projektu v DB.
     */
    int getProjectCount();
    
}
