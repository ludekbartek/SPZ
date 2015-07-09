/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.managers;

import cz.dcb.support.db.jpa.Attachment;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import cz.dcb.support.db.jpa.Spznote;
import cz.dcb.support.db.managers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.managers.exceptions.PreexistingEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author bar
 */
public class AttachmentJpaController implements Serializable, AttachmentManager {

    public AttachmentJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Attachment attachment) throws PreexistingEntityException, Exception {
        if(attachment==null){
            throw new NullPointerException("parameter is null");
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Spznote spznoteId = attachment.getSpznoteId();
            if (spznoteId != null) {
                spznoteId = em.getReference(spznoteId.getClass(), spznoteId.getId());
                attachment.setSpznoteId(spznoteId);
            }
            em.persist(attachment);
            if (spznoteId != null) {
                spznoteId.getAttachmentCollection().add(attachment);
                spznoteId = em.merge(spznoteId);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAttachment(attachment.getId()) != null) {
                throw new PreexistingEntityException("Attachment " + attachment + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Attachment attachment) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Attachment persistentAttachment = em.find(Attachment.class, attachment.getId());
            Spznote spznoteIdOld = persistentAttachment.getSpznoteId();
            Spznote spznoteIdNew = attachment.getSpznoteId();
            if (spznoteIdNew != null) {
                spznoteIdNew = em.getReference(spznoteIdNew.getClass(), spznoteIdNew.getId());
                attachment.setSpznoteId(spznoteIdNew);
            }
            attachment = em.merge(attachment);
            if (spznoteIdOld != null && !spznoteIdOld.equals(spznoteIdNew)) {
                spznoteIdOld.getAttachmentCollection().remove(attachment);
                spznoteIdOld = em.merge(spznoteIdOld);
            }
            if (spznoteIdNew != null && !spznoteIdNew.equals(spznoteIdOld)) {
                spznoteIdNew.getAttachmentCollection().add(attachment);
                spznoteIdNew = em.merge(spznoteIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = attachment.getId();
                if (findAttachment(id) == null) {
                    throw new NonexistentEntityException("The attachment with id " + id + " no longer exists.");
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
            Attachment attachment;
            try {
                attachment = em.getReference(Attachment.class, id);
                attachment.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The attachment with id " + id + " no longer exists.", enfe);
            }
            Spznote spznoteId = attachment.getSpznoteId();
            if (spznoteId != null) {
                spznoteId.getAttachmentCollection().remove(attachment);
                spznoteId = em.merge(spznoteId);
            }
            em.remove(attachment);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Attachment> findAttachmentEntities() {
        return findAttachmentEntities(true, -1, -1);
    }

    @Override
    public List<Attachment> findAttachmentEntities(int maxResults, int firstResult) {
        return findAttachmentEntities(false, maxResults, firstResult);
    }

    private List<Attachment> findAttachmentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Attachment.class));
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
    public Attachment findAttachment(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Attachment.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getAttachmentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Attachment> rt = cq.from(Attachment.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
