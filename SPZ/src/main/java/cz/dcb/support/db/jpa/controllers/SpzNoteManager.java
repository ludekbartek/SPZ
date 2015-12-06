/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spznote;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface SpzNoteManager {

    void create(Spznote spznote);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Spznote spznote) throws NonexistentEntityException, Exception;

    Spznote findSpznote(Integer id);

    List<Spznote> findSpznoteEntities();

    List<Spznote> findSpznoteEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSpznoteCount();
    
}
