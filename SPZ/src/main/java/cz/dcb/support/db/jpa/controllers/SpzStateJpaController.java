/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzstate;
import java.io.Serializable;
import java.util.Date;
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
public class SpzStateJpaController implements Serializable, SpzStateManager {

    public SpzStateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Spzstate spzstate) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(spzstate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Spzstate spzstate) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            spzstate = em.merge(spzstate);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spzstate.getId();
                if (findSpzstate(id) == null) {
                    throw new NonexistentEntityException("The spzstate with id " + id + " no longer exists.");
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
            Spzstate spzstate;
            try {
                spzstate = em.getReference(Spzstate.class, id);
                spzstate.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spzstate with id " + id + " no longer exists.", enfe);
            }
            em.remove(spzstate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Spzstate> findSpzstateEntities() {
        return findSpzstateEntities(true, -1, -1);
    }

    @Override
    public List<Spzstate> findSpzstateEntities(int maxResults, int firstResult) {
        return findSpzstateEntities(false, maxResults, firstResult);
    }

    private List<Spzstate> findSpzstateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spzstate.class));
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
    public Spzstate findSpzstate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spzstate.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getSpzstateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spzstate> rt = cq.from(Spzstate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Date getLastChange(Integer id) {
        EntityManager em = getEntityManager();
        Date date = null;
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Query query= em.createQuery("select max(state.idate) from Spzstate state where state.id=:stateid");
            query.setParameter("stateid", id);
            date = (Date)query.getSingleResult();
        }finally{
            em.close();
        }
        return date;
    }
    
}
