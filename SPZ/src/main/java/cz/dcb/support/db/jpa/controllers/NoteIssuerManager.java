/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Noteissuer;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface NoteIssuerManager {

    void create(Noteissuer noteissuer);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Noteissuer noteissuer) throws NonexistentEntityException, Exception;

    Noteissuer findNoteissuer(Integer id);

    List<Noteissuer> findNoteissuerEntities();

    List<Noteissuer> findNoteissuerEntities(int maxResults, int firstResult);

    EntityManager getEntityManager();

    int getNoteissuerCount();
    
}
