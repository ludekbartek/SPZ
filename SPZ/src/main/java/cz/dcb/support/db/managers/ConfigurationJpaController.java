/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Configuration;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cz.dcb.support.db.jpa.Project;
import cz.dcb.support.db.jpa.Spz;
import java.util.ArrayList;
import java.util.Collection;
import cz.dcb.support.db.jpa.Useraccess;
import cz.dcb.support.db.managers.exceptions.IllegalOrphanException;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author bar
 */
public class ConfigurationJpaController implements Serializable, ConfigurationManager {

    public ConfigurationJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Configuration configuration) throws PreexistingEntityException, Exception {
        if (configuration.getSpzCollection() == null) {
            configuration.setSpzCollection(new ArrayList<Spz>());
        }
        if (configuration.getUseraccessCollection() == null) {
            configuration.setUseraccessCollection(new ArrayList<Useraccess>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project projectCode = configuration.getProjectCode();
            if (projectCode != null) {
                projectCode = em.getReference(projectCode.getClass(), projectCode.getName());
                configuration.setProjectCode(projectCode);
            }
            Collection<Spz> attachedSpzCollection = new ArrayList<Spz>();
            for (Spz spzCollectionSpzToAttach : configuration.getSpzCollection()) {
                spzCollectionSpzToAttach = em.getReference(spzCollectionSpzToAttach.getClass(), spzCollectionSpzToAttach.getId());
                attachedSpzCollection.add(spzCollectionSpzToAttach);
            }
            configuration.setSpzCollection(attachedSpzCollection);
            Collection<Useraccess> attachedUseraccessCollection = new ArrayList<Useraccess>();
            for (Useraccess useraccessCollectionUseraccessToAttach : configuration.getUseraccessCollection()) {
                useraccessCollectionUseraccessToAttach = em.getReference(useraccessCollectionUseraccessToAttach.getClass(), useraccessCollectionUseraccessToAttach.getId());
                attachedUseraccessCollection.add(useraccessCollectionUseraccessToAttach);
            }
            configuration.setUseraccessCollection(attachedUseraccessCollection);
            em.persist(configuration);
            if (projectCode != null) {
                projectCode.getConfigurationCollection().add(configuration);
                projectCode = em.merge(projectCode);
            }
            for (Spz spzCollectionSpz : configuration.getSpzCollection()) {
                Configuration oldConfigurationIdOfSpzCollectionSpz = spzCollectionSpz.getConfigurationId();
                spzCollectionSpz.setConfigurationId(configuration);
                spzCollectionSpz = em.merge(spzCollectionSpz);
                if (oldConfigurationIdOfSpzCollectionSpz != null) {
                    oldConfigurationIdOfSpzCollectionSpz.getSpzCollection().remove(spzCollectionSpz);
                    oldConfigurationIdOfSpzCollectionSpz = em.merge(oldConfigurationIdOfSpzCollectionSpz);
                }
            }
            for (Useraccess useraccessCollectionUseraccess : configuration.getUseraccessCollection()) {
                Configuration oldConfigurationIdOfUseraccessCollectionUseraccess = useraccessCollectionUseraccess.getConfigurationId();
                useraccessCollectionUseraccess.setConfigurationId(configuration);
                useraccessCollectionUseraccess = em.merge(useraccessCollectionUseraccess);
                if (oldConfigurationIdOfUseraccessCollectionUseraccess != null) {
                    oldConfigurationIdOfUseraccessCollectionUseraccess.getUseraccessCollection().remove(useraccessCollectionUseraccess);
                    oldConfigurationIdOfUseraccessCollectionUseraccess = em.merge(oldConfigurationIdOfUseraccessCollectionUseraccess);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findConfiguration(configuration.getId()) != null) {
                throw new PreexistingEntityException("Configuration " + configuration + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Configuration configuration) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Configuration persistentConfiguration = em.find(Configuration.class, configuration.getId());
            Project projectCodeOld = persistentConfiguration.getProjectCode();
            Project projectCodeNew = configuration.getProjectCode();
            Collection<Spz> spzCollectionOld = persistentConfiguration.getSpzCollection();
            Collection<Spz> spzCollectionNew = configuration.getSpzCollection();
            Collection<Useraccess> useraccessCollectionOld = persistentConfiguration.getUseraccessCollection();
            Collection<Useraccess> useraccessCollectionNew = configuration.getUseraccessCollection();
            List<String> illegalOrphanMessages = null;
            for (Spz spzCollectionOldSpz : spzCollectionOld) {
                if (!spzCollectionNew.contains(spzCollectionOldSpz)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Spz " + spzCollectionOldSpz + " since its configurationId field is not nullable.");
                }
            }
            for (Useraccess useraccessCollectionOldUseraccess : useraccessCollectionOld) {
                if (!useraccessCollectionNew.contains(useraccessCollectionOldUseraccess)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Useraccess " + useraccessCollectionOldUseraccess + " since its configurationId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (projectCodeNew != null) {
                projectCodeNew = em.getReference(projectCodeNew.getClass(), projectCodeNew.getName());
                configuration.setProjectCode(projectCodeNew);
            }
            Collection<Spz> attachedSpzCollectionNew = new ArrayList<Spz>();
            for (Spz spzCollectionNewSpzToAttach : spzCollectionNew) {
                spzCollectionNewSpzToAttach = em.getReference(spzCollectionNewSpzToAttach.getClass(), spzCollectionNewSpzToAttach.getId());
                attachedSpzCollectionNew.add(spzCollectionNewSpzToAttach);
            }
            spzCollectionNew = attachedSpzCollectionNew;
            configuration.setSpzCollection(spzCollectionNew);
            Collection<Useraccess> attachedUseraccessCollectionNew = new ArrayList<Useraccess>();
            for (Useraccess useraccessCollectionNewUseraccessToAttach : useraccessCollectionNew) {
                useraccessCollectionNewUseraccessToAttach = em.getReference(useraccessCollectionNewUseraccessToAttach.getClass(), useraccessCollectionNewUseraccessToAttach.getId());
                attachedUseraccessCollectionNew.add(useraccessCollectionNewUseraccessToAttach);
            }
            useraccessCollectionNew = attachedUseraccessCollectionNew;
            configuration.setUseraccessCollection(useraccessCollectionNew);
            configuration = em.merge(configuration);
            if (projectCodeOld != null && !projectCodeOld.equals(projectCodeNew)) {
                projectCodeOld.getConfigurationCollection().remove(configuration);
                projectCodeOld = em.merge(projectCodeOld);
            }
            if (projectCodeNew != null && !projectCodeNew.equals(projectCodeOld)) {
                projectCodeNew.getConfigurationCollection().add(configuration);
                projectCodeNew = em.merge(projectCodeNew);
            }
            for (Spz spzCollectionNewSpz : spzCollectionNew) {
                if (!spzCollectionOld.contains(spzCollectionNewSpz)) {
                    Configuration oldConfigurationIdOfSpzCollectionNewSpz = spzCollectionNewSpz.getConfigurationId();
                    spzCollectionNewSpz.setConfigurationId(configuration);
                    spzCollectionNewSpz = em.merge(spzCollectionNewSpz);
                    if (oldConfigurationIdOfSpzCollectionNewSpz != null && !oldConfigurationIdOfSpzCollectionNewSpz.equals(configuration)) {
                        oldConfigurationIdOfSpzCollectionNewSpz.getSpzCollection().remove(spzCollectionNewSpz);
                        oldConfigurationIdOfSpzCollectionNewSpz = em.merge(oldConfigurationIdOfSpzCollectionNewSpz);
                    }
                }
            }
            for (Useraccess useraccessCollectionNewUseraccess : useraccessCollectionNew) {
                if (!useraccessCollectionOld.contains(useraccessCollectionNewUseraccess)) {
                    Configuration oldConfigurationIdOfUseraccessCollectionNewUseraccess = useraccessCollectionNewUseraccess.getConfigurationId();
                    useraccessCollectionNewUseraccess.setConfigurationId(configuration);
                    useraccessCollectionNewUseraccess = em.merge(useraccessCollectionNewUseraccess);
                    if (oldConfigurationIdOfUseraccessCollectionNewUseraccess != null && !oldConfigurationIdOfUseraccessCollectionNewUseraccess.equals(configuration)) {
                        oldConfigurationIdOfUseraccessCollectionNewUseraccess.getUseraccessCollection().remove(useraccessCollectionNewUseraccess);
                        oldConfigurationIdOfUseraccessCollectionNewUseraccess = em.merge(oldConfigurationIdOfUseraccessCollectionNewUseraccess);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = configuration.getId();
                if (findConfiguration(id) == null) {
                    throw new NonexistentEntityException("The configuration with id " + id + " no longer exists.");
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
    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Configuration configuration;
            try {
                configuration = em.getReference(Configuration.class, id);
                configuration.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configuration with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Spz> spzCollectionOrphanCheck = configuration.getSpzCollection();
            for (Spz spzCollectionOrphanCheckSpz : spzCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Configuration (" + configuration + ") cannot be destroyed since the Spz " + spzCollectionOrphanCheckSpz + " in its spzCollection field has a non-nullable configurationId field.");
            }
            Collection<Useraccess> useraccessCollectionOrphanCheck = configuration.getUseraccessCollection();
            for (Useraccess useraccessCollectionOrphanCheckUseraccess : useraccessCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Configuration (" + configuration + ") cannot be destroyed since the Useraccess " + useraccessCollectionOrphanCheckUseraccess + " in its useraccessCollection field has a non-nullable configurationId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Project projectCode = configuration.getProjectCode();
            if (projectCode != null) {
                projectCode.getConfigurationCollection().remove(configuration);
                projectCode = em.merge(projectCode);
            }
            em.remove(configuration);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Configuration> findConfigurationEntities() {
        return findConfigurationEntities(true, -1, -1);
    }

    @Override
    public List<Configuration> findConfigurationEntities(int maxResults, int firstResult) {
        return findConfigurationEntities(false, maxResults, firstResult);
    }

    private List<Configuration> findConfigurationEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Configuration.class));
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
    public Configuration findConfiguration(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Configuration.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getConfigurationCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Configuration> rt = cq.from(Configuration.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
