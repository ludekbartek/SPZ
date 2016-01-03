/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dcb.support.db.jpa.controllers;

import cz.dcb.support.db.jpa.controllers.exceptions.NonexistentEntityException;
import cz.dcb.support.db.jpa.entities.Spz;
import cz.dcb.support.db.jpa.entities.Spznote;
import cz.dcb.support.db.jpa.entities.Spzstate;
import cz.dcb.support.db.jpa.entities.Spzstatenote;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;


@SuppressWarnings ("unchecked")
/**
 *
 * @author bar
 */
public class SpzStateJpaController implements Serializable, SpzStateManager {

    public SpzStateJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    @Override
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public void create(Spzstate spzstate) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(spzstate);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    @Override
    public void edit(Spzstate spzstate) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            spzstate = em.merge(spzstate);
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
    public void destroy(Integer id) throws NonexistentEntityException {
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
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
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
            CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
            Root<Spzstate> rt = cq.from(Spzstate.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Date getLastChange(Integer id) {
        EntityManager em = getEntityManager();
        Date date = null;
        try{
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Query query= em.createQuery("select max(state.idate) from Spzstate state where state.id=:stateid");
            query.setParameter("stateid", id);
            date = (Date)query.getSingleResult();
        }finally{
            em.close();
        }
        return date;
    }

    @Override
    public Spzstate getCurrentState(Spz spz) {
        EntityManager em = getEntityManager();
        Spzstate state = null;
        try{
            em.getTransaction().begin();
            Query query=em.createQuery("select spzst from Spzstate spzst where spzst.currentstate=1 and spzst.id in (select spzsts.stateid from Spzstates spzsts where spzsts.spzid = :spzid)");
            query.setParameter("spzid", spz.getId());
            state = (Spzstate)query.getSingleResult();
            em.getTransaction().commit();
        }catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE,"Error selecting current state: ",ex);
            em.getTransaction().rollback();
        }
        finally{
            em.close();
        }
        return state;
    }

    @Override
    public void create(Spzstate spzstate, EntityManager em) {
        em.persist(spzstate);
    }

    @Override
    public List<Spznote> getStateNotes(Integer spzStateId) {
        EntityManager em = getEntityManager();
        List<Spznote> notes = null;
        try{ 
            em.getTransaction().begin();
            Query query = em.createQuery("select note from Spznote note  where note.id in (Select statenote.noteid from Spzstatenote statenote where statenote.stateid=:stateid) ORDER BY note.notedate DESC");
            query.setParameter("stateid", spzStateId);
            notes = query.getResultList();
        }catch(Exception ex){
            Logger.getAnonymousLogger().log(Level.SEVERE,String.format("Error retrieving spznotes for state with id %d:",spzStateId),ex);
            em.getTransaction().rollback();
        }
        return notes;
    }

    

}
