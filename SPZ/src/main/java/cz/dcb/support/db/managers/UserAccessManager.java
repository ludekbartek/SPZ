/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Useraccess;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface UserAccessManager {

    /**
     * Vytvori nova pristupova prava.
     * @param useraccess pristupova prava
     * @throws PreexistingEntityException pristupova prava jiz existuji.
     * @throws Exception Jina vyjimka pri pridavani pristupovych prav.
     */
    void create(Useraccess useraccess) throws PreexistingEntityException, Exception;

    /**
     * Odstrani pristupova prava.
     * @param id klic pristupovych prav.
     * @throws NonexistentEntityException pristupova prava neexistuji.  
     */
    void destroy(Integer id) throws NonexistentEntityException;

    /**
     * Upravuje pristupova prava.
     * @param useraccess nova pristupova prava pro entitu 
     *                   s klicem useraccess.id
     * @throws NonexistentEntityException menena pristupova prava neexistuji.
     * @throws Exception jina vyjimka pri pokusu o modifikaci uzivatelskych prav.
     */
    void edit(Useraccess useraccess) throws NonexistentEntityException, Exception;

    /**
     * Vraci pristupova prava s klicem id.
     * @param id klic pristupovych prav.
     * @return pristupova prava.
     */
    Useraccess findUseraccess(Integer id);

    /**
     * Seznam vsech pristupovych prav.
     * @return Seznam vsech pristupovych prav.
     */
    List<Useraccess> findUseraccessEntities();

    /**
     * Interval maxResults pristupovych prav pocinaje firstResult.
     * @param maxResults maximalni pocet pristupovych prav v seznamu.
     * @param firstResult prvni pristupova prava.
     * @return Interval pristupovych prav.
     */
    List<Useraccess> findUseraccessEntities(int maxResults, int firstResult);

    /**
     * Pocet pristupovych prav v systemu.
     * @return pocet zavedenych pristupovych prav.
     */
    int getUseraccessCount();
    
}
