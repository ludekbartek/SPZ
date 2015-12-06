/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.User;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface UserManager {

    void create(User user);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(User user) throws NonexistentEntityException, Exception;

    User findUser(Integer id);

    User findUserByLogin(String login);
    
    List<User> findUserEntities();

    List<User> findUserEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getUserCount();
    
}
