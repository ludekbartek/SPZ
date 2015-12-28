/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Attachment;
import cz.dcb.support.db.jpa.entities.Attachmentnote;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author bar
 */

@SuppressWarnings("unchecked")
public class AttachmentNoteJpaController implements Serializable, AttachmentNoteManager {

    public AttachmentNoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Attachmentnote attachmentnote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(attachmentnote);
            em.getTransaction().commit();
            em.refresh(attachmentnote);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Attachmentnote attachmentnote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            attachmentnote = em.merge(attachmentnote);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attachmentnote.getId();
                if (findAttachmentnote(id) == null) {
                    throw new NonexistentEntityException("The attachmentnote with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attachmentnote attachmentnote;
            try {
                attachmentnote = em.getReference(Attachmentnote.class, id);
                attachmentnote.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attachmentnote with id " + id + " no longer exists.", enfe);
            }
            em.remove(attachmentnote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Attachmentnote> findAttachmentnoteEntities() {
        return findAttachmentnoteEntities(true, -1, -1);
    }

    @Override
    public List<Attachmentnote> findAttachmentnoteEntities(int maxResults, int firstResult) {
        return findAttachmentnoteEntities(false, maxResults, firstResult);
    }

    private List<Attachmentnote> findAttachmentnoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attachmentnote.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Attachmentnote findAttachmentnote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attachmentnote.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getAttachmentnoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attachmentnote> rt = cq.from(Attachmentnote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Attachment> getAttachmentsForNote(Integer id) {
        EntityManager em = getEntityManager();
        List<Attachment> attachments = null;
        List<Attachment> result = null;
        try{
            Query queryIds = em.createQuery("select ATTACHNOTE.id from Attachmentnote attachnote where ATTACHNOTE.spznoteid = :noteid");
            List<Integer> attachmentNoteIds = queryIds.getResultList();
            result = new ArrayList<>();
            for(int ident :attachmentNoteIds){
               Query query;
                query = em.createQuery("select ATTACHMENT from Attachment attachment where ATTACHMENT.id = :atid");
               query.setParameter(":atid", ident);
               attachments = query.getResultList();
               result.addAll(attachments);
            }
        }catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE,String.format("Error retrieving attachment with id = %d: %s",id,ex));
        }
        return result;
    }
    
}
