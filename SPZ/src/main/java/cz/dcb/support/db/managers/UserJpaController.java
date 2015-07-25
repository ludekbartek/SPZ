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
import cz.dcb.support.db.jpa.Spznote;
import java.util.ArrayList;
import java.util.Collection;
import cz.dcb.support.db.jpa.Spzstate;
import cz.dcb.support.db.jpa.Spz;
import cz.dcb.support.db.jpa.User;
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
public class UserJpaController implements Serializable, UserManager {

    public UserJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(User user) throws PreexistingEntityException, Exception {
        if (user.getSpznoteCollection() == null) {
            user.setSpznoteCollection(new ArrayList<Spznote>());
        }
        if (user.getSpzstateCollection() == null) {
            user.setSpzstateCollection(new ArrayList<Spzstate>());
        }
        if (user.getSpzCollection() == null) {
            user.setSpzCollection(new ArrayList<Spz>());
        }
        if (user.getSpzCollection1() == null) {
            user.setSpzCollection1(new ArrayList<Spz>());
        }
        if (user.getUseraccessCollection() == null) {
            user.setUseraccessCollection(new ArrayList<Useraccess>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Spznote> attachedSpznoteCollection = new ArrayList<Spznote>();
            for (Spznote spznoteCollectionSpznoteToAttach : user.getSpznoteCollection()) {
                spznoteCollectionSpznoteToAttach = em.getReference(spznoteCollectionSpznoteToAttach.getClass(), spznoteCollectionSpznoteToAttach.getId());
                attachedSpznoteCollection.add(spznoteCollectionSpznoteToAttach);
            }
            user.setSpznoteCollection(attachedSpznoteCollection);
            Collection<Spzstate> attachedSpzstateCollection = new ArrayList<Spzstate>();
            for (Spzstate spzstateCollectionSpzstateToAttach : user.getSpzstateCollection()) {
                spzstateCollectionSpzstateToAttach = em.getReference(spzstateCollectionSpzstateToAttach.getClass(), spzstateCollectionSpzstateToAttach.getId());
                attachedSpzstateCollection.add(spzstateCollectionSpzstateToAttach);
            }
            user.setSpzstateCollection(attachedSpzstateCollection);
            Collection<Spz> attachedSpzCollection = new ArrayList<Spz>();
            for (Spz spzCollectionSpzToAttach : user.getSpzCollection()) {
                spzCollectionSpzToAttach = em.getReference(spzCollectionSpzToAttach.getClass(), spzCollectionSpzToAttach.getId());
                attachedSpzCollection.add(spzCollectionSpzToAttach);
            }
            user.setSpzCollection(attachedSpzCollection);
            Collection<Spz> attachedSpzCollection1 = new ArrayList<Spz>();
            for (Spz spzCollection1SpzToAttach : user.getSpzCollection1()) {
                spzCollection1SpzToAttach = em.getReference(spzCollection1SpzToAttach.getClass(), spzCollection1SpzToAttach.getId());
                attachedSpzCollection1.add(spzCollection1SpzToAttach);
            }
            user.setSpzCollection1(attachedSpzCollection1);
            Collection<Useraccess> attachedUseraccessCollection = new ArrayList<Useraccess>();
            for (Useraccess useraccessCollectionUseraccessToAttach : user.getUseraccessCollection()) {
                useraccessCollectionUseraccessToAttach = em.getReference(useraccessCollectionUseraccessToAttach.getClass(), useraccessCollectionUseraccessToAttach.getId());
                attachedUseraccessCollection.add(useraccessCollectionUseraccessToAttach);
            }
            user.setUseraccessCollection(attachedUseraccessCollection);
            em.persist(user);
            for (Spznote spznoteCollectionSpznote : user.getSpznoteCollection()) {
                User oldIssuerLoginOfSpznoteCollectionSpznote = spznoteCollectionSpznote.getIssuerLogin();
                spznoteCollectionSpznote.setIssuerLogin(user);
                spznoteCollectionSpznote = em.merge(spznoteCollectionSpznote);
                if (oldIssuerLoginOfSpznoteCollectionSpznote != null) {
                    oldIssuerLoginOfSpznoteCollectionSpznote.getSpznoteCollection().remove(spznoteCollectionSpznote);
                    oldIssuerLoginOfSpznoteCollectionSpznote = em.merge(oldIssuerLoginOfSpznoteCollectionSpznote);
                }
            }
            for (Spzstate spzstateCollectionSpzstate : user.getSpzstateCollection()) {
                User oldIssuerLoginOfSpzstateCollectionSpzstate = spzstateCollectionSpzstate.getIssuerLogin();
                spzstateCollectionSpzstate.setIssuerLogin(user);
                spzstateCollectionSpzstate = em.merge(spzstateCollectionSpzstate);
                if (oldIssuerLoginOfSpzstateCollectionSpzstate != null) {
                    oldIssuerLoginOfSpzstateCollectionSpzstate.getSpzstateCollection().remove(spzstateCollectionSpzstate);
                    oldIssuerLoginOfSpzstateCollectionSpzstate = em.merge(oldIssuerLoginOfSpzstateCollectionSpzstate);
                }
            }
            for (Spz spzCollectionSpz : user.getSpzCollection()) {
                User oldAnalystLoginOfSpzCollectionSpz = spzCollectionSpz.getAnalystLogin();
                spzCollectionSpz.setAnalystLogin(user);
                spzCollectionSpz = em.merge(spzCollectionSpz);
                if (oldAnalystLoginOfSpzCollectionSpz != null) {
                    oldAnalystLoginOfSpzCollectionSpz.getSpzCollection().remove(spzCollectionSpz);
                    oldAnalystLoginOfSpzCollectionSpz = em.merge(oldAnalystLoginOfSpzCollectionSpz);
                }
            }
            for (Spz spzCollection1Spz : user.getSpzCollection1()) {
                User oldDeveloperLoginOfSpzCollection1Spz = spzCollection1Spz.getDeveloperLogin();
                spzCollection1Spz.setDeveloperLogin(user);
                spzCollection1Spz = em.merge(spzCollection1Spz);
                if (oldDeveloperLoginOfSpzCollection1Spz != null) {
                    oldDeveloperLoginOfSpzCollection1Spz.getSpzCollection1().remove(spzCollection1Spz);
                    oldDeveloperLoginOfSpzCollection1Spz = em.merge(oldDeveloperLoginOfSpzCollection1Spz);
                }
            }
            for (Useraccess useraccessCollectionUseraccess : user.getUseraccessCollection()) {
                User oldLoginOfUseraccessCollectionUseraccess = useraccessCollectionUseraccess.getLogin();
                useraccessCollectionUseraccess.setLogin(user);
                useraccessCollectionUseraccess = em.merge(useraccessCollectionUseraccess);
                if (oldLoginOfUseraccessCollectionUseraccess != null) {
                    oldLoginOfUseraccessCollectionUseraccess.getUseraccessCollection().remove(useraccessCollectionUseraccess);
                    oldLoginOfUseraccessCollectionUseraccess = em.merge(oldLoginOfUseraccessCollectionUseraccess);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findUser(user.getLogin()) != null) {
                throw new PreexistingEntityException("User " + user + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(User user) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User persistentUser = em.find(User.class, user.getLogin());
            Collection<Spznote> spznoteCollectionOld = persistentUser.getSpznoteCollection();
            Collection<Spznote> spznoteCollectionNew = user.getSpznoteCollection();
            Collection<Spzstate> spzstateCollectionOld = persistentUser.getSpzstateCollection();
            Collection<Spzstate> spzstateCollectionNew = user.getSpzstateCollection();
            Collection<Spz> spzCollectionOld = persistentUser.getSpzCollection();
            Collection<Spz> spzCollectionNew = user.getSpzCollection();
            Collection<Spz> spzCollection1Old = persistentUser.getSpzCollection1();
            Collection<Spz> spzCollection1New = user.getSpzCollection1();
            Collection<Useraccess> useraccessCollectionOld = persistentUser.getUseraccessCollection();
            Collection<Useraccess> useraccessCollectionNew = user.getUseraccessCollection();
            List<String> illegalOrphanMessages = null;
            for (Spznote spznoteCollectionOldSpznote : spznoteCollectionOld) {
                if (!spznoteCollectionNew.contains(spznoteCollectionOldSpznote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Spznote " + spznoteCollectionOldSpznote + " since its issuerLogin field is not nullable.");
                }
            }
            for (Spzstate spzstateCollectionOldSpzstate : spzstateCollectionOld) {
                if (!spzstateCollectionNew.contains(spzstateCollectionOldSpzstate)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Spzstate " + spzstateCollectionOldSpzstate + " since its issuerLogin field is not nullable.");
                }
            }
            for (Useraccess useraccessCollectionOldUseraccess : useraccessCollectionOld) {
                if (!useraccessCollectionNew.contains(useraccessCollectionOldUseraccess)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Useraccess " + useraccessCollectionOldUseraccess + " since its login field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Spznote> attachedSpznoteCollectionNew = new ArrayList<Spznote>();
            for (Spznote spznoteCollectionNewSpznoteToAttach : spznoteCollectionNew) {
                spznoteCollectionNewSpznoteToAttach = em.getReference(spznoteCollectionNewSpznoteToAttach.getClass(), spznoteCollectionNewSpznoteToAttach.getId());
                attachedSpznoteCollectionNew.add(spznoteCollectionNewSpznoteToAttach);
            }
            spznoteCollectionNew = attachedSpznoteCollectionNew;
            user.setSpznoteCollection(spznoteCollectionNew);
            Collection<Spzstate> attachedSpzstateCollectionNew = new ArrayList<Spzstate>();
            for (Spzstate spzstateCollectionNewSpzstateToAttach : spzstateCollectionNew) {
                spzstateCollectionNewSpzstateToAttach = em.getReference(spzstateCollectionNewSpzstateToAttach.getClass(), spzstateCollectionNewSpzstateToAttach.getId());
                attachedSpzstateCollectionNew.add(spzstateCollectionNewSpzstateToAttach);
            }
            spzstateCollectionNew = attachedSpzstateCollectionNew;
            user.setSpzstateCollection(spzstateCollectionNew);
            Collection<Spz> attachedSpzCollectionNew = new ArrayList<Spz>();
            for (Spz spzCollectionNewSpzToAttach : spzCollectionNew) {
                spzCollectionNewSpzToAttach = em.getReference(spzCollectionNewSpzToAttach.getClass(), spzCollectionNewSpzToAttach.getId());
                attachedSpzCollectionNew.add(spzCollectionNewSpzToAttach);
            }
            spzCollectionNew = attachedSpzCollectionNew;
            user.setSpzCollection(spzCollectionNew);
            Collection<Spz> attachedSpzCollection1New = new ArrayList<Spz>();
            for (Spz spzCollection1NewSpzToAttach : spzCollection1New) {
                spzCollection1NewSpzToAttach = em.getReference(spzCollection1NewSpzToAttach.getClass(), spzCollection1NewSpzToAttach.getId());
                attachedSpzCollection1New.add(spzCollection1NewSpzToAttach);
            }
            spzCollection1New = attachedSpzCollection1New;
            user.setSpzCollection1(spzCollection1New);
            Collection<Useraccess> attachedUseraccessCollectionNew = new ArrayList<Useraccess>();
            for (Useraccess useraccessCollectionNewUseraccessToAttach : useraccessCollectionNew) {
                useraccessCollectionNewUseraccessToAttach = em.getReference(useraccessCollectionNewUseraccessToAttach.getClass(), useraccessCollectionNewUseraccessToAttach.getId());
                attachedUseraccessCollectionNew.add(useraccessCollectionNewUseraccessToAttach);
            }
            useraccessCollectionNew = attachedUseraccessCollectionNew;
            user.setUseraccessCollection(useraccessCollectionNew);
            user = em.merge(user);
            for (Spznote spznoteCollectionNewSpznote : spznoteCollectionNew) {
                if (!spznoteCollectionOld.contains(spznoteCollectionNewSpznote)) {
                    User oldIssuerLoginOfSpznoteCollectionNewSpznote = spznoteCollectionNewSpznote.getIssuerLogin();
                    spznoteCollectionNewSpznote.setIssuerLogin(user);
                    spznoteCollectionNewSpznote = em.merge(spznoteCollectionNewSpznote);
                    if (oldIssuerLoginOfSpznoteCollectionNewSpznote != null && !oldIssuerLoginOfSpznoteCollectionNewSpznote.equals(user)) {
                        oldIssuerLoginOfSpznoteCollectionNewSpznote.getSpznoteCollection().remove(spznoteCollectionNewSpznote);
                        oldIssuerLoginOfSpznoteCollectionNewSpznote = em.merge(oldIssuerLoginOfSpznoteCollectionNewSpznote);
                    }
                }
            }
            for (Spzstate spzstateCollectionNewSpzstate : spzstateCollectionNew) {
                if (!spzstateCollectionOld.contains(spzstateCollectionNewSpzstate)) {
                    User oldIssuerLoginOfSpzstateCollectionNewSpzstate = spzstateCollectionNewSpzstate.getIssuerLogin();
                    spzstateCollectionNewSpzstate.setIssuerLogin(user);
                    spzstateCollectionNewSpzstate = em.merge(spzstateCollectionNewSpzstate);
                    if (oldIssuerLoginOfSpzstateCollectionNewSpzstate != null && !oldIssuerLoginOfSpzstateCollectionNewSpzstate.equals(user)) {
                        oldIssuerLoginOfSpzstateCollectionNewSpzstate.getSpzstateCollection().remove(spzstateCollectionNewSpzstate);
                        oldIssuerLoginOfSpzstateCollectionNewSpzstate = em.merge(oldIssuerLoginOfSpzstateCollectionNewSpzstate);
                    }
                }
            }
            for (Spz spzCollectionOldSpz : spzCollectionOld) {
                if (!spzCollectionNew.contains(spzCollectionOldSpz)) {
                    spzCollectionOldSpz.setAnalystLogin(null);
                    spzCollectionOldSpz = em.merge(spzCollectionOldSpz);
                }
            }
            for (Spz spzCollectionNewSpz : spzCollectionNew) {
                if (!spzCollectionOld.contains(spzCollectionNewSpz)) {
                    User oldAnalystLoginOfSpzCollectionNewSpz = spzCollectionNewSpz.getAnalystLogin();
                    spzCollectionNewSpz.setAnalystLogin(user);
                    spzCollectionNewSpz = em.merge(spzCollectionNewSpz);
                    if (oldAnalystLoginOfSpzCollectionNewSpz != null && !oldAnalystLoginOfSpzCollectionNewSpz.equals(user)) {
                        oldAnalystLoginOfSpzCollectionNewSpz.getSpzCollection().remove(spzCollectionNewSpz);
                        oldAnalystLoginOfSpzCollectionNewSpz = em.merge(oldAnalystLoginOfSpzCollectionNewSpz);
                    }
                }
            }
            for (Spz spzCollection1OldSpz : spzCollection1Old) {
                if (!spzCollection1New.contains(spzCollection1OldSpz)) {
                    spzCollection1OldSpz.setDeveloperLogin(null);
                    spzCollection1OldSpz = em.merge(spzCollection1OldSpz);
                }
            }
            for (Spz spzCollection1NewSpz : spzCollection1New) {
                if (!spzCollection1Old.contains(spzCollection1NewSpz)) {
                    User oldDeveloperLoginOfSpzCollection1NewSpz = spzCollection1NewSpz.getDeveloperLogin();
                    spzCollection1NewSpz.setDeveloperLogin(user);
                    spzCollection1NewSpz = em.merge(spzCollection1NewSpz);
                    if (oldDeveloperLoginOfSpzCollection1NewSpz != null && !oldDeveloperLoginOfSpzCollection1NewSpz.equals(user)) {
                        oldDeveloperLoginOfSpzCollection1NewSpz.getSpzCollection1().remove(spzCollection1NewSpz);
                        oldDeveloperLoginOfSpzCollection1NewSpz = em.merge(oldDeveloperLoginOfSpzCollection1NewSpz);
                    }
                }
            }
            for (Useraccess useraccessCollectionNewUseraccess : useraccessCollectionNew) {
                if (!useraccessCollectionOld.contains(useraccessCollectionNewUseraccess)) {
                    User oldLoginOfUseraccessCollectionNewUseraccess = useraccessCollectionNewUseraccess.getLogin();
                    useraccessCollectionNewUseraccess.setLogin(user);
                    useraccessCollectionNewUseraccess = em.merge(useraccessCollectionNewUseraccess);
                    if (oldLoginOfUseraccessCollectionNewUseraccess != null && !oldLoginOfUseraccessCollectionNewUseraccess.equals(user)) {
                        oldLoginOfUseraccessCollectionNewUseraccess.getUseraccessCollection().remove(useraccessCollectionNewUseraccess);
                        oldLoginOfUseraccessCollectionNewUseraccess = em.merge(oldLoginOfUseraccessCollectionNewUseraccess);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                String id = user.getLogin();
                if (findUser(id) == null) {
                    throw new NonexistentEntityException("The user with id " + id + " no longer exists.");
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
            User user;
            try {
                user = em.getReference(User.class, id);
                user.getLogin();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The user with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Spznote> spznoteCollectionOrphanCheck = user.getSpznoteCollection();
            for (Spznote spznoteCollectionOrphanCheckSpznote : spznoteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Spznote " + spznoteCollectionOrphanCheckSpznote + " in its spznoteCollection field has a non-nullable issuerLogin field.");
            }
            Collection<Spzstate> spzstateCollectionOrphanCheck = user.getSpzstateCollection();
            for (Spzstate spzstateCollectionOrphanCheckSpzstate : spzstateCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Spzstate " + spzstateCollectionOrphanCheckSpzstate + " in its spzstateCollection field has a non-nullable issuerLogin field.");
            }
            Collection<Useraccess> useraccessCollectionOrphanCheck = user.getUseraccessCollection();
            for (Useraccess useraccessCollectionOrphanCheckUseraccess : useraccessCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This User (" + user + ") cannot be destroyed since the Useraccess " + useraccessCollectionOrphanCheckUseraccess + " in its useraccessCollection field has a non-nullable login field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Spz> spzCollection = user.getSpzCollection();
            for (Spz spzCollectionSpz : spzCollection) {
                spzCollectionSpz.setAnalystLogin(null);
                spzCollectionSpz = em.merge(spzCollectionSpz);
            }
            Collection<Spz> spzCollection1 = user.getSpzCollection1();
            for (Spz spzCollection1Spz : spzCollection1) {
                spzCollection1Spz.setDeveloperLogin(null);
                spzCollection1Spz = em.merge(spzCollection1Spz);
            }
            em.remove(user);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<User> findUserEntities() {
        return findUserEntities(true, -1, -1);
    }

    @Override
    public List<User> findUserEntities(int maxResults, int firstResult) {
        return findUserEntities(false, maxResults, firstResult);
    }

    private List<User> findUserEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(User.class));
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
    public User findUser(String id) {
        if(id==null){
            throw new NullPointerException("Parameter id is null");
        }
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getUserCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<User> rt = cq.from(User.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
