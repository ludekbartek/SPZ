/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Useraccess;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface UserAccesManager {

    void create(Useraccess useraccess);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Useraccess useraccess) throws NonexistentEntityException, Exception;

    Useraccess findUseraccess(Integer id);

    List<Useraccess> findUseraccessEntities();

    List<Useraccess> findUseraccessEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getUseraccessCount();
    
}
