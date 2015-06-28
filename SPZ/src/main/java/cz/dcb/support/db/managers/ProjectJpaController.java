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
import cz.dcb.support.db.jpa.Project;
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
public class ProjectJpaController implements Serializable, ProjectManager {

    public ProjectJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Project project) throws PreexistingEntityException, Exception {
        if (project.getConfigurationCollection() == null) {
            project.setConfigurationCollection(new ArrayList<Configuration>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Configuration> attachedConfigurationCollection = new ArrayList<Configuration>();
            for (Configuration configurationCollectionConfigurationToAttach : project.getConfigurationCollection()) {
                configurationCollectionConfigurationToAttach = em.getReference(configurationCollectionConfigurationToAttach.getClass(), configurationCollectionConfigurationToAttach.getId());
                attachedConfigurationCollection.add(configurationCollectionConfigurationToAttach);
            }
            project.setConfigurationCollection(attachedConfigurationCollection);
            em.persist(project);
            for (Configuration configurationCollectionConfiguration : project.getConfigurationCollection()) {
                Project oldProjectCodeOfConfigurationCollectionConfiguration = configurationCollectionConfiguration.getProjectCode();
                configurationCollectionConfiguration.setProjectCode(project);
                configurationCollectionConfiguration = em.merge(configurationCollectionConfiguration);
                if (oldProjectCodeOfConfigurationCollectionConfiguration != null) {
                    oldProjectCodeOfConfigurationCollectionConfiguration.getConfigurationCollection().remove(configurationCollectionConfiguration);
                    oldProjectCodeOfConfigurationCollectionConfiguration = em.merge(oldProjectCodeOfConfigurationCollectionConfiguration);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProject(project.getName()) != null) {
                throw new PreexistingEntityException("Project " + project + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project persistentProject = em.find(Project.class, project.getName());
            Collection<Configuration> configurationCollectionOld = persistentProject.getConfigurationCollection();
            Collection<Configuration> configurationCollectionNew = project.getConfigurationCollection();
            List<String> illegalOrphanMessages = null;
            for (Configuration configurationCollectionOldConfiguration : configurationCollectionOld) {
                if (!configurationCollectionNew.contains(configurationCollectionOldConfiguration)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Configuration " + configurationCollectionOldConfiguration + " since its projectCode field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Configuration> attachedConfigurationCollectionNew = new ArrayList<Configuration>();
            for (Configuration configurationCollectionNewConfigurationToAttach : configurationCollectionNew) {
                configurationCollectionNewConfigurationToAttach = em.getReference(configurationCollectionNewConfigurationToAttach.getClass(), configurationCollectionNewConfigurationToAttach.getId());
                attachedConfigurationCollectionNew.add(configurationCollectionNewConfigurationToAttach);
            }
            configurationCollectionNew = attachedConfigurationCollectionNew;
            project.setConfigurationCollection(configurationCollectionNew);
            project = em.merge(project);
            for (Configuration configurationCollectionNewConfiguration : configurationCollectionNew) {
                if (!configurationCollectionOld.contains(configurationCollectionNewConfiguration)) {
                    Project oldProjectCodeOfConfigurationCollectionNewConfiguration = configurationCollectionNewConfiguration.getProjectCode();
                    configurationCollectionNewConfiguration.setProjectCode(project);
                    configurationCollectionNewConfiguration = em.merge(configurationCollectionNewConfiguration);
                    if (oldProjectCodeOfConfigurationCollectionNewConfiguration != null && !oldProjectCodeOfConfigurationCollectionNewConfiguration.equals(project)) {
                        oldProjectCodeOfConfigurationCollectionNewConfiguration.getConfigurationCollection().remove(configurationCollectionNewConfiguration);
                        oldProjectCodeOfConfigurationCollectionNewConfiguration = em.merge(oldProjectCodeOfConfigurationCollectionNewConfiguration);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = project.getName();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
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
    public void destroy(String id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getName();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Configuration> configurationCollectionOrphanCheck = project.getConfigurationCollection();
            for (Configuration configurationCollectionOrphanCheckConfiguration : configurationCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Configuration " + configurationCollectionOrphanCheckConfiguration + " in its configurationCollection field has a non-nullable projectCode field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(project);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    @Override
    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Project.class));
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
    public Project findProject(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Project> rt = cq.from(Project.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
