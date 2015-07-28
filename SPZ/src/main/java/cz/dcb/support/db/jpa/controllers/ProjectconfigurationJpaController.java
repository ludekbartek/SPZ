/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Projectconfiguration;
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
public class ProjectconfigurationJpaController implements Serializable {

    public ProjectconfigurationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Projectconfiguration projectconfiguration) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(projectconfiguration);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Projectconfiguration projectconfiguration) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            projectconfiguration = em.merge(projectconfiguration);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = projectconfiguration.getId();
                if (findProjectconfiguration(id) == null) {
                    throw new NonexistentEntityException("The projectconfiguration with id " + id + " no longer exists.");
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
            Projectconfiguration projectconfiguration;
            try {
                projectconfiguration = em.getReference(Projectconfiguration.class, id);
                projectconfiguration.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The projectconfiguration with id " + id + " no longer exists.", enfe);
            }
            em.remove(projectconfiguration);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Projectconfiguration> findProjectconfigurationEntities() {
        return findProjectconfigurationEntities(true, -1, -1);
    }

    public List<Projectconfiguration> findProjectconfigurationEntities(int maxResults, int firstResult) {
        return findProjectconfigurationEntities(false, maxResults, firstResult);
    }

    private List<Projectconfiguration> findProjectconfigurationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Projectconfiguration.class));
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

    public Projectconfiguration findProjectconfiguration(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Projectconfiguration.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectconfigurationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Projectconfiguration> rt = cq.from(Projectconfiguration.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
