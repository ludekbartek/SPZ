/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.User;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;

/**
 *
 * @author bar
 */
public interface UserManager {

    /**
     * Vytvori noveho uzivatele
     * @param user uzivatel, ktery se ma vytvorit.
     * @throws PreexistingEntityException dany uzivatel uz existuje.
     * @throws Exception  jina JPA vyjimka.
     */
    void create(User user) throws PreexistingEntityException, Exception;

    /**
     * Smaze uzivatele
     * @param id klic mazaneho uzivatele.
     * @throws IllegalOrphanException Mazany uzivatel je odkazovan z jine entity.
     *                                Mozne naruseni konzistence dat.
     * @throws NonexistentEntityException  mazany uzivatel neexistuje.
     */
    void destroy(String id) throws IllegalOrphanException, NonexistentEntityException;

    /**
     * Modifikace uzivatele.
     * @param user nove udaje modifikovaneho uzivatele.
     * @throws IllegalOrphanException Meneny uzivatel je odkazovan z jine entity.
     *                                Mozne naruseni konzistence dat.
     * @throws NonexistentEntityException meneny uzivatel neexistuje.
     * @throws Exception Jina vyjimka vznikla pri modifikaci uzivatele.
     */
    void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception;

    /**
     * Vraci uzivatele s danym klicem.
     * @param id klic uzivatele.
     * @return Uzivatel s klicem id.
     */
    User findUser(String id);

    /**
     * Vraci vsechny uzivatele.
     * @return vsichni uzivatele v systemu.
     */
    List<User> findUserEntities();

    /**
     * Vraci interval maxResults uzivatelu pocinaje uzivatelem s id firstResult.
     * @param maxResults maximalni pocet vracenych uzivatel.
     * @param firstResult Prvni vraceny uzivatel.
     * @return interval uzivatelu.
     */
    List<User> findUserEntities(int maxResults, int firstResult);

    /**
     * Vraci pocet uzivatelu.
     * @return pocet uzivatelu v systemu.
     */
    int getUserCount();
    
}
