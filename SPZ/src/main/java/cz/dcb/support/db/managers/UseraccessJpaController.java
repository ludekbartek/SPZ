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
import cz.dcb.support.db.jpa.Configuration;
import cz.dcb.support.db.jpa.User;
import cz.dcb.support.db.jpa.Useraccess;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author bar
 */
public class UseraccessJpaController implements Serializable, UserAccessManager {

    public UseraccessJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Useraccess useraccess) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Configuration configurationId = useraccess.getConfigurationId();
            if (configurationId != null) {
                configurationId = em.getReference(configurationId.getClass(), configurationId.getId());
                useraccess.setConfigurationId(configurationId);
            }
            User login = useraccess.getLogin();
            if (login != null) {
                login = em.getReference(login.getClass(), login.getLogin());
                useraccess.setLogin(login);
            }
            em.persist(useraccess);
            if (configurationId != null) {
                configurationId.getUseraccessCollection().add(useraccess);
                configurationId = em.merge(configurationId);
            }
            if (login != null) {
                login.getUseraccessCollection().add(useraccess);
                login = em.merge(login);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUseraccess(useraccess.getId()) != null) {
                throw new PreexistingEntityException("Useraccess " + useraccess + " already exists.", ex);
            }
            throw ex;
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
            Useraccess persistentUseraccess = em.find(Useraccess.class, useraccess.getId());
            Configuration configurationIdOld = persistentUseraccess.getConfigurationId();
            Configuration configurationIdNew = useraccess.getConfigurationId();
            User loginOld = persistentUseraccess.getLogin();
            User loginNew = useraccess.getLogin();
            if (configurationIdNew != null) {
                configurationIdNew = em.getReference(configurationIdNew.getClass(), configurationIdNew.getId());
                useraccess.setConfigurationId(configurationIdNew);
            }
            if (loginNew != null) {
                loginNew = em.getReference(loginNew.getClass(), loginNew.getLogin());
                useraccess.setLogin(loginNew);
            }
            useraccess = em.merge(useraccess);
            if (configurationIdOld != null && !configurationIdOld.equals(configurationIdNew)) {
                configurationIdOld.getUseraccessCollection().remove(useraccess);
                configurationIdOld = em.merge(configurationIdOld);
            }
            if (configurationIdNew != null && !configurationIdNew.equals(configurationIdOld)) {
                configurationIdNew.getUseraccessCollection().add(useraccess);
                configurationIdNew = em.merge(configurationIdNew);
            }
            if (loginOld != null && !loginOld.equals(loginNew)) {
                loginOld.getUseraccessCollection().remove(useraccess);
                loginOld = em.merge(loginOld);
            }
            if (loginNew != null && !loginNew.equals(loginOld)) {
                loginNew.getUseraccessCollection().add(useraccess);
                loginNew = em.merge(loginNew);
            }
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
            Configuration configurationId = useraccess.getConfigurationId();
            if (configurationId != null) {
                configurationId.getUseraccessCollection().remove(useraccess);
                configurationId = em.merge(configurationId);
            }
            User login = useraccess.getLogin();
            if (login != null) {
                login.getUseraccessCollection().remove(useraccess);
                login = em.merge(login);
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
    
}
