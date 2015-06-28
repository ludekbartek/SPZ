/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Roles;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface RoleManager {
    
    /**
     * Vytvori novou roli.
     * @param role role, ktera se ma vytvorit. 
     * @throws PreexistingEntityException - role jiz v DB existuje.
     * @throws Exception - jina JPA vyjimka.
     */
    void create(Roles role) throws PreexistingEntityException, Exception;
    
    /**
     * Smaze roli. 
     * @param id id role, ktera se ma smazat.
     * @throws NonexistentEntityException - role v db neexistuje.
     */
    void destroy(String id) throws NonexistentEntityException;
    
    /**
     * Zmena udaju o roli.
     * @param role nove udaje o roli s id = role.id.
     * @throws NonexistentEntityException - role v db neexistuje.
     * @throws Exception - jina JPA vyjimka.
     */
    void edit(Roles role) throws NonexistentEntityException, Exception;

    /**
     * Vrati roli s danym id.
     * @param id id hledane role.
     * @return nalezenou roli.
     */
    Roles findRoles(String id);

    /**
     * Vrati vsechny role.
     * @return Seznam vsech roli obsazenych v systemu.
     */
    List<Roles> findRolesEntities();

    /**
     * Vrati seznam roli v danem rozsahu.     
     * @param maxResults maximalni pocet roli, ktere maji byt vraceny
     * @param firstResult id prvni role ve vracenem seznamu.
     * @return seznam roli v danem rozsahu.
     */
    List<Roles> findRolesEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet roli.
     * @return pocet roli.
     */
    int getRolesCount();
    
}
