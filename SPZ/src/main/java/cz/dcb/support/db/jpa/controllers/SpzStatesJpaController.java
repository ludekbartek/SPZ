/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.Spzstates;
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
public class SpzStatesJpaController implements Serializable, SpzStatesManager {

    public SpzStatesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Spzstates spzstates) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(spzstates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Spzstates spzstates) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            spzstates = em.merge(spzstates);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spzstates.getId();
                if (findSpzstates(id) == null) {
                    throw new NonexistentEntityException("The spzstates with id " + id + " no longer exists.");
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
            Spzstates spzstates;
            try {
                spzstates = em.getReference(Spzstates.class, id);
                spzstates.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spzstates with id " + id + " no longer exists.", enfe);
            }
            em.remove(spzstates);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Spzstates> findSpzstatesEntities() {
        return findSpzstatesEntities(true, -1, -1);
    }

    @Override
    public List<Spzstates> findSpzstatesEntities(int maxResults, int firstResult) {
        return findSpzstatesEntities(false, maxResults, firstResult);
    }

    private List<Spzstates> findSpzstatesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spzstates.class));
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
    public Spzstates findSpzstates(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spzstates.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getSpzstatesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spzstates> rt = cq.from(Spzstates.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
    @Override
    public List<Spzstate> findSpzstates(Spz spz) {
        EntityManager em = getEntityManager();
        List<Spzstate> states = null;
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Query query = em.createQuery("select state from Spzstate state where state.id in (select stateid from spzstates states where states.spzid=:spzid)");
            query.setParameter("spzid", spz.getId());
            states = (List<Spzstate>)query.getResultList();
        }finally{
            em.close();
        }
        return states;
    }
}
