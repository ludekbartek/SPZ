/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface SpzManager {

    void create(Spz spz);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Spz spz) throws NonexistentEntityException, Exception;

    Spz findSpz(Integer id);

    List<Spz> findSpzEntities();

    List<Spz> findSpzEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSpzCount();
    
}
