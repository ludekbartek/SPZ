/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.Spzstate;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface SpzStateManager {

    void create(Spzstate spzstate);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Spzstate spzstate) throws NonexistentEntityException, Exception;

    Spzstate findSpzstate(Integer id);

    List<Spzstate> findSpzstateEntities();

    List<Spzstate> findSpzstateEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSpzstateCount();

    Date getLastChange(Integer id);

    public Spzstate getCurrentState(Spz spz);
    
    List<Spznote> getStateNotes(Integer spzNoteId);

    public void create(Spzstate state,EntityManager em);

    public String getSolution(Spz spz);

    public String getRevisedDescription(Spz spz);

}
