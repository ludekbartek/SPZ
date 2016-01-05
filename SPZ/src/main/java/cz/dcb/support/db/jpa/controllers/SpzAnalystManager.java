/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzanalyst;
import cz.dcb.support.db.jpa.entities.User;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface SpzAnalystManager {

    void create(Spzanalyst spzanalyst);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Spzanalyst spzanalyst) throws NonexistentEntityException, Exception;

    Spzanalyst findSpzanalyst(Integer id);

    List<Spzanalyst> findSpzanalystEntities();

    List<Spzanalyst> findSpzanalystEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSpzanalystCount();

    public int findSpzanalystUserId(Integer spzId);
    
}
