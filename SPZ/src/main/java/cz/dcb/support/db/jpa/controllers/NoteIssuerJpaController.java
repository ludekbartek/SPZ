/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Noteissuer;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author bar
 */
public class NoteIssuerJpaController implements Serializable, NoteIssuerManager {

    public NoteIssuerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Noteissuer noteissuer) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(noteissuer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Noteissuer noteissuer) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            noteissuer = em.merge(noteissuer);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = noteissuer.getId();
                if (findNoteissuer(id) == null) {
                    throw new NonexistentEntityException("The noteissuer with id " + id + " no longer exists.");
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
            Noteissuer noteissuer;
            try {
                noteissuer = em.getReference(Noteissuer.class, id);
                noteissuer.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The noteissuer with id " + id + " no longer exists.", enfe);
            }
            em.remove(noteissuer);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public List<Noteissuer> findNoteissuerEntities() {
        return findNoteissuerEntities(true, -1, -1);
    }

    @Override
    public List<Noteissuer> findNoteissuerEntities(int maxResults, int firstResult) {
        return findNoteissuerEntities(false, maxResults, firstResult);
    }

    private List<Noteissuer> findNoteissuerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Noteissuer.class));
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
    public Noteissuer findNoteissuer(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Noteissuer.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public int getNoteissuerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Noteissuer> rt = cq.from(Noteissuer.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
