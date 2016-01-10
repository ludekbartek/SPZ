/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.controllers.exceptions.PreexistingEntityException;
import cz.dcb.support.db.jpa.entities.Configurationspz;
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
public class ConfigurationSpzJpaController implements ConfiguratinspzManager, ConfigurationSpzManager {

    public ConfigurationSpzJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Configurationspz configurationspz) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(configurationspz);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConfigurationspz(configurationspz.getId()) != null) {
                throw new PreexistingEntityException("Configurationspz " + configurationspz + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Configurationspz configurationspz) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            configurationspz = em.merge(configurationspz);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = configurationspz.getId();
                if (findConfigurationspz(id) == null) {
                    throw new NonexistentEntityException("The configurationspz with id " + id + " no longer exists.");
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
            Configurationspz configurationspz;
            try {
                configurationspz = em.getReference(Configurationspz.class, id);
                configurationspz.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configurationspz with id " + id + " no longer exists.", enfe);
            }
            em.remove(configurationspz);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Configurationspz> findConfigurationspzEntities() {
        return findConfigurationspzEntities(true, -1, -1);
    }

    @Override
    public List<Configurationspz> findConfigurationspzEntities(int maxResults, int firstResult) {
        return findConfigurationspzEntities(false, maxResults, firstResult);
    }

    private List<Configurationspz> findConfigurationspzEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Configurationspz.class));
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
    public Configurationspz findConfigurationspz(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Configurationspz.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getConfigurationspzCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Configurationspz> rt = cq.from(Configurationspz.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Integer getSpzConfiguration(int spzId) {
        Integer confId = null;
        EntityManager em=getEntityManager();
        try{
            Query query = em.createQuery("select CONFSPZ.configurationid from Configurationspz CONFSPZ where  CONFSPZ.spzid =:spzid");
            query.setParameter("spzid", spzId);
            List<Integer> confIds = query.getResultList();
            if(confIds.size()>0){
               confId=confIds.get(0);
            }else{
                confId=null;
            }
        }finally{
            em.close();
        }
        return confId;
    }
    
}
