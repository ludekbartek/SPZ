/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Useraccess;
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
public class UserAccessJpaController implements Serializable, UserAccessManager {

    public UserAccessJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Useraccess useraccess) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(useraccess);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Useraccess useraccess) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            useraccess = em.merge(useraccess);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = useraccess.getId();
                if (findUseraccess(id) == null) {
                    throw new NonexistentEntityException("The useraccess with id " + id + " no longer exists.");
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
            Useraccess useraccess;
            try {
                useraccess = em.getReference(Useraccess.class, id);
                useraccess.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The useraccess with id " + id + " no longer exists.", enfe);
            }
            em.remove(useraccess);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Useraccess> findUseraccessEntities() {
        return findUseraccessEntities(true, -1, -1);
    }

    @Override
    public List<Useraccess> findUseraccessEntities(int maxResults, int firstResult) {
        return findUseraccessEntities(false, maxResults, firstResult);
    }

    private List<Useraccess> findUseraccessEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Useraccess.class));
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
    public Useraccess findUseraccess(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Useraccess.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getUseraccessCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Useraccess> rt = cq.from(Useraccess.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Useraccess> findUseraccessEntities(int userId) {
        EntityManager em = getEntityManager();
        List<Useraccess> roles = null;
        try{
            Query query = em.createQuery("select access from Useraccess access where access.userid=:userid");
            query.setParameter("userid", userId);
            roles = query.getResultList();
        }finally{
            em.close();
        }
        return roles;
    }
    
}
