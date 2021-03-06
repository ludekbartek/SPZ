/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzissuer;
import cz.dcb.support.db.jpa.entities.User;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

@SuppressWarnings("unchecked")
/**
 *
 * @author bar
 */
public class SpzIssuerJpaController implements Serializable, SpzIssuerManager {

    public SpzIssuerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Spzissuer spzissuer) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(spzissuer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Spzissuer spzissuer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            spzissuer = em.merge(spzissuer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spzissuer.getId();
                if (findSpzissuer(id) == null) {
                    throw new NonexistentEntityException("The spzissuer with id " + id + " no longer exists.");
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
            Spzissuer spzissuer;
            try {
                spzissuer = em.getReference(Spzissuer.class, id);
                spzissuer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spzissuer with id " + id + " no longer exists.", enfe);
            }
            em.remove(spzissuer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Spzissuer> findSpzissuerEntities() {
        return findSpzissuerEntities(true, -1, -1);
    }

    @Override
    public List<Spzissuer> findSpzissuerEntities(int maxResults, int firstResult) {
        return findSpzissuerEntities(false, maxResults, firstResult);
    }

    private List<Spzissuer> findSpzissuerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spzissuer.class));
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
    public Spzissuer findSpzissuer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spzissuer.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getSpzissuerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spzissuer> rt = cq.from(Spzissuer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Integer findSpzIssuerIdBySpzId(Integer spzId) {
        EntityManager em = getEntityManager();
        try{
            Query select = em.createQuery("select spzis.userid from Spzissuer spzis where spzis.spzid = :spzId");
            select.setParameter("spzId", spzId);
            if(!select.getResultList().isEmpty())
                return (Integer)select.getSingleResult();
            return -1;
        }finally{
            em.close();
        }
    }
    
}
