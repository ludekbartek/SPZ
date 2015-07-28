/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Attachmentnote;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface AttachmentNoteManager {

    void create(Attachmentnote attachmentnote);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Attachmentnote attachmentnote) throws NonexistentEntityException, Exception;

    Attachmentnote findAttachmentnote(Integer id);

    List<Attachmentnote> findAttachmentnoteEntities();

    List<Attachmentnote> findAttachmentnoteEntities(int maxResults, int firstResult);

    int getAttachmentnoteCount();

    EntityManager getEntityManager();
    
}
