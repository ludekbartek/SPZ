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
public interface RolesManager {

    void create(Roles roles) throws PreexistingEntityException, Exception;

    void destroy(String id) throws NonexistentEntityException;

    void edit(Roles roles) throws NonexistentEntityException, Exception;

    Roles findRoles(String id);

    List<Roles> findRolesEntities();

    List<Roles> findRolesEntities(int maxResults, int firstResult);

    int getRolesCount();
    
}
