/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.JPAController;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import model.JPAController.exceptions.NonexistentEntityException;
import model.JPAController.exceptions.PreexistingEntityException;
import model.TalonarioComprobante;

/**
 *
 * @author Ariel
 */
public class TalonarioComprobanteJpaController implements Serializable {

    public TalonarioComprobanteJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(TalonarioComprobante talonarioComprobante) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(talonarioComprobante);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTalonarioComprobante(talonarioComprobante.getId_Unidad()) != null) {
                throw new PreexistingEntityException("TalonarioComprobante " + talonarioComprobante + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(TalonarioComprobante talonarioComprobante) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            talonarioComprobante = em.merge(talonarioComprobante);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = talonarioComprobante.getId_Unidad();
                if (findTalonarioComprobante(id) == null) {
                    throw new NonexistentEntityException("The talonarioComprobante with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TalonarioComprobante talonarioComprobante;
            try {
                talonarioComprobante = em.getReference(TalonarioComprobante.class, id);
                talonarioComprobante.getId_Unidad();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The talonarioComprobante with id " + id + " no longer exists.", enfe);
            }
            em.remove(talonarioComprobante);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<TalonarioComprobante> findTalonarioComprobanteEntities() {
        return findTalonarioComprobanteEntities(true, -1, -1);
    }

    public List<TalonarioComprobante> findTalonarioComprobanteEntities(int maxResults, int firstResult) {
        return findTalonarioComprobanteEntities(false, maxResults, firstResult);
    }

    private List<TalonarioComprobante> findTalonarioComprobanteEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(TalonarioComprobante.class));
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

    public TalonarioComprobante findTalonarioComprobante(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(TalonarioComprobante.class, id);
        } finally {
            em.close();
        }
    }

    public int getTalonarioComprobanteCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<TalonarioComprobante> rt = cq.from(TalonarioComprobante.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
