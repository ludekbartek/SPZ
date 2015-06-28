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
import cz.dcb.support.db.jpa.Spzstate;
import cz.dcb.support.db.jpa.Attachment;
import cz.dcb.support.db.jpa.Spznote;
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
public class SpznoteJpaController implements Serializable, SpznoteManager {

    public SpznoteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Spznote spznote) throws PreexistingEntityException, Exception {
        if (spznote.getAttachmentCollection() == null) {
            spznote.setAttachmentCollection(new ArrayList<Attachment>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            User issuerLogin = spznote.getIssuerLogin();
            if (issuerLogin != null) {
                issuerLogin = em.getReference(issuerLogin.getClass(), issuerLogin.getLogin());
                spznote.setIssuerLogin(issuerLogin);
            }
            Spzstate stateId = spznote.getStateId();
            if (stateId != null) {
                stateId = em.getReference(stateId.getClass(), stateId.getId());
                spznote.setStateId(stateId);
            }
            Collection<Attachment> attachedAttachmentCollection = new ArrayList<Attachment>();
            for (Attachment attachmentCollectionAttachmentToAttach : spznote.getAttachmentCollection()) {
                attachmentCollectionAttachmentToAttach = em.getReference(attachmentCollectionAttachmentToAttach.getClass(), attachmentCollectionAttachmentToAttach.getId());
                attachedAttachmentCollection.add(attachmentCollectionAttachmentToAttach);
            }
            spznote.setAttachmentCollection(attachedAttachmentCollection);
            em.persist(spznote);
            if (issuerLogin != null) {
                issuerLogin.getSpznoteCollection().add(spznote);
                issuerLogin = em.merge(issuerLogin);
            }
            if (stateId != null) {
                stateId.getSpznoteCollection().add(spznote);
                stateId = em.merge(stateId);
            }
            for (Attachment attachmentCollectionAttachment : spznote.getAttachmentCollection()) {
                Spznote oldSpznoteIdOfAttachmentCollectionAttachment = attachmentCollectionAttachment.getSpznoteId();
                attachmentCollectionAttachment.setSpznoteId(spznote);
                attachmentCollectionAttachment = em.merge(attachmentCollectionAttachment);
                if (oldSpznoteIdOfAttachmentCollectionAttachment != null) {
                    oldSpznoteIdOfAttachmentCollectionAttachment.getAttachmentCollection().remove(attachmentCollectionAttachment);
                    oldSpznoteIdOfAttachmentCollectionAttachment = em.merge(oldSpznoteIdOfAttachmentCollectionAttachment);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSpznote(spznote.getId()) != null) {
                throw new PreexistingEntityException("Spznote " + spznote + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Spznote spznote) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Spznote persistentSpznote = em.find(Spznote.class, spznote.getId());
            User issuerLoginOld = persistentSpznote.getIssuerLogin();
            User issuerLoginNew = spznote.getIssuerLogin();
            Spzstate stateIdOld = persistentSpznote.getStateId();
            Spzstate stateIdNew = spznote.getStateId();
            Collection<Attachment> attachmentCollectionOld = persistentSpznote.getAttachmentCollection();
            Collection<Attachment> attachmentCollectionNew = spznote.getAttachmentCollection();
            List<String> illegalOrphanMessages = null;
            for (Attachment attachmentCollectionOldAttachment : attachmentCollectionOld) {
                if (!attachmentCollectionNew.contains(attachmentCollectionOldAttachment)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Attachment " + attachmentCollectionOldAttachment + " since its spznoteId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (issuerLoginNew != null) {
                issuerLoginNew = em.getReference(issuerLoginNew.getClass(), issuerLoginNew.getLogin());
                spznote.setIssuerLogin(issuerLoginNew);
            }
            if (stateIdNew != null) {
                stateIdNew = em.getReference(stateIdNew.getClass(), stateIdNew.getId());
                spznote.setStateId(stateIdNew);
            }
            Collection<Attachment> attachedAttachmentCollectionNew = new ArrayList<Attachment>();
            for (Attachment attachmentCollectionNewAttachmentToAttach : attachmentCollectionNew) {
                attachmentCollectionNewAttachmentToAttach = em.getReference(attachmentCollectionNewAttachmentToAttach.getClass(), attachmentCollectionNewAttachmentToAttach.getId());
                attachedAttachmentCollectionNew.add(attachmentCollectionNewAttachmentToAttach);
            }
            attachmentCollectionNew = attachedAttachmentCollectionNew;
            spznote.setAttachmentCollection(attachmentCollectionNew);
            spznote = em.merge(spznote);
            if (issuerLoginOld != null && !issuerLoginOld.equals(issuerLoginNew)) {
                issuerLoginOld.getSpznoteCollection().remove(spznote);
                issuerLoginOld = em.merge(issuerLoginOld);
            }
            if (issuerLoginNew != null && !issuerLoginNew.equals(issuerLoginOld)) {
                issuerLoginNew.getSpznoteCollection().add(spznote);
                issuerLoginNew = em.merge(issuerLoginNew);
            }
            if (stateIdOld != null && !stateIdOld.equals(stateIdNew)) {
                stateIdOld.getSpznoteCollection().remove(spznote);
                stateIdOld = em.merge(stateIdOld);
            }
            if (stateIdNew != null && !stateIdNew.equals(stateIdOld)) {
                stateIdNew.getSpznoteCollection().add(spznote);
                stateIdNew = em.merge(stateIdNew);
            }
            for (Attachment attachmentCollectionNewAttachment : attachmentCollectionNew) {
                if (!attachmentCollectionOld.contains(attachmentCollectionNewAttachment)) {
                    Spznote oldSpznoteIdOfAttachmentCollectionNewAttachment = attachmentCollectionNewAttachment.getSpznoteId();
                    attachmentCollectionNewAttachment.setSpznoteId(spznote);
                    attachmentCollectionNewAttachment = em.merge(attachmentCollectionNewAttachment);
                    if (oldSpznoteIdOfAttachmentCollectionNewAttachment != null && !oldSpznoteIdOfAttachmentCollectionNewAttachment.equals(spznote)) {
                        oldSpznoteIdOfAttachmentCollectionNewAttachment.getAttachmentCollection().remove(attachmentCollectionNewAttachment);
                        oldSpznoteIdOfAttachmentCollectionNewAttachment = em.merge(oldSpznoteIdOfAttachmentCollectionNewAttachment);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = spznote.getId();
                if (findSpznote(id) == null) {
                    throw new NonexistentEntityException("The spznote with id " + id + " no longer exists.");
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
            Spznote spznote;
            try {
                spznote = em.getReference(Spznote.class, id);
                spznote.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The spznote with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Attachment> attachmentCollectionOrphanCheck = spznote.getAttachmentCollection();
            for (Attachment attachmentCollectionOrphanCheckAttachment : attachmentCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Spznote (" + spznote + ") cannot be destroyed since the Attachment " + attachmentCollectionOrphanCheckAttachment + " in its attachmentCollection field has a non-nullable spznoteId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            User issuerLogin = spznote.getIssuerLogin();
            if (issuerLogin != null) {
                issuerLogin.getSpznoteCollection().remove(spznote);
                issuerLogin = em.merge(issuerLogin);
            }
            Spzstate stateId = spznote.getStateId();
            if (stateId != null) {
                stateId.getSpznoteCollection().remove(spznote);
                stateId = em.merge(stateId);
            }
            em.remove(spznote);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Spznote> findSpznoteEntities() {
        return findSpznoteEntities(true, -1, -1);
    }

    @Override
    public List<Spznote> findSpznoteEntities(int maxResults, int firstResult) {
        return findSpznoteEntities(false, maxResults, firstResult);
    }

    private List<Spznote> findSpznoteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Spznote.class));
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
    public Spznote findSpznote(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Spznote.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getSpznoteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Spznote> rt = cq.from(Spznote.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
