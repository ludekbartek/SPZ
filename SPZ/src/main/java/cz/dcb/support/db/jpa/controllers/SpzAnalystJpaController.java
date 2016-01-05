/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spzanalyst;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
@SuppressWarnings( "unchecked" )
/**
 *
 * @author bar
 */
public class SpzAnalystJpaController implements Serializable, SpzAnalystManager {

    public SpzAnalystJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Spzanalyst spzanalyst) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(spzanalyst);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Spzanalyst spzanalyst) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            spzanalyst = em.merge(spzanalyst);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spzanalyst.getId();
                if (findSpzanalyst(id) == null) {
                    throw new NonexistentEntityException("The spzanalyst with id " + id + " no longer exists.");
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
            Spzanalyst spzanalyst;
            try {
                spzanalyst = em.getReference(Spzanalyst.class, id);
                spzanalyst.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spzanalyst with id " + id + " no longer exists.", enfe);
            }
            em.remove(spzanalyst);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Spzanalyst> findSpzanalystEntities() {
        return findSpzanalystEntities(true, -1, -1);
    }

    @Override
    public List<Spzanalyst> findSpzanalystEntities(int maxResults, int firstResult) {
        return findSpzanalystEntities(false, maxResults, firstResult);
    }

    private List<Spzanalyst> findSpzanalystEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spzanalyst.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return (List<Spzanalyst>)q.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Spzanalyst findSpzanalyst(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spzanalyst.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getSpzanalystCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<Spzanalyst> rt = cq.from(Spzanalyst.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public int findSpzanalystUserId(Integer spzId) {
        EntityManager em = getEntityManager();
        int analystId=-1;
        try{
            Query query = em.createQuery("select analyst.userid from Spzanalyst analyst where analyst.spzid=:spzid");
            query.setParameter("spzid", spzId);
            List<Integer> results = query.getResultList();
            if(!results.isEmpty()){
                    analystId = results.get(0);
            }
        }finally{
            em.close();
        }
        return analystId;
    }
    
}
