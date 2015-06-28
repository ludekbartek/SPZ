/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cz.dcb.support.db.jpa.User;
import cz.dcb.support.db.jpa.Configuration;
import cz.dcb.support.db.jpa.Spz;
import cz.dcb.support.db.jpa.Spzstate;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author bar
 */
public class SpzJpaController implements Serializable {

    public SpzJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Spz spz) throws PreexistingEntityException, Exception {
        if (spz.getSpzstateCollection() == null) {
            spz.setSpzstateCollection(new ArrayList<Spzstate>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User developerLogin = spz.getDeveloperLogin();
            if (developerLogin != null) {
                developerLogin = em.getReference(developerLogin.getClass(), developerLogin.getLogin());
                spz.setDeveloperLogin(developerLogin);
            }
            User analystLogin = spz.getAnalystLogin();
            if (analystLogin != null) {
                analystLogin = em.getReference(analystLogin.getClass(), analystLogin.getLogin());
                spz.setAnalystLogin(analystLogin);
            }
            Configuration configurationId = spz.getConfigurationId();
            if (configurationId != null) {
                configurationId = em.getReference(configurationId.getClass(), configurationId.getId());
                spz.setConfigurationId(configurationId);
            }
            Collection<Spzstate> attachedSpzstateCollection = new ArrayList<Spzstate>();
            for (Spzstate spzstateCollectionSpzstateToAttach : spz.getSpzstateCollection()) {
                spzstateCollectionSpzstateToAttach = em.getReference(spzstateCollectionSpzstateToAttach.getClass(), spzstateCollectionSpzstateToAttach.getId());
                attachedSpzstateCollection.add(spzstateCollectionSpzstateToAttach);
            }
            spz.setSpzstateCollection(attachedSpzstateCollection);
            em.persist(spz);
            if (developerLogin != null) {
                developerLogin.getSpzCollection().add(spz);
                developerLogin = em.merge(developerLogin);
            }
            if (analystLogin != null) {
                analystLogin.getSpzCollection().add(spz);
                analystLogin = em.merge(analystLogin);
            }
            if (configurationId != null) {
                configurationId.getSpzCollection().add(spz);
                configurationId = em.merge(configurationId);
            }
            for (Spzstate spzstateCollectionSpzstate : spz.getSpzstateCollection()) {
                Spz oldSpzIdOfSpzstateCollectionSpzstate = spzstateCollectionSpzstate.getSpzId();
                spzstateCollectionSpzstate.setSpzId(spz);
                spzstateCollectionSpzstate = em.merge(spzstateCollectionSpzstate);
                if (oldSpzIdOfSpzstateCollectionSpzstate != null) {
                    oldSpzIdOfSpzstateCollectionSpzstate.getSpzstateCollection().remove(spzstateCollectionSpzstate);
                    oldSpzIdOfSpzstateCollectionSpzstate = em.merge(oldSpzIdOfSpzstateCollectionSpzstate);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSpz(spz.getId()) != null) {
                throw new PreexistingEntityException("Spz " + spz + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Spz spz) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Spz persistentSpz = em.find(Spz.class, spz.getId());
            User developerLoginOld = persistentSpz.getDeveloperLogin();
            User developerLoginNew = spz.getDeveloperLogin();
            User analystLoginOld = persistentSpz.getAnalystLogin();
            User analystLoginNew = spz.getAnalystLogin();
            Configuration configurationIdOld = persistentSpz.getConfigurationId();
            Configuration configurationIdNew = spz.getConfigurationId();
            Collection<Spzstate> spzstateCollectionOld = persistentSpz.getSpzstateCollection();
            Collection<Spzstate> spzstateCollectionNew = spz.getSpzstateCollection();
            List<String> illegalOrphanMessages = null;
            for (Spzstate spzstateCollectionOldSpzstate : spzstateCollectionOld) {
                if (!spzstateCollectionNew.contains(spzstateCollectionOldSpzstate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Spzstate " + spzstateCollectionOldSpzstate + " since its spzId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (developerLoginNew != null) {
                developerLoginNew = em.getReference(developerLoginNew.getClass(), developerLoginNew.getLogin());
                spz.setDeveloperLogin(developerLoginNew);
            }
            if (analystLoginNew != null) {
                analystLoginNew = em.getReference(analystLoginNew.getClass(), analystLoginNew.getLogin());
                spz.setAnalystLogin(analystLoginNew);
            }
            if (configurationIdNew != null) {
                configurationIdNew = em.getReference(configurationIdNew.getClass(), configurationIdNew.getId());
                spz.setConfigurationId(configurationIdNew);
            }
            Collection<Spzstate> attachedSpzstateCollectionNew = new ArrayList<Spzstate>();
            for (Spzstate spzstateCollectionNewSpzstateToAttach : spzstateCollectionNew) {
                spzstateCollectionNewSpzstateToAttach = em.getReference(spzstateCollectionNewSpzstateToAttach.getClass(), spzstateCollectionNewSpzstateToAttach.getId());
                attachedSpzstateCollectionNew.add(spzstateCollectionNewSpzstateToAttach);
            }
            spzstateCollectionNew = attachedSpzstateCollectionNew;
            spz.setSpzstateCollection(spzstateCollectionNew);
            spz = em.merge(spz);
            if (developerLoginOld != null && !developerLoginOld.equals(developerLoginNew)) {
                developerLoginOld.getSpzCollection().remove(spz);
                developerLoginOld = em.merge(developerLoginOld);
            }
            if (developerLoginNew != null && !developerLoginNew.equals(developerLoginOld)) {
                developerLoginNew.getSpzCollection().add(spz);
                developerLoginNew = em.merge(developerLoginNew);
            }
            if (analystLoginOld != null && !analystLoginOld.equals(analystLoginNew)) {
                analystLoginOld.getSpzCollection().remove(spz);
                analystLoginOld = em.merge(analystLoginOld);
            }
            if (analystLoginNew != null && !analystLoginNew.equals(analystLoginOld)) {
                analystLoginNew.getSpzCollection().add(spz);
                analystLoginNew = em.merge(analystLoginNew);
            }
            if (configurationIdOld != null && !configurationIdOld.equals(configurationIdNew)) {
                configurationIdOld.getSpzCollection().remove(spz);
                configurationIdOld = em.merge(configurationIdOld);
            }
            if (configurationIdNew != null && !configurationIdNew.equals(configurationIdOld)) {
                configurationIdNew.getSpzCollection().add(spz);
                configurationIdNew = em.merge(configurationIdNew);
            }
            for (Spzstate spzstateCollectionNewSpzstate : spzstateCollectionNew) {
                if (!spzstateCollectionOld.contains(spzstateCollectionNewSpzstate)) {
                    Spz oldSpzIdOfSpzstateCollectionNewSpzstate = spzstateCollectionNewSpzstate.getSpzId();
                    spzstateCollectionNewSpzstate.setSpzId(spz);
                    spzstateCollectionNewSpzstate = em.merge(spzstateCollectionNewSpzstate);
                    if (oldSpzIdOfSpzstateCollectionNewSpzstate != null && !oldSpzIdOfSpzstateCollectionNewSpzstate.equals(spz)) {
                        oldSpzIdOfSpzstateCollectionNewSpzstate.getSpzstateCollection().remove(spzstateCollectionNewSpzstate);
                        oldSpzIdOfSpzstateCollectionNewSpzstate = em.merge(oldSpzIdOfSpzstateCollectionNewSpzstate);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spz.getId();
                if (findSpz(id) == null) {
                    throw new NonexistentEntityException("The spz with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Spz spz;
            try {
                spz = em.getReference(Spz.class, id);
                spz.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spz with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Spzstate> spzstateCollectionOrphanCheck = spz.getSpzstateCollection();
            for (Spzstate spzstateCollectionOrphanCheckSpzstate : spzstateCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Spz (" + spz + ") cannot be destroyed since the Spzstate " + spzstateCollectionOrphanCheckSpzstate + " in its spzstateCollection field has a non-nullable spzId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User developerLogin = spz.getDeveloperLogin();
            if (developerLogin != null) {
                developerLogin.getSpzCollection().remove(spz);
                developerLogin = em.merge(developerLogin);
            }
            User analystLogin = spz.getAnalystLogin();
            if (analystLogin != null) {
                analystLogin.getSpzCollection().remove(spz);
                analystLogin = em.merge(analystLogin);
            }
            Configuration configurationId = spz.getConfigurationId();
            if (configurationId != null) {
                configurationId.getSpzCollection().remove(spz);
                configurationId = em.merge(configurationId);
            }
            em.remove(spz);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Spz> findSpzEntities() {
        return findSpzEntities(true, -1, -1);
    }

    public List<Spz> findSpzEntities(int maxResults, int firstResult) {
        return findSpzEntities(false, maxResults, firstResult);
    }

    private List<Spz> findSpzEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spz.class));
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

    public Spz findSpz(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spz.class, id);
        } finally {
            em.close();
        }
    }

    public int getSpzCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spz> rt = cq.from(Spz.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
