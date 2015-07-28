/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzstatenote;
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
public class SpzstatenoteJpaController implements Serializable {

    public SpzstatenoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Spzstatenote spzstatenote) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(spzstatenote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Spzstatenote spzstatenote) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            spzstatenote = em.merge(spzstatenote);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spzstatenote.getId();
                if (findSpzstatenote(id) == null) {
                    throw new NonexistentEntityException("The spzstatenote with id " + id + " no longer exists.");
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
            Spzstatenote spzstatenote;
            try {
                spzstatenote = em.getReference(Spzstatenote.class, id);
                spzstatenote.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spzstatenote with id " + id + " no longer exists.", enfe);
            }
            em.remove(spzstatenote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Spzstatenote> findSpzstatenoteEntities() {
        return findSpzstatenoteEntities(true, -1, -1);
    }

    public List<Spzstatenote> findSpzstatenoteEntities(int maxResults, int firstResult) {
        return findSpzstatenoteEntities(false, maxResults, firstResult);
    }

    private List<Spzstatenote> findSpzstatenoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spzstatenote.class));
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

    public Spzstatenote findSpzstatenote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spzstatenote.class, id);
        } finally {
            em.close();
        }
    }

    public int getSpzstatenoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spzstatenote> rt = cq.from(Spzstatenote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
