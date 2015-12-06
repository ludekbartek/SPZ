/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Attachment;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author bar
 */
public interface AttachmentManager {

    void create(Attachment attachment);

    void destroy(Integer id) throws NonexistentEntityException;

    void edit(Attachment attachment) throws NonexistentEntityException, Exception;

    Attachment findAttachment(Integer id);

    List<Attachment> findAttachmentEntities();

    List<Attachment> findAttachmentEntities(int maxResults, int firstResult);

    int getAttachmentCount();

    EntityManager getEntityManager();
    
}
