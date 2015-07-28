/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Attachmentnote;
import java.io.Serializable;
import java.util.List;
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
public class AttachmentnoteJpaController implements Serializable {

    public AttachmentnoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Attachmentnote attachmentnote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(attachmentnote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

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

    public List<Attachmentnote> findAttachmentnoteEntities() {
        return findAttachmentnoteEntities(true, -1, -1);
    }

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

    public Attachmentnote findAttachmentnote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attachmentnote.class, id);
        } finally {
            em.close();
        }
    }

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
    
}
