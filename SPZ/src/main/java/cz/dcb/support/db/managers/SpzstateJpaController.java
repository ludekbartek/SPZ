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
import cz.dcb.support.db.jpa.Spz;
import cz.dcb.support.db.jpa.Spznote;
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
public class SpzstateJpaController implements Serializable, SpzstateManager {

    public SpzstateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Spzstate spzstate) throws PreexistingEntityException, Exception {
        if (spzstate.getSpznoteCollection() == null) {
            spzstate.setSpznoteCollection(new ArrayList<Spznote>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User issuerLogin = spzstate.getIssuerLogin();
            if (issuerLogin != null) {
                issuerLogin = em.getReference(issuerLogin.getClass(), issuerLogin.getLogin());
                spzstate.setIssuerLogin(issuerLogin);
            }
            Spz spzId = spzstate.getSpzId();
            if (spzId != null) {
                spzId = em.getReference(spzId.getClass(), spzId.getId());
                spzstate.setSpzId(spzId);
            }
            Collection<Spznote> attachedSpznoteCollection = new ArrayList<Spznote>();
            for (Spznote spznoteCollectionSpznoteToAttach : spzstate.getSpznoteCollection()) {
                spznoteCollectionSpznoteToAttach = em.getReference(spznoteCollectionSpznoteToAttach.getClass(), spznoteCollectionSpznoteToAttach.getId());
                attachedSpznoteCollection.add(spznoteCollectionSpznoteToAttach);
            }
            spzstate.setSpznoteCollection(attachedSpznoteCollection);
            em.persist(spzstate);
            if (issuerLogin != null) {
                issuerLogin.getSpzstateCollection().add(spzstate);
                issuerLogin = em.merge(issuerLogin);
            }
            if (spzId != null) {
                spzId.getSpzstateCollection().add(spzstate);
                spzId = em.merge(spzId);
            }
            for (Spznote spznoteCollectionSpznote : spzstate.getSpznoteCollection()) {
                Spzstate oldStateIdOfSpznoteCollectionSpznote = spznoteCollectionSpznote.getStateId();
                spznoteCollectionSpznote.setStateId(spzstate);
                spznoteCollectionSpznote = em.merge(spznoteCollectionSpznote);
                if (oldStateIdOfSpznoteCollectionSpznote != null) {
                    oldStateIdOfSpznoteCollectionSpznote.getSpznoteCollection().remove(spznoteCollectionSpznote);
                    oldStateIdOfSpznoteCollectionSpznote = em.merge(oldStateIdOfSpznoteCollectionSpznote);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSpzstate(spzstate.getId()) != null) {
                throw new PreexistingEntityException("Spzstate " + spzstate + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Spzstate spzstate) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Spzstate persistentSpzstate = em.find(Spzstate.class, spzstate.getId());
            User issuerLoginOld = persistentSpzstate.getIssuerLogin();
            User issuerLoginNew = spzstate.getIssuerLogin();
            Spz spzIdOld = persistentSpzstate.getSpzId();
            Spz spzIdNew = spzstate.getSpzId();
            Collection<Spznote> spznoteCollectionOld = persistentSpzstate.getSpznoteCollection();
            Collection<Spznote> spznoteCollectionNew = spzstate.getSpznoteCollection();
            List<String> illegalOrphanMessages = null;
            for (Spznote spznoteCollectionOldSpznote : spznoteCollectionOld) {
                if (!spznoteCollectionNew.contains(spznoteCollectionOldSpznote)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Spznote " + spznoteCollectionOldSpznote + " since its stateId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (issuerLoginNew != null) {
                issuerLoginNew = em.getReference(issuerLoginNew.getClass(), issuerLoginNew.getLogin());
                spzstate.setIssuerLogin(issuerLoginNew);
            }
            if (spzIdNew != null) {
                spzIdNew = em.getReference(spzIdNew.getClass(), spzIdNew.getId());
                spzstate.setSpzId(spzIdNew);
            }
            Collection<Spznote> attachedSpznoteCollectionNew = new ArrayList<Spznote>();
            for (Spznote spznoteCollectionNewSpznoteToAttach : spznoteCollectionNew) {
                spznoteCollectionNewSpznoteToAttach = em.getReference(spznoteCollectionNewSpznoteToAttach.getClass(), spznoteCollectionNewSpznoteToAttach.getId());
                attachedSpznoteCollectionNew.add(spznoteCollectionNewSpznoteToAttach);
            }
            spznoteCollectionNew = attachedSpznoteCollectionNew;
            spzstate.setSpznoteCollection(spznoteCollectionNew);
            spzstate = em.merge(spzstate);
            if (issuerLoginOld != null && !issuerLoginOld.equals(issuerLoginNew)) {
                issuerLoginOld.getSpzstateCollection().remove(spzstate);
                issuerLoginOld = em.merge(issuerLoginOld);
            }
            if (issuerLoginNew != null && !issuerLoginNew.equals(issuerLoginOld)) {
                issuerLoginNew.getSpzstateCollection().add(spzstate);
                issuerLoginNew = em.merge(issuerLoginNew);
            }
            if (spzIdOld != null && !spzIdOld.equals(spzIdNew)) {
                spzIdOld.getSpzstateCollection().remove(spzstate);
                spzIdOld = em.merge(spzIdOld);
            }
            if (spzIdNew != null && !spzIdNew.equals(spzIdOld)) {
                spzIdNew.getSpzstateCollection().add(spzstate);
                spzIdNew = em.merge(spzIdNew);
            }
            for (Spznote spznoteCollectionNewSpznote : spznoteCollectionNew) {
                if (!spznoteCollectionOld.contains(spznoteCollectionNewSpznote)) {
                    Spzstate oldStateIdOfSpznoteCollectionNewSpznote = spznoteCollectionNewSpznote.getStateId();
                    spznoteCollectionNewSpznote.setStateId(spzstate);
                    spznoteCollectionNewSpznote = em.merge(spznoteCollectionNewSpznote);
                    if (oldStateIdOfSpznoteCollectionNewSpznote != null && !oldStateIdOfSpznoteCollectionNewSpznote.equals(spzstate)) {
                        oldStateIdOfSpznoteCollectionNewSpznote.getSpznoteCollection().remove(spznoteCollectionNewSpznote);
                        oldStateIdOfSpznoteCollectionNewSpznote = em.merge(oldStateIdOfSpznoteCollectionNewSpznote);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spzstate.getId();
                if (findSpzstate(id) == null) {
                    throw new NonexistentEntityException("The spzstate with id " + id + " no longer exists.");
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
            Spzstate spzstate;
            try {
                spzstate = em.getReference(Spzstate.class, id);
                spzstate.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spzstate with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Spznote> spznoteCollectionOrphanCheck = spzstate.getSpznoteCollection();
            for (Spznote spznoteCollectionOrphanCheckSpznote : spznoteCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Spzstate (" + spzstate + ") cannot be destroyed since the Spznote " + spznoteCollectionOrphanCheckSpznote + " in its spznoteCollection field has a non-nullable stateId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User issuerLogin = spzstate.getIssuerLogin();
            if (issuerLogin != null) {
                issuerLogin.getSpzstateCollection().remove(spzstate);
                issuerLogin = em.merge(issuerLogin);
            }
            Spz spzId = spzstate.getSpzId();
            if (spzId != null) {
                spzId.getSpzstateCollection().remove(spzstate);
                spzId = em.merge(spzId);
            }
            em.remove(spzstate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Spzstate> findSpzstateEntities() {
        return findSpzstateEntities(true, -1, -1);
    }

    @Override
    public List<Spzstate> findSpzstateEntities(int maxResults, int firstResult) {
        return findSpzstateEntities(false, maxResults, firstResult);
    }

    private List<Spzstate> findSpzstateEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spzstate.class));
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
    public Spzstate findSpzstate(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spzstate.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getSpzstateCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spzstate> rt = cq.from(Spzstate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
