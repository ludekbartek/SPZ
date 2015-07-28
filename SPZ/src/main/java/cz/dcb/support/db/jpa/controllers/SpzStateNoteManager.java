/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzstatenote;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface SpzStateNoteManager {

    void create(Spzstatenote spzstatenote);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Spzstatenote spzstatenote) throws NonexistentEntityException, Exception;

    Spzstatenote findSpzstatenote(Integer id);

    List<Spzstatenote> findSpzstatenoteEntities();

    List<Spzstatenote> findSpzstatenoteEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSpzstatenoteCount();
    
}
