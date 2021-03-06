/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.SpzStates;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.Spzstates;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author bar
 */
public interface SpzStatesManager {

    void create(Spzstates spzstates);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Spzstates spzstates) throws NonexistentEntityException, Exception;

    Spzstates findSpzstates(Integer id);

    List<Spzstates> findSpzstatesEntities();

    List<Spzstates> findSpzstatesEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSpzstatesCount();
    
    List<Spzstate> findSpzstates(Spz spz);

    Spzstate getCurrentState(Spz spz);

    public void create(Spzstates states, EntityManager em);
    
}
