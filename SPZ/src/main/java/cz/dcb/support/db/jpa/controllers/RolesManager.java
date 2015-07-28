/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Roles;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface RolesManager {

    void create(Roles roles);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Roles roles) throws NonexistentEntityException, Exception;

    Roles findRoles(Integer id);

    List<Roles> findRolesEntities();

    List<Roles> findRolesEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getRolesCount();
    
}
