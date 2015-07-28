/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzissuer;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface SpzIssuerManager {

    void create(Spzissuer spzissuer);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Spzissuer spzissuer) throws NonexistentEntityException, Exception;

    Spzissuer findSpzissuer(Integer id);

    List<Spzissuer> findSpzissuerEntities();

    List<Spzissuer> findSpzissuerEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getSpzissuerCount();
    
}
